package com.tteokguk.tteokguk.member.application;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.member.domain.RefreshToken;
import com.tteokguk.tteokguk.member.infra.persistence.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public void save(String token, Long memberId, LocalDateTime expiredDateTime) {
		RefreshToken entity = refreshTokenRepository.findByMemberId(memberId)
			.orElseGet(() -> new RefreshToken(memberId, token, expiredDateTime));
		entity.update(token, expiredDateTime);
		refreshTokenRepository.save(entity);
	}

}
