package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class FacilityDetailss {
    @JacksonXmlProperty(localName = "FacilityDetails" , namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private List<FacilityDetails> facilityDetails;

    public List<FacilityDetails> getFacilityDetails() {
        return facilityDetails;
    }

    public void setFacilityDetails(List<FacilityDetails> facilityDetails) {
        this.facilityDetails = facilityDetails;
    }
}
