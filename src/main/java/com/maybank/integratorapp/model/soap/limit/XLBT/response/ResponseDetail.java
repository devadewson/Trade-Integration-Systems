package com.maybank.integratorapp.model.soap.limit.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseDetail {
    @JacksonXmlProperty(localName = "additionalData")
    private AdditionalData additionalData;

    @JacksonXmlProperty(localName = "error_message")
    private String error_message;

    public AdditionalData getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalData additionalData) {
        this.additionalData = additionalData;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
