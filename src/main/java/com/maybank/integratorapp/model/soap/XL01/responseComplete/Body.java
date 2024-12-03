package com.maybank.integratorapp.model.soap.XL01.responseComplete;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

public class Body {
    @JacksonXmlProperty(localName = "XL01Draw001Response", namespace = "http://cms.middleware.bankbii.com/")
    private XL01Draw001Response xl01Draw001Response;

    public XL01Draw001Response getXl01Draw001Response() {
        return xl01Draw001Response;
    }

    public void setXl01Draw001Response(XL01Draw001Response xl01Draw001Response) {
        this.xl01Draw001Response = xl01Draw001Response;
    }
}
