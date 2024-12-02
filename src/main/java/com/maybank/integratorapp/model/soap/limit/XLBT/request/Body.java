package com.maybank.integratorapp.model.soap.limit.XLBT.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "soapenv:Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class Body {
    public Body(){
        this.xLBT = new XLBT();
    }
    @JacksonXmlProperty(localName = "XLBT",namespace = "http://www.bankbii.com/AccountServices/")
    private XLBT xLBT;

    public XLBT getxLBT() {
        return xLBT;
    }

    public void setxLBT(XLBT xLBT) {
        this.xLBT = xLBT;
    }
}
