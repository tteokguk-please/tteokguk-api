package com.tteokguk.tteokguk.global.security.model;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public abstract class AbstractOAuth2User implements OAuth2User {
	protected Map<String, Object> attributes;

	public AbstractOAuth2User(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public abstract String getId();
}
