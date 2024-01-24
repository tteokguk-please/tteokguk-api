package com.tteokguk.tteokguk.member.application;

import com.tteokguk.tteokguk.member.domain.Item;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Component
@NoArgsConstructor(access = PROTECTED)
public class ItemService {

    public static boolean hasSufficientIngredients(
            List<Item> items,
            List<Ingredient> ingredients
    ) {
        return ingredients.stream()
                .allMatch(
                        ingredient -> items.stream()
                                .filter(item -> item.getIngredient().equals(ingredient))
                                .allMatch(item -> item.getStockQuantity() >= 1)
                );
    }

    public static void decreaseStockQuantities(
            List<Item> items,
            List<Ingredient> ingredients
    ) {
        ingredients.forEach(ingredient -> {
            items.stream()
                    .filter(item -> item.getIngredient().equals(ingredient))
                    .forEach(item -> item.decreaseStockQuantity(1));
        });
    }
}
