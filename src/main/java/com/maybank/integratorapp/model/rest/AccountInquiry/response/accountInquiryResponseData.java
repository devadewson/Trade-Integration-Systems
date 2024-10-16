package com.maybank.integratorapp.model.rest.AccountInquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class accountInquiryResponseData {
    @JsonProperty("AccountName")
    private String AccountName;
    @JsonProperty("CIF_No")
    private String CIF_No;
    @JsonProperty("Balance")
    private String Balance;
    @JsonProperty("MinimumBalance")
    private String MinimumBalance;
    @JsonProperty("TodayClearing")
    private String TodayClearing;
    @JsonProperty("YesterdayClearing")
    private String YesterdayClearing;
    @JsonProperty("YesterdayClearingStatus")
    private String YesterdayClearingStatus;
    @JsonProperty("HoldAmount")
    private String HoldAmount;
    @JsonProperty("Plafond")
    private String Plafond;
    @JsonProperty("AccountStatus")
    private String AccountStatus;
    @JsonProperty("CleanUpStatus")
    private String CleanUpStatus;
    @JsonProperty("AccountType")
    private String AccountType;
    @JsonProperty("Address1")
    private String Address1;
    @JsonProperty("Address2")
    private String Address2;
    @JsonProperty("Address3")
    private String Address3;
    @JsonProperty("Address4")
    private String Address4;

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getCIF_No() {
        return CIF_No;
    }

    public void setCIF_No(String CIF_No) {
        this.CIF_No = CIF_No;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getMinimumBalance() {
        return MinimumBalance;
    }

    public void setMinimumBalance(String minimumBalance) {
        MinimumBalance = minimumBalance;
    }

    public String getTodayClearing() {
        return TodayClearing;
    }

    public void setTodayClearing(String todayClearing) {
        TodayClearing = todayClearing;
    }

    public String getYesterdayClearing() {
        return YesterdayClearing;
    }

    public void setYesterdayClearing(String yesterdayClearing) {
        YesterdayClearing = yesterdayClearing;
    }

    public String getYesterdayClearingStatus() {
        return YesterdayClearingStatus;
    }

    public void setYesterdayClearingStatus(String yesterdayClearingStatus) {
        YesterdayClearingStatus = yesterdayClearingStatus;
    }

    public String getHoldAmount() {
        return HoldAmount;
    }

    public void setHoldAmount(String holdAmount) {
        HoldAmount = holdAmount;
    }

    public String getPlafond() {
        return Plafond;
    }

    public void setPlafond(String plafond) {
        Plafond = plafond;
    }

    public String getAccountStatus() {
        return AccountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        AccountStatus = accountStatus;
    }

    public String getCleanUpStatus() {
        return CleanUpStatus;
    }

    public void setCleanUpStatus(String cleanUpStatus) {
        CleanUpStatus = cleanUpStatus;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getAddress3() {
        return Address3;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }

    public String getAddress4() {
        return Address4;
    }

    public void setAddress4(String address4) {
        Address4 = address4;
    }
}
