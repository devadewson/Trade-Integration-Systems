package com.maybank.integratorapp.model.mq.reservationsreversal.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ReservationsReversalResponse {
    @JacksonXmlProperty(localName = "ErrorCode", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String errorCode;
    @JacksonXmlProperty(localName = "ErrorText", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String errorText;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
