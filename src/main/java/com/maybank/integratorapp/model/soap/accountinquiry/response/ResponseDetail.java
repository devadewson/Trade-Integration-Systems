package com.maybank.integratorapp.model.soap.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseDetail {
    @JacksonXmlProperty(localName = "error_origin")
    private String errorOrigin;
    @JacksonXmlProperty(localName = "response_code")

    private String responseCode;
    @JacksonXmlProperty(localName = "response_data")

    private String responseData;

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public void setErrorOrigin(String errorOrigin) {
        this.errorOrigin = errorOrigin;
    }

    public String getResponseData() {
        return responseData;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorOrigin() {
        return errorOrigin;
    }

}
