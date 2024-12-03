package com.maybank.integratorapp.model.soap.XL31.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;
public class responseDetail {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "additionalData")
    private List<com.maybank.integratorapp.model.soap.XL31.response.additionalData> additionalData;

    @JacksonXmlProperty(localName = "response_data")
    private String responseData;

    @JacksonXmlProperty(localName = "error_message")
    private String errormessage;


    public List<com.maybank.integratorapp.model.soap.XL31.response.additionalData> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(List<com.maybank.integratorapp.model.soap.XL31.response.additionalData> additionalData) {
        this.additionalData = additionalData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }
}
