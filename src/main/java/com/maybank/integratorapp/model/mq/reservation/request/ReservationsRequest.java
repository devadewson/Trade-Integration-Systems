package com.maybank.integratorapp.model.mq.reservation.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.customersearch.request.RequestHeader;

@JacksonXmlRootElement(localName = "ReservationsRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ReservationsRequest {
    @JacksonXmlProperty(localName = "ReservationRequestDetails")
    private ReservationRequestDetails reservationRequestDetails ;
    @JacksonXmlProperty(localName = "ExtraDataFieldss")
    private ExtraDataFieldss extraDataFieldss  ;

    public ReservationRequestDetails getReservationRequestDetails() {
        return reservationRequestDetails;
    }

    public void setReservationRequestDetails(ReservationRequestDetails reservationRequestDetails) {
        this.reservationRequestDetails = reservationRequestDetails;
    }

    public ExtraDataFieldss getExtraDataFieldss() {
        return extraDataFieldss;
    }

    public void setExtraDataFieldss(ExtraDataFieldss extraDataFieldss) {
        this.extraDataFieldss = extraDataFieldss;
    }
}
