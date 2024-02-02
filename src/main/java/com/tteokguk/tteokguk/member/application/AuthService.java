package com.tteokguk.tteokguk.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.application.dto.request.AppJoinRequest;
import com.tteokguk.tteokguk.member.application.dto.response.AppJoinResponse;
import com.tteokguk.tteokguk.member.domain.RoleType;
import com.tteokguk.tteokguk.member.domain.SimpleMember;
import com.tteokguk.tteokguk.member.exception.MemberError;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final SimpleMemberRepository simpleMemberRepository;
	private final PasswordEncoder encoder;

	public AppJoinResponse join(AppJoinRequest request) {
		if (existsByEmail(request.email()))
			throw new BusinessException(MemberError.DUPLICATE_EMAIL);

		if (existsByNickname(request.nickname()))
			throw new BusinessException(MemberError.DUPLICATE_NICKNAME);

		SimpleMember entity = simpleMemberRepository.save(
			SimpleMember.of(
				request.email(),
				encoder.encode(request.password()),
				request.nickname(),
				RoleType.ROLE_USER,
				request.acceptsMarketing()
			)
		);
		return AppJoinResponse.of(entity);
	}

	public boolean existsByEmail(String email) {
		return simpleMemberRepository.existsByEmail(email);
	}

	public boolean existsByNickname(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}
}
