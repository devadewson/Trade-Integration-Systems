package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CorporateAccessMappings {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ID")

    private String id;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }
}
