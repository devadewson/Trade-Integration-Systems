package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.vw_tbr_mapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VwTbrMappingRepository extends CrudRepository<vw_tbr_mapping, Long> {

    // Additional methods if needed
}