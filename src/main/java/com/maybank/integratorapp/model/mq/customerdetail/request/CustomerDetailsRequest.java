package com.maybank.integratorapp.model.mq.customerdetail.request;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CustomerDetailsRequest {
    @JacksonXmlProperty(localName = "CustomerId")
    private String customerId;
    @JacksonXmlProperty(localName = "PrimeType")
    private String primeType;
    @JacksonXmlProperty(localName = "SwiftType")
    private String swiftType;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPrimeType() {
        return primeType;
    }

    public void setPrimeType(String primeType) {
        this.primeType = primeType;
    }

    public String getSwiftType() {
        return swiftType;
    }

    public void setSwiftType(String swiftType) {
        this.swiftType = swiftType;
    }
}
