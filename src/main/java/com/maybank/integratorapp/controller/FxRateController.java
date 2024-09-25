package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessFXRate;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.MsQueueConfigRepository;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecord;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecords;
import com.maybank.integratorapp.model.rest.fxrate.request.FxRateRequest;
import com.maybank.integratorapp.model.rest.fxrate.response.FxRateResponse;
import com.maybank.integratorapp.model.rest.fxratelist.response.FxRateListData;
import com.maybank.integratorapp.service.MsQueueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class FxRateController {

    @Autowired
    MsQueueConfigService queueConfigService;

    @PostMapping("/Rate")
    public ResponseEntity<String> GetRate(){
        try{

            ProcessFXRate fxRate = new ProcessFXRate();
            List<FxRateListData> data = fxRate.getAllFxRate();

            ExchangeRateRecords response = new ExchangeRateRecords();
            List<ExchangeRateRecord> records = response.getExchangeRateRecord();

            // mapping each FxRateListData into ExchangeRateRecord
            for (FxRateListData item : data) {
                ExchangeRateRecord dataRecord = new ExchangeRateRecord();

                String _baseCcy = item.getCcy().split("\\.")[0];
                String _againstCcy = item.getCcy().split("\\.")[1];

                dataRecord.setBaseISOCode(_baseCcy);
                dataRecord.setIsoCode(_againstCcy);
                dataRecord.setBuyTtRate(item.getBid());
                dataRecord.setMidTtRate(item.getBid());
                dataRecord.setSellTtRate(item.getAsk());

                records.add(dataRecord);
            }

            XmlMapper xmlMapper = new XmlMapper();
            // Serialize the object to XML
            String xml = null;
            try {
                xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
                xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);

                xml = xmlMapper.writeValueAsString(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            MsQueueConfig config = queueConfigService.findByServiceName("FxRate");
            if(config != null && config.getEnableStatus() == 1){
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                String correlationId = "FXRATEDATA_"+date;

                MessagePublisher publisher = new MessagePublisher(config.getResponse_Queue_Address(),config.getResponse_Queue_Username(),config.getResponse_Queue_Password(), config.getResponse_Queue_Name());
                publisher.PublishMessage(xml, correlationId);

                return new ResponseEntity<>(xml, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
            }



        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
