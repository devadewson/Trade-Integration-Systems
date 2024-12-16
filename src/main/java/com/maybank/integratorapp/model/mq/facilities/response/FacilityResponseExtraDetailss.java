package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityResponseExtraDetailss {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "FacilityResponseExtraDetails", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FacilityResponseExtraDetails facilityResponseExtraDetails;

    public FacilityResponseExtraDetails getFacilityResponseExtraDetails() {
        return facilityResponseExtraDetails;
    }

    public void setFacilityResponseExtraDetails(FacilityResponseExtraDetails facilityResponseExtraDetails) {
        this.facilityResponseExtraDetails = facilityResponseExtraDetails;
    }
}
