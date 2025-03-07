package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccountListResponse {
    @JsonProperty("GCIFNo")
    private String gcifNo;

    @JsonProperty("CustomerName")
    private String customerName;

    @JsonProperty("Birthdate")
    private String birthdate;

    @JsonProperty("MobileNo")
    private String mobileNo;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Address1")
    private String address1;

    @JsonProperty("Zipcode")
    private String zipcode;

    @JsonProperty("ResidentPhoneNo")
    private String residentPhoneNo;

    @JsonProperty("AccountData")
    private List<AccountData> accountData;

    @JsonProperty("CreditCardData")
    private List<CreditCardData> creditCardData;

    @JsonProperty("WealthAccountData")
    private WealthAccountData wealthAccountData;

    @JsonProperty("BancAssuranceAccountData")
    private BancAssuranceAccountData bancAssuranceAccountData;


    public String getGcifNo() {
        return gcifNo;
    }

    public void setGcifNo(String gcifNo) {
        this.gcifNo = gcifNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getResidentPhoneNo() {
        return residentPhoneNo;
    }

    public void setResidentPhoneNo(String residentPhoneNo) {
        this.residentPhoneNo = residentPhoneNo;
    }

    public List<AccountData> getAccountData() {
        return accountData;
    }

    public void setAccountData(List<AccountData> accountData) {
        this.accountData = accountData;
    }

    public List<CreditCardData> getCreditCardData() {
        return creditCardData;
    }

    public void setCreditCardData(List<CreditCardData> creditCardData) {
        this.creditCardData = creditCardData;
    }

    public WealthAccountData getWealthAccountData() {
        return wealthAccountData;
    }

    public void setWealthAccountData(WealthAccountData wealthAccountData) {
        this.wealthAccountData = wealthAccountData;
    }

    public BancAssuranceAccountData getBancAssuranceAccountData() {
        return bancAssuranceAccountData;
    }

    public void setBancAssuranceAccountData(BancAssuranceAccountData bancAssuranceAccountData) {
        this.bancAssuranceAccountData = bancAssuranceAccountData;
    }
}
