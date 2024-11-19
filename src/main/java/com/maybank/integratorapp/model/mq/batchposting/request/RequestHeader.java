package com.maybank.integratorapp.model.mq.batchposting.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.mq.singleposting.request.Credentials;

import java.io.Serializable;

//@XmlAccessorType(XmlAccessType.FIELD)
public class RequestHeader implements Serializable {
    //    @XmlElement(name = "Service")
    public RequestHeader(){
        this.credentials = new Credentials();
    }
    @JacksonXmlProperty(localName = "Service")
    private String service;
    @JacksonXmlProperty(localName = "Operation")

    private String operation;
    @JacksonXmlProperty(localName = "Credentials")

    private Credentials credentials;
    @JacksonXmlProperty(localName = "ReplyFormat")

    private String replyFormat;
    @JacksonXmlProperty(localName = "ReplyTarget")

    private String replyTarget;
    @JacksonXmlProperty(localName = "TargetSystem")

    private String targetSystem;
    @JacksonXmlProperty(localName = "SourceSystem")

    private String sourceSystem;
    @JacksonXmlProperty(localName = "NoRepair")

    private String noRepair;
    @JacksonXmlProperty(localName = "NoOverride")

    private String noOverride;
    @JacksonXmlProperty(localName = "CorrelationId")

    private String correlationID;
    @JacksonXmlProperty(localName = "TransactionControl")

    private String transactionControl;
    @JacksonXmlProperty(localName = "CreationDate")

    private String creationDate;
    @JacksonXmlProperty(localName = "GroupingId")

    private String groupingID;

    public String getService() { return service; }
    public void setService(String value) { this.service = value; }

    public String getOperation() { return operation; }
    public void setOperation(String value) { this.operation = value; }

    public Credentials getCredentials() { return credentials; }
    public void setCredentials(Credentials value) { this.credentials = value; }

    public String getReplyFormat() { return replyFormat; }
    public void setReplyFormat(String value) { this.replyFormat = value; }

    public String getReplyTarget() { return replyTarget; }
    public void setReplyTarget(String value) { this.replyTarget = value; }

    public String getTargetSystem() { return targetSystem; }
    public void setTargetSystem(String value) { this.targetSystem = value; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String value) { this.sourceSystem = value; }

    public String getNoRepair() { return noRepair; }
    public void setNoRepair(String value) { this.noRepair = value; }

    public String getNoOverride() { return noOverride; }
    public void setNoOverride(String value) { this.noOverride = value; }

    public String getCorrelationID() { return correlationID; }
    public void setCorrelationID(String value) { this.correlationID = value; }

    public String getTransactionControl() { return transactionControl; }
    public void setTransactionControl(String value) { this.transactionControl = value; }

    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String value) { this.creationDate = value; }

    public String getGroupingID() { return groupingID; }
    public void setGroupingID(String value) { this.groupingID = value; }
}

