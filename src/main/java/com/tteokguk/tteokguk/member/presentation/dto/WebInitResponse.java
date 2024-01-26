package com.tteokguk.tteokguk.member.presentation.dto;

import com.tteokguk.tteokguk.member.application.dto.response.AppInitResponse;

public record WebInitResponse(
	Long id,
	String nickname,
	String primaryIngredient
) {
	public static WebInitResponse of(AppInitResponse response) {
		return new WebInitResponse(
			response.id(),
			response.nickname(),
			response.primaryIngredient()
		);
	}
}
