package com.maybank.integratorapp.model.soap.limit.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.soap.limit.XLBT.response.XLBTResponse;

public class Body {
    public Body(){
        this.xlbtResponse = new XLBTResponse();
    }
    @JacksonXmlProperty(localName = "XLBTResponse",namespace = "http://www.bankbii.com/AccountServices/")
    private XLBTResponse xlbtResponse;

    public XLBTResponse getXlbtResponse() {
        return xlbtResponse;
    }

    public void setXlbtResponse(XLBTResponse xlbtResponse) {
        this.xlbtResponse = xlbtResponse;
    }
}
