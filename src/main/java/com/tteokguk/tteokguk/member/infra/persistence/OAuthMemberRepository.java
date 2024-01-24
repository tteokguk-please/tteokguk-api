package com.tteokguk.tteokguk.member.infra.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.member.domain.OAuthMember;
import com.tteokguk.tteokguk.member.domain.ProviderType;

@Transactional
public interface OAuthMemberRepository extends JpaRepository<OAuthMember, Long> {
	Optional<OAuthMember> findByProviderTypeAndResourceId(ProviderType providerType, String resourceId);
}
