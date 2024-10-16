package com.maybank.integratorapp.model.mq.customerdetail.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class SwiftDetails {
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "MainBankingEntity")
    private String mainBankingEntity;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "SwiftAddress")
    private String swiftAddress;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "Authenticated")
    private String authenticated;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "Blocked")
    private String blocked;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "Closed")
    private String closed;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "TransliterationRequired")
    private String transliterationRequired;

    public String getMainBankingEntity() { return mainBankingEntity; }
    public void setMainBankingEntity(String value) { this.mainBankingEntity = value; }

    public String getSwiftAddress() { return swiftAddress; }
    public void setSwiftAddress(String value) { this.swiftAddress = value; }

    public String getAuthenticated() { return authenticated; }
    public void setAuthenticated(String value) { this.authenticated = value; }

    public String getBlocked() { return blocked; }
    public void setBlocked(String value) { this.blocked = value; }

    public String getClosed() { return closed; }
    public void setClosed(String value) { this.closed = value; }

    public String getTransliterationRequired() { return transliterationRequired; }
    public void setTransliterationRequired(String value) { this.transliterationRequired = value; }

}