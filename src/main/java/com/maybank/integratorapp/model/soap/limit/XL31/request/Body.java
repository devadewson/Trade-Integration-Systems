package com.maybank.integratorapp.model.soap.limit.XL31.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    public Body(){
        this.xl31 = new XL31();
    }
    @JacksonXmlProperty(localName = "XL31",namespace = "http://cms.middleware.bankbii.com/")
    private XL31 xl31  ;

    public XL31 getXl31() {
        return xl31;
    }

    public void setXl31(XL31 xl31) {
        this.xl31 = xl31;
    }
}
