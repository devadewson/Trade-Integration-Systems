package com.maybank.integratorapp.model.mq.reservation.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ReservationRequestDetails {
    @JacksonXmlProperty(localName = "Product", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String product;
    @JacksonXmlProperty(localName = "ProductSubType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String productSubType;
    @JacksonXmlProperty(localName = "MasterReference", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String masterReference;
    @JacksonXmlProperty(localName = "EventReference", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String eventReference;
    @JacksonXmlProperty(localName = "AccountNumber", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String accountNumber;
    @JacksonXmlProperty(localName = "BackOfficeAccount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String backOfficeAccount;
    @JacksonXmlProperty(localName = "ExternalAccount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String externalAccount;
    @JacksonXmlProperty(localName = "IBAN", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String iban;
    @JacksonXmlProperty(localName = "AccountType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String accountType;
    @JacksonXmlProperty(localName = "Customer", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customer;
    @JacksonXmlProperty(localName = "Branch", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String branch;
    @JacksonXmlProperty(localName = "CustomerType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customerType;
    @JacksonXmlProperty(localName = "RelatedParty", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String relatedParty;
    @JacksonXmlProperty(localName = "PostingAmount")
    private PostingAmount postingAmount;
    @JacksonXmlProperty(localName = "DebitCreditFlag", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String debitCreditFlag;
    @JacksonXmlProperty(localName = "ValueDate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String valueDate;
    @JacksonXmlProperty(localName = "TenorStartDate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String tenorStartDate;
    @JacksonXmlProperty(localName = "TenorEndDate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String tenorEndDate;
    @JacksonXmlProperty(localName = "Narrative", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String narrative;
    @JacksonXmlProperty(localName = "UserCodes", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String userCodes;
    @JacksonXmlProperty(localName = "BankCode1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode1;
    @JacksonXmlProperty(localName = "BankCode2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode2;
    @JacksonXmlProperty(localName = "BankCode3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode3;
    @JacksonXmlProperty(localName = "BankCode4", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode4;
    @JacksonXmlProperty(localName = "BankCode5", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode5;
    @JacksonXmlProperty(localName = "PostingKey", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingKey;
    @JacksonXmlProperty(localName = "AccountIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String accountIdentifier;
    @JacksonXmlProperty(localName = "FacilityIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String facilityIdentifier;
    @JacksonXmlProperty(localName = "FacilitySequence", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String facilitySequence;
    @JacksonXmlProperty(localName = "RelatedPartyIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String relatedPartyIdentifier;


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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public String getBankCode5() {
        return bankCode5;
    }

    public void setBankCode5(String bankCode5) {
        this.bankCode5 = bankCode5;
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

    public String getFacilityIdentifier() {
        return facilityIdentifier;
    }

    public void setFacilityIdentifier(String facilityIdentifier) {
        this.facilityIdentifier = facilityIdentifier;
    }

    public String getFacilitySequence() {
        return facilitySequence;
    }

    public void setFacilitySequence(String facilitySequence) {
        this.facilitySequence = facilitySequence;
    }

    public String getRelatedPartyIdentifier() {
        return relatedPartyIdentifier;
    }

    public void setRelatedPartyIdentifier(String relatedPartyIdentifier) {
        this.relatedPartyIdentifier = relatedPartyIdentifier;
    }
}
