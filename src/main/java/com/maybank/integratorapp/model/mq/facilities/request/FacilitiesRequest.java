package com.maybank.integratorapp.model.mq.facilities.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilitiesRequest {
    @JacksonXmlProperty(localName = "FacilityRequestDetails")
    private FacilityRequestDetails facilityRequestDetails;
    @JacksonXmlProperty(localName = "ExtraDataFieldss")
    private ExtraDataFieldss extraDataFieldss;
    @JacksonXmlProperty(localName = "Filterss")
    private Filterss filterss;
    @JacksonXmlProperty(localName = "ExpectedColumns")
    private ExpectedColumns expectedColumns;
    @JacksonXmlProperty(localName = "SortOn")
    private String sortOn;

    public FacilityRequestDetails getFacilityRequestDetails() {
        return facilityRequestDetails;
    }

    public void setFacilityRequestDetails(FacilityRequestDetails facilityRequestDetails) {
        this.facilityRequestDetails = facilityRequestDetails;
    }

    public ExtraDataFieldss getExtraDataFieldss() {
        return extraDataFieldss;
    }

    public void setExtraDataFieldss(ExtraDataFieldss extraDataFieldss) {
        this.extraDataFieldss = extraDataFieldss;
    }

    public Filterss getFilterss() {
        return filterss;
    }

    public void setFilterss(Filterss filterss) {
        this.filterss = filterss;
    }

    public ExpectedColumns getExpectedColumns() {
        return expectedColumns;
    }

    public void setExpectedColumns(ExpectedColumns expectedColumns) {
        this.expectedColumns = expectedColumns;
    }

    public String getSortOn() {
        return sortOn;
    }

    public void setSortOn(String sortOn) {
        this.sortOn = sortOn;
    }
}
