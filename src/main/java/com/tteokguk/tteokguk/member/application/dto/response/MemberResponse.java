package com.tteokguk.tteokguk.member.application.dto.response;

import java.util.List;

import com.tteokguk.tteokguk.tteokguk.application.dto.response.SimpleTteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record MemberResponse(
	Long id,
	String nickname,
	Ingredient primaryIngredient,
	List<SimpleTteokgukResponse> tteokguks
) {
}
