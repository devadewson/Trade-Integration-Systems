package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPosting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FtiTransactionDetailPostingRepository extends CrudRepository<FtiTransactionDetailPosting, Long> {
    @Query("SELECT t FROM FtiTransactionDetailPosting t WHERE t.idGroup = ?1")
    List<FtiTransactionDetailPosting> findByIdGroup(@Param("IdGroup") Long IdGroup);
}