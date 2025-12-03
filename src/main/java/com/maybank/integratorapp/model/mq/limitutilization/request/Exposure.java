package com.maybank.integratorapp.model.mq.limitutilization.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class Exposure {
    @JacksonXmlProperty(localName = "TransactionId")
    private String transactionId;

    @JacksonXmlProperty(localName = "TransactionSeqNo")
    private String transactionSeqNo;

    @JacksonXmlProperty(localName = "ReservationIdentifier")
    private String reservationIdentifier;

    @JacksonXmlProperty(localName = "ReservationSequence")
    private String reservationSequence;

    @JacksonXmlProperty(localName = "FacilityIdentifier")
    private String facilityIdentifier;

    @JacksonXmlProperty(localName = "FacilitySequence")
    private String facilitySequence;

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

    @JacksonXmlProperty(localName = "BackOfficeAccountNo")
    private String backOfficeAccountNo;

    @JacksonXmlProperty(localName = "ExternalAccountNo")
    private String externalAccountNo;

    @JacksonXmlProperty(localName = "IBAN")
    private String iban;

    @JacksonXmlProperty(localName = "AccountIdentifier")
    private String accountIdentifier;

    @JacksonXmlProperty(localName = "Customer")
    private String customer;

    @JacksonXmlProperty(localName = "RelatedParty")
    private String relatedParty;

    @JacksonXmlProperty(localName = "RelatedPartyIdentifier")
    private String relatedPartyIdentifier;

    @JacksonXmlProperty(localName = "Branch")
    private String branch;

    @JacksonXmlProperty(localName = "InputBranch")
    private String inputBranch;

    @JacksonXmlProperty(localName = "InputBranchNumber")
    private String inputBranchNumber;

    @JacksonXmlProperty(localName = "BehalfOfBranch")
    private String behalfOfBranch;

    @JacksonXmlProperty(localName = "BehalfOfBranchNumber")
    private String behalfOfBranchNumber;

    @JacksonXmlProperty(localName = "OriginatingMasterReference")
    private String originatingMasterReference;

    @JacksonXmlProperty(localName = "OriginatingEventReference")
    private String originatingEventReference;

    @JacksonXmlProperty(localName = "AccountType")
    private String accountType;

    @JacksonXmlProperty(localName = "CustomerType")
    private String customerType;

    @JacksonXmlProperty(localName = "Amount")
    private String amount;

    @JacksonXmlProperty(localName = "DebitCreditFlag")
    private String debitCreditFlag;

    @JacksonXmlProperty(localName = "Currency")
    private String currency;

    @JacksonXmlProperty(localName = "ValueDate")
    private String valueDate;

    @JacksonXmlProperty(localName = "TenorStartDate")
    private String tenorStartDate;

    @JacksonXmlProperty(localName = "TenorEndDate")
    private String tenorEndDate;

    @JacksonXmlProperty(localName = "Narrative")
    private String narrative;

    @JacksonXmlProperty(localName = "SundryReferenceCode")
    private String sundryReferenceCode;

    @JacksonXmlProperty(localName = "UserCode1")
    private String userCode1;

    @JacksonXmlProperty(localName = "UserCode2")
    private String userCode2;

    @JacksonXmlProperty(localName = "BankCode1")
    private String bankCode1;

    @JacksonXmlProperty(localName = "BankCode2")
    private String bankCode2;

    @JacksonXmlProperty(localName = "BankCode3")
    private String bankCode3;

    @JacksonXmlProperty(localName = "BankCode4")
    private String bankCode4;

    @JacksonXmlProperty(localName = "BankCode5")
    private String bankCode5;

    @JacksonXmlProperty(localName = "MasterKey")
    private String masterKey;

    @JacksonXmlProperty(localName = "EventKey")
    private String eventKey;

    @JacksonXmlProperty(localName = "PostingKey")
    private String postingKey;

    @JacksonXmlProperty(localName = "EventSubReference")
    private String eventSubReference;

    @JacksonXmlProperty(localName = "ReservationReference")
    private String reservationReference;

    @JacksonXmlProperty(localName = "FacilityExposureIdentifier")
    private String facilityExposureIdentifier;

    @JacksonXmlProperty(localName = "AddMntDelFlag")
    private String addMntDelFlag;

    @JacksonXmlProperty(localName = "ExtraData")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private ExtraData extraData;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionSeqNo() {
        return transactionSeqNo;
    }

    public void setTransactionSeqNo(String transactionSeqNo) {
        this.transactionSeqNo = transactionSeqNo;
    }

    public String getReservationIdentifier() {
        return reservationIdentifier;
    }

    public void setReservationIdentifier(String reservationIdentifier) {
        this.reservationIdentifier = reservationIdentifier;
    }

    public String getReservationSequence() {
        return reservationSequence;
    }

    public void setReservationSequence(String reservationSequence) {
        this.reservationSequence = reservationSequence;
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

    public String getBackOfficeAccountNo() {
        return backOfficeAccountNo;
    }

    public void setBackOfficeAccountNo(String backOfficeAccountNo) {
        this.backOfficeAccountNo = backOfficeAccountNo;
    }

    public String getExternalAccountNo() {
        return externalAccountNo;
    }

    public void setExternalAccountNo(String externalAccountNo) {
        this.externalAccountNo = externalAccountNo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(String relatedParty) {
        this.relatedParty = relatedParty;
    }

    public String getRelatedPartyIdentifier() {
        return relatedPartyIdentifier;
    }

    public void setRelatedPartyIdentifier(String relatedPartyIdentifier) {
        this.relatedPartyIdentifier = relatedPartyIdentifier;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getInputBranch() {
        return inputBranch;
    }

    public void setInputBranch(String inputBranch) {
        this.inputBranch = inputBranch;
    }

    public String getInputBranchNumber() {
        return inputBranchNumber;
    }

    public void setInputBranchNumber(String inputBranchNumber) {
        this.inputBranchNumber = inputBranchNumber;
    }

    public String getBehalfOfBranch() {
        return behalfOfBranch;
    }

    public void setBehalfOfBranch(String behalfOfBranch) {
        this.behalfOfBranch = behalfOfBranch;
    }

    public String getBehalfOfBranchNumber() {
        return behalfOfBranchNumber;
    }

    public void setBehalfOfBranchNumber(String behalfOfBranchNumber) {
        this.behalfOfBranchNumber = behalfOfBranchNumber;
    }

    public String getOriginatingMasterReference() {
        return originatingMasterReference;
    }

    public void setOriginatingMasterReference(String originatingMasterReference) {
        this.originatingMasterReference = originatingMasterReference;
    }

    public String getOriginatingEventReference() {
        return originatingEventReference;
    }

    public void setOriginatingEventReference(String originatingEventReference) {
        this.originatingEventReference = originatingEventReference;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDebitCreditFlag() {
        return debitCreditFlag;
    }

    public void setDebitCreditFlag(String debitCreditFlag) {
        this.debitCreditFlag = debitCreditFlag;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getSundryReferenceCode() {
        return sundryReferenceCode;
    }

    public void setSundryReferenceCode(String sundryReferenceCode) {
        this.sundryReferenceCode = sundryReferenceCode;
    }

    public String getUserCode1() {
        return userCode1;
    }

    public void setUserCode1(String userCode1) {
        this.userCode1 = userCode1;
    }

    public String getUserCode2() {
        return userCode2;
    }

    public void setUserCode2(String userCode2) {
        this.userCode2 = userCode2;
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

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getPostingKey() {
        return postingKey;
    }

    public void setPostingKey(String postingKey) {
        this.postingKey = postingKey;
    }

    public String getEventSubReference() {
        return eventSubReference;
    }

    public void setEventSubReference(String eventSubReference) {
        this.eventSubReference = eventSubReference;
    }

    public String getReservationReference() {
        return reservationReference;
    }

    public void setReservationReference(String reservationReference) {
        this.reservationReference = reservationReference;
    }

    public String getFacilityExposureIdentifier() {
        return facilityExposureIdentifier;
    }

    public void setFacilityExposureIdentifier(String facilityExposureIdentifier) {
        this.facilityExposureIdentifier = facilityExposureIdentifier;
    }

    public String getAddMntDelFlag() {
        return addMntDelFlag;
    }

    public void setAddMntDelFlag(String addMntDelFlag) {
        this.addMntDelFlag = addMntDelFlag;
    }

    public ExtraData getExtraData() {
        return extraData;
    }

    public void setExtraData(ExtraData extraData) {
        this.extraData = extraData;
    }
}
