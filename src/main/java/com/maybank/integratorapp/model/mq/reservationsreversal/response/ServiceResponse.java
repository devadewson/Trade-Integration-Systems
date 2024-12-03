package com.maybank.integratorapp.model.mq.reservationsreversal.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ServiceResponse {
    @JacksonXmlProperty(localName = "ResponseHeader")
    private ResponseHeader responseHeader;
    @JacksonXmlProperty(localName = "ReservationsReversalResponse")
    private ReservationsReversalResponse reservationsReversalResponse;

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public ReservationsReversalResponse getReservationsReversalResponse() {
        return reservationsReversalResponse;
    }

    public void setReservationsReversalResponse(ReservationsReversalResponse reservationsReversalResponse) {
        this.reservationsReversalResponse = reservationsReversalResponse;
    }
}
