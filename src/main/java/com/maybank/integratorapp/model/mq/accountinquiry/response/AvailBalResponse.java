package com.maybank.integratorapp.model.mq.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AvailBalResponse {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Balance")
    private String balance;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Negative")
    private String negative;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Applies")
    private String applies;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Blocked")
    private String blocked;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ErrorCode")
    private String errorCode;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "CheckedInBackOffice")
    private String checkedInBackOffice;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ErrorMessage")
    private String errorMessage;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ErrorOrWarning")
    private String errorOrWarning;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getApplies() {
        return applies;
    }

    public String getBlocked() {
        return blocked;
    }

    public String getCheckedInBackOffice() {
        return checkedInBackOffice;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorOrWarning() {
        return errorOrWarning;
    }

    public String getNegative() {
        return negative;
    }

    public void setApplies(String applies) {
        this.applies = applies;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public void setCheckedInBackOffice(String checkedInBackOffice) {
        this.checkedInBackOffice = checkedInBackOffice;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorOrWarning(String errorOrWarning) {
        this.errorOrWarning = errorOrWarning;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }
}
