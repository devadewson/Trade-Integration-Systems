package com.maybank.integratorapp.model.mq.customerdetail.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class SpecialInstructionDetail {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Severity")
    private String severity;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Code")
    private String code;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Details")
    private String details;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Style")
    private String style;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Emphasis")
    private String emphasis;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Type")
    private String type;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "BusinessArea")
    private String businessArea;

    public String getSeverity() { return severity; }
    public void setSeverity(String value) { this.severity = value; }

    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }

    public String getDetails() { return details; }
    public void setDetails(String value) { this.details = value; }

    public String getStyle() { return style; }
    public void setStyle(String value) { this.style = value; }

    public String getEmphasis() { return emphasis; }
    public void setEmphasis(String value) { this.emphasis = value; }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getBusinessArea() { return businessArea; }
    public void setBusinessArea(String value) { this.businessArea = value; }

}
