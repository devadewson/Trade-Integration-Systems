package com.maybank.integratorapp.model.restv2.CompositeTbr.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgHeader {
    @JsonProperty("MsgID")
    private String msgID;

    @JsonProperty("Ver")
    private String ver;

    @JsonProperty("SvcID")
    private String svcID;

    @JsonProperty("TxnCode")
    private String txnCode;

    @JsonProperty("Env")
    private String env;

    @JsonProperty("BranchCode")
    private String branchCode;

    @JsonProperty("SpvOverride")
    private String spvOverride;

    @JsonProperty("ClientSpvID")
    private String clientSpvID;

    @JsonProperty("userid")
    private String userid;

    @JsonProperty("password")
    private String password;

    @JsonProperty("terminalname")
    private String terminalname;

    @JsonProperty("StatusCode")
    private String statusCode;

    @JsonProperty("StatusDesc")
    private String statusDesc;

    @JsonProperty("E2ETime")
    private String e2eTime;

    @JsonProperty("TrasactionDetail")
    private TransactionDetail trasactionDetail;

    @JsonProperty("AdditionalStatusCodes")
    private AdditionalStatusCode[] additionalStatusCodes;

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getSvcID() {
        return svcID;
    }

    public void setSvcID(String svcID) {
        this.svcID = svcID;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getSpvOverride() {
        return spvOverride;
    }

    public void setSpvOverride(String spvOverride) {
        this.spvOverride = spvOverride;
    }

    public String getClientSpvID() {
        return clientSpvID;
    }

    public void setClientSpvID(String clientSpvID) {
        this.clientSpvID = clientSpvID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTerminalname() {
        return terminalname;
    }

    public void setTerminalname(String terminalname) {
        this.terminalname = terminalname;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getE2eTime() {
        return e2eTime;
    }

    public void setE2eTime(String e2eTime) {
        this.e2eTime = e2eTime;
    }

    public TransactionDetail getTrasactionDetail() {
        return trasactionDetail;
    }

    public void setTrasactionDetail(TransactionDetail trasactionDetail) {
        this.trasactionDetail = trasactionDetail;
    }

    public AdditionalStatusCode[] getAdditionalStatusCodes() {
        return additionalStatusCodes;
    }

    public void setAdditionalStatusCodes(AdditionalStatusCode[] additionalStatusCodes) {
        this.additionalStatusCodes = additionalStatusCodes;
    }
}
