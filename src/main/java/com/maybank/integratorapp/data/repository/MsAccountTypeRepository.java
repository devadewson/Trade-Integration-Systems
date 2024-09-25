package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.entity.MsTBRField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsAccountTypeRepository extends CrudRepository<MsAccountType, Long> {

    // Additional methods if needed
}
