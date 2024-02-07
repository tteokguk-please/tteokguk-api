package com.tteokguk.tteokguk.global.security.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tteokguk.tteokguk.global.security.jwt.Jwt;
import com.tteokguk.tteokguk.global.security.jwt.JwtFactory;
import com.tteokguk.tteokguk.global.security.model.CustomUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";

	private final JwtFactory jwtFactory;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException, IOException {
		String encodedBody = getAccessToken(request);

		if (encodedBody == null) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ANONYMOUS");
			User principal = new CustomUser(
				null,
				"anonymousUser",
				"",
				Collections.singleton(authority)
			);

			AnonymousAuthenticationToken authenticationToken =
				new AnonymousAuthenticationToken("anonymousUser", principal, Collections.singleton(authority));
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			chain.doFilter(request, response);
			return;
		}

		Jwt accessToken = jwtFactory.convertAuthToken(encodedBody);
		Authentication authentication = accessToken.getAuthentication();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	public static String getAccessToken(HttpServletRequest request) {
		String headerValue = request.getHeader(HEADER_AUTHORIZATION);

		if (headerValue == null) {
			return null;
		}

		if (headerValue.startsWith(TOKEN_PREFIX)) {
			return headerValue.substring(TOKEN_PREFIX.length());
		}

		return null;
	}

	@Bean
	public FilterRegistrationBean<CustomAuthorizationFilter> filterRegistrationBean() {
		FilterRegistrationBean<CustomAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(this);
		filterRegistrationBean.setEnabled(false);
		return filterRegistrationBean;
	}
}
