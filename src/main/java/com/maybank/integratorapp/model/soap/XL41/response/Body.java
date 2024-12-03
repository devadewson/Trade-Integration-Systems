package com.maybank.integratorapp.model.soap.XL41.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
    @JacksonXmlProperty(localName = "XL41Response", namespace = "http://cms.middleware.bankbii.com/")
    private XL41Response xl41Response ;

    public XL41Response getXl41Response() {
        return xl41Response;
    }

    public void setXl41Response(XL41Response xl41Response) {
        this.xl41Response = xl41Response;
    }

}
