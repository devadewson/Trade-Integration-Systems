package com.maybank.integratorapp.model.soap.limit.XLBT.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

public class Body {
    public Body(){
        this.xLBT = new XLBT();
    }
    @JacksonXmlProperty(localName = "XLBT",namespace = "http://cms.middleware.bankbii.com/")
    private XLBT xLBT;

    public XLBT getxLBT() {
        return xLBT;
    }

    public void setxLBT(XLBT xLBT) {
        this.xLBT = xLBT;
    }
}
