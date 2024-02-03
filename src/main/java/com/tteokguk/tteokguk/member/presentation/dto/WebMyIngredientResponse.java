package com.tteokguk.tteokguk.member.presentation.dto;

import java.util.List;

import com.tteokguk.tteokguk.item.application.dto.response.ItemResponse;
import com.tteokguk.tteokguk.member.application.dto.response.AppMyIngredientResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record WebMyIngredientResponse(
	Long id,
	String nickname,
	Ingredient primaryIngredient,
	List<ItemResponse> itemResponses
) {
	public static WebMyIngredientResponse of(AppMyIngredientResponse response) {
		return new WebMyIngredientResponse(
			response.id(),
			response.nickname(),
			response.primaryIngredient(),
			response.items()
		);
	}
}
