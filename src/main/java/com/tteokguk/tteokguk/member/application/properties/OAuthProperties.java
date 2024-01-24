package com.tteokguk.tteokguk.member.application.properties;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.tteokguk.tteokguk.member.domain.ProviderType;

import lombok.Getter;

@Getter
@Component
@ConfigurationProperties("spring.security.oauth2.client")
public class OAuthProperties {

	private final Map<ProviderType, OAuthProviderProperty> provider = new EnumMap<>(ProviderType.class);
	private final Map<ProviderType, OAuthRegistrationProperty> registration = new EnumMap<>(ProviderType.class);

	public OAuthProviderProperty getProviderProperties(ProviderType providerType) {
		return provider.get(providerType);
	}

	public OAuthRegistrationProperty getRegistrationProperties(ProviderType providerType) {
		return registration.get(providerType);
	}

	public record OAuthProviderProperty(
		String tokenUri,
		String userInfoUri,
		String authorizationUri,
		String userNameAttribute
	) {}

	public record OAuthRegistrationProperty(
		String clientId,
		String clientSecret,
		String clientAuthenticationMethod,
		String redirectUri,
		String authorizationGrantType,
		String clientName
	) {}
}
