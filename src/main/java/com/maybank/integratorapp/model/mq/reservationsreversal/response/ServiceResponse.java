package com.maybank.integratorapp.model.mq.reservationsreversal.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceResponse",namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceResponse {
    @JacksonXmlProperty(localName = "ResponseHeader", namespace = "urn:control.services.tiplus2.misys.com")
    private ResponseHeader responseHeader;
    @JacksonXmlProperty(localName = "ReservationsReversalResponse", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JsonInclude(JsonInclude.Include.ALWAYS)
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
