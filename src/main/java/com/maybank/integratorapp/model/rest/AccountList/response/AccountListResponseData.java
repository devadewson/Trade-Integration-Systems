package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "AccountListResponseData")
public class AccountListResponseData {
    @JsonProperty("GCIFNo")
    private String gcifNo;

    @JsonProperty("CIFType")
    private String cifType;

    @JsonProperty("CustomerName")
    private String customerName;

    @JsonProperty("BirthDate")
    private String birthDate;

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

    @JsonProperty("Address2")
    private String address2;

    @JsonProperty("Address3")
    private String address3;

    @JsonProperty("Zipcode")
    private String zipcode;

    @JsonProperty("ResidentPhoneNo")
    private String residentPhoneNo;

    @JsonProperty("AccountData")
    private List<AccountData> accountData;

    @JsonProperty("CreditCardData")
    private List<CreditCardData> creditCardData;

    @JsonProperty("WealthAccountData")
    private List<WealthAccountData> wealthAccountData;

    public String getGcifNo() {
        return gcifNo;
    }

    public void setGcifNo(String gcifNo) {
        this.gcifNo = gcifNo;
    }

    public String getCifType() {
        return cifType;
    }

    public void setCifType(String cifType) {
        this.cifType = cifType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRaceCode() {
        return raceCode;
    }

    public void setRaceCode(String raceCode) {
        this.raceCode = raceCode;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
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

    public List<WealthAccountData> getWealthAccountData() {
        return wealthAccountData;
    }

    public void setWealthAccountData(List<WealthAccountData> wealthAccountData) {
        this.wealthAccountData = wealthAccountData;
    }
}
