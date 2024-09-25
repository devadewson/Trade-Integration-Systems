package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.MsTBRField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsTBRRepository extends CrudRepository<MsTBR, Long> {

    // Additional methods if needed
}
