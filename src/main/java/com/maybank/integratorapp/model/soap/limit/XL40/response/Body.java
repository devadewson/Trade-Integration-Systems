package com.maybank.integratorapp.model.soap.limit.XL40.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
    @JacksonXmlProperty(localName = "XL40Response", namespace = "http://cms.middleware.bankbii.com/")
    private XL40Response xl40Response;

    public XL40Response getXl40Response() {
        return xl40Response;
    }

    public void setXl40Response(XL40Response xl40Response) {
        this.xl40Response = xl40Response;
    }

}
