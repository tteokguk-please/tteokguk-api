package com.tteokguk.tteokguk.member.application.dto;

public record AppOAuthLoginResponse(
	Long id,
	String accessToken,
	String refreshToken,
	boolean isInitialized
) {
}
