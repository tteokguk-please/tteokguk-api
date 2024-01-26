package com.tteokguk.tteokguk.member.infra.persistence;

import com.tteokguk.tteokguk.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);

    // todo 성능 이슈를 고려한 리팩토링 필요
    @Query(value = "SELECT m FROM Member m ORDER BY RAND() LIMIT 1")
    Member findRandomUser();
}
