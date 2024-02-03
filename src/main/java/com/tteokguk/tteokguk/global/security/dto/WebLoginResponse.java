package com.tteokguk.tteokguk.global.security.dto;

import java.util.List;

import com.tteokguk.tteokguk.item.application.dto.response.ItemResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record WebLoginResponse(
	Long id,
	String nickname,
	Ingredient primaryIngridient,
	List<ItemResponse> items,
	String accessToken,
	String refreshToken
) {
}
