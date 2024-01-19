package com.tteokguk.tteokguk.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.member.application.dto.AppJoinRequest;
import com.tteokguk.tteokguk.member.domain.SimpleMember;
import com.tteokguk.tteokguk.member.infra.persistence.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder encoder;

	public Long join(AppJoinRequest request) {
		SimpleMember saved = memberRepository.save(
			SimpleMember.join(request.email(), encoder.encode(request.password()), request.nickname())
		);
		return saved.getId();
	}
}