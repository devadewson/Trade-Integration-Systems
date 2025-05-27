package com.maybank.integratorapp.model.mq.reservation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceResponse", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceResponse {
    @JacksonXmlProperty(localName = "ResponseHeader", namespace = "urn:control.services.tiplus2.misys.com")
    private ResponseHeader responseHeader;
    @JacksonXmlProperty(localName = "ReservationsResponse", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
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
