package com.maybank.integratorapp.model.mq.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseHeader {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "Service")
    private String service;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "Operation")
    private String operation;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "Status")
    private String status;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "Details")
    private Details details;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "CorrelationID")
    private String correlationID;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "TargetSystem")
    private String targetSystem;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "SourceSystem")
    private String sourceSystem;

    public String getService() { return service; }
    public void setService(String value) { this.service = value; }

    public String getOperation() { return operation; }
    public void setOperation(String value) { this.operation = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public Details getDetails() { return details; }
    public void setDetails(Details value) { this.details = value; }

    public String getCorrelationID() { return correlationID; }
    public void setCorrelationID(String value) { this.correlationID = value; }

    public String getTargetSystem() { return targetSystem; }
    public void setTargetSystem(String value) { this.targetSystem = value; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String value) { this.sourceSystem = value; }
}

