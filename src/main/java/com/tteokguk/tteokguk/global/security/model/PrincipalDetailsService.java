package com.tteokguk.tteokguk.global.security.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tteokguk.tteokguk.global.exception.BusinessException;
import com.tteokguk.tteokguk.member.exception.AuthError;
import com.tteokguk.tteokguk.member.infra.persistence.SimpleMemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final SimpleMemberRepository simpleMemberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return simpleMemberRepository.findByEmail(username)
			.map(PrincipalDetails::new)
			.orElseThrow(() -> new BusinessException(AuthError.NOT_FOUND_EMAIL));
	}
}
