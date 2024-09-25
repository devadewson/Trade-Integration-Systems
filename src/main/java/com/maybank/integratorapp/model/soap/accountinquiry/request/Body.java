package com.maybank.integratorapp.model.soap.accountinquiry.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    public Body(){
        this.accountInquiry = new AccountInquiry();
    }
    @JacksonXmlProperty(localName = "AccountInquiry",namespace = "http://www.bankbii.com/AccountServices/")
    private AccountInquiry accountInquiry;

    public AccountInquiry getAccountInquiry() {
        return accountInquiry;
    }

    public void setAccountInquiry(AccountInquiry accountInquiry) {
        this.accountInquiry = accountInquiry;
    }
}
