package com.maybank.integratorapp.model.soap.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class responseDetail {
    @JacksonXmlProperty(localName = "additionalData")
    private String additionalData;

    public String getAdditionalData() {
        return additionalData;
    }
    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
