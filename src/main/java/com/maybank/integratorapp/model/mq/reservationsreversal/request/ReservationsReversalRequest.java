package com.maybank.integratorapp.model.mq.reservationsreversal.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ReservationsReversalRequest {
    @JacksonXmlProperty(localName = "FacilityIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String facilityIdentifier;
    @JacksonXmlProperty(localName = "FacilitySequence", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String facilitySequence;
    @JacksonXmlProperty(localName = "ReservationIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String reservationIdentifier;
    @JacksonXmlProperty(localName = "ReservationSequence", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String reservationSequence;
    @JacksonXmlProperty(localName = "Product", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String product;
    @JacksonXmlProperty(localName = "ProductSubType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String productSubType;
    @JacksonXmlProperty(localName = "MasterReference", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String masterReference;
    @JacksonXmlProperty(localName = "EventReference", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String eventReference;
    @JacksonXmlProperty(localName = "Customer", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customer;
    @JacksonXmlProperty(localName = "Branch", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String branch;
    @JacksonXmlProperty(localName = "PostingKey", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingKey;

    public String getFacilityIdentifier() {
        return facilityIdentifier;
    }

    public void setFacilityIdentifier(String facilityIdentifier) {
        this.facilityIdentifier = facilityIdentifier;
    }

    public String getFacilitySequence() {
        return facilitySequence;
    }

    public void setFacilitySequence(String facilitySequence) {
        this.facilitySequence = facilitySequence;
    }

    public String getReservationIdentifier() {
        return reservationIdentifier;
    }

    public void setReservationIdentifier(String reservationIdentifier) {
        this.reservationIdentifier = reservationIdentifier;
    }

    public String getReservationSequence() {
        return reservationSequence;
    }

    public void setReservationSequence(String reservationSequence) {
        this.reservationSequence = reservationSequence;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductSubType() {
        return productSubType;
    }

    public void setProductSubType(String productSubType) {
        this.productSubType = productSubType;
    }

    public String getMasterReference() {
        return masterReference;
    }

    public void setMasterReference(String masterReference) {
        this.masterReference = masterReference;
    }

    public String getEventReference() {
        return eventReference;
    }

    public void setEventReference(String eventReference) {
        this.eventReference = eventReference;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPostingKey() {
        return postingKey;
    }

    public void setPostingKey(String postingKey) {
        this.postingKey = postingKey;
    }
}
