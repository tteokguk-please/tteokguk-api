package com.tteokguk.tteokguk.member.application.dto.response;

import java.util.List;

import com.tteokguk.tteokguk.member.domain.Item;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;

public record MyPageResponse(
	Long id,
	Ingredient primaryIngredient,
	String nickname,
	List<Tteokguk> tteokguks,
	List<Item> items
) {
}
