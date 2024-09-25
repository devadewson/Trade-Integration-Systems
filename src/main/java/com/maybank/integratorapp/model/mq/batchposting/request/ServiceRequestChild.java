package com.maybank.integratorapp.model.mq.batchposting.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

//@Getter
//@Setter
//@XmlRootElement(name = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
//@XmlAccessorType(XmlAccessType.FIELD)

public class ServiceRequestChild {
    //    @XmlElement(name = "RequestHeader")
//@XmlElement(name = "RequestHeader")
    @JacksonXmlProperty(localName = "RequestHeader")
    private com.maybank.integratorapp.model.mq.batchposting.request.RequestHeader requestHeader;
    //    @XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
//@XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JacksonXmlProperty(localName = "Posting", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private com.maybank.integratorapp.model.mq.batchposting.request.Posting posting;

    public com.maybank.integratorapp.model.mq.batchposting.request.RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }
    public com.maybank.integratorapp.model.mq.batchposting.request.Posting getPosting() { return posting; }
    public void setPosting(Posting value) { this.posting = value; }



}