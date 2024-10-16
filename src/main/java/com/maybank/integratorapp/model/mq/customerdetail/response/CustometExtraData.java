package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
public class CustometExtraData {
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "LineOfBusiness")
    private String LineOfBusiness;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "TaxId")
    private String taxId;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "CifNumber")
    private String cifNumber;

    public String getLineOfBusiness() {
        return LineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        LineOfBusiness = lineOfBusiness;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getCifNumber() {
        return cifNumber;
    }

    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }
}
