package com.maybank.integratorapp.model.soap.customerinformation_cif.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.soap.accountinquiry.request.Body;

@JacksonXmlRootElement(localName = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapEnvelope {
    public SoapEnvelope(){
        this.body = new Body();
    }

    @JacksonXmlProperty(localName = "Header",namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private String header;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @JacksonXmlProperty(localName = "Body",namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
