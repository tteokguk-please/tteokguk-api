package com.tteokguk.tteokguk.tteokguk.application.dto.response;

import java.util.List;

import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

import lombok.Builder;

@Builder
public record CreateTteokgukResponse(
	Long tteokgukId,
	Long memberId,
	String wish,
	List<Ingredient> ingredients
) {
}
