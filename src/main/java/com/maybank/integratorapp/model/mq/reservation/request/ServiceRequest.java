package com.maybank.integratorapp.model.mq.reservation.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequest {
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader requestHeader;
    @JacksonXmlProperty(localName = "ReservationsRequest")
    private ReservationsRequest reservationsRequest;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public ReservationsRequest getReservationsRequest() {
        return reservationsRequest;
    }

    public void setReservationsRequest(ReservationsRequest reservationsRequest) {
        this.reservationsRequest = reservationsRequest;
    }
}
