package com.tteokguk.tteokguk.support.infra.persistence;

import com.tteokguk.tteokguk.support.domain.Support;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SupportRepository extends JpaRepository<Support, Long> {

    @Query("SELECT s FROM Support s GROUP BY s.supportedTteokguk.id HAVING s.sender.id = :id")
    List<Support> findSupportTteokguks(@Param("id") Long id, Pageable pageable);
}
