package com.maybank.integratorapp.model.mq.swiftin.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.mq.swiftout.request.Messages;

import java.util.List;

public class SwiftIn {
    public SwiftIn(){
//        this.embeddedItems = new EmbeddedItems();
    }
    @JacksonXmlProperty(localName = "Acknowledged", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String acknowledged;

    @JacksonXmlProperty(localName = "Message", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String message;

    @JacksonXmlProperty(localName = "EmbeddedItems", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private EmbeddedItems embeddedItems;

    public String getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(String acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmbeddedItems getEmbeddedItems() {
        return embeddedItems;
    }

    public void setEmbeddedItems(EmbeddedItems embeddedItems) {
        this.embeddedItems = embeddedItems;
    }
}
