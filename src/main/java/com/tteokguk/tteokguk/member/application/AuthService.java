package com.tteokguk.tteokguk.member.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.application.dto.AppJoinRequest;
import com.tteokguk.tteokguk.member.domain.SimpleMember;
import com.tteokguk.tteokguk.member.exception.AuthError;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private final SimpleMemberRepository simpleMemberRepository;
	private final PasswordEncoder encoder;

	public Long join(AppJoinRequest request) {
		if (existsByEmail(request.email()))
			throw new BusinessException(AuthError.DUPLICATE_EMAIL);

		SimpleMember saved = simpleMemberRepository.save(
			SimpleMember.join(request.email(), encoder.encode(request.password()), request.nickname())
		);
		return saved.getId();
	}

	public boolean existsByEmail(String email) {
		return simpleMemberRepository.existsByEmail(email);
	}
}
