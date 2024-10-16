package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "AccountListResponse" )
public class AccountListResponse {
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseDetail")
    private ResponseDetail responseDetail;

    @JsonProperty("AccountListResponseData")
    private AccountListResponseData accountListResponseData;

    public AccountListResponse() {
        this.responseDetail = new ResponseDetail();
        this.accountListResponseData = new AccountListResponseData();
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseDetail getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(ResponseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public AccountListResponseData getAccountListResponseData() {
        return accountListResponseData;
    }

    public void setAccountListResponseData(AccountListResponseData accountListResponseData) {
        this.accountListResponseData = accountListResponseData;
    }
}
