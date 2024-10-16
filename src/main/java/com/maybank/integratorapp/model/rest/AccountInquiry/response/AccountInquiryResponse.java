package com.maybank.integratorapp.model.rest.AccountInquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountInquiryResponse {
    @JsonProperty("responseDetail")
    private responseDetail responseDetail;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("accountInquiryResponseData")
    private accountInquiryResponseData accountInquiryResponseData;

    public responseDetail getResponseDetail() {
        return responseDetail;
    }
    public void setResponseDetail(responseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public String getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public accountInquiryResponseData getAccountInquiryResponseData() {
        return accountInquiryResponseData;
    }

    public void setAccountInquiryResponseData(accountInquiryResponseData accountInquiryResponseData) {
        this.accountInquiryResponseData = accountInquiryResponseData;
    }
}
