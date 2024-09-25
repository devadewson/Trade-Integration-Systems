package com.maybank.integratorapp.model.mq.swiftin.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class EmbeddedItems {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "EmbeddedItems")
    private List<EmbeddedItem> EmbeddedItems;

    public List<EmbeddedItem> getEmbeddedItems() {
        return EmbeddedItems;
    }

    public void setEmbeddedItems(List<EmbeddedItem> embeddedItems) {
        EmbeddedItems = embeddedItems;
    }
}
