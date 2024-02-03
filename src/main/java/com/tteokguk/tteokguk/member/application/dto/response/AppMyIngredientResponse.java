package com.tteokguk.tteokguk.member.application.dto.response;

import java.util.List;

import com.tteokguk.tteokguk.item.application.dto.response.ItemResponse;
import com.tteokguk.tteokguk.member.application.dto.response.assembler.UserInfoResponseAssembler;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;

public record AppMyIngredientResponse(
	Long id,
	String nickname,
	Ingredient primaryIngredient,
	List<ItemResponse> items
) {
	public static AppMyIngredientResponse of(Member member) {
		return new AppMyIngredientResponse(
			member.getId(),
			member.getNickname(),
			member.getPrimaryIngredient(),
			member.getItems().stream()
				.map(item -> ItemResponse.builder()
					.ingredient(item.getIngredient())
					.stockQuantity(item.getStockQuantity())
					.build()
				).toList()
		);
	}
}
