package com.maybank.integratorapp.model.mq.fxrate.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ItemRequest {
    @JacksonXmlProperty(localName = "ServiceRequestChild")
    private ServiceRequestChild serviceRequestChild;

    public ServiceRequestChild getServiceRequestChild() {
        return serviceRequestChild;
    }

    public void setServiceRequestChild(ServiceRequestChild serviceRequestChild) {
        this.serviceRequestChild = serviceRequestChild;
    }
}
