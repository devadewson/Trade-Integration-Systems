package com.maybank.integratorapp.model.mq.fxrate.response;

public class ResponseHeader {
    private String service;
    private String operation;
    private Credentials credentials;
    private String correlationID;
    private String targetSystem;
    private String sourceSystem;
    private String replyFormat;
    private String replyTarget;
    private String noRepair;
    private String noOverride;
    private String transactionControl;
    private String creationDate;
    private String groupingId;

    public String getService() { return service; }
    public void setService(String value) { this.service = value; }

    public String getOperation() { return operation; }
    public void setOperation(String value) { this.operation = value; }

    public Credentials getCredentials() { return credentials; }
    public void setCredentials(Credentials value) { this.credentials = value; }

    public String getCorrelationID() { return correlationID; }
    public void setCorrelationID(String value) { this.correlationID = value; }

    public String getTargetSystem() { return targetSystem; }
    public void setTargetSystem(String value) { this.targetSystem = value; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String value) { this.sourceSystem = value; }

    public String getReplyFormat() {
        return replyFormat;
    }

    public void setReplyFormat(String replyFormat) {
        this.replyFormat = replyFormat;
    }

    public String getReplyTarget() {
        return replyTarget;
    }

    public void setReplyTarget(String replyTarget) {
        this.replyTarget = replyTarget;
    }

    public String getNoRepair() {
        return noRepair;
    }

    public void setNoRepair(String noRepair) {
        this.noRepair = noRepair;
    }

    public String getNoOverride() {
        return noOverride;
    }

    public void setNoOverride(String noOverride) {
        this.noOverride = noOverride;
    }

    public String getTransactionControl() {
        return transactionControl;
    }

    public void setTransactionControl(String transactionControl) {
        this.transactionControl = transactionControl;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(String groupingId) {
        this.groupingId = groupingId;
    }
}
