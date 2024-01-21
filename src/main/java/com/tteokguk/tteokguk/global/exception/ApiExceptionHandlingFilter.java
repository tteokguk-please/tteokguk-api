package com.tteokguk.tteokguk.global.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tteokguk.tteokguk.global.exception.error.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiExceptionHandlingFilter extends OncePerRequestFilter {

	private final ObjectMapper om;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException, IOException {
		try {
			chain.doFilter(request, response);
		} catch (BusinessException e) {
			setErrorResponse(response, e);
		}
	}

	private void setErrorResponse(HttpServletResponse response, BusinessException e) {
		try {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			om.writeValue(response.getOutputStream(), ErrorResponse.of(e.getErrorCode()));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
