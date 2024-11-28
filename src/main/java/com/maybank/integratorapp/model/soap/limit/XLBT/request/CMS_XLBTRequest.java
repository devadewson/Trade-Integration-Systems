package com.maybank.integratorapp.model.soap.limit.XLBT.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CMS_XLBTRequest")
public class CMS_XLBTRequest {
    @JacksonXmlProperty(localName = "aid")
    private String aid;
    @JacksonXmlProperty(localName = "cifno")
    private String cifno;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }
}
