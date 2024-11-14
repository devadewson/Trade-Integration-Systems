package com.maybank.integratorapp.model.soap.limit.XL41;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ChannelHeader {

    @JacksonXmlProperty(localName = "messageID")
    private String messageID;

    @JacksonXmlProperty(localName = "branchCode")
    private String branchCode;

    @JacksonXmlProperty(localName = "channelID")
    private String channelID;

    @JacksonXmlProperty(localName = "clientSupervisorID")
    private String clientSupervisorID;

    @JacksonXmlProperty(localName = "clientUserID")
    private String clientUserID;

    @JacksonXmlProperty(localName = "reference")
    private String reference;

    @JacksonXmlProperty(localName = "reversalsequenceno")
    private String reversalSequenceNo;

    @JacksonXmlProperty(localName = "sequenceno")
    private String sequenceNo;

    @JacksonXmlProperty(localName = "transactiondate")
    private String transactionDate;

    @JacksonXmlProperty(localName = "transactiontime")
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
