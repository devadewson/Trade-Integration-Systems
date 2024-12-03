package com.maybank.integratorapp.model.mq.reservationsreversal.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequest {
    @JacksonXmlProperty(localName = "RequestHeader")
    private com.maybank.integratorapp.model.mq.reservationsreversal.request.RequestHeader requestHeader ;
    @JacksonXmlProperty(localName = "ReservationsReversalRequest")
    private ReservationsReversalRequest reservationsReversalRequest;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public ReservationsReversalRequest getReservationsReversalRequest() {
        return reservationsReversalRequest;
    }

    public void setReservationsReversalRequest(ReservationsReversalRequest reservationsReversalRequest) {
        this.reservationsReversalRequest = reservationsReversalRequest;
    }
}
