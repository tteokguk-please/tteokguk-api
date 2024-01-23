package com.tteokguk.tteokguk.global.security.model;

import java.util.Map;

import com.tteokguk.tteokguk.member.domain.ProviderType;

public class OAuth2UserFactory {
	public static AbstractOAuth2User build(ProviderType providerType, Map<String, Object> attributes) {
		switch (providerType) {
			case KAKAO: return new KakaoOAuth2User(attributes);
			default: throw new IllegalArgumentException("잘못된 Provider 타입입니다.");
		}
	}
}
