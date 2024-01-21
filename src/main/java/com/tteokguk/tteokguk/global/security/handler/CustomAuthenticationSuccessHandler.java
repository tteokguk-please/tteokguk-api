package com.tteokguk.tteokguk.global.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tteokguk.tteokguk.global.security.dto.WebLoginResponse;
import com.tteokguk.tteokguk.global.security.jwt.Jwt;
import com.tteokguk.tteokguk.global.security.jwt.JwtFactory;
import com.tteokguk.tteokguk.global.security.model.PrincipalDetails;
import com.tteokguk.tteokguk.member.domain.SimpleMember;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ObjectMapper om = new ObjectMapper();
	private final JwtFactory jwtFactory;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException {
		PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
		writeResponseBody(response, getJsonResponse(details.getMember()));
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
	}

	private void writeResponseBody(HttpServletResponse response, String jsonResponse) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.write(jsonResponse);
	}

	private String getJsonResponse(SimpleMember member) throws IOException {
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		Long now = System.currentTimeMillis();
		String accessToken = createAccessToken(member, now);
		String refreshToken = createRefreshToken(now);

		return om.writerWithDefaultPrettyPrinter()
			.writeValueAsString(
				new WebLoginResponse(member.getId(), accessToken, refreshToken)
			);
	}

	private String createAccessToken(SimpleMember member, Long now) {
		Long expiryOfAccessToken = jwtFactory.getExpiryOfAccessToken(now);
		Jwt accessToken = jwtFactory.createAuthToken(member.getEmail(), "ROLE_USER", new Date(expiryOfAccessToken));
		return accessToken.getEncodedBody();
	}

	private String createRefreshToken(Long now) {
		Long expiryOfRefreshToken = jwtFactory.getExpiryOfRefreshToken(now);
		Jwt accessToken = jwtFactory.createAuthToken(null, new Date(expiryOfRefreshToken));
		return accessToken.getEncodedBody();
	}
}
