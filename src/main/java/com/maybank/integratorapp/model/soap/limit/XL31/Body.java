package com.maybank.integratorapp.model.soap.limit.XL31;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    public Body(){
        this.xL31 = new XL31();
    }
    @JacksonXmlProperty(localName = "XL31",namespace = "http://www.bankbii.com/AccountServices/")
    private XL31 xL31;

    public XL31 getxL31() {
        return xL31;
    }

    public void setxL31(XL31 xL31) {
        this.xL31 = xL31;
    }
}
