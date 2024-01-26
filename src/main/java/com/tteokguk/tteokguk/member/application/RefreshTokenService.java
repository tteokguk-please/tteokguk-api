package com.tteokguk.tteokguk.member.application;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.global.security.jwt.Jwt;
import com.tteokguk.tteokguk.global.security.jwt.JwtFactory;
import com.tteokguk.tteokguk.global.utils.LocalDateTimeUtils;
import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.domain.RefreshToken;
import com.tteokguk.tteokguk.member.exception.AuthError;
import com.tteokguk.tteokguk.member.infra.persistence.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtFactory jwtFactory;

	public void save(String token, Member member, LocalDateTime expiredDateTime) {
		RefreshToken entity = refreshTokenRepository.findByMember(member)
			.orElseGet(() -> new RefreshToken(member, token, expiredDateTime));
		entity.update(token, expiredDateTime);
		refreshTokenRepository.save(entity);
	}

	public String issueRefreshToken(String refreshToken) {
		RefreshToken entity = refreshTokenRepository.findByToken(refreshToken)
			.orElseThrow(() -> new BusinessException(AuthError.UNSUPPORTED_JWT_TOKEN));

		Long expiry = jwtFactory.getExpiryOfRefreshToken(System.currentTimeMillis());
		Jwt newToken = jwtFactory.createAuthToken(null, new Date(expiry));
		save(newToken.getEncodedBody(), entity.getMember(), LocalDateTimeUtils.convertBy(expiry));
		return newToken.getEncodedBody();
	}

	public String issueAccessToken(String refreshToken) {
		RefreshToken entity = refreshTokenRepository.findByToken(refreshToken)
			.orElseThrow(() -> new BusinessException(AuthError.UNSUPPORTED_JWT_TOKEN));

		Member member = entity.getMember();
		Long expiry = jwtFactory.getExpiryOfAccessToken(System.currentTimeMillis());
		return jwtFactory.createAuthToken(String.valueOf(member.getId()), member.getRole().name(), new Date(expiry))
			.getEncodedBody();
	}
}
