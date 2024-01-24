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

import java.util.List;

import static com.tteokguk.tteokguk.member.exception.ItemError.INGREDIENTS_COUNT_CONFLICT;
import static com.tteokguk.tteokguk.member.exception.ItemError.INSUFFICIENT_INGREDIENTS;
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
    @ColumnDefault("false")
    private boolean completion;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Column(name = "ingredient")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Ingredient.class)
    @CollectionTable(
            name = "t_tteokguk_ingredients",
            joinColumns = @JoinColumn(name = "tteokguk_id")
    )
    private List<Ingredient> ingredients;

    @Builder(access = PROTECTED)
    private Tteokguk(
            String wish,
            List<Ingredient> ingredients,
            Member member,
            boolean access
    ) {
        if (hasInvalidIngredientCount(ingredients)) {
            throw BusinessException.of(INGREDIENTS_COUNT_CONFLICT);
        } else if (!member.hasSufficientIngredients(ingredients)) {
            throw BusinessException.of(INSUFFICIENT_INGREDIENTS);
        }

        this.wish = wish;
        this.ingredients = ingredients;
        this.member = member;
        this.access = access;
        this.deleted = false;
        this.completion = isCompletion(this.ingredients);
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

    //== Validation Method ==//
    private boolean hasInvalidIngredientCount(List<Ingredient> ingredients) {
        return ingredients.isEmpty() || ingredients.size() > 5;
    }

    private boolean isCompletion(List<Ingredient> ingredients) {
        return ingredients.size() == 5;
    }

    public void delete() {
        this.deleted = true;
    }
}
