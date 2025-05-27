package com.maybank.integratorapp.model.soap.XL31.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class XL31Response {
    @JacksonXmlProperty(localName = "CMS_XL31Response",namespace = "http://cms.middleware.bankbii.com/")
    private CMS_XL31Response cmsXl31Response ;

    public CMS_XL31Response getCmsXl31Response() {
        return cmsXl31Response;
    }

    public void setCmsXl31Response(CMS_XL31Response cmsXl31Response) {
        this.cmsXl31Response = cmsXl31Response;
    }
}
