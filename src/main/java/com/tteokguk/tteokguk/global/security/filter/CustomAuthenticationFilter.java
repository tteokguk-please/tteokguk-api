package com.tteokguk.tteokguk.global.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tteokguk.tteokguk.global.security.dto.WebLoginRequest;
import com.tteokguk.tteokguk.global.security.handler.CustomAuthenticationFailureHandler;
import com.tteokguk.tteokguk.global.security.handler.CustomAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final ObjectMapper om;

	public CustomAuthenticationFilter(
		AuthenticationManager authenticationManager,
		ObjectMapper om,
		CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
		CustomAuthenticationFailureHandler customAuthenticationFailureHandler
	) {
		super(authenticationManager);
		super.setFilterProcessesUrl("/api/v1/auth/login");
		super.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
		super.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
		this.om = om;
	}

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
			WebLoginRequest dto = om.readValue(request.getInputStream(), WebLoginRequest.class);
			log.debug("CustomAuthenticationFilter :: email : {}, password : {}", dto.email(), dto.password());
			return new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
