package com.maybank.integratorapp.model.rest.AccountInquiry.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelHeader {
    @JsonProperty("messageID")
    private String messageID;

    @JsonProperty("branchCode")
    private String branchCode;

    @JsonProperty("channelID")
    private String channelID;

    @JsonProperty("clientSupervisorID")
    private String clientSupervisorID;

    @JsonProperty("clientUserID")
    private String clientUserID;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("reversalsequenceno")
    private String reversalSequenceNo;

    @JsonProperty("sequenceno")
    private String sequenceNo;

    @JsonProperty("transactiondate")
    private String transactionDate;

    @JsonProperty("transactiontime")
    private String transactionTime;

    // Getters and setters

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setClientUserID(String clientUserID) {
        this.clientUserID = clientUserID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public void setClientSupervisorID(String clientSupervisorID) {
        this.clientSupervisorID = clientSupervisorID;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getReference() {
        return reference;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getClientUserID() {
        return clientUserID;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getClientSupervisorID() {
        return clientSupervisorID;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getReversalSequenceNo() {
        return reversalSequenceNo;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setReversalSequenceNo(String reversalSequenceNo) {
        this.reversalSequenceNo = reversalSequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
}
