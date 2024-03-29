package com.tteokguk.tteokguk.member.presentation.dto;

import com.tteokguk.tteokguk.member.application.dto.response.AppJoinResponse;

public record WebJoinResponse(
	Long id,
	String nickname,
	String primaryIngredient,
	String accessToken,
	String refreshToken
) {
	public static WebJoinResponse of(AppJoinResponse response) {
		return new WebJoinResponse(
			response.id(),
			response.nickname(),
			response.primaryIngredient(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}
