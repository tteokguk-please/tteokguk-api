package com.tteokguk.tteokguk.support.infra.persistence;

import com.tteokguk.tteokguk.support.domain.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SupportRepository extends JpaRepository<Support, Long> {
}
