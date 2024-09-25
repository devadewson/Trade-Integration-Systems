package com.maybank.integratorapp.model.soap.accountinformation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.soap.accountinquiry.response.AccountInquiryResponseData;
import com.maybank.integratorapp.model.soap.accountinquiry.response.ResponseDetail;

@JacksonXmlRootElement(localName = "ns2:AccountInformationResponse", namespace = "http://www.bankbii.com/AccountServices/")
public class AccountInformationResponse {
    public AccountInformationResponse(){
        this.responseData = new AccountInformationResponseData();
        this.responseDetail = new AccountInformationResponseDetail();
    }
    @JacksonXmlProperty(localName = "responseCode")
    private String responseCode;
    @JacksonXmlProperty(localName = "responseDetail")

    AccountInformationResponseDetail responseDetail;
    @JacksonXmlProperty(localName = "AccountInformationResponseData")

    AccountInformationResponseData responseData;


    // Getter Methods

    public String getResponseCode() {
        return responseCode;
    }

    public AccountInformationResponseDetail getResponseDetail() {
        return responseDetail;
    }

    public AccountInformationResponseData getAccountInformationResponseData() {
        return responseData;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseDetail(AccountInformationResponseDetail responseDetailObject) {
        this.responseDetail = responseDetailObject;
    }

    public void setAccountInformationResponseData(AccountInformationResponseData AccountInformationResponseDataObject) {
        this.responseData = AccountInformationResponseDataObject;
    }

}
