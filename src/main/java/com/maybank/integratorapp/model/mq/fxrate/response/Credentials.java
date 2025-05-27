package com.maybank.integratorapp.model.mq.fxrate.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Credentials {
    @JacksonXmlProperty(localName = "Name", namespace = "urn:control.services.tiplus2.misys.com")
    private String name;
    @JacksonXmlProperty(localName = "Password", namespace = "urn:control.services.tiplus2.misys.com")

    private String password;
    @JacksonXmlProperty(localName = "Certificate", namespace = "urn:control.services.tiplus2.misys.com")

    private String certificate;
    @JacksonXmlProperty(localName = "Digest", namespace = "urn:control.services.tiplus2.misys.com")

    private String digest;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public String getCertificate() { return certificate; }
    public void setCertificate(String value) { this.certificate = value; }

    public String getDigest() { return digest; }
    public void setDigest(String value) { this.digest = value; }
}
