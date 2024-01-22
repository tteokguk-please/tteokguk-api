package com.tteokguk.tteokguk.global.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.global.security.model.PrincipalDetails;
import com.tteokguk.tteokguk.member.domain.SimpleMember;
import com.tteokguk.tteokguk.member.exception.AuthError;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final PasswordEncoder encoder;
	private final SimpleMemberRepository simpleMemberRepository;

	@Override
	public Authentication authenticate(Authentication authentication) {
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

		String email = (String) token.getPrincipal();
		String password = (String) token.getCredentials();

		SimpleMember member = simpleMemberRepository.findByEmail(email)
			.orElseThrow(() -> new BusinessException(AuthError.BAD_EMAIL));

		if (!encoder.matches(password, member.getPassword()))
			throw new BusinessException(AuthError.BAD_PASSWORD);

		PrincipalDetails principal = new PrincipalDetails(member);
		return new UsernamePasswordAuthenticationToken(principal, password, principal.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
