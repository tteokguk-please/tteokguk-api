package com.tteokguk.tteokguk.member.application;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.global.security.jwt.Jwt;
import com.tteokguk.tteokguk.global.security.jwt.JwtFactory;
import com.tteokguk.tteokguk.member.application.dto.AppOAuthLoginResponse;
import com.tteokguk.tteokguk.member.domain.OAuthMember;
import com.tteokguk.tteokguk.member.domain.ProviderType;
import com.tteokguk.tteokguk.member.domain.RoleType;
import com.tteokguk.tteokguk.member.infra.persistence.OAuthHttpRequestHelper;
import com.tteokguk.tteokguk.member.infra.persistence.OAuthMemberRepository;
import com.tteokguk.tteokguk.member.infra.persistence.dto.TokenResponse;
import com.tteokguk.tteokguk.member.infra.persistence.dto.UserInfoResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthService {

	private final OAuthHttpRequestHelper oAuthHttpRequestHelper;
	private final OAuthMemberRepository oAuthMemberRepository;
	private final JwtFactory jwtFactory;

	public AppOAuthLoginResponse getOAuthMember(ProviderType providerType, String code) {
		TokenResponse tokenResponse = oAuthHttpRequestHelper.exchangeToken(providerType, code);
		UserInfoResponse userInfoResponse = oAuthHttpRequestHelper.getUserInfo(
			providerType, tokenResponse.accessToken()
		);

		OAuthMember member = oAuthMemberRepository.findByProviderTypeAndResourceId(providerType, userInfoResponse.id())
			.orElseGet(() -> oAuthMemberRepository.save(
				OAuthMember.of(providerType, userInfoResponse.id(), providerType + "_" + userInfoResponse.id(), RoleType.ROLE_TEMP_USER)
			));

		Long now = System.currentTimeMillis();
		Long expiryOfAccessToken = jwtFactory.getExpiryOfAccessToken(now);
		Long expiryOfRefreshToken = jwtFactory.getExpiryOfRefreshToken(now);
		Jwt accessToken = jwtFactory.createAuthToken(
			String.valueOf(member.getId()), member.getRole().name(), new Date(expiryOfAccessToken)
		);
		Jwt refreshToken = jwtFactory.createAuthToken(null, new Date(expiryOfRefreshToken));

		return new AppOAuthLoginResponse(
			accessToken.getEncodedBody(), refreshToken.getEncodedBody(), member.getRole() == RoleType.ROLE_USER
		);
	}
}
