package com.maybank.integratorapp.model.mq.limitutilization.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.mq.reservationsreversal.request.RequestHeader;

public class ServiceRequestChild {
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader requestHeader ;

    @JacksonXmlProperty(localName = "Exposure")
    private Exposure exposure ;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public Exposure getExposure() {
        return exposure;
    }

    public void setExposure(Exposure exposure) {
        this.exposure = exposure;
    }
}
