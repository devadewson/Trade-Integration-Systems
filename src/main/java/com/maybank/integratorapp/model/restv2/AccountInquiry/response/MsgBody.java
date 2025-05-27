package com.maybank.integratorapp.model.restv2.AccountInquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgBody {
    @JsonProperty("responseDetail")
    private Object responseDetail;

    @JsonProperty("AccountInformationResponseData")
    private AccountInformationResponseData accountInformationResponseData;

    public Object getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(Object responseDetail) {
        this.responseDetail = responseDetail;
    }

    public AccountInformationResponseData getAccountInformationResponseData() {
        return accountInformationResponseData;
    }

    public void setAccountInformationResponseData(AccountInformationResponseData accountInformationResponseData) {
        this.accountInformationResponseData = accountInformationResponseData;
    }
}
