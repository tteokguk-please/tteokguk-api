package com.tteokguk.tteokguk.global.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.CharsetEncoder;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tteokguk.tteokguk.global.security.dto.WebLoginResponse;
import com.tteokguk.tteokguk.global.security.jwt.Jwt;
import com.tteokguk.tteokguk.global.security.jwt.JwtFactory;
import com.tteokguk.tteokguk.global.security.jwt.JwtService;
import com.tteokguk.tteokguk.global.security.model.PrincipalDetails;
import com.tteokguk.tteokguk.global.utils.LocalDateTimeUtils;
import com.tteokguk.tteokguk.member.application.RefreshTokenService;
import com.tteokguk.tteokguk.member.application.UserInfoService;
import com.tteokguk.tteokguk.member.application.dto.response.AppMyIngredientResponse;
import com.tteokguk.tteokguk.member.application.dto.response.MyPageResponse;
import com.tteokguk.tteokguk.member.domain.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ObjectMapper om;
	private final JwtService jwtService;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException {
		PrincipalDetails details = (PrincipalDetails)authentication.getPrincipal();
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		writeResponseBody(response, getJsonResponse(details.getMember()));
	}

	private void writeResponseBody(HttpServletResponse response, String jsonResponse) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.write(jsonResponse);
	}

	private String getJsonResponse(Member member) throws IOException {
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		Long now = System.currentTimeMillis();
		String accessToken = jwtService.getAccessToken(member, now).getEncodedBody();
		String refreshToken = jwtService.getRefreshToken(member, now).getEncodedBody();

		return om.writerWithDefaultPrettyPrinter()
			.writeValueAsString(
				new WebLoginResponse(
					member.getId(),
					accessToken,
					refreshToken
				)
			);
	}
}
