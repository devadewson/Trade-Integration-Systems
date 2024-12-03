package com.maybank.integratorapp.model.soap.XL41.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class CMS_XL41Response {
    @JacksonXmlProperty(localName ="responseDetail")
    private com.maybank.integratorapp.model.soap.XL41.response.responseDetail responseDetail;

    @JacksonXmlProperty(localName = "responsecode")
    private String responsecode;

    public com.maybank.integratorapp.model.soap.XL41.response.responseDetail getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(com.maybank.integratorapp.model.soap.XL41.response.responseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }
}
