package com.maybank.integratorapp.model.mq.accountinquiry.request;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AvailBalRequest {
    @JacksonXmlProperty(localName = "BackOfficeAccount")

    private String backOfficeAccount;
    @JacksonXmlProperty(localName = "ExternalAccount")

    private String externalAccount;
    @JacksonXmlProperty(localName = "PostingValueDate")

    private String postingValueDate;
    @JacksonXmlProperty(localName = "PostingAmount")

    private String postingAmount;
    @JacksonXmlProperty(localName = "PostingCurrency")

    private String postingCurrency;
    @JacksonXmlProperty(localName = "DebitCredit")

    private String debitCredit;
    @JacksonXmlProperty(localName = "Customer")

    private String customer;
    @JacksonXmlProperty(localName = "AccountIdentifier")

    private String accountIdentifier;
    public String getBackOfficeAccount() { return backOfficeAccount; }
    public void setBackOfficeAccount(String value) { this.backOfficeAccount = value; }

    public String getExternalAccount() { return externalAccount; }
    public void setExternalAccount(String value) { this.externalAccount = value; }

    public String getPostingValueDate() { return postingValueDate; }
    public void setPostingValueDate(String value) { this.postingValueDate = value; }

    public String getPostingAmount() { return postingAmount; }
    public void setPostingAmount(String value) { this.postingAmount = value; }

    public String getPostingCurrency() { return postingCurrency; }
    public void setPostingCurrency(String value) { this.postingCurrency = value; }

    public String getDebitCredit() { return debitCredit; }
    public void setDebitCredit(String value) { this.debitCredit = value; }

    public String getCustomer() { return customer; }
    public void setCustomer(String value) { this.customer = value; }

    public String getAccountIdentifier() { return accountIdentifier; }
    public void setAccountIdentifier(String value) { this.accountIdentifier = value; }

}
