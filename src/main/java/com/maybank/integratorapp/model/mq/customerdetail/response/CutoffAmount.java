package com.maybank.integratorapp.model.mq.customerdetail.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CutoffAmount {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Amount")

    private String amount;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Currency")

    private String currency;

    public String getAmount() { return amount; }
    public void setAmount(String value) { this.amount = value; }

    public String getCurrency() { return currency; }
    public void setCurrency(String value) { this.currency = value; }
}
