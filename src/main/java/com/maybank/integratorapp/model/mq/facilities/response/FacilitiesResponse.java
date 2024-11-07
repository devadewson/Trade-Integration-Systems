package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilitiesResponse {
    @JacksonXmlProperty(localName = "FacilityDetailss")
    private FacilityDetailss facilityDetailss;
    @JacksonXmlProperty(localName = "FacilityResponseExtraDetailss")
    private FacilityResponseExtraDetailss facilityResponseExtraDetailss;

    public FacilityDetailss getFacilityDetailss() {
        return facilityDetailss;
    }

    public void setFacilityDetailss(FacilityDetailss facilityDetailss) {
        this.facilityDetailss = facilityDetailss;
    }

    public FacilityResponseExtraDetailss getFacilityResponseExtraDetailss() {
        return facilityResponseExtraDetailss;
    }

    public void setFacilityResponseExtraDetailss(FacilityResponseExtraDetailss facilityResponseExtraDetailss) {
        this.facilityResponseExtraDetailss = facilityResponseExtraDetailss;
    }
}
