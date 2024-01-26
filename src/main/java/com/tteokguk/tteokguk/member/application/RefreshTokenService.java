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

	public AppIssuedTokensResponse issueTokens(String refreshToken) {
		RefreshToken entity = refreshTokenRepository.findByToken(refreshToken)
			.orElseThrow(() -> new BusinessException(AuthError.UNSUPPORTED_JWT_TOKEN));

		long currentTimeMillis = System.currentTimeMillis();
		String issuedAccessToken = issueAccessToken(entity, currentTimeMillis);
		String issuedRefreshToken = issueRefreshToken(entity, currentTimeMillis);
		return new AppIssuedTokensResponse(entity.getMember().getId(), issuedAccessToken, issuedRefreshToken);
	}

	public String issueRefreshToken(RefreshToken entity, Long currentTimeMillis) {
		return jwtService.getRefreshToken(entity.getMember(), currentTimeMillis).getEncodedBody();
	}

	public String issueAccessToken(RefreshToken entity, Long currentTimeMillis) {
		return jwtService.getAccessToken(entity.getMember(), currentTimeMillis).getEncodedBody();
	}
}
