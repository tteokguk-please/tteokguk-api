package com.tteokguk.tteokguk.tteokguk.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Ingredient {
	RICE_CAKE("떡"),
	GREEN_ONION("파"),
	FISH_CAKE("어묵"),
	STAR_CANDY("별사탕"),
	DUMPLING("만두"),
	EGG("계란"),
	SEAWEED("김"),
	TAIYAKI("붕어빵"),
	MUSHROOM("버섯"),
	TOFU("두부"),
	BEEF("고기"),
	PEPPER("후추");

	private final String name;
}
