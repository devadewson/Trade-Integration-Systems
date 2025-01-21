package com.maybank.integratorapp.model.mq.reservation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ReservationResponseDetails {
    @JacksonXmlProperty(localName = "Description", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String description;
    @JacksonXmlProperty(localName = "StartDate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String startDate;
    @JacksonXmlProperty(localName = "ExpiryDate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String expiryDate;
    @JacksonXmlProperty(localName = "Currency", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String currency;
    @JacksonXmlProperty(localName = "LimitAmount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String limitAmount;
    @JacksonXmlProperty(localName = "ExposureAmount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String exposureAmount;
    @JacksonXmlProperty(localName = "ReservedAmount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String reservedAmount;
    @JacksonXmlProperty(localName = "AvailableAmount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String availableAmount;
    @JacksonXmlProperty(localName = "LiabilityCurrency", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String liabilityCurrency;
    @JacksonXmlProperty(localName = "AvailableAmountInLiabilityCurrency", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String availableAmountInLiabilityCurrency;
    @JacksonXmlProperty(localName = "LimitCheckStatus", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String limitCheckStatus;
    @JacksonXmlProperty(localName = "WarningErrorMessage", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String warningErrorMessage;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLimitCheckStatus() {
        return limitCheckStatus;
    }

    public void setLimitCheckStatus(String limitCheckStatus) {
        this.limitCheckStatus = limitCheckStatus;
    }

    public String getWarningErrorMessage() {
        return warningErrorMessage;
    }

    public void setWarningErrorMessage(String warningErrorMessage) {
        this.warningErrorMessage = warningErrorMessage;
    }
}
