package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FtiTransactionRepository extends CrudRepository<FtiTransaction, Long> {
    Optional<FtiTransaction> findByMasterRefNo(String masterRefNo);
    Page<FtiTransaction> findAll(Pageable pageable);
    // Default sorting by ID descending
    Page<FtiTransaction> findAllByOrderByIdDesc(Pageable pageable);

    // Search by masterRefNo (case-insensitive)
    @Query("SELECT t FROM FtiTransaction t WHERE LOWER(t.masterRefNo) LIKE LOWER(CONCAT('%', :masterRefNo, '%'))")
    Page<FtiTransaction> findByMasterRefNoContainingIgnoreCase(@Param("masterRefNo") String masterRefNo, Pageable pageable);
}
