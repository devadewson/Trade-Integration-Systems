package com.maybank.integratorapp.model.restv2.CustomerInformation.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maybank.integratorapp.model.restv2.AccountInquiry.response.AccountInformationResponseData;

public class MsgBody {
    @JsonProperty("responseDetail")
    private Object responseDetail;

    @JsonProperty("CustomerInfoData")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private CustomerInfoData customerInfoData;

    public CustomerInfoData getCustomerInfoData() { return customerInfoData; }
    public void setCustomerInfoData(CustomerInfoData customerInfoData) { this.customerInfoData = customerInfoData; }

    public Object getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(Object responseDetail) {
        this.responseDetail = responseDetail;
    }


}
