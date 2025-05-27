package com.maybank.integratorapp.model.mq.fxrate.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
@JacksonXmlRootElement(localName = "ServiceResponse")
public class ServiceResponse {
    @JacksonXmlProperty(localName = "ResponseHeader")
    private ResponseHeader responseHeader;

    @JacksonXmlProperty(localName = "FXRate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FXRate fxRate;

    public ServiceResponse(){
        ResponseHeader header = new ResponseHeader();
        header.setCredentials(new Credentials());
        this.responseHeader = header;
        this.fxRate = new FXRate();
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public FXRate getFxRate() {
        return fxRate;
    }

    public void setFxRate(FXRate fxRate) {
        this.fxRate = fxRate;
    }
}
