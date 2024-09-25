package com.maybank.integratorapp.model.mq.fxratefcc.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.XmlAttribute;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "exchange_rate_records",namespace = "http://www.w3.org/2001/XMLSchema-instance")
public class ExchangeRateRecords {

    public ExchangeRateRecords(){
        this.exchangeRateRecord = new ArrayList<ExchangeRateRecord>();
    }
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "exchange_rate_record")
    private List<ExchangeRateRecord> exchangeRateRecord;

    public List<ExchangeRateRecord> getExchangeRateRecord() {
        return exchangeRateRecord;
    }

    public void setExchangeRateRecord(List<ExchangeRateRecord> exchangeRateRecord) {
        this.exchangeRateRecord = exchangeRateRecord;
    }


    public String getNoNamespaceSchemaLocation() {
        return "http://www.misys.com/portal/interfaces/xsd/exchangeRate.xsd";
    }
}
