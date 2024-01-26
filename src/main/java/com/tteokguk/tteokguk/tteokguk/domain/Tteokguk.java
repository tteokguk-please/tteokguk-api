package com.tteokguk.tteokguk.tteokguk.domain;

import com.tteokguk.tteokguk.global.auditing.BaseEntity;
import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.tteokguk.tteokguk.item.exception.ItemError.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SQLRestriction("deleted = false")
@Table(name = "t_tteokguk")
@NoArgsConstructor(access = PROTECTED)
public class Tteokguk extends BaseEntity {

    @Id
    @Column(name = "tteokguk_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "wish", columnDefinition = "varchar(120)")
    private String wish;

    @Column(name = "deleted")
    @ColumnDefault("false")
    private boolean deleted;

    @Column(name = "access")
    @ColumnDefault("true")
    private boolean access;

    @Column(name = "completion")
    @ColumnDefault("true")
    private boolean completion;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Column(name = "ingredient")
    @Enumerated(STRING)
    @ElementCollection(targetClass = Ingredient.class)
    @CollectionTable(
            name = "t_tteokguk_ingredients",
            joinColumns = @JoinColumn(name = "tteokguk_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column(name = "used_ingredient")
    @Enumerated(STRING)
    @ElementCollection(targetClass = Ingredient.class)
    @CollectionTable(
            name = "t_tteokguk_used_ingredients",
            joinColumns = @JoinColumn(name = "tteokguk_id")
    )
    private List<Ingredient> usedIngredients = new ArrayList<>();

    @Builder(access = PROTECTED)
    private Tteokguk(
            String wish,
            List<Ingredient> ingredients,
            Member member,
            boolean access
    ) {
        validateIngredientConstraint(ingredients);

        this.wish = wish;
        this.ingredients = ingredients;
        this.member = member;
        this.access = access;
        this.deleted = false;
        this.completion = false;
    }

    public static Tteokguk of(
            String wish,
            List<Ingredient> ingredients,
            Member member,
            boolean access
    ) {
        return Tteokguk.builder()
                .wish(wish)
                .ingredients(ingredients)
                .access(access)
                .member(member)
                .build();
    }

    public void delete() {
        this.deleted = true;
    }

    public void useIngredients(List<Ingredient> desiredIngredients) {
        member.validateHasSufficientIngredients(desiredIngredients);
        validateHasAppropriateIngredients(desiredIngredients);
        validateAlreadyUsedIngredients(desiredIngredients);

        usedIngredients.addAll(desiredIngredients);
        member.useIngredients(desiredIngredients);

        if (usedIngredients.size() == 5) {
            completion = true;
        }
    }

    public void validateHasAppropriateIngredients(List<Ingredient> desiredIngredients) {
        if (!hasAppropriateIngredients(desiredIngredients)) {
            throw BusinessException.of(UNNECESSARY_INGREDIENTS_REQUESTED);
        }
    }

    public void validateAlreadyUsedIngredients(List<Ingredient> desiredIngredients) {
        if (alreadyUsedIngredients(desiredIngredients)) {
            throw BusinessException.of(ALREADY_USED_INGREDIENTS);
        }
    }

    public boolean hasAppropriateIngredients(List<Ingredient> desiredIngredients) {
        return new HashSet<>(ingredients).containsAll(desiredIngredients)
                && desiredIngredients.size() <= 5;
    }

    private boolean alreadyUsedIngredients(List<Ingredient> desiredIngredients) {
        return usedIngredients.stream()
                .anyMatch(desiredIngredients::contains);
    }

    private void validateIngredientConstraint(List<Ingredient> ingredients) {
        if (ingredients.size() != 5) {
            throw BusinessException.of(INGREDIENTS_COUNT_CONFLICT);
        }
    }
}
