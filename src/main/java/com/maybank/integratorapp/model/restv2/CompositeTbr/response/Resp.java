package com.maybank.integratorapp.model.restv2.CompositeTbr.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resp {
    @JsonProperty("Sequence")
    private String sequence;

    @JsonProperty("UUID")
    private String uuid;

    @JsonProperty("TBRCode")
    private String tbrCode;

    @JsonProperty("StatusByte")
    private String statusByte;

    @JsonProperty("UserID")
    private String userID;

    @JsonProperty("TerminalID")
    private String terminalID;

    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("ResponseData")
    private String responseData;

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTbrCode() {
        return tbrCode;
    }

    public void setTbrCode(String tbrCode) {
        this.tbrCode = tbrCode;
    }

    public String getStatusByte() {
        return statusByte;
    }

    public void setStatusByte(String statusByte) {
        this.statusByte = statusByte;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
