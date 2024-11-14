package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityResponseExtraDetailss {
    @JacksonXmlProperty(localName = "FacilityResponseExtraDetails")
    private FacilityResponseExtraDetails facilityResponseExtraDetails;

    public FacilityResponseExtraDetails getFacilityResponseExtraDetails() {
        return facilityResponseExtraDetails;
    }

    public void setFacilityResponseExtraDetails(FacilityResponseExtraDetails facilityResponseExtraDetails) {
        this.facilityResponseExtraDetails = facilityResponseExtraDetails;
    }
}
