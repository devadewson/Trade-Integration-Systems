package com.maybank.integratorapp.model.mq.facilitiesdetails.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityDetailsResponse {
    @JacksonXmlProperty(localName = "Identifier")
    private String identifier;
    @JacksonXmlProperty(localName = "SequenceNumber")
    private String sequenceNumber;
    @JacksonXmlProperty(localName = "FacilityCode")
    private String facilityCode;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "Customer")
    private String customer;
    @JacksonXmlProperty(localName = "StartDate")
    private String startDate;
    @JacksonXmlProperty(localName = "ExpiryDate")
    private String expiryDate;
    @JacksonXmlProperty(localName = "Currency")
    private String currency;
    @JacksonXmlProperty(localName = "LimitAmount")
    private String limitAmount;
    @JacksonXmlProperty(localName = "ExposureAmount")
    private String exposureAmount;
    @JacksonXmlProperty(localName = "ReservedAmount")
    private String reservedAmount;
    @JacksonXmlProperty(localName = "AvailableAmount")
    private String availableAmount;
    @JacksonXmlProperty(localName = "LiabilityCurrency")
    private String liabilityCurrency;
    @JacksonXmlProperty(localName = "AvailableAmountInLiabilityCurrency")
    private String availableAmountInLiabilityCurrency;
    @JacksonXmlProperty(localName = "Status")
    private String status;
    @JacksonXmlProperty(localName = "MultiCurrency")
    private String multiCurrency;
    @JacksonXmlProperty(localName = "AllowableCurrencies")
    private String allowableCurrencies;
    @JacksonXmlProperty(localName = "RelatedParty")
    private String relatedParty;
    @JacksonXmlProperty(localName = "RelatedPartyIdentifier")
    private String relatedPartyIdentifier;
    @JacksonXmlProperty(localName = "ExtraDataKey")
    private String extraDataKey;
    @JacksonXmlProperty(localName = "FacilityExtraDetailss")
    private FacilityExtraDetailss facilityExtraDetailss;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getExposureAmount() {
        return exposureAmount;
    }

    public void setExposureAmount(String exposureAmount) {
        this.exposureAmount = exposureAmount;
    }

    public String getReservedAmount() {
        return reservedAmount;
    }

    public void setReservedAmount(String reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getLiabilityCurrency() {
        return liabilityCurrency;
    }

    public void setLiabilityCurrency(String liabilityCurrency) {
        this.liabilityCurrency = liabilityCurrency;
    }

    public String getAvailableAmountInLiabilityCurrency() {
        return availableAmountInLiabilityCurrency;
    }

    public void setAvailableAmountInLiabilityCurrency(String availableAmountInLiabilityCurrency) {
        this.availableAmountInLiabilityCurrency = availableAmountInLiabilityCurrency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMultiCurrency() {
        return multiCurrency;
    }

    public void setMultiCurrency(String multiCurrency) {
        this.multiCurrency = multiCurrency;
    }

    public String getAllowableCurrencies() {
        return allowableCurrencies;
    }

    public void setAllowableCurrencies(String allowableCurrencies) {
        this.allowableCurrencies = allowableCurrencies;
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

    public String getExtraDataKey() {
        return extraDataKey;
    }

    public void setExtraDataKey(String extraDataKey) {
        this.extraDataKey = extraDataKey;
    }

    public FacilityExtraDetailss getFacilityExtraDetailss() {
        return facilityExtraDetailss;
    }

    public void setFacilityExtraDetailss(FacilityExtraDetailss facilityExtraDetailss) {
        this.facilityExtraDetailss = facilityExtraDetailss;
    }
}
