package com.maybank.integratorapp.model.soap.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XLBTResponse {
    @JacksonXmlProperty(localName = "CMS_XLBTResponse")
    private String  cms_XLBTResponse;

    public String getCms_XLBTResponse() {
        return cms_XLBTResponse;
    }
    public void setCms_XLBTResponse(String cms_XLBTResponse) {
        this.cms_XLBTResponse = cms_XLBTResponse;
    }
}
