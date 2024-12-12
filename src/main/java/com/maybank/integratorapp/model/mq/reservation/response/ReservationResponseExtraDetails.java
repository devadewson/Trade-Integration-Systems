package com.maybank.integratorapp.model.mq.reservation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ReservationResponseExtraDetails {
    @JacksonXmlProperty(localName = "Name")
    private String name;
    @JacksonXmlProperty(localName = "Value")
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
