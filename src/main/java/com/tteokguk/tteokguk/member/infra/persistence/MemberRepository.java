package com.tteokguk.tteokguk.member.infra.persistence;

import com.tteokguk.tteokguk.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);

    @Query(value = "SELECT m FROM Member m WHERE m.deleted = false AND m.role = 'ROLE_USER' ORDER BY RAND() LIMIT 1")
    Member findRandomUser();

    @Query(value = "SELECT m FROM Member m WHERE m.deleted = :deleted AND m.role = 'ROLE_USER' AND m.id = :id")
    Optional<Member> findByIdAndDeleted(Long id, boolean deleted);

    Optional<Member> findByNickname(String nickname);

    Page<Member> findByNicknameStartingWithAndDeleted(String nickname, Pageable pageable, boolean deleted);

    List<Member> findAllByNicknameStartingWithAndDeleted(String nickname, boolean deleted);
}
