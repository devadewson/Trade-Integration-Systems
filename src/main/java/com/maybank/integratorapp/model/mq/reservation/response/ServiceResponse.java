package com.maybank.integratorapp.model.mq.reservation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceResponse", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceResponse {
    @JacksonXmlProperty(localName = "ResponseHeader")
    private ResponseHeader responseHeader;
    @JacksonXmlProperty(localName = "ReservationsResponse")
    private ReservationsResponse reservationsResponse;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public ReservationsResponse getReservationsResponse() {
        return reservationsResponse;
    }

    public void setReservationsResponse(ReservationsResponse reservationsResponse) {
        this.reservationsResponse = reservationsResponse;
    }
}
