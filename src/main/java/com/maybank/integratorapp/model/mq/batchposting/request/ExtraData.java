package com.maybank.integratorapp.model.mq.batchposting.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ExtraData {

    @JacksonXmlProperty(localName = "HoldCodeOverride", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")
    private String holdCodeOverride;
    @JacksonXmlProperty(localName = "InSuffientBalanceOverride", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")

    private String inSuffientBalanceOverride;
    @JacksonXmlProperty(localName = "MessageId", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")

    private String messageId;
    @JacksonXmlProperty(localName = "IDCurrencyNumber", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")

    private String iDCurrencyNumber;

    @JacksonXmlProperty(localName = "GroupID", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")
    private String groupID;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getHoldCodeOverride() {
        return holdCodeOverride;
    }

    public void setHoldCodeOverride(String holdCodeOverride) {
        this.holdCodeOverride = holdCodeOverride;
    }

    public String getInSuffientBalanceOverride() {
        return inSuffientBalanceOverride;
    }

    public void setInSuffientBalanceOverride(String inSuffientBalanceOverride) {
        this.inSuffientBalanceOverride = inSuffientBalanceOverride;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getiDCurrencyNumber() {
        return iDCurrencyNumber;
    }

    public void setiDCurrencyNumber(String iDCurrencyNumber) {
        this.iDCurrencyNumber = iDCurrencyNumber;
    }
}
