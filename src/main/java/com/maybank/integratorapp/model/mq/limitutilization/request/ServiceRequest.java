package com.maybank.integratorapp.model.mq.limitutilization.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.reservationsreversal.request.RequestHeader;
import com.maybank.integratorapp.model.mq.reservationsreversal.request.ReservationsReversalRequest;

@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequest {
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader  requestHeader ;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "BatchRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private BatchRequest batchRequest;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }
    public BatchRequest getBatchRequest() { return batchRequest; }
    public void setBatchRequest(BatchRequest value) { this.batchRequest = value; }
}
