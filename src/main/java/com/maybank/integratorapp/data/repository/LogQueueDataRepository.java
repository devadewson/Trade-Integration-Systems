package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.LogQueueData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface LogQueueDataRepository extends CrudRepository<LogQueueData, Long> {

    // Additional methods if needed
    @Query("select m from LogQueueData m where m.correlationID = ?1")
    LogQueueData findByCorrelationId(String prmKey);
}
