package com.maybank.integratorapp.model.soap.fcclimit.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RequestData {
    @JacksonXmlProperty(localName = "CIFNo")
    private String cifNo;

    @JacksonXmlProperty(localName = "TransactionCurrency")
    private String transactionCurrency;

    @JacksonXmlProperty(localName = "Status")
    private String status;

    @JacksonXmlProperty(localName = "AccountType")
    private String accountType;

    // Getters and Setters
    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
