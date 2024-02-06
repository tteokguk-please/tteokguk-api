package com.tteokguk.tteokguk.tteokguk.infra.persistence;

import com.tteokguk.tteokguk.tteokguk.domain.Tteokguk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface TteokgukRepository extends JpaRepository<Tteokguk, Long> {

    Optional<Tteokguk> findByIdAndDeleted(Long id, boolean deleted);

    @Query("SELECT t FROM Tteokguk t WHERE t.access = true and t.member.deleted = false and t.completion = false ORDER BY t.id DESC")
    List<Tteokguk> findNewTteokguks(Pageable pageable);

    @Query("SELECT t FROM Tteokguk t WHERE t.access = true and t.member.deleted = false and t.completion = true ORDER BY t.id DESC")
    List<Tteokguk> findCompletionTteokguks(Pageable pageable);
}
