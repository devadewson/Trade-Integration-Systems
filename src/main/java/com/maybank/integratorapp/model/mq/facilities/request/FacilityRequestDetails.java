package com.maybank.integratorapp.model.mq.facilities.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityRequestDetails {
    @JacksonXmlProperty(localName = "Product")
    private String product;
    @JacksonXmlProperty(localName = "ProductSubType")
    private String productSubType;
    @JacksonXmlProperty(localName = "MasterReference")
    private String masterReference;
    @JacksonXmlProperty(localName = "EventReference")
    private String eventReference;
    @JacksonXmlProperty(localName = "AccountNumber")
    private String accountNumber;
    @JacksonXmlProperty(localName = "BackOfficeAccount")
    private String backOfficeAccount;
    @JacksonXmlProperty(localName = "ExternalAccount")
    private String externalAccount;
    @JacksonXmlProperty(localName = "IBAN")
    private String iBAN;
    @JacksonXmlProperty(localName = "AccountType")
    private String accountType;
    @JacksonXmlProperty(localName = "Customer")
    private String customer;
    @JacksonXmlProperty(localName = "Branch")
    private String branch;
    @JacksonXmlProperty(localName = "CustomerType")
    private String customerType;
    @JacksonXmlProperty(localName = "RelatedParty")
    private String relatedParty;
    @JacksonXmlProperty(localName = "PostingAmount")
    private PostingAmount postingAmount;
    @JacksonXmlProperty(localName = "DebitCreditFlag")
    private String debitCreditFlag;
    @JacksonXmlProperty(localName = "ValueDate")
    private String valueDate;
    @JacksonXmlProperty(localName = "TenorStartDate")
    private String tenorStartDate;
    @JacksonXmlProperty(localName = "TenorEndDate")
    private String tenorEndDate;
    @JacksonXmlProperty(localName = "Narrative")
    private String narrative;
    @JacksonXmlProperty(localName = "UserCodes")
    private String userCodes;
    @JacksonXmlProperty(localName = "BankCode1")
    private String bankCode1;
    @JacksonXmlProperty(localName = "BankCode2")
    private String bankCode2;
    @JacksonXmlProperty(localName = "BankCode3")
    private String bankCode3;
    @JacksonXmlProperty(localName = "BankCode4")
    private String bankCode4;
    @JacksonXmlProperty(localName = "BankCode5")
    private String bBankCode5;
    @JacksonXmlProperty(localName = "PostingKey")
    private String postingKey;
    @JacksonXmlProperty(localName = "AccountIdentifier")
    private String accountIdentifier;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductSubType() {
        return productSubType;
    }

    public void setProductSubType(String productSubType) {
        this.productSubType = productSubType;
    }

    public String getMasterReference() {
        return masterReference;
    }

    public void setMasterReference(String masterReference) {
        this.masterReference = masterReference;
    }

    public String getEventReference() {
        return eventReference;
    }

    public void setEventReference(String eventReference) {
        this.eventReference = eventReference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBackOfficeAccount() {
        return backOfficeAccount;
    }

    public void setBackOfficeAccount(String backOfficeAccount) {
        this.backOfficeAccount = backOfficeAccount;
    }

    public String getExternalAccount() {
        return externalAccount;
    }

    public void setExternalAccount(String externalAccount) {
        this.externalAccount = externalAccount;
    }

    public String getiBAN() {
        return iBAN;
    }

    public void setiBAN(String iBAN) {
        this.iBAN = iBAN;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(String relatedParty) {
        this.relatedParty = relatedParty;
    }

    public PostingAmount getPostingAmount() {
        return postingAmount;
    }

    public void setPostingAmount(PostingAmount postingAmount) {
        this.postingAmount = postingAmount;
    }

    public String getDebitCreditFlag() {
        return debitCreditFlag;
    }

    public void setDebitCreditFlag(String debitCreditFlag) {
        this.debitCreditFlag = debitCreditFlag;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getTenorStartDate() {
        return tenorStartDate;
    }

    public void setTenorStartDate(String tenorStartDate) {
        this.tenorStartDate = tenorStartDate;
    }

    public String getTenorEndDate() {
        return tenorEndDate;
    }

    public void setTenorEndDate(String tenorEndDate) {
        this.tenorEndDate = tenorEndDate;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getUserCodes() {
        return userCodes;
    }

    public void setUserCodes(String userCodes) {
        this.userCodes = userCodes;
    }

    public String getBankCode1() {
        return bankCode1;
    }

    public void setBankCode1(String bankCode1) {
        this.bankCode1 = bankCode1;
    }

    public String getBankCode2() {
        return bankCode2;
    }

    public void setBankCode2(String bankCode2) {
        this.bankCode2 = bankCode2;
    }

    public String getBankCode3() {
        return bankCode3;
    }

    public void setBankCode3(String bankCode3) {
        this.bankCode3 = bankCode3;
    }

    public String getBankCode4() {
        return bankCode4;
    }

    public void setBankCode4(String bankCode4) {
        this.bankCode4 = bankCode4;
    }

    public String getbBankCode5() {
        return bBankCode5;
    }

    public void setbBankCode5(String bBankCode5) {
        this.bBankCode5 = bBankCode5;
    }

    public String getPostingKey() {
        return postingKey;
    }

    public void setPostingKey(String postingKey) {
        this.postingKey = postingKey;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }
}
