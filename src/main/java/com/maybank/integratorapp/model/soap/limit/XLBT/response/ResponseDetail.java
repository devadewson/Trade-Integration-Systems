package com.maybank.integratorapp.model.soap.limit.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseDetail {
    @JacksonXmlProperty(localName = "additionalData")
    private AdditionalData additionalData;

    public AdditionalData getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalData additionalData) {
        this.additionalData = additionalData;
    }
}
