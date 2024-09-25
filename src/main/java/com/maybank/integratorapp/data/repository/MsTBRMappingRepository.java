package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.MsTBRMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsTBRMappingRepository extends CrudRepository<MsTBRMapping, Long> {

    // Additional methods if needed
}