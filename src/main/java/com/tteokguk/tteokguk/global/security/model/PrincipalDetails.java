package com.tteokguk.tteokguk.global.security.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tteokguk.tteokguk.member.domain.Member;

import lombok.Getter;

@Getter
public class PrincipalDetails implements UserDetails {

	private final Member member;

	public PrincipalDetails(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(() -> member.getRole().name());
		return authorities;
	}

	public Long getId() {
		return member.getId();
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return String.valueOf(member.getId());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
