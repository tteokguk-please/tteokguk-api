package com.tteokguk.tteokguk.global.security;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tteokguk.tteokguk.global.exception.ApiExceptionHandlingFilter;
import com.tteokguk.tteokguk.global.security.filter.CustomAuthenticationFilter;
import com.tteokguk.tteokguk.global.security.filter.CustomAuthorizationFilter;
import com.tteokguk.tteokguk.global.security.handler.CustomAuthenticationFailureHandler;
import com.tteokguk.tteokguk.global.security.handler.CustomAuthenticationSuccessHandler;
import com.tteokguk.tteokguk.global.security.jwt.JwtFactory;
import com.tteokguk.tteokguk.global.security.provider.CustomAuthenticationProvider;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final SimpleMemberRepository simpleMemberRepository;

	private final JwtFactory jwtFactory;

	@Bean
	@Order(0)
	public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/api/v1/auth/**")
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

		return commonHttpSecurity(http).build();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain anyRequestFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
			.addFilterAfter(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

		return commonHttpSecurity(http).build();
	}

	private HttpSecurity commonHttpSecurity(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(configurer -> corsConfigurationSource())
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.addFilterBefore(apiExceptionHandlingFilter(), CustomAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CustomAuthenticationFilter customAuthenticationFilter() {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
		customAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
		customAuthenticationFilter.setAuthenticationManager(
			authenticationManager(passwordEncoder(), simpleMemberRepository)
		);
		customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
		customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
		return customAuthenticationFilter;
	}

	@Bean
	public CustomAuthorizationFilter customAuthorizationFilter() {
		return new CustomAuthorizationFilter(jwtFactory);
	}

	@Bean
	public FilterRegistrationBean<CustomAuthorizationFilter> filterRegistrationBean() {
		FilterRegistrationBean<CustomAuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(customAuthorizationFilter());
		filterRegistrationBean.setEnabled(false);
		return filterRegistrationBean;
	}

	@Bean
	public ApiExceptionHandlingFilter apiExceptionHandlingFilter() {
		return new ApiExceptionHandlingFilter();
	}

	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler(jwtFactory);
	}

	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationManager authenticationManager(
		PasswordEncoder passwordEncoder,
		SimpleMemberRepository simpleMemberRepository
	) {
		CustomAuthenticationProvider authenticationProvider =
			new CustomAuthenticationProvider(passwordEncoder, simpleMemberRepository);
		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);
		return providerManager;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("HEAD", "POST", "GET", "DELETE", "PUT"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
