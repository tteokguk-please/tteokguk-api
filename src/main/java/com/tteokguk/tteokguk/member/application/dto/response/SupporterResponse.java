package com.tteokguk.tteokguk.member.application.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tteokguk.tteokguk.support.domain.Support;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;

public record SupporterResponse(
	Long tteokgukId,
	Map<Ingredient, SupporterBody> supporters
) {
	record SupporterBody (
		Long supportId,
		String nickname,
		String message,
		Ingredient ingredient
	) {
		public static SupporterBody of(Support support) {
			return new SupporterBody(
				support.getId(),
				parseNickname(support),
				support.getMessage(),
				support.getSupportIngredient()
			);
		}

		private static String parseNickname(Support support) {
			if (!support.isAccess())
				return "익명의 사용자";
			return support.getSender().getNickname();
		}
	}

	public static SupporterResponse of(Tteokguk tteokguk, List<Support> supports) {
		Map<Ingredient, SupporterBody> supporterBodies = supports.stream().map(SupporterBody::of)
			.collect(Collectors.toMap(
				s -> s.ingredient, s -> s
			));

		return new SupporterResponse(
			tteokguk.getId(), supporterBodies
		);
	}
}
