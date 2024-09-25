package com.maybank.integratorapp.data.repository;

import com.maybank.integratorapp.data.entity.MsQueueConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MsQueueConfigRepository extends CrudRepository<MsQueueConfig, Long> {

    @Query("select m from MsQueueConfig m where m.ServiceName = ?1")
    MsQueueConfig findByServiceName(String serviceName);
// Additional methods if needed
}
