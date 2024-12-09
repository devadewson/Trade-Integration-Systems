package com.maybank.integratorapp.model.soap.XLBT.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    public Body(){
        this.xlbt = new XLBT();
    }
    @JacksonXmlProperty(localName = "XLBT",namespace = "http://www.bankbii.com/AccountServices/")
    private XLBT xlbt ;

    public XLBT getXlbt() {
        return xlbt;
    }

    public void setXlbt(XLBT xlbt) {
        this.xlbt = xlbt;
    }
}
