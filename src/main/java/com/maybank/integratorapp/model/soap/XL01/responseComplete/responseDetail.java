package com.maybank.integratorapp.model.soap.XL01.responseComplete;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class responseDetail {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "additionalData")
    private List<additionalData> additionalData;

    @JacksonXmlProperty(localName = "response_data")
    private String responseData;
    public List<additionalData> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(List<additionalData> additionalData) {
        this.additionalData = additionalData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
