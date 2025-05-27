package com.maybank.integratorapp.model.soap.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    @JacksonXmlProperty(localName = "XLBTResponse", namespace = "http://www.bankbii.com/AccountServices/")
    private XLBTResponse xlbtResponse ;

    public XLBTResponse getXlbtResponse() {
        return xlbtResponse;
    }

    public void setXlbtResponse(XLBTResponse xlbtResponse) {
        this.xlbtResponse = xlbtResponse;
    }
}
