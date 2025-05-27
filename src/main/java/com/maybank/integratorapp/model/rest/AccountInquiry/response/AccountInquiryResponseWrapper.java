package com.maybank.integratorapp.model.rest.AccountInquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;
public class AccountInquiryResponseWrapper {
    @JsonProperty("AccountInquiryResponse")
    private AccountInquiryResponse accountInquiryResponse;

    public AccountInquiryResponse getAccountInquiryResponse() {
        return accountInquiryResponse;
    }

    public void setAccountInquiryResponse(AccountInquiryResponse accountInquiryResponse) {
        this.accountInquiryResponse = accountInquiryResponse;
    }
}
