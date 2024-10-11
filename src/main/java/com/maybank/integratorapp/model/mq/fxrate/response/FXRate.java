package com.maybank.integratorapp.model.mq.fxrate.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FXRate {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "MaintType")
    private String maintType;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "MaintainedInBackOffice")
    private String maintainedInBackOffice;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "BankingEntity")
    private String bankingEntity;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "Currency")
    private String currency;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "BuyExchangeRate")
    private String buyExchangeRate;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "BuyPercentSpread")
    private String buyPercentSpread;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "BuySpreadRate")
    private String buySpreadRate;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "SellExchangeRate")
    private String sellExchangeRate;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "SellPercentSpread")
    private String sellPercentSpread;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "SellSpreadRate")
    private String sellSpreadRate;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "BuyRateSpecific")
    private String buyRateSpecific;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "SellRateSpecific")
    private String sellRateSpecific;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "BaseCurrency")
    private String baseCurrency;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "Reciprocal")
    private String reciprocal;

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

    public String getBuyExchangeRate() {
        return buyExchangeRate;
    }

    public void setBuyExchangeRate(String buyExchangeRate) {
        this.buyExchangeRate = buyExchangeRate;
    }

    public String getBuyPercentSpread() {
        return buyPercentSpread;
    }

    public void setBuyPercentSpread(String buyPercentSpread) {
        this.buyPercentSpread = buyPercentSpread;
    }

    public String getBuySpreadRate() {
        return buySpreadRate;
    }

    public void setBuySpreadRate(String buySpreadRate) {
        this.buySpreadRate = buySpreadRate;
    }

    public String getSellExchangeRate() {
        return sellExchangeRate;
    }

    public void setSellExchangeRate(String sellExchangeRate) {
        this.sellExchangeRate = sellExchangeRate;
    }

    public String getSellPercentSpread() {
        return sellPercentSpread;
    }

    public void setSellPercentSpread(String sellPercentSpread) {
        this.sellPercentSpread = sellPercentSpread;
    }

    public String getSellSpreadRate() {
        return sellSpreadRate;
    }

    public void setSellSpreadRate(String sellSpreadRate) {
        this.sellSpreadRate = sellSpreadRate;
    }

    public String getBuyRateSpecific() {
        return buyRateSpecific;
    }

    public void setBuyRateSpecific(String buyRateSpecific) {
        this.buyRateSpecific = buyRateSpecific;
    }

    public String getSellRateSpecific() {
        return sellRateSpecific;
    }

    public void setSellRateSpecific(String sellRateSpecific) {
        this.sellRateSpecific = sellRateSpecific;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getReciprocal() {
        return reciprocal;
    }

    public void setReciprocal(String reciprocal) {
        this.reciprocal = reciprocal;
    }
}
