package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgBody {
    @JsonProperty("responseDetail")
    private Object responseDetail;

    @JsonProperty("AccountListResponse")
    public AccountListResponse accountListResponse;

    public Object getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(Object responseDetail) {
        this.responseDetail = responseDetail;
    }

    public AccountListResponse getAccountListResponse() {
        return accountListResponse;
    }

    public void setAccountListResponse(AccountListResponse accountListResponse) {
        this.accountListResponse = accountListResponse;
    }
}
