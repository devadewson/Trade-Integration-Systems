package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
public class TiCustomerExtraData {
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "ConventionalBranchFacility")
    private String conventionalBranchFacility;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "IslamicBranchFacility")
    private String islamicBranchFacility;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "TlxAnswerBack")
    private String tlxAnswerBack;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "RegionCode")
    private String regionCode;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "SicCode")
    private String sicCode;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "ParentSubsidiaryStatus")
    private String parentSubsidiaryStatus;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "ParentsAccount")
    private String parentsAccount;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "TaxPayerId")
    private String taxPayerId;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "WicNo")
    private String wicNo;

    public String getConventionalBranchFacility() {
        return conventionalBranchFacility;
    }

    public void setConventionalBranchFacility(String conventionalBranchFacility) {
        this.conventionalBranchFacility = conventionalBranchFacility;
    }

    public String getIslamicBranchFacility() {
        return islamicBranchFacility;
    }

    public void setIslamicBranchFacility(String islamicBranchFacility) {
        this.islamicBranchFacility = islamicBranchFacility;
    }

    public String getTlxAnswerBack() {
        return tlxAnswerBack;
    }

    public void setTlxAnswerBack(String tlxAnswerBack) {
        this.tlxAnswerBack = tlxAnswerBack;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSicCode() {
        return sicCode;
    }

    public void setSicCode(String sicCode) {
        this.sicCode = sicCode;
    }

    public String getParentSubsidiaryStatus() {
        return parentSubsidiaryStatus;
    }

    public void setParentSubsidiaryStatus(String parentSubsidiaryStatus) {
        this.parentSubsidiaryStatus = parentSubsidiaryStatus;
    }

    public String getParentsAccount() {
        return parentsAccount;
    }

    public void setParentsAccount(String parentsAccount) {
        this.parentsAccount = parentsAccount;
    }

    public String getTaxPayerId() {
        return taxPayerId;
    }

    public void setTaxPayerId(String taxPayerId) {
        this.taxPayerId = taxPayerId;
    }

    public String getWicNo() {
        return wicNo;
    }

    public void setWicNo(String wicNo) {
        this.wicNo = wicNo;
    }
}
