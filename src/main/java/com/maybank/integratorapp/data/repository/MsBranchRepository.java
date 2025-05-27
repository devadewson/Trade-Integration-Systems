package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.entity.MsBranch;
import com.maybank.integratorapp.data.entity.MsTBRMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsBranchRepository extends CrudRepository<MsBranch, Long> {
    @Query("select m from MsBranch m where m.branchCode = ?1")
    MsBranch findByBranchCode(String code);
}
