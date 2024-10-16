package com.maybank.integratorapp.model.rest.AccountInquiry.request;

import com.fasterxml.jackson.annotation.JsonProperty;
public class AccountInquiryRequest {
    @JsonProperty("accountNo")
    private String accountNo;
    @JsonProperty("accountBranchCode")
    private String accountBranchCode;
    @JsonProperty("accountCurrency")
    private String accountCurrency;
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getAccountBranchCode() {
        return accountBranchCode;
    }
    public void setAccountBranchCode(String accountBranchCode) {
        this.accountBranchCode = accountBranchCode;
    }
    public String getAccountCurrency() {
        return accountCurrency;
    }
    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }
    @Override
    public String toString() {
        return "AccountInquiryRequest{" +
                "accountNo='" + accountNo + '\'' +
                ", accountBranchCode='" + accountBranchCode + '\'' +
                ", accountCurrency='" + accountCurrency + '\'' +
                '}';
    }
}
