package com.maybank.integratorapp.service;

import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueConfigService {

    @Autowired
    private NewDynamicJmsListenerService jmsListenerService;

    @Autowired
    private MsQueueConfigRepository queueConfigRepository;

    @Scheduled(fixedDelay = 60000) // Run every 1 minutes
    public void checkForConfigUpdates() {
        List<MsQueueConfig> queueConfigs = (List<MsQueueConfig>) queueConfigRepository.findAll();
        System.out.println("Refreshing Queue configuration...");
        jmsListenerService.configureListeners(queueConfigs);
        System.out.println("Completed refreshing Queue configuration");
    }
}

