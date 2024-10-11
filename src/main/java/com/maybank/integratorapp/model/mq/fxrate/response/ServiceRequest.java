package com.maybank.integratorapp.model.mq.fxrate.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.batchposting.request.BatchRequest;

import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@XmlRootElement(name = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
//@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequest {
    public ServiceRequest(){
        this.fxRate = new ArrayList<FXRate>();
        this.requestHeader = new RequestHeader();
    }
    //    @XmlElement(name = "RequestHeader")
//@XmlElement(name = "RequestHeader")
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader requestHeader;
    //    @XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
//@XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "FXRate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private List<FXRate> fxRate;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }

    public List<FXRate> getFxRate() {
        return fxRate;
    }

    public void setFxRate(List<FXRate> fxRate) {
        this.fxRate = fxRate;
    }
}

