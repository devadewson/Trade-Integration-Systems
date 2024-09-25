package com.maybank.integratorapp.model.soap.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    public Body(){
        this.accountInquiryResponseData = new AccountInquiryResponse();
    }

    @JacksonXmlProperty(localName = "AccountInquiryResponse", namespace = "http://www.bankbii.com/AccountServices/")
    private AccountInquiryResponse accountInquiryResponseData;

    public AccountInquiryResponse getAccountInquiryResponseData() {
        return accountInquiryResponseData;
    }

    public void setAccountInquiryResponseData(AccountInquiryResponse accountInquiryResponseData) {
        this.accountInquiryResponseData = accountInquiryResponseData;
    }
}
