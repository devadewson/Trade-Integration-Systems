package com.maybank.integratorapp.model.soap.XL41.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {

    public Body() {this.xl41 = new XL41();
    }
    @JacksonXmlProperty(localName = "XL41",namespace = "http://cms.middleware.bankbii.com/")
    private  XL41 xl41;

    public XL41 getXl41() {
        return xl41;
    }

    public void setXl41(XL41 xl41) {
        this.xl41 = xl41;
    }
}
