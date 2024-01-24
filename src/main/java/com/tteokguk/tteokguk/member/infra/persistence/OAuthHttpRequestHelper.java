package com.tteokguk.tteokguk.member.infra.persistence;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tteokguk.tteokguk.member.application.properties.OAuthProperties;

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

}
