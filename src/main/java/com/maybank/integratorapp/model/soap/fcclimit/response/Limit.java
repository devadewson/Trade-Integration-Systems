package com.maybank.integratorapp.model.soap.fcclimit.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Limit {
    @JacksonXmlProperty(localName = "LimitName")
    private String limitName;

    @JacksonXmlProperty(localName = "LimitNo")
    private String limitNo;

    @JacksonXmlProperty(localName = "ParentLimitNo")
    private String parentLimitNo;

    @JacksonXmlProperty(localName = "ProductCode")
    private String productCode;

    @JacksonXmlProperty(localName = "LimitCurrency")
    private String limitCurrency;

    @JacksonXmlProperty(localName = "LimitAmount")
    private String limitAmount;

    @JacksonXmlProperty(localName = "ExpiryDate")
    private String expiryDate;

    @JacksonXmlProperty(localName = "TenorPeriod")
    private String tenorPeriod;

    @JacksonXmlProperty(localName = "TenorFrequency")
    private String tenorFrequency;

    @JacksonXmlProperty(localName = "AvailableLimitCurrency")
    private String availableLimitCurrency;

    @JacksonXmlProperty(localName = "AvailableLimit")
    private String availableLimit;

    @JacksonXmlProperty(localName = "AvailableLimitTransactionCurrency")
    private String availableLimitTransactionCurrency;

    @JacksonXmlProperty(localName = "EarmarkedLimitCurrency")
    private String earmarkedLimitCurrency;

    @JacksonXmlProperty(localName = "EarmarkedLimit")
    private String earmarkedLimit;

    @JacksonXmlProperty(localName = "UtilisationCurrency")
    private String utilisationCurrency;

    @JacksonXmlProperty(localName = "Utilisation")
    private String utilisation;

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public String getLimitNo() {
        return limitNo;
    }

    public void setLimitNo(String limitNo) {
        this.limitNo = limitNo;
    }

    public String getParentLimitNo() {
        return parentLimitNo;
    }

    public void setParentLimitNo(String parentLimitNo) {
        this.parentLimitNo = parentLimitNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getLimitCurrency() {
        return limitCurrency;
    }

    public void setLimitCurrency(String limitCurrency) {
        this.limitCurrency = limitCurrency;
    }

    public String getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getTenorPeriod() {
        return tenorPeriod;
    }

    public void setTenorPeriod(String tenorPeriod) {
        this.tenorPeriod = tenorPeriod;
    }

    public String getTenorFrequency() {
        return tenorFrequency;
    }

    public void setTenorFrequency(String tenorFrequency) {
        this.tenorFrequency = tenorFrequency;
    }

    public String getAvailableLimitCurrency() {
        return availableLimitCurrency;
    }

    public void setAvailableLimitCurrency(String availableLimitCurrency) {
        this.availableLimitCurrency = availableLimitCurrency;
    }

    public String getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(String availableLimit) {
        this.availableLimit = availableLimit;
    }

    public String getAvailableLimitTransactionCurrency() {
        return availableLimitTransactionCurrency;
    }

    public void setAvailableLimitTransactionCurrency(String availableLimitTransactionCurrency) {
        this.availableLimitTransactionCurrency = availableLimitTransactionCurrency;
    }

    public String getEarmarkedLimitCurrency() {
        return earmarkedLimitCurrency;
    }

    public void setEarmarkedLimitCurrency(String earmarkedLimitCurrency) {
        this.earmarkedLimitCurrency = earmarkedLimitCurrency;
    }

    public String getEarmarkedLimit() {
        return earmarkedLimit;
    }

    public void setEarmarkedLimit(String earmarkedLimit) {
        this.earmarkedLimit = earmarkedLimit;
    }

    public String getUtilisationCurrency() {
        return utilisationCurrency;
    }

    public void setUtilisationCurrency(String utilisationCurrency) {
        this.utilisationCurrency = utilisationCurrency;
    }

    public String getUtilisation() {
        return utilisation;
    }

    public void setUtilisation(String utilisation) {
        this.utilisation = utilisation;
    }

    // Getters and Setters
    // Omitted here for brevity but should be included for all fields
}