package com.maybank.integratorapp.model.rest.CustomerSearchh.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ResponseDetail")
public class ResponseDetail {
    @JsonProperty("additionalData")
    private String additionalData;
    @JsonProperty("error_origin")
    private String error_origin;
    @JsonProperty("response_code")
    private String response_code;
    @JsonProperty("response_data")
    private String response_data;


    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    public String getErrorOrigin() {
        return error_origin;
    }

    public void setErrorOrigin(String errorOrigin) {
        this.error_origin = errorOrigin;
    }

    public String getResponseCode() {
        return response_code;
    }

    public void setResponseCode(String responseCode) {
        this.response_code = responseCode;
    }

    public String getResponseData() {
        return response_data;
    }

    public void setResponseData(String responseData) {
        this.response_data = responseData;
    }
}
