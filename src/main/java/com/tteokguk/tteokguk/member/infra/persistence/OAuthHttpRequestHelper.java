package com.tteokguk.tteokguk.member.infra.persistence;

import static com.tteokguk.tteokguk.member.application.properties.OAuthProperties.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tteokguk.tteokguk.member.application.properties.OAuthProperties;
import com.tteokguk.tteokguk.member.domain.ProviderType;
import com.tteokguk.tteokguk.tteokguk.infra.persistence.dto.TokenResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthHttpRequestHelper {

	private static final String CODE = "code";
	private static final String GRANT_TYPE = "grant_type";
	private static final String REDIRECT_URI = "redirect_uri";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";

	private final RestTemplate restTemplate;
	private final OAuthProperties oAuthProperties;

	public TokenResponse exchangeToken(ProviderType providerType, String code) {
		OAuthProviderProperty providerProperty = oAuthProperties.getProviderProperties(providerType);
		OAuthRegistrationProperty registrationProperty = oAuthProperties.getRegistrationProperties(providerType);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add(GRANT_TYPE, registrationProperty.authorizationGrantType());
		params.add(CODE, code);
		params.add(REDIRECT_URI, registrationProperty.redirectUri());
		params.add(CLIENT_ID, registrationProperty.clientId());
		params.add(CLIENT_SECRET, registrationProperty.clientSecret());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
			providerProperty.tokenUri(), request, TokenResponse.class
		);

		return response.getBody();
	}
}
