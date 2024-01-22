package com.tteokguk.tteokguk.member.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tteokguk.tteokguk.member.domain.SimpleMember;

public interface SimpleMemberRepository extends JpaRepository<SimpleMember, Long> {
	boolean existsByEmail(String email);

	Optional<SimpleMember> findByEmail(String email);

	boolean existsByNickname(String nickname);
}
