package com.maybank.integratorapp.model.rest.compositetbr.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ChannelHeader {

    @JsonProperty("messageID")
    private String messageID;

    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("channelID")
    private String channelID;

//    @JsonProperty("clientSupervisorID")
//    private String clientSupervisorID;

//    @JsonProperty("clientUserID")
//    private String clientUserID;

    @JsonProperty("reference")
    private String reference;

//    @JsonProperty("reversalsequenceno")
//    private String reversalSequenceNo;

    @JsonProperty("sequenceno")
    private String sequenceNo;

    @JsonProperty("transactiondate")
    private String transactionDate;

    @JsonProperty("transactiontime")
    private String transactionTime;

    // Getters and setters

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
}
