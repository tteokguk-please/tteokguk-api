package com.tteokguk.tteokguk.member.application.dto.response;

public record AppOAuthLoginResponse(
	Long id,
	String accessToken,
	String refreshToken,
	boolean isInitialized
) {
}
