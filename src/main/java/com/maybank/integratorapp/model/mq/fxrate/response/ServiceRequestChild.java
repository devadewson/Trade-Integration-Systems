package com.maybank.integratorapp.model.mq.fxrate.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@XmlRootElement(name = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
//@XmlAccessorType(XmlAccessType.FIELD)
//@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequestChild {
    public ServiceRequestChild(){
        this.fxRate = new FXRate();
        this.requestHeader = new RequestHeader();
    }
    //    @XmlElement(name = "RequestHeader")
//@XmlElement(name = "RequestHeader")
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader requestHeader;
    //    @XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
//@XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JacksonXmlProperty(localName = "FXRate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FXRate fxRate;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }


    public FXRate getFxRate() {
        return fxRate;
    }

    public void setFxRate(FXRate fxRate) {
        this.fxRate = fxRate;
    }
}

