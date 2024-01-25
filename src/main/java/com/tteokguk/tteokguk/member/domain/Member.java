package com.tteokguk.tteokguk.member.domain;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;
import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.item.domain.Item;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tteokguk.tteokguk.item.exception.ItemError.INSUFFICIENT_INGREDIENTS;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_member")
@NoArgsConstructor(access = PROTECTED)
@Inheritance
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

    protected Member(
            Ingredient primaryIngredient,
            String nickname,
            List<Item> items
    ) {
        this(primaryIngredient, nickname, items, RoleType.ROLE_USER);
    }

    protected Member(
            Ingredient primaryIngredient,
            String nickname,
            List<Item> items,
            RoleType role
    ) {
        this.primaryIngredient = primaryIngredient;
        this.nickname = nickname;
        this.items = items;
        this.role = role;
    }

    // Initialize Item
    protected void initializeItem(Ingredient primaryIngredient) {
        final int INF = 1_000_000_000;
        final int INITIALIZE_AMOUNT = 0;

        List<Item> items = Arrays.stream(Ingredient.values())
                .filter(this::isNotPrimaryIngredient)
                .map(ingredient -> Item.of(ingredient, INITIALIZE_AMOUNT, this))
                .toList();

        Item primaryIngredientItem = Item.of(primaryIngredient, INF, this);

        this.items.addAll(items);
        this.items.add(primaryIngredientItem);
    }

    private boolean isNotPrimaryIngredient(Object ingredient) {
        return !primaryIngredient.equals(ingredient);
    }

    public void validateSufficientIngredients(List<Ingredient> requiredIngredients) {
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

    public void useIngredients(List<Ingredient> ingredients) {
        items.stream()
                .filter(item -> ingredients.contains(item.getIngredient()))
                .forEach(Item::use);
    }

    public void addTteokguk(Tteokguk tteokguk) {
        this.tteokguks.add(tteokguk);
    }
}
