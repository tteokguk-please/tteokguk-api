package com.tteokguk.tteokguk.tteokguk.constants;

import java.util.List;
import java.util.Random;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Ingredient {
	RICE_CAKE("희망떡"),
	EGG("사랑계란"),
	SEAWEED("해피김"),
	GREEN_ONION("행운파"),
	BEEF("튼튼고기"),
	MUSHROOM("용기버섯"),
	TOFU("스마일두부"),
	FISH_CAKE("응원어묵"),
	CANDY("일등사탕"),
	DUMPLING("당첨만두"),
	TAIYAKI("금붕어빵"),
	GARLIC("성공마늘");

	private final String name;

	public static List<Ingredient> toIngredients(List<String> inputs) {
		return inputs.stream()
			.map(Ingredient::valueOf)
			.toList();
	}

	public static Ingredient random() {
		Random random = new Random();

		Ingredient[] ingredients = Ingredient.values();
		int randomIdx = random.nextInt(ingredients.length);
		return ingredients[randomIdx];
	}
}
