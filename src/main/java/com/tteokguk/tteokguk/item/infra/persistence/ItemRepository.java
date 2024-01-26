package com.tteokguk.tteokguk.item.infra.persistence;

import com.tteokguk.tteokguk.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ItemRepository extends JpaRepository<Item, Long> {
}
