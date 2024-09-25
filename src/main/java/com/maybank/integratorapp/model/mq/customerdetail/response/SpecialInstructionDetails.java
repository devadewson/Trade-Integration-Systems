package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class SpecialInstructionDetails {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "SpecialInstructionDetail")

    private SpecialInstructionDetail specialInstructionDetail;

    public SpecialInstructionDetail getSpecialInstructionDetail() { return specialInstructionDetail; }
    public void setSpecialInstructionDetail(SpecialInstructionDetail value) { this.specialInstructionDetail = value; }

}
