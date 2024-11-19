package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "LogInterfaceProcess",schema = "dbo")
@NoArgsConstructor
public class LogInterfaceProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long IdLogParent;
    private String Activity;
    private String ActivityDescription;
    private String ActivityStatus;
    private Date LogDate;
    @Column(columnDefinition = "TEXT")
    private String IntegrationMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLogParent() {
        return IdLogParent;
    }

    public void setIdLogParent(Long idLogParent) {
        IdLogParent = idLogParent;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getActivityDescription() {
        return ActivityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        ActivityDescription = activityDescription;
    }

    public String getActivityStatus() {
        return ActivityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        ActivityStatus = activityStatus;
    }

    public Date getLogDate() {
        return LogDate;
    }

    public void setLogDate(Date logDate) {
        LogDate = logDate;
    }

    public String getIntegrationMessage() {
        return IntegrationMessage;
    }

    public void setIntegrationMessage(String integrationMessage) {
        IntegrationMessage = integrationMessage;
    }
}
