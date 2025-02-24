package com.maybank.integratorapp.model.mq.fxratespot.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@XmlRootElement(name = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
//@XmlAccessorType(XmlAccessType.FIELD)
@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequest {
    //    @XmlElement(name = "RequestHeader")
//@XmlElement(name = "RequestHeader")
    public ServiceRequest(){
        this.requestHeader= new RequestHeader();
        this.itemRequest = new ArrayList<>();
    }
    @JacksonXmlProperty(localName = "RequestHeader", namespace = "urn:control.services.tiplus2.misys.com")
    private RequestHeader requestHeader;
    //    @XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
//@XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ItemRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private List<ItemRequest> itemRequest;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }

    public List<ItemRequest> getItemRequest() {
        return itemRequest;
    }

    public void setItemRequest(List<ItemRequest> itemRequest) {
        this.itemRequest = itemRequest;
    }
}

