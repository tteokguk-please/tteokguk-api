package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.member.domain.Member;

public record AppInitResponse(
	Long id,
	String nickname,
	String primaryIngredient,
	String accessToken,
	String refreshToken
) {
	public static AppInitResponse of(Member entity, String accessToken, String refreshToken) {
		return new AppInitResponse(
			entity.getId(),
			entity.getNickname(),
			entity.getPrimaryIngredient().name(),
			accessToken,
			refreshToken
		);
	}
}
