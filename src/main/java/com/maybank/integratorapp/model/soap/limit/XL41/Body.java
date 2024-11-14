package com.maybank.integratorapp.model.soap.limit.XL41;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    public Body(){
        this.xL41 = new XL41();
    }
    @JacksonXmlProperty(localName = "XL41",namespace = "http://www.bankbii.com/AccountServices/")
    private XL41 xL41;

    public XL41 getxL41() {
        return xL41;
    }

    public void setxL41(XL41 xL41) {
        this.xL41 = xL41;
    }
}
