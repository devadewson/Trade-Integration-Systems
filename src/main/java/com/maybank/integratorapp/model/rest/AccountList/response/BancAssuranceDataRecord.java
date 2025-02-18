package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BancAssuranceDataRecord {

    @JsonProperty("GCIF")
    private String gcif;

    @JsonProperty("ProductAccNo")
    private String productAccNo;

    @JsonProperty("AsOfDate")
    private String asOfDate;

    @JsonProperty("PolicyNumber")
    private String policyNumber;

    @JsonProperty("PayorName")
    private String payorName;

    @JsonProperty("PolicyHolder")
    private String policyHolder;

    @JsonProperty("ProductName")
    private String productName;

    @JsonProperty("FundName")
    private String fundName;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("BalanceUnit")
    private String balanceUnit;

    @JsonProperty("NAVDate")
    private String navDate;

    @JsonProperty("NAV")
    private String nav;

    @JsonProperty("BalanceAmount")
    private String balanceAmount;

    // Getters and Setters
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

    public String getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(String asOfDate) {
        this.asOfDate = asOfDate;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPayorName() {
        return payorName;
    }

    public void setPayorName(String payorName) {
        this.payorName = payorName;
    }

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBalanceUnit() {
        return balanceUnit;
    }

    public void setBalanceUnit(String balanceUnit) {
        this.balanceUnit = balanceUnit;
    }

    public String getNavDate() {
        return navDate;
    }

    public void setNavDate(String navDate) {
        this.navDate = navDate;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}