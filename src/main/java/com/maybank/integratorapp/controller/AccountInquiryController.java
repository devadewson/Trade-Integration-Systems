package com.maybank.integratorapp.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessAccountInquiry;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ResponseHeader;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ServiceResponse;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.AccountInquiryResponse;
import com.maybank.integratorapp.service.MsQueueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class AccountInquiryController {
    @Autowired
    MsQueueConfigService queueConfigService;
    @PostMapping("/AccountInquiry")
    public ResponseEntity<String> GetAccountBalance(){
        System.out.println("31");
        ProcessAccountInquiry processAccountInquiry = new ProcessAccountInquiry();
        AccountInquiryResponse accountInquiryResponse = processAccountInquiry.getAccInq();

        ServiceResponse serviceResponse = new ServiceResponse();
        AvailBalResponse availBalResponse = new AvailBalResponse();
        ResponseHeader header = new ResponseHeader();
        serviceResponse.setResponseHeader(header);
        serviceResponse.setAvailBalResponse(availBalResponse);

        serviceResponse.getAvailBalResponse().setBalance(accountInquiryResponse.getAccountInquiryResponseData().getBalance());
        serviceResponse.getAvailBalResponse().setErrorMessage(accountInquiryResponse.getResponseDetail().getError_origin());
        serviceResponse.getResponseHeader().setStatus(accountInquiryResponse.getResponseDetail().getResponse_data());

        XmlMapper xmlMapper = new XmlMapper();
        String xmlResponse = "";
        try {
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
            xmlResponse = xmlMapper.writeValueAsString(serviceResponse);
        }
        catch (JsonProcessingException e) {
            serviceResponse.getResponseHeader().setStatus("Error");
            serviceResponse.getResponseHeader().getDetails().setError("Parse Mapping Error");
            throw new RuntimeException(e);
        }

        System.out.println("===========================xmlResponse=================================");
        System.out.println(xmlResponse);
        System.out.println("============================================================");

        MsQueueConfig config = queueConfigService.findByServiceName("AccountInquiry");
        if(config != null && config.getEnableStatus() == 1){
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String correlationId = "ACCOUNTINQUIRYDATA_"+date;

            MessagePublisher publisher = new MessagePublisher(config.getResponse_Queue_Address(),
                    config.getResponse_Queue_Username(),config.getResponse_Queue_Password(), config.getResponse_Queue_Name());
            publisher.PublishMessage(xmlResponse, correlationId);

            return new ResponseEntity<>(xmlResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/TestingXML")
    public ResponseEntity<String> TestingXML(@RequestBody String xmlData){
        System.out.println("===========================xmlData=================================");
        System.out.println(xmlData);
        System.out.println("============================================================");
        return new ResponseEntity<>(xmlData, HttpStatus.OK);
    }
}
