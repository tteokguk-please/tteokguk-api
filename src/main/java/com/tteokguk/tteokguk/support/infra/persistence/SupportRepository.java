package com.tteokguk.tteokguk.support.infra.persistence;

import com.tteokguk.tteokguk.support.domain.Support;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SupportRepository extends JpaRepository<Support, Long> {

    @Query("""
                SELECT DISTINCT s 
                FROM Support s JOIN FETCH Tteokguk t 
                ON s.supportedTteokguk.id = t.id 
                WHERE t.deleted = false
                AND s.sender.id = :id
                ORDER BY s.supportedTteokguk.updatedDate desc 
            """)
    List<Support> findSupportTteokguks(Long id, Pageable pageable);

    @Query("""
                SELECT s 
                FROM Support s JOIN FETCH Tteokguk t 
                ON s.supportedTteokguk.id = t.id 
                WHERE t.deleted = false 
                GROUP BY s.supportedTteokguk.id 
                HAVING s.sender.id = :id
            """)
    List<Support> findSupportTteokguksTemp(Long id, Pageable pageable);
}
