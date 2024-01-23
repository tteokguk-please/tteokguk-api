package com.tteokguk.tteokguk.tteokguk.application;

import static com.tteokguk.tteokguk.member.application.ItemService.*;
import static com.tteokguk.tteokguk.member.exception.ItemError.*;
import static com.tteokguk.tteokguk.member.exception.MemberError.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.domain.Item;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.CreateTteokgukResponse;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import com.tteokguk.tteokguk.tteokguk.infra.persistence.TteokgukRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TteokgukService {

	private final SimpleMemberRepository memberRepository;
	private final TteokgukRepository tteokgukRepository;

	public CreateTteokgukResponse createTteokguk(
		String email,
		CreateTteokgukRequest request
	) {
		final int INGREDIENTS_CONSTRAINT = 5;

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

		List<Ingredient> ingredients = Ingredient.toIngredients(request.ingredients());
		List<Item> items = member.getItems();

		if (ingredients.size() != INGREDIENTS_CONSTRAINT) {
			throw BusinessException.of(INGREDIENTS_COUNT_CONFLICT);
		} else if (!hasSufficientIngredients(items, ingredients)) {
			throw BusinessException.of(INSUFFICIENT_INGREDIENTS);
		}

		decreaseStockQuantities(items, ingredients);
		Tteokguk tteokguk = Tteokguk.of(request.wish(), ingredients, member, request.access());
		Tteokguk savedTteokguk = tteokgukRepository.save(tteokguk);
		member.addTteokguk(savedTteokguk);

		return CreateTteokgukResponse.builder()
			.tteokgukId(savedTteokguk.getId())
			.memberId(savedTteokguk.getMember().getId())
			.wish(savedTteokguk.getWish())
			.ingredients(savedTteokguk.getTteokgukIngredients())
			.access(savedTteokguk.isAccess())
			.build();
	}
}
