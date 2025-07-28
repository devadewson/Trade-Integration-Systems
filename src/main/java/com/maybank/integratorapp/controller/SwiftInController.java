package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessSwiftIn;
import com.maybank.integratorapp.component.coresystem.ProcessSwiftOut;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.model.mq.swiftin.response.Credentials;
import com.maybank.integratorapp.model.mq.swiftin.response.RequestHeader;
import com.maybank.integratorapp.model.mq.swiftin.response.ServiceRequest;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Hidden // Hides the entire controller
@RestController
public class SwiftInController {
    @Autowired
    MsQueueConfigService queueConfigService;
    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    LogInterfaceProcessService logger;
    
    @Autowired
    ProcessSwiftIn swiftIn;
    @Autowired
    ProcessSwiftOut swiftOut;

    @GetMapping("/GetSwiftIn")
    public ResponseEntity<String> GetSwiftIn(){
        long LoggerID = 0;

        try{

            MsQueueConfig config = queueConfigService.findByServiceName("SwiftIn");
            if(config != null && config.getEnableStatus() == 1){
                LogQueueData _data = new LogQueueData();
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setOrigin("SwiftIn Integrator Scheduler");
                _data.setCreated_date(new Date());

                _data = dataDTO.save(_data);
                LoggerID = _data.getId();
//                logger.SetLogParent(_data.getId());
                logger.Log(_data.getId(),"SwiftIn - SwiftIn Scheduler","Begin","START");

                Map<String, List<String>> fileContents = swiftIn.getFileContent(LoggerID);
//            ProcessFXRate fxRate = new ProcessFXRate();
//            List<FxRateListData> data = fxRate.getAllFxRate();


                fileContents.forEach((fileName, content) -> {
                    String date = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
                    String correlationId = "SwiftIn_"+date+"_"+MQUtil.generateRandomString(6);

                    ServiceRequest response = new ServiceRequest();
                    response.getSwiftIn().setMessage(String.join("\n", content));
                    response.setRequestHeader(new RequestHeader());
                    response.getRequestHeader().setCorrelationID(correlationId);
                    response.getRequestHeader().setService("TI");
                    response.getRequestHeader().setOperation("SwiftIn");
                    response.getRequestHeader().setCredentials(new Credentials());
                    response.getRequestHeader().getCredentials().setName("SUPERVISOR");
                    response.getRequestHeader().setReplyFormat("FULL");
                    response.getRequestHeader().setNoRepair("Y");
                    response.getRequestHeader().setNoOverride("Y");
                    response.getRequestHeader().setTransactionControl("NONE");

                    LogQueueData data = new LogQueueData();
                    data.setMessageUID(new MQUtil().getMessageUID());
                    data.setOrigin("SwiftSAA");
                    data.setCreated_date(new Date());
                    data.setStatus("Success");
                    data.setDelivery_date(new Date());
                    data.setUpdated_date(new Date());
                    data = dataDTO.save(data);

//                    logger.SetLogParent(data.getId());
//                    System.out.println("File: " + fileName);
//                    content.forEach(System.out::println);
//                    System.out.println();

                    XmlMapper xmlMapper = new XmlMapper();
                    // Serialize the object to XML
                    String xml = null;
                    try {
                        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
                        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);

                        xml = xmlMapper.writeValueAsString(response);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }


                    logger.Log(data.getId(),"SwiftIn - Sending Swift Messages","Sending to FTI Queues","START");
                    // logging

                    data.setCorrelationID(correlationId);
                    data.setResMessage(xml);
                    data.setDestination(config.getResponse_Queue_Name());
                    data = dataDTO.save(data);


                    MessagePublisher publisher = new MessagePublisher(
                            config.getResponse_Queue_Address(),
                            Integer.parseInt(config.getRequest_Queue_Port()),
                            config.getResponse_Queue_Manager(),
                            config.getResponse_Queue_Channel(),
                            config.getResponse_Queue_Username(),
                            config.getResponse_Queue_Password(),
                            config.getResponse_Queue_Name());
                    publisher.PublishMessage(xml, correlationId);
                    publisher.close();
                    logger.Log(data.getId(),"SwiftIn - Sending Swift Messages","Sending to FTI Queues","END");

                    swiftIn.moveToBackup(data.getId());

                    logger.Log(data.getId(),"SwiftIn - SwiftIn Scheduler","End","END");
                });

//                logger.SetLogParent(_data.getId());


                return new ResponseEntity<>("SwiftIn Success", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
            }



        }
        catch (Exception e){
            logger.Log(LoggerID,"SwiftIn - SwiftIn Scheduler","Error","ERROR",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/DuplicateSwiftIn")
    public ResponseEntity<String> DuplicateSwiftIn(){
        long LoggerId = 0;
        try{
            LogQueueData _data = new LogQueueData();
            _data.setMessageUID(new MQUtil().getMessageUID());
            _data.setOrigin("Duplicate SwiftSAA");
            _data.setCreated_date(new Date());
            _data.setStatus("Success");
            _data.setDelivery_date(new Date());
            _data.setUpdated_date(new Date());
            _data = dataDTO.save(_data);
            LoggerId = _data.getId();
//            logger.SetLogParent(_data.getId());
            logger.Log(_data.getId(),"Duplicator - SwiftIn Duplicator Scheduler","Start","START");

            swiftIn.duplicateSwift(LoggerId);
            logger.Log(_data.getId(),"Duplicator - SwiftIn Duplicator Scheduler","End","END");


        return new ResponseEntity<>("Duplicate SwiftIn Success", HttpStatus.OK);


        }
        catch (Exception e){
            logger.Log(LoggerId,"SwiftIn - SwiftIn Scheduler","Error","ERROR",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/TestSwiftOutFTP")
    public ResponseEntity<String> testSwiftOutFTP(){
        long LoggerId = 0;
        try{
            LogQueueData _data = new LogQueueData();
            _data.setMessageUID(new MQUtil().getMessageUID());
            _data.setOrigin("TestSwiftOutFTP SwiftSAA");
            _data.setCreated_date(new Date());
            _data.setStatus("Success");
            _data.setDelivery_date(new Date());
            _data.setUpdated_date(new Date());
            _data = dataDTO.save(_data);
            LoggerId = _data.getId();
//            logger.SetLogParent(_data.getId());
            logger.Log(_data.getId(),"TestSwiftOutFTP","Start","START");

            List<String> _listContent = new ArrayList<>();
            _listContent.add("Test");
            swiftOut.putFileContent(_listContent,"Testing",_data.getId());
            logger.Log(_data.getId(),"TestSwiftOutFTP","End","END");


            return new ResponseEntity<>("TestSwiftOutFTP", HttpStatus.OK);


        }
        catch (Exception e){
            logger.Log(LoggerId,"TestSwiftOutFTP","Error","ERROR",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
