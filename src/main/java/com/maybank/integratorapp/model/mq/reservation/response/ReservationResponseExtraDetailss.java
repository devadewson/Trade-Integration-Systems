package com.maybank.integratorapp.model.mq.reservation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class ReservationResponseExtraDetailss {

    public ReservationResponseExtraDetailss() { this.reservationResponseExtraDetails = new ArrayList<ReservationResponseExtraDetails>();}

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ReservationResponseExtraDetails")
    private List<ReservationResponseExtraDetails> reservationResponseExtraDetails;

    public List<ReservationResponseExtraDetails> getReservationResponseExtraDetails() {
        return reservationResponseExtraDetails;
    }

    public void setReservationResponseExtraDetails(List<ReservationResponseExtraDetails> reservationResponseExtraDetails) {
        this.reservationResponseExtraDetails = reservationResponseExtraDetails;
    }
}
