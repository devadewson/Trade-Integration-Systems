package com.maybank.integratorapp.model.soap.limit.XL40.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {

    public Body() {this.xl40 = new XL40();
    }
    @JacksonXmlProperty(localName = "XL40",namespace = "http://cms.middleware.bankbii.com/")
    private XL40 xl40;

    public XL40 getXl40() {
        return xl40;
    }

    public void setXl40(XL40 xl40) {
        this.xl40 = xl40;
    }
}
