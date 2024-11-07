package com.maybank.integratorapp.model.mq.facilitiesdetails.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityDetailsRequest {
    @JacksonXmlProperty(localName = "Customer")
    private String customer;
    @JacksonXmlProperty(localName = "FacilityID")
    private String facilityID;
    @JacksonXmlProperty(localName = "FacilitySequence")
    private String facilitySequence;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(String facilityID) {
        this.facilityID = facilityID;
    }

    public String getFacilitySequence() {
        return facilitySequence;
    }

    public void setFacilitySequence(String facilitySequence) {
        this.facilitySequence = facilitySequence;
    }
}
