package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.LogQueueData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogQueueDataRepository extends CrudRepository<LogQueueData, Long> {

    // Additional methods if needed
    @Query("select m from LogQueueData m where m.correlationID = ?1")
    LogQueueData findByCorrelationId(String prmKey);

    Optional<LogQueueData> findByCorrelationID(String correlationId);
    Page<LogQueueData> findAll(Pageable pageable);
    @Query("SELECT t FROM LogQueueData t WHERE LOWER(t.correlationID) LIKE LOWER(CONCAT('%', :correlationId, '%'))")
    Page<LogQueueData> findByCorrelationIdContainingIgnoreCase(@Param("correlationId") String correlationId, Pageable pageable);

    @Query("SELECT t FROM LogQueueData t WHERE t.reqMessage LIKE LOWER(CONCAT('%', :transref, '%'))")
    Page<LogQueueData> findByTransactionIdContainingIgnoreCase(@Param("transref") String transref, Pageable pageable);

    @Query(value = "SELECT " +
            "REPLACE(origin,'MQ_queue:///',''), " +
            "DATEPART(HOUR, created_date) as hour, " +
            "COUNT(*) as messageCount " +
            "FROM log_queue_data " +
            "WHERE created_date >= :startDate AND created_date <= :endDate " +
            "and origin != 'Integrator Scheduler'" +
            "GROUP BY origin, DATEPART(HOUR, created_date) " +
            "ORDER BY origin, DATEPART(HOUR, created_date)",
            nativeQuery = true)
    List<Object[]> countMessagesByOriginAndHour(@Param("startDate") String startDate,
                                                @Param("endDate") String endDate);

}
