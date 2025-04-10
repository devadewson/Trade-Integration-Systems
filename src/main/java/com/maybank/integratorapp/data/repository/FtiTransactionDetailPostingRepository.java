package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPosting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FtiTransactionDetailPostingRepository extends CrudRepository<FtiTransactionDetailPosting, Long> {
    List<FtiTransactionDetailPosting> findByIdGroup(Long IdGroup);
}