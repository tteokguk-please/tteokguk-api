package com.tteokguk.tteokguk.tteokguk.application;

import static com.tteokguk.tteokguk.member.exception.MemberError.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;
import com.tteokguk.tteokguk.tteokguk.application.dto.request.CreateTteokgukRequest;
import com.tteokguk.tteokguk.tteokguk.application.dto.response.CreateTteokgukResponse;
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
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> BusinessException.of(MEMBER_NOT_FOUND));

		// 유저 재고 바탕 예외처리 및 재고 감산 로직 추가 예정
		
		Tteokguk tteokguk = Tteokguk.of(request.wish(), request.ingredients(), member);
		Tteokguk savedTteokguk = tteokgukRepository.save(tteokguk);

		return CreateTteokgukResponse.builder()
			.tteokgukId(savedTteokguk.getId())
			.memberId(savedTteokguk.getMember().getId())
			.wish(savedTteokguk.getWish())
			.ingredients(savedTteokguk.getTteokgukIngredients())
			.build();
	}
}
