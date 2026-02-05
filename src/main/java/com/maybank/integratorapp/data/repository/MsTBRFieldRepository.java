package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsTBRField;
import com.maybank.integratorapp.data.entity.MsTBRMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface MsTBRFieldRepository extends CrudRepository<MsTBRField, Long> {

    // Additional methods if needed
    @Query("select m from MsTBRField m join MsTBR tbr on m.TBR_Id = tbr.id where tbr.TBRCode = ?1")
    ArrayList<MsTBRField> findFieldsByTbrCode(String prmKey);
    @Query("select m from MsTBRField m join MsTBR tbr on m.TBR_Id = tbr.id where tbr.TBRName like %?1%")
    ArrayList<MsTBRField> findFieldsByTbrName(String prmKey);
    @Query("SELECT m FROM MsTBRField m JOIN FETCH m.tbr WHERE m.tbr.id = :tbrId")
    List<MsTBRField> findFieldsByTBRId(@Param("tbrId") Long tbrId);
}
