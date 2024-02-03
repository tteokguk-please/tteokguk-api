package com.tteokguk.tteokguk.member.infra.persistence;

import java.util.List;
import java.util.Optional;

import com.tteokguk.tteokguk.member.domain.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);

    // todo 성능 이슈를 고려한 리팩토링 필요
    @Query(value = "SELECT m FROM Member m ORDER BY RAND() LIMIT 1")
    Member findRandomUser();

    Optional<Member> findByNickname(String nickname);

    Page<Member> findByNicknameStartingWith(String nickname, Pageable pageable);

    List<Member> findAllByNicknameStartingWith(String nickname);
}
