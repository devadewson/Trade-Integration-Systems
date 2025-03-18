package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogQueueDataService {
    @Autowired
    private LogQueueDataRepository repo;

    public LogQueueData findByCorrelationId(String correlationId){
        return repo.findByCorrelationId(correlationId);
    }

    public Optional<LogQueueData> findById(Long id){
        return repo.findById(id);
    }
}
