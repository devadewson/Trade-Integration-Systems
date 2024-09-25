package com.maybank.integratorapp.model.soap.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AccountInquiryResponseData {
    @JacksonXmlProperty(localName = "AccountName")
    private String accountName;
    @JacksonXmlProperty(localName = "CIF_No")

    private String cifNo;
    @JacksonXmlProperty(localName = "Balance")

    private String balance;
    @JacksonXmlProperty(localName = "MinimumBalance")

    private String minimumBalance;
    @JacksonXmlProperty(localName = "TodayClearing")

    private String todayClearing;
    @JacksonXmlProperty(localName = "YesterdayClearing")

    private String yesterdayClearing;
    @JacksonXmlProperty(localName = "YesterdayClearingStatus")

    private String yesterdayClearingStatus;
    @JacksonXmlProperty(localName = "HoldAmount")

    private String holdAmount;
    @JacksonXmlProperty(localName = "Plafond")

    private String plafond;
    @JacksonXmlProperty(localName = "AccountStatus")

    private String accountStatus;
    @JacksonXmlProperty(localName = "CleanUpStatus")

    private String cleanUpStatus;
    @JacksonXmlProperty(localName = "AccountType")

    private String accountType;
    @JacksonXmlProperty(localName = "Address1")

    private String address1;
    @JacksonXmlProperty(localName = "Address2")

    private String address2;
    @JacksonXmlProperty(localName = "Address3")
    private String address3;
    @JacksonXmlProperty(localName = "Address4")
    private String address4;

    // Jackson annotations for XML properties

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(String minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public String getTodayClearing() {
        return todayClearing;
    }

    public void setTodayClearing(String todayClearing) {
        this.todayClearing = todayClearing;
    }

    public String getYesterdayClearing() {
        return yesterdayClearing;
    }

    public void setYesterdayClearing(String yesterdayClearing) {
        this.yesterdayClearing = yesterdayClearing;
    }

    public String getYesterdayClearingStatus() {
        return yesterdayClearingStatus;
    }

    public void setYesterdayClearingStatus(String yesterdayClearingStatus) {
        this.yesterdayClearingStatus = yesterdayClearingStatus;
    }

    public String getHoldAmount() {
        return holdAmount;
    }

    public void setHoldAmount(String holdAmount) {
        this.holdAmount = holdAmount;
    }

    public String getPlafond() {
        return plafond;
    }

    public void setPlafond(String plafond) {
        this.plafond = plafond;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getCleanUpStatus() {
        return cleanUpStatus;
    }

    public void setCleanUpStatus(String cleanUpStatus) {
        this.cleanUpStatus = cleanUpStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }
}
