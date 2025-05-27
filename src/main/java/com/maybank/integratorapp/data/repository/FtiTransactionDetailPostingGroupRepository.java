package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPosting;
import com.maybank.integratorapp.data.entity.FtiTransactionDetailPostingGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FtiTransactionDetailPostingGroupRepository extends CrudRepository<FtiTransactionDetailPostingGroup, Long> {
    @Query("SELECT t FROM FtiTransactionDetailPostingGroup t WHERE t.DetailId = ?1")
    List<FtiTransactionDetailPostingGroup> findByDetailId(Long DetailId);
}