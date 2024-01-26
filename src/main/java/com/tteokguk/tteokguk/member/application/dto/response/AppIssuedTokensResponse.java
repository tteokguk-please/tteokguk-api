package com.tteokguk.tteokguk.member.application.dto.response;

public record AppIssuedTokensResponse(
	Long id,
	String accessToken,
	String refreshToken
) {
}
