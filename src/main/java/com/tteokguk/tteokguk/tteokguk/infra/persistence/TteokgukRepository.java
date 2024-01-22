package com.tteokguk.tteokguk.tteokguk.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;

@Transactional
public interface TteokgukRepository extends JpaRepository<Tteokguk, Long> {
}
