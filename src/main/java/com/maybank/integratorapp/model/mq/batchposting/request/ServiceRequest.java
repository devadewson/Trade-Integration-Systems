package com.maybank.integratorapp.model.mq.batchposting.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.accountinquiry.request.AvailBalRequest;
//import com.maybank.integratorapp.model.mq.batchposting.request.Posting;
import com.maybank.integratorapp.model.mq.batchposting.request.RequestHeader;

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
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "BatchRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private BatchRequest batchRequest;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }
    public BatchRequest getBatchRequest() { return batchRequest; }
    public void setBatchRequest(BatchRequest value) { this.batchRequest = value; }

}

