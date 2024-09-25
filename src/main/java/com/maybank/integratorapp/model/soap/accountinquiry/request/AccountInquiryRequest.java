package com.maybank.integratorapp.model.soap.accountinquiry.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AccountInquiryRequest {

    @JacksonXmlProperty(localName = "accountNo")
    private String accountNo;

    @JacksonXmlProperty(localName = "accountBranchCode")
    private String accountBranchCode;

    @JacksonXmlProperty(localName = "accountCurrency")
    private String accountCurrency;

    // Getters and setters

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public void setAccountBranchCode(String accountBranchCode) {
        this.accountBranchCode = accountBranchCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public String getAccountBranchCode() {
        return accountBranchCode;
    }
}