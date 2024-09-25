package com.maybank.integratorapp.model.mq.accountinquiry.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

//@Getter
//@Setter
//@XmlRootElement(name = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
//@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")

public class ServiceRequest {
    //    @XmlElement(name = "RequestHeader")
//@XmlElement(name = "RequestHeader")
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader requestHeader;
    //    @XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
//@XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JacksonXmlProperty(localName = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private AvailBalRequest availBalRequest;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }
    public AvailBalRequest getAvailBALRequest() { return availBalRequest; }
    public void setAvailBALRequest(AvailBalRequest value) { this.availBalRequest = value; }

}

