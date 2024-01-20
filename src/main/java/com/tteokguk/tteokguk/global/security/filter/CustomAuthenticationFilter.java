package com.tteokguk.tteokguk.global.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	record LoginRequest(String email, String password) {}

	private ObjectMapper om = new ObjectMapper();

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
		setDetails(request, authenticationToken);
		Authentication authenticate;
		try {
			authenticate = getAuthenticationManager().authenticate(authenticationToken);
		} catch (AuthenticationException e) {
			request.setAttribute("exception", e);
			throw e;
		}
		return authenticate;
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		try {
			LoginRequest dto = om.readValue(request.getInputStream(), LoginRequest.class);
			log.debug("CustomAuthenticationFilter :: email : {}, password : {}", dto.email, dto.password);
			return new UsernamePasswordAuthenticationToken(dto.email, dto.password);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
