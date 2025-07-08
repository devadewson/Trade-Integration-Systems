package com.maybank.integratorapp.model.mq.swiftin.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

//@XmlAccessorType(XmlAccessType.FIELD)
public class RequestHeader implements Serializable {
    //    @XmlElement(name = "Service",namespace = "urn:control.services.tiplus2.misys.com")
    @JacksonXmlProperty(localName = "Service",namespace = "urn:control.services.tiplus2.misys.com")
    private String service;
    @JacksonXmlProperty(localName = "Operation",namespace = "urn:control.services.tiplus2.misys.com")

    private String operation;
    @JacksonXmlProperty(localName = "Credentials",namespace = "urn:control.services.tiplus2.misys.com")

    private Credentials credentials;
    @JacksonXmlProperty(localName = "ReplyFormat",namespace = "urn:control.services.tiplus2.misys.com")

    private String replyFormat;
    @JacksonXmlProperty(localName = "ReplyTarget",namespace = "urn:control.services.tiplus2.misys.com")

    private String replyTarget;
    @JacksonXmlProperty(localName = "TargetSystem",namespace = "urn:control.services.tiplus2.misys.com")

    private String targetSystem;
    @JacksonXmlProperty(localName = "SourceSystem",namespace = "urn:control.services.tiplus2.misys.com")

    private String sourceSystem;
    @JacksonXmlProperty(localName = "NoRepair",namespace = "urn:control.services.tiplus2.misys.com")

    private String noRepair;
    @JacksonXmlProperty(localName = "NoOverride",namespace = "urn:control.services.tiplus2.misys.com")

    private String noOverride;
    @JacksonXmlProperty(localName = "CorrelationId",namespace = "urn:control.services.tiplus2.misys.com")

    private String correlationID;
    @JacksonXmlProperty(localName = "TransactionControl",namespace = "urn:control.services.tiplus2.misys.com")

    private String transactionControl;
    @JacksonXmlProperty(localName = "CreationDate",namespace = "urn:control.services.tiplus2.misys.com")

    private String creationDate;
    @JacksonXmlProperty(localName = "GroupingId",namespace = "urn:control.services.tiplus2.misys.com")

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

