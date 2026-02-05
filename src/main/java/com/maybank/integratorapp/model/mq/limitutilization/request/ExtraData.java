package com.maybank.integratorapp.model.mq.limitutilization.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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
    @JacksonXmlProperty(localName = "LineOfBusiness", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")
    private String lineOfBusiness;
    @JacksonXmlProperty(localName = "CustBranchFacility", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")

    private String custBranchFacility;
    @JacksonXmlProperty(localName = "LinkedClaim", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")

    private String linkedClaim;

    @JacksonXmlProperty(localName = "PaymentOption", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")

    private String paymentOption;

    @JacksonXmlProperty(localName = "LinkedClaimResId", namespace = "urn:custom.service.ti.apps.tiplus2.misys.com")

    private String linkedClaimResId;


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

    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        this.lineOfBusiness = lineOfBusiness;
    }

    public String getCustBranchFacility() {
        return custBranchFacility;
    }

    public void setCustBranchFacility(String custBranchFacility) {
        this.custBranchFacility = custBranchFacility;
    }

    public String getLinkedClaim() {
        return linkedClaim;
    }

    public void setLinkedClaim(String linkedClaim) {
        this.linkedClaim = linkedClaim;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getLinkedClaimResId() {
        return linkedClaimResId;
    }

    public void setLinkedClaimResId(String linkedClaimResId) {
        this.linkedClaimResId = linkedClaimResId;
    }
}
