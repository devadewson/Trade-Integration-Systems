package com.maybank.integratorapp.model.soap.limit.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XLBTResponse {
    public XLBTResponse() {
        this.cmsXlbtResponse = new CMS_XLBTResponse();
    }
    @JacksonXmlProperty(localName = "CMS_XLBTResponse",namespace = "http://www.bankbii.com/AccountServices/")
    private CMS_XLBTResponse cmsXlbtResponse;

    public CMS_XLBTResponse getCmsXlbtResponse() {
        return cmsXlbtResponse;
    }

    public void setCmsXlbtResponse(CMS_XLBTResponse cmsXlbtResponse) {
        this.cmsXlbtResponse = cmsXlbtResponse;
    }
}
