package com.maybank.integratorapp.model.soap.accountinformation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AccountInformationResponseData {
    private String AccountName;
    private String CIF_No;
    private String AccountStatus;
    private String Address1;
    private String Address2;
    private String Address3;
    private String Address4;
    @JacksonXmlProperty(localName = "AccountData")
    AccountInformationAccountData accountInformationAccountDataObject;
    private String ApplCode;
    private String ProductCode;
    private String SubBranch;
    private String xSeller;
    private String CardNumber;
    private String TransactionInAmount;
    private String TransactionOutAmount;
    private String TransactionInFrequency;
    private String TransactionOutFrequency;
    private String SourceOfFund;
    private String PurposeOfFund;


    // Getter Methods

    public String getAccountName() {
        return AccountName;
    }

    public String getCIF_No() {
        return CIF_No;
    }

    public String getAccountStatus() {
        return AccountStatus;
    }

    public String getAddress1() {
        return Address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public String getAddress3() {
        return Address3;
    }

    public String getAddress4() {
        return Address4;
    }

    public AccountInformationAccountData getAccountData() {
        return accountInformationAccountDataObject;
    }

    public String getApplCode() {
        return ApplCode;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public String getSubBranch() {
        return SubBranch;
    }

    public String getXSeller() {
        return xSeller;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public String getTransactionInAmount() {
        return TransactionInAmount;
    }

    public String getTransactionOutAmount() {
        return TransactionOutAmount;
    }

    public String getTransactionInFrequency() {
        return TransactionInFrequency;
    }

    public String getTransactionOutFrequency() {
        return TransactionOutFrequency;
    }

    public String getSourceOfFund() {
        return SourceOfFund;
    }

    public String getPurposeOfFund() {
        return PurposeOfFund;
    }

    // Setter Methods

    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    public void setCIF_No(String CIF_No) {
        this.CIF_No = CIF_No;
    }

    public void setAccountStatus(String AccountStatus) {
        this.AccountStatus = AccountStatus;
    }

    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }

    public void setAddress2(String Address2) {
        this.Address2 = Address2;
    }

    public void setAddress3(String Address3) {
        this.Address3 = Address3;
    }

    public void setAddress4(String Address4) {
        this.Address4 = Address4;
    }

    public void setAccountData(AccountInformationAccountData accountInformationAccountDataObject) {
        this.accountInformationAccountDataObject = accountInformationAccountDataObject;
    }

    public void setApplCode(String ApplCode) {
        this.ApplCode = ApplCode;
    }

    public void setProductCode(String ProductCode) {
        this.ProductCode = ProductCode;
    }

    public void setSubBranch(String SubBranch) {
        this.SubBranch = SubBranch;
    }

    public void setXSeller(String xSeller) {
        this.xSeller = xSeller;
    }

    public void setCardNumber(String CardNumber) {
        this.CardNumber = CardNumber;
    }

    public void setTransactionInAmount(String TransactionInAmount) {
        this.TransactionInAmount = TransactionInAmount;
    }

    public void setTransactionOutAmount(String TransactionOutAmount) {
        this.TransactionOutAmount = TransactionOutAmount;
    }

    public void setTransactionInFrequency(String TransactionInFrequency) {
        this.TransactionInFrequency = TransactionInFrequency;
    }

    public void setTransactionOutFrequency(String TransactionOutFrequency) {
        this.TransactionOutFrequency = TransactionOutFrequency;
    }

    public void setSourceOfFund(String SourceOfFund) {
        this.SourceOfFund = SourceOfFund;
    }

    public void setPurposeOfFund(String PurposeOfFund) {
        this.PurposeOfFund = PurposeOfFund;
    }
}

