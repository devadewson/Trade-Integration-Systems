package com.maybank.integratorapp.model.mq.fxratespot.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CurrencySpotRate {
    @JacksonXmlProperty(localName = "MaintType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public String maintType;

    @JacksonXmlProperty(localName = "MaintainedInBackOffice", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public String maintainedInBackOffice;

    @JacksonXmlProperty(localName = "BankingEntity", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public String bankingEntity;

    @JacksonXmlProperty(localName = "Currency", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public String currency;

    @JacksonXmlProperty(localName = "SpotRate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public Double spotRate;

    @JacksonXmlProperty(localName = "Reciprocal", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public String reciprocal;

    @JacksonXmlProperty(localName = "InvalidTradingCurrency", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public String invalidTradingCurrency;

    @JacksonXmlProperty(localName = "QuotationUnit", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    public String quotationUnit;

    public String getMaintType() {
        return maintType;
    }

    public void setMaintType(String maintType) {
        this.maintType = maintType;
    }

    public String getMaintainedInBackOffice() {
        return maintainedInBackOffice;
    }

    public void setMaintainedInBackOffice(String maintainedInBackOffice) {
        this.maintainedInBackOffice = maintainedInBackOffice;
    }

    public String getBankingEntity() {
        return bankingEntity;
    }

    public void setBankingEntity(String bankingEntity) {
        this.bankingEntity = bankingEntity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getSpotRate() {
        return spotRate;
    }

    public void setSpotRate(Double spotRate) {
        this.spotRate = spotRate;
    }

    public String getReciprocal() {
        return reciprocal;
    }

    public void setReciprocal(String reciprocal) {
        this.reciprocal = reciprocal;
    }

    public String getInvalidTradingCurrency() {
        return invalidTradingCurrency;
    }

    public void setInvalidTradingCurrency(String invalidTradingCurrency) {
        this.invalidTradingCurrency = invalidTradingCurrency;
    }

    public String getQuotationUnit() {
        return quotationUnit;
    }

    public void setQuotationUnit(String quotationUnit) {
        this.quotationUnit = quotationUnit;
    }
}
