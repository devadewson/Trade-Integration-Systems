package com.maybank.integratorapp.model.rest.AccountInquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class responseDetail {
    @JacksonXmlProperty(localName = "error_origin")
    private String error_origin;

    @JacksonXmlProperty(localName = "response_code")
    private String response_code;

    @JacksonXmlProperty(localName = "response_data")
    private String response_data;

    public String getError_origin() {
        return error_origin;
    }

    public void setError_origin(String error_origin) {
        this.error_origin = error_origin;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_data() {
        return response_data;
    }

    public void setResponse_data(String response_data) {
        this.response_data = response_data;
    }
}
