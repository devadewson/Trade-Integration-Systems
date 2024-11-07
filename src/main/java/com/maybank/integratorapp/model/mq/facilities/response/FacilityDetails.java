package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityDetails {
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
    private String AllowableCurrencies;
    @JacksonXmlProperty(localName = "RelatedParty")
    private String relatedParty;
    @JacksonXmlProperty(localName = "RelatedPartyIdentifier")
    private String relatedPartyIdentifier;
    @JacksonXmlProperty(localName = "ExtraDataKey")
    private String extraDataKey;
    @JacksonXmlProperty(localName = "DisplayField1")
    private String displayField1;
    @JacksonXmlProperty(localName = "DisplayField2")
    private String displayField2;
    @JacksonXmlProperty(localName = "DisplayField3")
    private String displayField3;
    @JacksonXmlProperty(localName = "DisplayField4")
    private String displayField4;
    @JacksonXmlProperty(localName = "DisplayField5")
    private String displayField5;
    @JacksonXmlProperty(localName = "DisplayField6")
    private String displayField6;
    @JacksonXmlProperty(localName = "DisplayField7")
    private String displayField7;
    @JacksonXmlProperty(localName = "DisplayField8")
    private String displayField8;
    @JacksonXmlProperty(localName = "DisplayField9")
    private String displayField9;
    @JacksonXmlProperty(localName = "DisplayField10")
    private String displayField10;

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
        return AllowableCurrencies;
    }

    public void setAllowableCurrencies(String allowableCurrencies) {
        AllowableCurrencies = allowableCurrencies;
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

    public String getDisplayField1() {
        return displayField1;
    }

    public void setDisplayField1(String displayField1) {
        this.displayField1 = displayField1;
    }

    public String getDisplayField2() {
        return displayField2;
    }

    public void setDisplayField2(String displayField2) {
        this.displayField2 = displayField2;
    }

    public String getDisplayField3() {
        return displayField3;
    }

    public void setDisplayField3(String displayField3) {
        this.displayField3 = displayField3;
    }

    public String getDisplayField4() {
        return displayField4;
    }

    public void setDisplayField4(String displayField4) {
        this.displayField4 = displayField4;
    }

    public String getDisplayField5() {
        return displayField5;
    }

    public void setDisplayField5(String displayField5) {
        this.displayField5 = displayField5;
    }

    public String getDisplayField6() {
        return displayField6;
    }

    public void setDisplayField6(String displayField6) {
        this.displayField6 = displayField6;
    }

    public String getDisplayField7() {
        return displayField7;
    }

    public void setDisplayField7(String displayField7) {
        this.displayField7 = displayField7;
    }

    public String getDisplayField8() {
        return displayField8;
    }

    public void setDisplayField8(String displayField8) {
        this.displayField8 = displayField8;
    }

    public String getDisplayField9() {
        return displayField9;
    }

    public void setDisplayField9(String displayField9) {
        this.displayField9 = displayField9;
    }

    public String getDisplayField10() {
        return displayField10;
    }

    public void setDisplayField10(String displayField10) {
        this.displayField10 = displayField10;
    }
}
