package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountListResponseWrapper {

    public AccountListResponseWrapper() {
        this.accountListResponse = new AccountListResponse();
    }

    @JsonProperty("AccountListResponse")
    private AccountListResponse accountListResponse;

    public AccountListResponse getAccountListResponse() {
        return accountListResponse;
    }

    public void setAccountListResponse(AccountListResponse accountListResponse) {
        this.accountListResponse = accountListResponse;
    }
}
