package com.maybank.integratorapp.model.restv2.CustomerInformation.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgBody {
    @JsonProperty("GCIFNo")
    private String gcifNo;


    public String getGcifNo() {
        return gcifNo;
    }

    public void setGcifNo(String gcifNo) {
        this.gcifNo = gcifNo;
    }

}
