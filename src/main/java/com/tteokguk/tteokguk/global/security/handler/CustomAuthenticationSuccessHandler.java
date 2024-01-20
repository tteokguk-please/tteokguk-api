package com.tteokguk.tteokguk.global.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tteokguk.tteokguk.global.security.dto.WebLoginResponse;
import com.tteokguk.tteokguk.global.security.model.PrincipalDetails;
import com.tteokguk.tteokguk.member.domain.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ObjectMapper om = new ObjectMapper();

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

	private String getJsonResponse(Member member) throws IOException {
		om.registerModule(new JavaTimeModule());
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return om.writerWithDefaultPrettyPrinter()
			.writeValueAsString(
				new WebLoginResponse(member.getId(), UUID.randomUUID().toString(), UUID.randomUUID().toString())
			);
	}
}
