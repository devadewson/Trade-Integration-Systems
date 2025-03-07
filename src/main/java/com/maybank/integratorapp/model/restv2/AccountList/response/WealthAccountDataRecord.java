package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WealthAccountDataRecord {
    @JsonProperty("ProductAccNo")
    private String productAccNo;

    @JsonProperty("ProductCategory")
    private String productCategory;

    @JsonProperty("Balance")
    private String balance;

    public String getProductAccNo() {
        return productAccNo;
    }

    public void setProductAccNo(String productAccNo) {
        this.productAccNo = productAccNo;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
