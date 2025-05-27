package com.maybank.integratorapp.model.mq.fxratespot.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ItemRequest {
    @JacksonXmlProperty(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
    private ServiceRequestChild serviceRequestChild;

    public ServiceRequestChild getServiceRequestChild() {
        return serviceRequestChild;
    }

    public void setServiceRequestChild(ServiceRequestChild serviceRequestChild) {
        this.serviceRequestChild = serviceRequestChild;
    }
}
