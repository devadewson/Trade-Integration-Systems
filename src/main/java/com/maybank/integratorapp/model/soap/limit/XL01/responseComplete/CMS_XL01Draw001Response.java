package com.maybank.integratorapp.model.soap.limit.XL01.responseComplete;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CMS_XL01Draw001Response {
    @JacksonXmlProperty(localName = "responseDetail")
    private com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.responseDetail responseDetail;

    @JacksonXmlProperty(localName  = "responsecode")
    private String responsecode;

    public com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.responseDetail getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.responseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }
}
