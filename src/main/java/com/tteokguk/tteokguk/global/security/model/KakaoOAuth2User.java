package com.tteokguk.tteokguk.global.security.model;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public class KakaoOAuth2User extends AbstractOAuth2User {

	public KakaoOAuth2User(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}
