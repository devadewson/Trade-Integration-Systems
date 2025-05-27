package com.maybank.integratorapp.model.soap.XL01.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


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

