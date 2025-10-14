package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.controller.DashboardController;
import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import com.maybank.integratorapp.data.entity.LogQueueData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface LogQueueDataRepository extends CrudRepository<LogQueueData, Long>, JpaSpecificationExecutor<LogQueueData> {

    // Additional methods if needed
    @Query("select m from LogQueueData m where m.correlationID = ?1")
    LogQueueData findByCorrelationId(String prmKey);

    Optional<LogQueueData> findByCorrelationID(String correlationId);
    Page<LogQueueData> findAll(Pageable pageable);
    @Query("SELECT t FROM LogQueueData t WHERE LOWER(t.correlationID) LIKE LOWER(CONCAT('%', :correlationId, '%'))")
    Page<LogQueueData> findByCorrelationIdContainingIgnoreCase(@Param("correlationId") String correlationId, Pageable pageable);

/*    @Query(value = "SELECT t FROM LogQueueData t WHERE " +
            "CONTAINS(t.reqMessage, :transref) = true",
            countQuery = "SELECT COUNT(t) FROM LogQueueData t WHERE " +
                    "CONTAINS(t.reqMessage, :transref) = true")
    Page<LogQueueData> findByTransactionIdContainingIgnoreCase(@Param("transref") String transref, Pageable pageable);*/

//    @Query("SELECT t FROM LogQueueData t WHERE t.reqMessage LIKE LOWER(CONCAT('%', :transref, '%'))")
//    Page<LogQueueData> findByTransactionIdContainingIgnoreCase(@Param("transref") String transref, Pageable pageable);

    @Query("SELECT t FROM LogQueueData t WHERE t.reqMessage LIKE LOWER(CONCAT('%', :transref, '%'))")
    Page<LogQueueData> findByTransactionIdContainingIgnoreCase(@Param("transref") String transref, Pageable pageable);
    @Query("SELECT t FROM LogQueueData t WHERE t.relatedTransRef = ?1")
    List<LogQueueData> findByTransactionIdContainingIgnoreCase(String transref);
    @Query("SELECT t FROM LogQueueData t WHERE t.origin LIKE LOWER(CONCAT('%', :queueorigin, '%'))")
    Page<LogQueueData> findByQueueOriginContainingIgnoreCase(@Param("queueorigin") String queueorigin, Pageable pageable);

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
    @Query(value = "SELECT REPLACE(d.origin,'MQ_queue:///','') as queue_name, "+
            "COUNT(d.id) as total_messages," +
            "AVG(DATEDIFF(second, d.created_date, d.delivery_date)) as avg_processing_seconds," +
            "MAX(DATEDIFF(second, d.created_date, d.delivery_date)) as max_processing_seconds," +
            "MIN(DATEDIFF(second, d.created_date, d.delivery_date)) as min_processing_seconds " +
            "FROM log_queue_data d " +
            "join ms_queue_config c on d.origin = 'MQ_queue:///'+c.request_queue_name " +
            "WHERE delivery_date IS NOT NULL " +
            "and CAST(created_date AS DATE) = CAST(GETDATE() AS DATE) "+
            "GROUP BY d.origin "
            ,nativeQuery = true)
    List<Object[]> getTodayQueueStats();
}
