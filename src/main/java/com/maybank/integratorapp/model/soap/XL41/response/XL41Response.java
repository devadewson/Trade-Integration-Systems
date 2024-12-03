package com.maybank.integratorapp.model.soap.XL41.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class XL41Response {
    @JacksonXmlProperty(localName = "CMS_XL41Response",namespace = "http://cms.middleware.bankbii.com/")
    private CMS_XL41Response cmsXl41Response  ;

    public CMS_XL41Response getCmsXl41Response() {
        return cmsXl41Response;
    }

    public void setCmsXl41Response(CMS_XL41Response cmsXl41Response) {
        this.cmsXl41Response = cmsXl41Response;
    }
}
