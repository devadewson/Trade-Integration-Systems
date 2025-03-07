package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BancAssuranceDataRecord {
    @JsonProperty("GCIF")
    private String gcif;

    @JsonProperty("ProductAccNo")
    private String productAccNo;

    @JsonProperty("BalanceAmount")
    private String balanceAmount;

    public String getGcif() {
        return gcif;
    }

    public void setGcif(String gcif) {
        this.gcif = gcif;
    }

    public String getProductAccNo() {
        return productAccNo;
    }

    public void setProductAccNo(String productAccNo) {
        this.productAccNo = productAccNo;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
