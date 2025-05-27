package com.maybank.integratorapp.model.soap.XL31.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.soap.XLBT.request.XLBT;
import jakarta.xml.bind.annotation.XmlRootElement;

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
