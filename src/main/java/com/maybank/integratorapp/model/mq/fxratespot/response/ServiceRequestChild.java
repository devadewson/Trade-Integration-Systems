package com.maybank.integratorapp.model.mq.fxratespot.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

//@Getter
//@Setter
//@XmlRootElement(name = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
//@XmlAccessorType(XmlAccessType.FIELD)
//@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceRequestChild {
    public ServiceRequestChild(){
        this.currencySpotRate = new CurrencySpotRate();
        this.requestHeader = new RequestHeader();
    }
    //    @XmlElement(name = "RequestHeader")
//@XmlElement(name = "RequestHeader")
    @JacksonXmlProperty(localName = "RequestHeader", namespace = "urn:control.services.tiplus2.misys.com")
    private RequestHeader requestHeader;
    //    @XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
//@XmlElement(name = "AvailBalRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    @JacksonXmlProperty(localName = "CurrencySpotRate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private CurrencySpotRate currencySpotRate;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }

    public CurrencySpotRate getCurrencySpotRate() {
        return currencySpotRate;
    }

    public void setCurrencySpotRate(CurrencySpotRate currencySpotRate) {
        this.currencySpotRate = currencySpotRate;
    }
}

