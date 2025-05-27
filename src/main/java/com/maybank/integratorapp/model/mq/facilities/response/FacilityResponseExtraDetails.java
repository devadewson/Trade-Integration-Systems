package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityResponseExtraDetails {
    @JacksonXmlProperty(localName = "ExtraDataKey", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String extraDataKey;
    @JacksonXmlProperty(localName = "FieldName", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String fieldName;
    @JacksonXmlProperty(localName = "FieldValue", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String fieldValue;

    public String getExtraDataKey() {
        return extraDataKey;
    }

    public void setExtraDataKey(String extraDataKey) {
        this.extraDataKey = extraDataKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
