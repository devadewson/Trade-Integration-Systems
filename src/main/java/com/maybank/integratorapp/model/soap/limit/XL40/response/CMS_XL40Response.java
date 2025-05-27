package com.maybank.integratorapp.model.soap.limit.XL40.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class CMS_XL40Response {
    @JacksonXmlProperty(localName ="responseDetail")
    private responseDetail responseDetail;

    @JacksonXmlProperty(localName = "responsecode")
    private String responsecode;

    public responseDetail getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(responseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }
}
