package com.tteokguk.tteokguk.global.security;

import com.tteokguk.tteokguk.global.exception.ApiExceptionHandlingFilter;
import com.tteokguk.tteokguk.global.security.filter.CustomAuthorizationFilter;
import com.tteokguk.tteokguk.global.security.matcher.CustomRequestMatcher;
import com.tteokguk.tteokguk.global.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiExceptionHandlingFilter apiExceptionHandlingFilter;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final CustomRequestMatcher customRequestMatcher;

    @Bean
    @Order(0)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        http.securityMatchers(matcher -> matcher.requestMatchers(
                        customRequestMatcher.authEndpoints(),
                        customRequestMatcher.mainPageEndPoints(),
                        customRequestMatcher.healthEndpoints(),
                        customRequestMatcher.serverInfoEndpoints(),
                        customRequestMatcher.errorEndpoints(),
                        customRequestMatcher.userEndpoints()
                ))
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterBefore(apiExceptionHandlingFilter, UsernamePasswordAuthenticationFilter.class);

        return commonHttpSecurity(http).build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain anyRequestFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(customRequestMatcher.tempUserEndpoints()).hasRole("TEMP_USER")
                        .requestMatchers(customRequestMatcher.secretTteokgukEndpoints()).hasAnyRole("ANONYMOUS", "USER")
                        .anyRequest().hasRole("USER"))
                .addFilterAfter(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(apiExceptionHandlingFilter, UsernamePasswordAuthenticationFilter.class);

        return commonHttpSecurity(http).build();
    }

    private HttpSecurity commonHttpSecurity(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> corsConfigurationSource())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(CustomAuthenticationProvider authenticationProvider) {
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
