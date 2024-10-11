package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessFXRate;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.fxrate.response.FXRate;
import com.maybank.integratorapp.model.mq.fxrate.response.ServiceRequest;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecord;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecords;
import com.maybank.integratorapp.model.rest.accountinquiry.request.AccountInquiry;
import com.maybank.integratorapp.model.rest.accountinquiry.request.AccountInquiryRequest;
import com.maybank.integratorapp.model.rest.accountinquiry.request.Body;
import com.maybank.integratorapp.model.rest.accountinquiry.request.ChannelHeader;
import com.maybank.integratorapp.model.rest.fxrate.request.FxRateRequest;
import com.maybank.integratorapp.model.rest.fxrate.response.FxRateResponse;
import com.maybank.integratorapp.model.rest.fxratelist.response.FxRateListData;
import com.maybank.integratorapp.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
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
    @Autowired
    private LogQueueDataRepository dataDTO;

    @PostMapping("/RateFCC")
    public ResponseEntity<String> GetRateFCC(){
        try{

            MsQueueConfig config = queueConfigService.findByServiceName("FxRate");
            if(config != null && config.getEnableStatus() == 1){
                LogQueueData _data = new LogQueueData();

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

                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                String correlationId = "FXRATEDATA_"+date;

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

                return new ResponseEntity<>(xml, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
            }



        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/RateFTI")
    public ResponseEntity<String> GetRateFTI(){
        try{

            MsQueueConfig config = queueConfigService.findByServiceName("FxRateFTI");
            if(config != null && config.getEnableStatus() == 1){
                LogQueueData _data = new LogQueueData();

                ProcessFXRate fxRate = new ProcessFXRate();
                List<FxRateListData> data = fxRate.getAllFxRate();

                ServiceRequest response = new ServiceRequest();
                List<FXRate> records = response.getFxRate();

                // mapping each FxRateListData into ExchangeRateRecord
                for (FxRateListData item : data) {
                    FXRate dataRecord = new FXRate();

                    String _baseCcy = item.getCcy().split("\\.")[0];
                    String _againstCcy = item.getCcy().split("\\.")[1];

                    dataRecord.setBaseCurrency(_baseCcy);
                    dataRecord.setCurrency(_againstCcy);
                    dataRecord.setBuyExchangeRate(item.getBid());
//                    dataRecord.setMidTtRate(item.getBid());
                    dataRecord.setSellExchangeRate(item.getAsk());

                    records.add(dataRecord);
                }

                XmlMapper xmlMapper = new XmlMapper();
                // Serialize the object to XML
                String xml = null;
                try {
                    xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
//                    xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);

                    xml = xmlMapper.writeValueAsString(response);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                String correlationId = "FXRATEDATA_"+date;

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

                return new ResponseEntity<>(xml, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
            }



        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/TestAccountInquiry")
    public ResponseEntity<String> GetAccountInquryFTI(){
        try{

//
            String apiUrl = "http://10.235.66.96:7800/accountservicesapi/v1/AccountServices";
            Body req = new Body();

            AccountInquiry accountInquiry = new AccountInquiry();
            ChannelHeader header = new ChannelHeader();
            AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();

            header.setChannelID("RCMS");

            accountInquiryRequest.setAccountNo("1001146880");
            accountInquiryRequest.setAccountBranchCode("001");
            accountInquiryRequest.setAccountCurrency("016");

            accountInquiry.setChannelHeader(header);
            accountInquiry.setAccountInquiryRequest(accountInquiryRequest);

            req.setAccountInquiry(accountInquiry);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            HttpEntity<Body> request = new HttpEntity<Body>(req, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl,request, String.class);

            response.getBody();

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
