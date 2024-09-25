package com.maybank.integratorapp.data.model;

import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LogQueueDataDTO {
    @Autowired
    private LogQueueDataRepository logDataRepository;

    public void AddNew(LogQueueData data){
        try{
            logDataRepository.save(data);
        }catch (Exception e){
            throw e;
        }
    }
}
