package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessSwiftIn;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.swiftin.response.ServiceRequest;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class SwiftInController {
    @Autowired
    MsQueueConfigService queueConfigService;
    @Autowired
    private LogQueueDataRepository dataDTO;

    @PostMapping("/BatchSwiftIn")
    public ResponseEntity<String> GetSwiftIn(){
        try{

            MsQueueConfig config = queueConfigService.findByServiceName("SwiftIn");
            if(config != null && config.getEnableStatus() == 1){


                ProcessSwiftIn swiftIn = new ProcessSwiftIn();
                Map<String, List<String>> fileContents = swiftIn.getFileContent();
//            ProcessFXRate fxRate = new ProcessFXRate();
//            List<FxRateListData> data = fxRate.getAllFxRate();


                fileContents.forEach((fileName, content) -> {
                    ServiceRequest response = new ServiceRequest();
                    response.getSwiftIn().setMessage(String.join("\n", content));
                    LogQueueData _data = new LogQueueData();

//                    System.out.println("File: " + fileName);
//                    content.forEach(System.out::println);
//                    System.out.println();

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

                    String date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
                    String correlationId = "SwiftIn_"+date;

                    // logging
                    _data.setMessageUID(new MQUtil().getMessageUID());
                    _data.setCreated_date(new Date());
                    _data.setCorrelationID(correlationId);
                    _data.setStatus("Success");
                    _data.setDelivery_date(new Date());
                    _data.setUpdated_date(new Date());
                    _data.setResMessage(xml);
                    _data.setDestination(config.getResponse_Queue_Username());
                    _data = dataDTO.save(_data);

                    MessagePublisher publisher = new MessagePublisher(config.getResponse_Queue_Address(),config.getResponse_Queue_Username(),config.getResponse_Queue_Password(), config.getResponse_Queue_Name());
                    publisher.PublishMessage(xml, correlationId);

                });
//
//            // mapping each FxRateListData into ExchangeRateRecord
//            for (FxRateListData item : data) {
//                ExchangeRateRecord dataRecord = new ExchangeRateRecord();
//
//                String _baseCcy = item.getCcy().split("\\.")[0];
//                String _againstCcy = item.getCcy().split("\\.")[1];
//
//                dataRecord.setBaseISOCode(_baseCcy);
//                dataRecord.setIsoCode(_againstCcy);
//                dataRecord.setBuyTtRate(item.getBid());
//                dataRecord.setMidTtRate(item.getBid());
//                dataRecord.setSellTtRate(item.getAsk());
//
//                records.add(dataRecord);
//            }



                return new ResponseEntity<>("SwiftIn Success", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
            }



        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
