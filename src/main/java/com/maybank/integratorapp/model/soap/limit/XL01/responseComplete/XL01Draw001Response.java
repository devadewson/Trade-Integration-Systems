package com.maybank.integratorapp.model.soap.limit.XL01.responseComplete;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class XL01Draw001Response {
    @JacksonXmlProperty(localName = "CMS_XL01Draw001Response" , namespace = "http://cms.middleware.bankbii.com/")
    private CMS_XL01Draw001Response cmsXL01Draw001Response;

    public CMS_XL01Draw001Response getCmsXL01Draw001Response() {
        return cmsXL01Draw001Response;
    }

    public void setCmsXL01Draw001Response(CMS_XL01Draw001Response cmsXL01Draw001Response) {
        this.cmsXL01Draw001Response = cmsXL01Draw001Response;
    }
}
