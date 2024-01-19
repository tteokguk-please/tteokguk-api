package com.tteokguk.tteokguk.member.infra.persistence;

import com.tteokguk.tteokguk.member.domain.SimpleMember;

public interface SimpleMemberRepository extends MemberRepository<SimpleMember> {
	boolean existsByEmail(String email);
}
