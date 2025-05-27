package com.maybank.integratorapp.model.mq.customersearch.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseHeader {
    @JacksonXmlProperty(localName = "Service",namespace = "urn:control.services.tiplus2.misys.com")

    private String service;
    @JacksonXmlProperty(localName = "Operation",namespace = "urn:control.services.tiplus2.misys.com")
    private String operation;
    @JacksonXmlProperty(localName = "Status",namespace = "urn:control.services.tiplus2.misys.com")
    private String status;
    @JacksonXmlProperty(localName = "Details",namespace = "urn:control.services.tiplus2.misys.com")
    private Details details;
    @JacksonXmlProperty(localName = "CorrelationId",namespace = "urn:control.services.tiplus2.misys.com")
    private String correlationID;
    @JacksonXmlProperty(localName = "TargetSystem",namespace = "urn:control.services.tiplus2.misys.com")

    private String targetSystem;
    @JacksonXmlProperty(localName = "SourceSystem",namespace = "urn:control.services.tiplus2.misys.com")

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

