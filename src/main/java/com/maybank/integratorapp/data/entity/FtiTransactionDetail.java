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

    private String additionalInfo1;
    private String additionalInfo2;
    private String additionalInfo3;
    private String additionalInfo4;
    private String additionalInfo5;


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

    public String getAdditionalInfo1() {
        return additionalInfo1;
    }

    public void setAdditionalInfo1(String additionalInfo1) {
        this.additionalInfo1 = additionalInfo1;
    }

    public String getAdditionalInfo2() {
        return additionalInfo2;
    }

    public void setAdditionalInfo2(String additionalInfo2) {
        this.additionalInfo2 = additionalInfo2;
    }

    public String getAdditionalInfo3() {
        return additionalInfo3;
    }

    public void setAdditionalInfo3(String additionalInfo3) {
        this.additionalInfo3 = additionalInfo3;
    }

    public String getAdditionalInfo4() {
        return additionalInfo4;
    }

    public void setAdditionalInfo4(String additionalInfo4) {
        this.additionalInfo4 = additionalInfo4;
    }

    public String getAdditionalInfo5() {
        return additionalInfo5;
    }

    public void setAdditionalInfo5(String additionalInfo5) {
        this.additionalInfo5 = additionalInfo5;
    }
}
