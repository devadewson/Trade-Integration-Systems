package com.maybank.integratorapp.model.soap.limit.XL2B.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.soap.limit.XL31.request.XL31;

public class Body {
    public Body(){
        this.xl2B = new XL2B();
    }
    @JacksonXmlProperty(localName = "XL2B",namespace = "http://cms.middleware.bankbii.com/")
    private XL2B xl2B;

    public XL2B getXl2B() {
        return xl2B;
    }

    public void setXl2B(XL2B xl2B) {
        this.xl2B = xl2B;
    }
}
