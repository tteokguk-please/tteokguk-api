package com.tteokguk.tteokguk.member.application.dto.response;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record MemberResponse(
	Long id,
	String nickname,
	Ingredient primaryIngredient
) {
}
