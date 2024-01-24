package com.tteokguk.tteokguk.member.application.dto;

public record AppOAuthLoginResponse(
	String accessToken,
	String refreshToken,
	boolean isInitialized
) {
}
