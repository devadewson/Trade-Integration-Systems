package com.maybank.integratorapp.model.rest.AccountInquiry.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountInquiryWrapper {
    public AccountInquiryWrapper(){
        this.accountInquiry = new AccountInquiry();
    }
    @JsonProperty("AccountInquiry")
    private  AccountInquiry accountInquiry;

    public AccountInquiry getAccountInquiry() {
        return accountInquiry;
    }

    public void setAccountInquiry(AccountInquiry accountInquiry) {
        this.accountInquiry = accountInquiry;
    }

    @Override
    public String toString() {
        return "AccountInquiryWrapper{" +
                "AccountInquiry=" + accountInquiry +
                '}';
    }
}
