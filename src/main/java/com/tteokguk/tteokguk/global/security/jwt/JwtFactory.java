package com.tteokguk.tteokguk.global.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFactory {

	private final Key key;
	private final Long periodOfAccessToken;
	private final Long periodOfRefreshToken;

	public JwtFactory(
		@Value("${jwt.secret.key}") String secret,
		@Value("${jwt.access-token-validity}") Long periodOfAccessToken,
		@Value("${jwt.refresh-token-validity}") Long periodOfRefreshToken
	) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.periodOfAccessToken = periodOfAccessToken;
		this.periodOfRefreshToken = periodOfRefreshToken;
	}

	public Jwt createAuthToken(String id, Date expiry) {
		return new Jwt(id, expiry, key);
	}

	public Jwt createAuthToken(String id, String role, Date expiry) {
		return new Jwt(id, role, expiry, key);
	}

	public Long getExpiryOfAccessToken(Long now) {
		return now + periodOfAccessToken;
	}

	public Long getExpiryOfRefreshToken(Long now) {
		return now + periodOfRefreshToken;
	}

	public Jwt convertAuthToken(String token) {
		return new Jwt(key, token);
	}
}
