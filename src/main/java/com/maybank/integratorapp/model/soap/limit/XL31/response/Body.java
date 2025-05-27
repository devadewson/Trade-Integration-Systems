package com.maybank.integratorapp.model.soap.limit.XL31.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
    @JacksonXmlProperty(localName = "XL31Response", namespace = "http://cms.middleware.bankbii.com/")
    private XL31Response xl31Response ;

    public XL31Response getXl31Response() {
        return xl31Response;
    }

    public void setXl31Response(XL31Response xl31Response) {
        this.xl31Response = xl31Response;
    }
}
