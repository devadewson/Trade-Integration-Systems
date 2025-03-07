package com.maybank.integratorapp.model.restv2.AccountList.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgBody {
    @JsonProperty("GCIFNo")
    private String gcifNo;
    @JsonProperty("RequestDataCategory")
    private String requestDataCategory;

    public String getGcifNo() {
        return gcifNo;
    }

    public void setGcifNo(String gcifNo) {
        this.gcifNo = gcifNo;
    }

    public String getRequestDataCategory() {
        return requestDataCategory;
    }

    public void setRequestDataCategory(String requestDataCategory) {
        this.requestDataCategory = requestDataCategory;
    }
}
