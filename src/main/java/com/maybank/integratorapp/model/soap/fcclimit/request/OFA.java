package com.maybank.integratorapp.model.soap.fcclimit.request;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "OFA")
public class OFA {
    @JacksonXmlProperty(localName = "ServiceName")
    private String serviceName;

    @JacksonXmlProperty(localName = "Request")
    private RequestData request;

    // Getters and Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public RequestData getRequest() {
        return request;
    }

    public void setRequest(RequestData request) {
        this.request = request;
    }
}
