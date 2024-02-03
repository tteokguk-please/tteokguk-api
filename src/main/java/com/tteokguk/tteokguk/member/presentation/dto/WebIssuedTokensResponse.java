package com.tteokguk.tteokguk.member.presentation.dto;

import com.tteokguk.tteokguk.member.application.dto.response.AppIssuedTokensResponse;

public record WebIssuedTokensResponse(
	Long id,
	String accessToken,
	String refreshToken
) {
	public static WebIssuedTokensResponse of(AppIssuedTokensResponse response) {
		return new WebIssuedTokensResponse(
			response.id(),
			response.accessToken(),
			response.refreshToken()
		);
	}
}
