package com.tteokguk.tteokguk.member.application;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.global.security.jwt.Jwt;
import com.tteokguk.tteokguk.global.security.jwt.JwtFactory;
import com.tteokguk.tteokguk.global.security.jwt.JwtService;
import com.tteokguk.tteokguk.member.application.dto.response.AppOAuthLoginResponse;
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
	private final JwtService jwtService;

	public AppOAuthLoginResponse getByAuthorizationCode(ProviderType providerType, String code, String userAgent) {
		TokenResponse tokenResponse = oAuthHttpRequestHelper.exchangeToken(providerType, code);

		UserInfoResponse userInfoResponse = oAuthHttpRequestHelper.getUserInfo(
			providerType, tokenResponse.accessToken()
		);

		return createResponse(getOAuthMember(providerType, userInfoResponse), userAgent);
	}

	public AppOAuthLoginResponse getByAccessToken(ProviderType providerType, String accessToken, String userAgent) {
		UserInfoResponse userInfoResponse = oAuthHttpRequestHelper.getUserInfo(providerType, accessToken);

		return createResponse(getOAuthMember(providerType, userInfoResponse), userAgent);
	}

	private OAuthMember getOAuthMember(ProviderType providerType, UserInfoResponse userInfoResponse) {
		return oAuthMemberRepository.findByProviderTypeAndResourceId(providerType, userInfoResponse.id())
			.orElseGet(() -> oAuthMemberRepository.save(
				OAuthMember.of(
					providerType,
					userInfoResponse.id(),
					providerType + "_" + userInfoResponse.id(),
					RoleType.ROLE_TEMP_USER,
					null
				)
			));
	}

	private AppOAuthLoginResponse createResponse(OAuthMember member, String userAgent) {
		Long now = System.currentTimeMillis();

		return new AppOAuthLoginResponse(
			member.getId(),
			jwtService.getAccessToken(member, now).getEncodedBody(),
			jwtService.getRefreshToken(member, userAgent, now).getEncodedBody(),
			member.getRole() == RoleType.ROLE_USER
		);
	}
}
