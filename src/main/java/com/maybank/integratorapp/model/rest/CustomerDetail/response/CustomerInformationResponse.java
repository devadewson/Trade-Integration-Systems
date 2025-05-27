package com.maybank.integratorapp.model.rest.CustomerDetail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CustomerInformationResponse" )
public class CustomerInformationResponse {
    @JsonProperty ("responseCode")
    private String responseCode;
    @JsonProperty ( "responseDetail")
    private ResponseDetail responseDetail;
    @JsonProperty ( "CustomerInformationResponseData")
    private CustomerInformationResponseData customerInformationResponseData;

    // Constructor, getters and setters
    public CustomerInformationResponse() {
        this.responseDetail = new ResponseDetail();
        this.customerInformationResponseData = new CustomerInformationResponseData();
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public ResponseDetail getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(ResponseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public CustomerInformationResponseData getCustomerInformationResponseData() {
        return customerInformationResponseData;
    }

    public void setCustomerInformationResponseData(CustomerInformationResponseData customerInformationResponseData) {
        this.customerInformationResponseData = customerInformationResponseData;
    }

}
