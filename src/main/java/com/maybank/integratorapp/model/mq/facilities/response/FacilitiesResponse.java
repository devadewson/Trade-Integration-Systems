package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilitiesResponse {
    @JacksonXmlProperty(localName = "FacilityDetailss" , namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FacilityDetailss facilityDetailss;
    @JacksonXmlProperty(localName = "FacilityResponseExtraDetailss" , namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
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
