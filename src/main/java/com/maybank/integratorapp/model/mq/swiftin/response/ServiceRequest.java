package com.maybank.integratorapp.model.mq.swiftin.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.swiftout.request.SwiftOut;

//@Getter
//@Setter
//@XmlRootElement(name = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
//@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequest {
    //    @XmlElement(name = "RequestHeader")
//@XmlElement(name = "RequestHeader")
    public ServiceRequest(){
        this.requestHeader = new RequestHeader();
        this.swiftIn = new SwiftIn();
    }
    @JacksonXmlProperty(localName = "RequestHeader",namespace = "urn:control.services.tiplus2.misys.com")
    private RequestHeader requestHeader;
    //    @XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
//@XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JacksonXmlProperty(localName = "SwiftIn", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private SwiftIn swiftIn;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }

    public SwiftIn getSwiftIn() {
        return swiftIn;
    }

    public void setSwiftIn(SwiftIn swiftIn) {
        this.swiftIn = swiftIn;
    }
}

