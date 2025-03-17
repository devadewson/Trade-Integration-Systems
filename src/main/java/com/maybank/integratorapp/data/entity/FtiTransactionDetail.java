package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "FtiTransactionDetail",schema = "dbo")
@NoArgsConstructor
public class FtiTransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long headerId;
    private Long transMessageLogId;
    private String transName;
    private String ftiEvent;
    private String coreSysName;
    private String coreSysStatus;
    private String coreSysMessage;
    private Date createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHeaderId() {
        return headerId;
    }

    public void setHeaderId(Long headerId) {
        this.headerId = headerId;
    }

    public Long getTransMessageLogId() {
        return transMessageLogId;
    }

    public void setTransMessageLogId(Long transMessageLogId) {
        this.transMessageLogId = transMessageLogId;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public String getFtiEvent() {
        return ftiEvent;
    }

    public void setFtiEvent(String ftiEvent) {
        this.ftiEvent = ftiEvent;
    }

    public String getCoreSysName() {
        return coreSysName;
    }

    public void setCoreSysName(String coreSysName) {
        this.coreSysName = coreSysName;
    }

    public String getCoreSysStatus() {
        return coreSysStatus;
    }

    public void setCoreSysStatus(String coreSysStatus) {
        this.coreSysStatus = coreSysStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCoreSysMessage() {
        return coreSysMessage;
    }

    public void setCoreSysMessage(String coreSysMessage) {
        this.coreSysMessage = coreSysMessage;
    }
}
