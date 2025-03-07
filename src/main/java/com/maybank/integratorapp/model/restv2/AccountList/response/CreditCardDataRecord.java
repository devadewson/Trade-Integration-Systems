package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardDataRecord {
    @JsonProperty("CreditCardNo")
    private String creditCardNo;

    @JsonProperty("Status")
    private String status;

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
