package com.maybank.integratorapp.model.soap.XL01.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlRootElement;

public class Body {
    public Body(){
        this.xl01Draw001 = new XL01Draw001();
    }
    @JacksonXmlProperty(localName = "XL01Draw001",namespace = "http://cms.middleware.bankbii.com/")
    private XL01Draw001 xl01Draw001  ;

    public XL01Draw001 getXl01Draw001() {
        return xl01Draw001;
    }
    public void setXl01Draw001(XL01Draw001 xl01Draw001) {
        this.xl01Draw001 = xl01Draw001;
    }
}
