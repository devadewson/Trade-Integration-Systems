package com.maybank.integratorapp.model.mq.limitutilization.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class BatchRequest {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ServiceRequest")
    private List<ServiceRequestChild> serviceRequestChild;

    public List<ServiceRequestChild> getServiceRequestChild() {
        return serviceRequestChild;
    }

    public void setServiceRequestChild(List<ServiceRequestChild> serviceRequestChild) {
        this.serviceRequestChild = serviceRequestChild;
    }
}
