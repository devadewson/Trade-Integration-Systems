package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityDetailss {
    @JacksonXmlProperty(localName = "FacilityDetails")
    private FacilityDetails facilityDetails;

    public FacilityDetails getFacilityDetails() {
        return facilityDetails;
    }

    public void setFacilityDetails(FacilityDetails facilityDetails) {
        this.facilityDetails = facilityDetails;
    }
}
