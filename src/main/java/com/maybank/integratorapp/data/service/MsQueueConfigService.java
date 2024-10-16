package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsQueueConfigService {
    @Autowired
    private MsQueueConfigRepository repo;

    public MsQueueConfig findByServiceName(String serviceName){
        return repo.findByServiceName(serviceName);
    }
}
