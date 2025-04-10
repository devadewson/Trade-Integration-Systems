package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransactionDetailPostingGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FtiTransactionDetailPostingGroupRepository extends CrudRepository<FtiTransactionDetailPostingGroup, Long> {

}