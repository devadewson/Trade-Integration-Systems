package com.maybank.integratorapp.model.mq.facilitiesdetails.response;

public class ResponseHeader {
    private String service;
    private String operation;
    private String status;
    private Details details;
    private String correlationID;
    private String targetSystem;
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

