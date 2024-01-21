package com.tteokguk.tteokguk.member.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.member.domain.Member;

@Transactional
public interface MemberRepository<T extends Member> extends JpaRepository<T, Long> {
	boolean existsByNickname(String nickname);
}
