package com.maybank.integratorapp.model.mq.reservation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.mq.customersearch.request.RequestHeader;

public class ReservationResponseDetailss {

    @JacksonXmlProperty(localName = "ReservationResponseDetails", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private ReservationResponseDetails reservationResponseDetails ;

    public ReservationResponseDetails getReservationResponseDetails() {
        return reservationResponseDetails;
    }

    public void setReservationResponseDetails(ReservationResponseDetails reservationResponseDetails) {
        this.reservationResponseDetails = reservationResponseDetails;
    }
}
