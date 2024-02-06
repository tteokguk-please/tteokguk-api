package com.tteokguk.tteokguk.member.infra.persistence;

import com.tteokguk.tteokguk.member.domain.SimpleMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimpleMemberRepository extends JpaRepository<SimpleMember, Long> {
    boolean existsByEmail(String email);

    Optional<SimpleMember> findByIdAndDeleted(Long id, boolean deleted);

    Optional<SimpleMember> findByEmail(String email);

    boolean existsByNickname(String nickname);
}
