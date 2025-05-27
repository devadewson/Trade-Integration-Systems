package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
public class CustometExtraData {
    @JacksonXmlProperty(namespace = "urn:custom.service.ti.apps.tiplus2.misys.com", localName = "LineOfBusiness")
    private String lineOfBusiness;
    @JacksonXmlProperty(namespace = "urn:custom.service.ti.apps.tiplus2.misys.com", localName = "TaxID")
    private String taxId;
    @JacksonXmlProperty(namespace = "urn:custom.service.ti.apps.tiplus2.misys.com", localName = "CIFNO")
    private String cifNumber;

    public String getLineOfBusiness() {
        return lineOfBusiness;
    }

    public void setLineOfBusiness(String _lineOfBusiness) {
        lineOfBusiness = _lineOfBusiness;
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
