package com.maybank.integratorapp.model.soap.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

//@JacksonXmlRootElement(localName = "AccountInquiryResponse", namespace = "http://www.bankbii.com/AccountServices/")
public class AccountInquiryResponse {
    public AccountInquiryResponse(){
        this.responseData = new AccountInquiryResponseData();
        this.responseDetail = new ResponseDetail();
    }
    @JacksonXmlProperty(localName = "responseCode")
    private String responseCode;
    @JacksonXmlProperty(localName = "responseDetail")

    private ResponseDetail responseDetail;
    @JacksonXmlProperty(localName = "accountInquiryResponseData")

    private AccountInquiryResponseData responseData;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseData(AccountInquiryResponseData responseData) {
        this.responseData = responseData;
    }

    public void setResponseDetail(ResponseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public AccountInquiryResponseData getResponseData() {
        return responseData;
    }

    public ResponseDetail getResponseDetail() {
        return responseDetail;
    }
}
