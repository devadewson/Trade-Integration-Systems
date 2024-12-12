package com.maybank.integratorapp.model.mq.reservation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.mq.customersearch.request.Credentials;

public class ReservationsResponse {

    @JacksonXmlProperty(localName = "FacilityIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String facilityIdentifier;
    @JacksonXmlProperty(localName = "FacilitySequence", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String facilitySequence;
    @JacksonXmlProperty(localName = "ReservationIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String reservationIdentifier;
    @JacksonXmlProperty(localName = "ReservationSequence", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String reservationSequence;
    @JacksonXmlProperty(localName = "Customer", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customer;
    @JacksonXmlProperty(localName = "ReservationResponseDetailss")
    private ReservationResponseDetailss reservationResponseDetailss ;
    @JacksonXmlProperty(localName = "ReservationResponseExtraDetailss")
    private ReservationResponseExtraDetailss reservationResponseExtraDetailss ;
    @JacksonXmlProperty(localName = "FacilityExposureIdentifier")
    private String facilityExposureIdentifier ;


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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ReservationResponseDetailss getReservationResponseDetailss() {
        return reservationResponseDetailss;
    }

    public void setReservationResponseDetailss(ReservationResponseDetailss reservationResponseDetailss) {
        this.reservationResponseDetailss = reservationResponseDetailss;
    }

    public ReservationResponseExtraDetailss getReservationResponseExtraDetailss() {
        return reservationResponseExtraDetailss;
    }

    public String getFacilityExposureIdentifier() {
        return facilityExposureIdentifier;
    }

    public void setFacilityExposureIdentifier(String facilityExposureIdentifier) {
        this.facilityExposureIdentifier = facilityExposureIdentifier;
    }

    public void setReservationResponseExtraDetailss(ReservationResponseExtraDetailss reservationResponseExtraDetailss) {
        this.reservationResponseExtraDetailss = reservationResponseExtraDetailss;


    }
}
