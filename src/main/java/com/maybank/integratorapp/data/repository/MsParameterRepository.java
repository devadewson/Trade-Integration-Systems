package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsParameter;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.vw_tbr_mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MsParameterRepository extends CrudRepository<MsParameter, Long> {

    // Additional methods if needed
    @Query("select prmValue from MsParameter m where m.prmKey = ?1")
    String findValueByPrmKey(String prmKey);

    Page<MsParameter> findAll(Pageable pageable);

    @Query("SELECT t FROM MsParameter t WHERE LOWER(t.prmKey) LIKE LOWER(CONCAT('%', :prmKey, '%'))")
    Page<MsParameter> findByParamKey(@Param("prmKey") String prmKey, Pageable pageable);
}