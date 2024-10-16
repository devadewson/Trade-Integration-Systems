package com.maybank.integratorapp.model.rest.AccountList.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountListRequest {
    @JsonProperty("GCIFNo")
    private String GCIFNo;

    public String getGCIFNo() {
        return GCIFNo;
    }

    public void setGCIFNo(String GCIFNo) {
        this.GCIFNo = GCIFNo;
    }
}
