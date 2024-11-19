package com.maybank.integratorapp.data.service;

import com.maybank.integratorapp.data.entity.LogInterfaceProcess;
import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.repository.FtiAccountTypeRepository;
import com.maybank.integratorapp.data.repository.LogInterfaceProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class LogInterfaceProcessService {
    @Autowired
    private LogInterfaceProcessRepository repo;

    private Long IdLogParent;

    public void SetLogParent(long idLogParent) {
        this.IdLogParent= idLogParent;
    }
    public void Log(String activity, String description) {
        LogInterfaceProcess logEntry = new LogInterfaceProcess();
        logEntry.setIdLogParent(this.IdLogParent);
        logEntry.setActivity(activity);
        logEntry.setActivityDescription(description);
        logEntry.setLogDate(new Date());
        repo.save(logEntry);
    }
    public void Log(String activity, String description,String status) {
        LogInterfaceProcess logEntry = new LogInterfaceProcess();
        logEntry.setIdLogParent(this.IdLogParent);
        logEntry.setActivity(activity);
        logEntry.setActivityDescription(description);
        logEntry.setActivityStatus(status);
        logEntry.setLogDate(new Date());
        repo.save(logEntry);
    }
    public void Log(String activity, String description,String status,String message) {
        LogInterfaceProcess logEntry = new LogInterfaceProcess();
        logEntry.setIdLogParent(this.IdLogParent);
        logEntry.setActivity(activity);
        logEntry.setActivityDescription(description);
        logEntry.setActivityStatus(status);
        logEntry.setIntegrationMessage(message);
        logEntry.setLogDate(new Date());
        repo.save(logEntry);
    }
}
