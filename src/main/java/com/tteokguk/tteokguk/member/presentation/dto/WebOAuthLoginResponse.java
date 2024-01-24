package com.tteokguk.tteokguk.member.presentation.dto;

import com.tteokguk.tteokguk.member.application.dto.response.AppOAuthLoginResponse;

public record WebOAuthLoginResponse(
	Long id,
	String accessToken,
	String refreshToken,
	boolean isInitialized
) {
	public static WebOAuthLoginResponse of(AppOAuthLoginResponse response) {
		return new WebOAuthLoginResponse(
			response.id(),
			response.accessToken(),
			response.refreshToken(),
			response.isInitialized()
		);
	}
}
