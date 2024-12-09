package com.maybank.integratorapp.model.soap.XLBT.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

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
