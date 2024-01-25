package com.tteokguk.tteokguk.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_item")
@NoArgsConstructor(access = PROTECTED)
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(name = "ingredient")
    private Ingredient ingredient;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @JsonIgnore
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @Builder(access = PRIVATE)
    private Item(
            Ingredient ingredient,
            int stockQuantity,
            Member member
    ) {
        this.ingredient = ingredient;
        this.stockQuantity = stockQuantity;
        this.member = member;
    }

    public static Item of(
            Ingredient ingredient,
            int stockQuantity,
            Member member
    ) {
        return Item.builder()
                .ingredient(ingredient)
                .stockQuantity(stockQuantity)
                .member(member)
                .build();
    }

    public synchronized void use() {
        this.stockQuantity--;
    }

    public synchronized void increaseStockQuantity(int amount) {
        this.stockQuantity += amount;
    }
}
