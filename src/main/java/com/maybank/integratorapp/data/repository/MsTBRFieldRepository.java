package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsTBRField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MsTBRFieldRepository extends CrudRepository<MsTBRField, Long> {

    // Additional methods if needed
}
