package com.tteokguk.tteokguk.member.application;

import static com.tteokguk.tteokguk.member.exception.MemberError.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.domain.Item;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;
import com.tteokguk.tteokguk.tteokguk.constants.Ingredient;
import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

	private final MemberRepository memberRepository;

	public MyPageResponse getMyPageInfo(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

		Long id = member.getId();
		Ingredient primaryIngredient = member.getPrimaryIngredient();
		String nickname = member.getNickname();
		List<Tteokguk> tteokguks = member.getTteokguks();
		List<Item> items = member.getItems();

		return new MyPageResponse(id, primaryIngredient, nickname, tteokguks, items);
	}
}
