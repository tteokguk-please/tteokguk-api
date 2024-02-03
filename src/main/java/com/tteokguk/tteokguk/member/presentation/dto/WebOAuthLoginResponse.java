package com.tteokguk.tteokguk.member.presentation.dto;

import java.util.List;

import com.tteokguk.tteokguk.item.application.dto.response.ItemResponse;
import com.tteokguk.tteokguk.member.application.dto.response.AppMyIngredientResponse;
import com.tteokguk.tteokguk.member.application.dto.response.AppOAuthLoginResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record WebOAuthLoginResponse(
	Long id,
	String nickname,
	Ingredient primaryIngredient,
	List<ItemResponse> items,
	String accessToken,
	String refreshToken,
	boolean isInitialized
) {
	public static WebOAuthLoginResponse of(AppOAuthLoginResponse response, AppMyIngredientResponse myInfo) {
		return new WebOAuthLoginResponse(
			response.id(),
			myInfo.nickname(),
			myInfo.primaryIngredient(),
			myInfo.items(),
			response.accessToken(),
			response.refreshToken(),
			response.isInitialized()
		);
	}
}
