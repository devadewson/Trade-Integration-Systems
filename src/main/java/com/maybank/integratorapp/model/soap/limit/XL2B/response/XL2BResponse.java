package com.maybank.integratorapp.model.soap.limit.XL2B.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class XL2BResponse {
    @JacksonXmlProperty(localName = "CMS_XL2BResponse",namespace = "http://cms.middleware.bankbii.com/")
    private CMS_XL2BResponse cmsXl2BResponse ;

    public CMS_XL2BResponse getCmsXl2BResponse() {
        return cmsXl2BResponse;
    }

    public void setCmsXl2BResponse(CMS_XL2BResponse cmsXl2BResponse) {
        this.cmsXl2BResponse = cmsXl2BResponse;
    }
}
