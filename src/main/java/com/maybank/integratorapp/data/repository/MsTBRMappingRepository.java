package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsTBR;
import com.maybank.integratorapp.data.entity.MsTBRMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsTBRMappingRepository extends CrudRepository<MsTBRMapping, Long> {
    @Query("select m from MsTBRMapping m where m.TBR_Id = ?1")
    List<MsTBRMapping> findByTBRId(Long id);

    @Query("SELECT m FROM MsTBRMapping m JOIN FETCH m.accountType WHERE m.tbr.id = :tbrId")
    List<MsTBRMapping> findByTBRIdWithAccountType(@Param("tbrId") Long tbrId);

    // Additional methods if needed
}