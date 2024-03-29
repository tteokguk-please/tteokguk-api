package com.tteokguk.tteokguk.member.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tteokguk.tteokguk.member.domain.Member;
import com.tteokguk.tteokguk.member.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByMemberAndUserAgent(Member member, String userAgent);
	Optional<RefreshToken> findByTokenAndUserAgent(String token, String userAgent);
}
