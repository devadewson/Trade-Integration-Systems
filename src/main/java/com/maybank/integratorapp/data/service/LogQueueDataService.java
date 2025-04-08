package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LogQueueDataService {
    @Autowired
    private LogQueueDataRepository repo;
    public Page<LogQueueData> getAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
    public Page<LogQueueData> searchByCorrelationId(String correlationId, Pageable pageable) {
        return repo.findByCorrelationIdContainingIgnoreCase(correlationId, pageable);
    }
    public LogQueueData findByCorrelationId(String correlationId){
        return repo.findByCorrelationId(correlationId);
    }

    public Optional<LogQueueData> findById(Long id){
        return repo.findById(id);
    }
}
