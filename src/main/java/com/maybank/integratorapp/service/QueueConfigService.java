package com.maybank.integratorapp.service;

import com.maybank.integratorapp.IntegratorAppMain;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueConfigService {

//    @Autowired
//    private NewDynamicJmsListenerService jmsListenerService;
    @Autowired
    private JmsListenerService jmsListenerService;
    @Autowired
    private MsQueueConfigRepository queueConfigRepository;

//    @Autowired
//    private MQListenerManager mqListenerManager;

//    @Scheduled(fixedDelay = 60000) // Run every 1 minutes
//    @PostConstruct
    @EventListener(ApplicationReadyEvent.class)
    public void checkForConfigUpdates() {
        List<MsQueueConfig> queueConfigs = (List<MsQueueConfig>) queueConfigRepository.findAll();
        System.out.println("Refreshing Queue configuration...");
//        List<String> queueNames = queueConfigs.stream().map(x-> ).toList();
//        List<String> queueNames = queueConfigs.stream()
//                .filter(x->x.getServiceName().equals("AccountInquiry"))
//                .map(MsQueueConfig::getRequest_Queue_Name) // Assuming the getter method follows Java conventions
//                .collect(Collectors.toList());
//        mqListenerManager.registerListeners(queueNames);
        jmsListenerService.configureListeners(queueConfigs);
        System.out.println("Completed refreshing Queue configuration");
    }
}

