package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsTBRField;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface MsTBRFieldRepository extends CrudRepository<MsTBRField, Long> {

    // Additional methods if needed
    @Query("select m from MsTBRField m join MsTBR tbr on m.TBR_Id = tbr.id where tbr.TBRCode = ?1")
    ArrayList<MsTBRField> findFieldsByTbrCode(String prmKey);
}
