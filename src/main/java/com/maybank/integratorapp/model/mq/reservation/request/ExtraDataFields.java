package com.maybank.integratorapp.model.mq.reservation.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ExtraDataFields {
    @JacksonXmlProperty(localName = "Name", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String name;
    @JacksonXmlProperty(localName = "Value", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
