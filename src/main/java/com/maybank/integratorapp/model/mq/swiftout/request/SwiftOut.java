package com.maybank.integratorapp.model.mq.swiftout.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.component.MessagePublisher;

public class SwiftOut {

    public SwiftOut(){
        this.messages = new Messages();
        this.embeddedItems = new EmbeddedItems();
    }
    @JacksonXmlProperty(localName = "Messages", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private Messages messages;
    @JacksonXmlProperty(localName = "FileIdentification", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String FileIdentification;
    @JacksonXmlProperty(localName = "EmbeddedItems", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private EmbeddedItems embeddedItems;

    public Messages getMessages() {
        return messages;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public String getFileIdentification() {
        return FileIdentification;
    }

    public void setFileIdentification(String fileIdentification) {
        FileIdentification = fileIdentification;
    }

    public EmbeddedItems getEmbeddedItems() {
        return embeddedItems;
    }

    public void setEmbeddedItems(EmbeddedItems embeddedItems) {
        this.embeddedItems = embeddedItems;
    }
}
