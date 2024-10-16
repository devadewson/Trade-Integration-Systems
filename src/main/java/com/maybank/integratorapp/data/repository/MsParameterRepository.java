package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsParameter;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.entity.vw_tbr_mapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MsParameterRepository extends CrudRepository<MsParameter, Long> {

    // Additional methods if needed
    @Query("select prmValue from MsParameter m where m.prmKey = ?1")
    String findValueByPrmKey(String prmKey);
}