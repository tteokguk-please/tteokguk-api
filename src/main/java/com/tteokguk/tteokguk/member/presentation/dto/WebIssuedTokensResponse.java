package com.tteokguk.tteokguk.member.presentation.dto;

import java.util.List;

import com.tteokguk.tteokguk.item.application.dto.response.ItemResponse;
import com.tteokguk.tteokguk.member.application.dto.response.AppIssuedTokensResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record WebIssuedTokensResponse(
	Long id,
	String nickname,
	Ingredient primaryIngredient,
	List<ItemResponse> items,
	String accessToken,
	String refreshToken
) {
	public static WebIssuedTokensResponse of(AppIssuedTokensResponse response, MyPageResponse myInfo) {
		return new WebIssuedTokensResponse(
			response.id(),
			myInfo.nickname(),
			myInfo.primaryIngredient(),
			myInfo.items(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}
