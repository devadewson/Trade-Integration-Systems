package com.maybank.integratorapp.model.mq.reservation.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class PostingAmount {
    @JacksonXmlProperty(localName = "Currency", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String currency;
    @JacksonXmlProperty(localName = "Amount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
