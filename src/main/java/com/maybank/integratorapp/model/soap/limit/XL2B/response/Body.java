package com.maybank.integratorapp.model.soap.limit.XL2B.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
    @JacksonXmlProperty(localName = "XL2BResponse", namespace = "http://cms.middleware.bankbii.com/")
    private XL2BResponse xl2BResponse ;

    public XL2BResponse getXl2BResponse() {
        return xl2BResponse;
    }

    public void setXl2BResponse(XL2BResponse xl2BResponse) {
        this.xl2BResponse = xl2BResponse;
    }
}
