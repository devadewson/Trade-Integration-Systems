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

    @JsonProperty("Salutation")
    private String salutation;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("RaceCode")
    private String raceCode;

    @JsonProperty("IdentityNo")
    private String identityNo;

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

    public String getGcifNo() {
        return gcifNo;
    }

    public void setGcifNo(String gcifNo) {
        this.gcifNo = gcifNo;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
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


}
