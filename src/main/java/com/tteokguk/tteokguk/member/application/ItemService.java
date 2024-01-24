package com.tteokguk.tteokguk.member.application;

import static lombok.AccessLevel.*;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.member.domain.Item;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import lombok.NoArgsConstructor;

@Component
@Transactional
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
