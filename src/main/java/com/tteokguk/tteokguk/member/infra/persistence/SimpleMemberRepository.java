package com.tteokguk.tteokguk.member.infra.persistence;

import java.util.Optional;

import com.tteokguk.tteokguk.member.domain.SimpleMember;

public interface SimpleMemberRepository extends MemberRepository<SimpleMember> {
	boolean existsByEmail(String email);

	Optional<SimpleMember> findByEmail(String email);
}
