package com.tteokguk.tteokguk.member.domain;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;
import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.item.domain.Item;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.tteokguk.tteokguk.item.exception.ItemError.INSUFFICIENT_INGREDIENTS;
import static com.tteokguk.tteokguk.item.exception.ItemError.UNKNOWN_INGREDIENT;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_member")
@NoArgsConstructor(access = PROTECTED)
@Inheritance
@Where(clause = "deleted = false")
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(
            mappedBy = "member",
            cascade = PERSIST,
            orphanRemoval = true)
    private final List<Tteokguk> tteokguks = new ArrayList<>();

    @Enumerated(STRING)
    @Column(name = "primary_ingredient")
    private Ingredient primaryIngredient;

    @Column(name = "nickname")
    private String nickname;

    @OneToMany(
            mappedBy = "member",
            cascade = PERSIST,
            orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @Enumerated(STRING)
    @Column(name = "role")
    private RoleType role;

    @Column(name = "accepts_marketing")
    private Boolean acceptsMarketing;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private boolean deleted;

    protected Member(
            Ingredient primaryIngredient,
            String nickname,
            List<Item> items,
            Boolean acceptsMarketing
    ) {
        this(primaryIngredient, nickname, items, RoleType.ROLE_USER, acceptsMarketing);
    }

    protected Member(
            Ingredient primaryIngredient,
            String nickname,
            List<Item> items,
            RoleType role,
            Boolean acceptsMarketing
    ) {
        this.primaryIngredient = primaryIngredient;
        this.nickname = nickname;
        this.items = items;
        this.role = role;
        this.acceptsMarketing = acceptsMarketing;
    }

    // Initialize Item
    protected void initializeItem(Ingredient primaryIngredient) {
        final int INF = 1_000_000_000;

        List<Item> items = Arrays.stream(Ingredient.values())
                .filter(this::isNotPrimaryIngredient)
                .map(ingredient -> Item.of(ingredient, 0, this))
                .toList();

        Item primaryIngredientItem = Item.of(primaryIngredient, INF, this);

        this.items.addAll(items);
        this.items.add(primaryIngredientItem);
    }

    private boolean isNotPrimaryIngredient(Object ingredient) {
        return !primaryIngredient.equals(ingredient);
    }

    public void validateHasSufficientIngredients(List<Ingredient> requiredIngredients) {
        if (!hasSufficientIngredients(requiredIngredients)) {
            throw BusinessException.of(INSUFFICIENT_INGREDIENTS);
        }
    }

    private boolean hasSufficientIngredients(List<Ingredient> requiredIngredients) {
        return requiredIngredients.stream()
                .flatMap(ingredient -> items.stream()
                        .filter(item -> item.getIngredient().equals(ingredient))
                        .map(Item::getStockQuantity))
                .allMatch(quantity -> quantity >= 1);
    }

    public synchronized void useIngredients(List<Ingredient> ingredients) {
        items.stream()
                .filter(item -> ingredients.contains(item.getIngredient()))
                .forEach(Item::minus);
    }

    public synchronized void sendIngredient(
            Member receiver,
            Ingredient ingredient
    ) {
        Item senderItem = findItemByIngredient(this, ingredient);
        Item receiverItem = findItemByIngredient(receiver, ingredient);

        senderItem.minus();
        receiverItem.plus();
    }

    public synchronized void giftToSender(
            Ingredient ingredient,
            int amount
    ) {
        Item item = findItemByIngredient(this, ingredient);
        item.addStockQuantity(amount);
    }

    private Item findItemByIngredient(Member member, Ingredient ingredient) {
        return member.getItems().stream()
                .filter(item -> item.getIngredient().equals(ingredient))
                .findFirst()
                .orElseThrow(() -> BusinessException.of(UNKNOWN_INGREDIENT));
    }

    public void addTteokguk(Tteokguk tteokguk) {
        this.tteokguks.add(tteokguk);
    }

    public void initialize(String nickname, Boolean acceptsMarketing) {
        this.nickname = nickname;
        this.acceptsMarketing = acceptsMarketing;
        this.role = RoleType.ROLE_USER;
    }

    public boolean isInitialized() {
        return this.role != RoleType.ROLE_TEMP_USER;
    }

    public void delete() {
        this.nickname = this.nickname + "::deleted::" + UUID.randomUUID().toString().substring(0, 8);
        this.deleted = true;
    }

}
