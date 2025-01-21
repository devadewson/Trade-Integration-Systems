package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessFXRate;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.model.mq.fxrate.response.FXRate;
import com.maybank.integratorapp.model.mq.fxrate.response.ItemRequest;
import com.maybank.integratorapp.model.mq.fxrate.response.ServiceRequest;
import com.maybank.integratorapp.model.mq.fxrate.response.ServiceRequestChild;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecord;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecords;
import com.maybank.integratorapp.model.rest.fxratelist.response.FxRateListData;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Hidden // Hides the entire controller
@RestController
public class FxRateController {

    @Autowired
    MsQueueConfigService queueConfigService;
    @Autowired
    MsParameterRepository parameterRepository;
    @Autowired
    private LogQueueDataRepository dataDTO;

    @PostMapping("/RateFCC")
    public ResponseEntity<String> GetRateFCC(){
        try{

            MsQueueConfig config = queueConfigService.findByServiceName("FxRate");
            if(config != null && config.getEnableStatus() == 1){
                LogQueueData _data = new LogQueueData();
                String api = parameterRepository.findValueByPrmKey("FxRateRequest");

                ProcessFXRate fxRate = new ProcessFXRate();
                List<FxRateListData> data = fxRate.getAllFxRate(api);

                ExchangeRateRecords response = new ExchangeRateRecords();
                List<ExchangeRateRecord> records = response.getExchangeRateRecord();
                String updateDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());

                // mapping each FxRateListData into ExchangeRateRecord
                for (FxRateListData item : data) {
                    ExchangeRateRecord dataRecord = new ExchangeRateRecord();

                    String _baseCcy = item.getCcy().split("\\.")[0];
                    String _againstCcy = item.getCcy().split("\\.")[1];

                    dataRecord.setBaseISOCode(_againstCcy);
                    dataRecord.setIsoCode(_baseCcy);
                    dataRecord.setPatyVal("1");
                    dataRecord.setBankAbbvName("MAYBANKID");
//                    dataRecord.setBuyTtRate(item.getBid());
//                    dataRecord.setMidTtRate(item.getBid());
//                    dataRecord.setSellTtRate(item.getAsk());
                    dataRecord.setBuyTtRate(item.getBidAllIn());
                    dataRecord.setMidTtRate(item.getBidAllIn());
                    dataRecord.setSellTtRate(item.getAskAllIn());
                    dataRecord.setUpdateDate(updateDate);
                    dataRecord.setStartValueDate(updateDate);
                    dataRecord.setEndValueDate(updateDate);

                    records.add(dataRecord);
                }

                response.setExchangeRateRecord(records);

                XmlMapper xmlMapper = new XmlMapper();
                // Serialize the object to XML
                String xml = null;
                try {
                    xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
                    xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);

                    xml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
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
                _data.setDestination(config.getResponse_Queue_Name());
                _data = dataDTO.save(_data);

                MessagePublisher publisher = new MessagePublisher(
                        config.getResponse_Queue_Address(),
                        Integer.parseInt(config.getResponse_Queue_Port()),
                        config.getResponse_Queue_Manager(),
                        config.getResponse_Queue_Channel(),
                        config.getResponse_Queue_Username(),
                        config.getResponse_Queue_Password(),
                        config.getResponse_Queue_Name());
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
                String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                String correlationId = "FXRATEDATA_"+date;

                String api = parameterRepository.findValueByPrmKey("FxRateRequest");


                LogQueueData _data = new LogQueueData();

                ProcessFXRate fxRate = new ProcessFXRate();
                List<FxRateListData> data = fxRate.getAllFxRate(api);

                ServiceRequest response = new ServiceRequest();
                response.getRequestHeader().setCorrelationID(correlationId);
                response.getRequestHeader().setService("TIBulk");
                response.getRequestHeader().setOperation("Item");
                response.getRequestHeader().getCredentials().setName("SUPERVISOR");
                response.getRequestHeader().setReplyFormat("FULL");

                List<ItemRequest> records = response.getItemRequest();

                // mapping each FxRateListData into ExchangeRateRecord
                for (FxRateListData item : data) {
                    ItemRequest itemRequest = new ItemRequest();
                    ServiceRequestChild child = new ServiceRequestChild();
                    child.getRequestHeader().setCorrelationID(correlationId);
                    child.getRequestHeader().setService("TI");
                    child.getRequestHeader().setOperation("FXRate");
                    child.getRequestHeader().getCredentials().setName("SUPERVISOR");
                    child.getRequestHeader().setReplyFormat("STATUS");
                    child.getRequestHeader().setNoOverride("Y");

                    FXRate dataRecord = new FXRate();

                    String _baseCcy = item.getCcy().split("\\.")[0];
                    String _againstCcy = item.getCcy().split("\\.")[1];

                    dataRecord.setMaintType("F");
                    dataRecord.setMaintainedInBackOffice("N");
                    dataRecord.setFxRateCode("CORP");
                    dataRecord.setBankingEntity("MAYBANKI");
                    dataRecord.setBuyRateSpecific("T");
                    dataRecord.setSellRateSpecific("T");
                    dataRecord.setBaseCurrency(_againstCcy);
                    dataRecord.setCurrency(_baseCcy);
                    dataRecord.setBuyExchangeRate(item.getBid());
//                    dataRecord.setMidTtRate(item.getBid());
                    dataRecord.setSellExchangeRate(item.getAsk());

                    child.setFxRate(dataRecord);
                    itemRequest.setServiceRequestChild(child);

                    records.add(itemRequest);
                }

                XmlMapper xmlMapper = new XmlMapper();
                // Serialize the object to XML
                String xml = null;
                try {
                    xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
//                    xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);

                    xml = xmlMapper.writeValueAsString(response);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }


                // logging
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setCreated_date(new Date());
                _data.setCorrelationID(correlationId);
                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());
                _data.setResMessage(xml);
                _data.setDestination(config.getResponse_Queue_Name());
                _data = dataDTO.save(_data);

                MessagePublisher publisher = new MessagePublisher(
                        config.getResponse_Queue_Address(),
                        Integer.parseInt(config.getRequest_Queue_Port()),
                        config.getResponse_Queue_Manager(),
                        config.getResponse_Queue_Channel(),
                        config.getResponse_Queue_Username(),
                        config.getResponse_Queue_Password(),
                        config.getResponse_Queue_Name());
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
