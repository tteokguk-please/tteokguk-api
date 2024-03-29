package com.tteokguk.tteokguk.member.application;

import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.global.security.jwt.JwtService;
import com.tteokguk.tteokguk.member.application.dto.response.AppIssuedTokensResponse;
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
	private final JwtService jwtService;

	public AppIssuedTokensResponse issueTokens(String refreshToken, String userAgent) {
		try {
			jwtService.validate(refreshToken);
		} catch (BusinessException e) {
			throw new BusinessException(AuthError.UNUSABLE_TOKEN);
		}

		RefreshToken entity = refreshTokenRepository.findByTokenAndUserAgent(refreshToken, userAgent)
			.orElseThrow(() -> new BusinessException(AuthError.UNUSABLE_TOKEN));

		long currentTimeMillis = System.currentTimeMillis();
		String issuedAccessToken = issueAccessToken(entity, currentTimeMillis);
		String issuedRefreshToken = issueRefreshToken(entity, userAgent, currentTimeMillis);
		return new AppIssuedTokensResponse(entity.getMember().getId(), issuedAccessToken, issuedRefreshToken);
	}

	public String issueRefreshToken(RefreshToken entity, String userAgent, Long currentTimeMillis) {
		return jwtService.getRefreshToken(entity.getMember(), userAgent, currentTimeMillis).getEncodedBody();
	}

	public String issueAccessToken(RefreshToken entity, Long currentTimeMillis) {
		return jwtService.getAccessToken(entity.getMember(), currentTimeMillis).getEncodedBody();
	}
}
