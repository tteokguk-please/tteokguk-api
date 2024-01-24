package com.tteokguk.tteokguk.member.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.tteokguk.tteokguk.member.domain.Item;

@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {
}
