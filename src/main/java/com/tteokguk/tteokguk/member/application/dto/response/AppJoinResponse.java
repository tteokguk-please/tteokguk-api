package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.member.domain.Member;

public record AppJoinResponse(
	Long id,
	String nickname,
	String primaryIngredient
) {
	public static AppJoinResponse of(Member entity) {
		return new AppJoinResponse(
			entity.getId(),
			entity.getNickname(),
			entity.getPrimaryIngredient().name()
		);
	}
}
