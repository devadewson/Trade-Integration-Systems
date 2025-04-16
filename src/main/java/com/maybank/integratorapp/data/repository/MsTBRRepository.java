package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.MsTBRField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsTBRRepository extends CrudRepository<MsTBR, Long> {
    Page<MsTBR> findAll(Pageable pageable);

    @Query("SELECT t FROM MsTBR t WHERE LOWER(t.TBRCode) LIKE LOWER(CONCAT('%', :TBRCode, '%'))")
    Page<MsTBR> findByTBRNoContainingIgnoreCase(@Param("TBRCode") String TBRCode, Pageable pageable);

    // Additional methods if needed
}
