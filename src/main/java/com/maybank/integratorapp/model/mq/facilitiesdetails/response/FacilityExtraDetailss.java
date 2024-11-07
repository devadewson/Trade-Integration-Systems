package com.maybank.integratorapp.model.mq.facilitiesdetails.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityExtraDetailss {
    @JacksonXmlProperty(localName = "FacilityExtraDetails")
    private FacilityExtraDetails facilityExtraDetails;

    public FacilityExtraDetails getFacilityExtraDetails() {
        return facilityExtraDetails;
    }

    public void setFacilityExtraDetails(FacilityExtraDetails facilityExtraDetails) {
        this.facilityExtraDetails = facilityExtraDetails;
    }
}
