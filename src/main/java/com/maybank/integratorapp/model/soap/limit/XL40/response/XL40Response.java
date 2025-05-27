package com.maybank.integratorapp.model.soap.limit.XL40.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class XL40Response {
    @JacksonXmlProperty(localName = "CMS_XL40Response",namespace = "http://cms.middleware.bankbii.com/")
    private CMS_XL40Response cmsXl40Response  ;

    public CMS_XL40Response getCmsXl40Response() {
        return cmsXl40Response;
    }

    public void setCmsXl40Response(CMS_XL40Response cmsXl40Response) {
        this.cmsXl40Response = cmsXl40Response;
    }
}
