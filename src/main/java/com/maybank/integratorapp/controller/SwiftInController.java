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
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.model.mq.swiftin.response.ServiceRequest;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
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

    @PostMapping("/GetSwiftIn")
    public ResponseEntity<String> GetSwiftIn(){
        try{

            MsQueueConfig config = queueConfigService.findByServiceName("SwiftIn");
            if(config != null && config.getEnableStatus() == 1){
                LogQueueData _data = new LogQueueData();
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setOrigin("Integrator Scheduler");
                _data.setCreated_date(new Date());
                dataDTO.save(_data);
                logger.SetLogParent(_data.getId());
                logger.Log("SwiftIn - SwiftIn Scheduler","Begin","START");

                Map<String, List<String>> fileContents = swiftIn.getFileContent(logger);
//            ProcessFXRate fxRate = new ProcessFXRate();
//            List<FxRateListData> data = fxRate.getAllFxRate();


                fileContents.forEach((fileName, content) -> {
                    ServiceRequest response = new ServiceRequest();
                    response.getSwiftIn().setMessage(String.join("\n", content));
                    LogQueueData data = new LogQueueData();
                    data.setMessageUID(new MQUtil().getMessageUID());
                    data.setOrigin("SwiftSAA");
                    data.setCreated_date(new Date());
                    data.setStatus("Success");
                    data.setDelivery_date(new Date());
                    data.setUpdated_date(new Date());
                    data = dataDTO.save(data);

                    logger.SetLogParent(data.getId());
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
                    String correlationId = "SwiftIn_"+date+"_"+MQUtil.generateRandomString(6);
                    logger.Log("SwiftIn - Sending Swift Messages","Sending to FTI Queues","START");
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
                    logger.Log("SwiftIn - Sending Swift Messages","Sending to FTI Queues","END");


                });

                logger.SetLogParent(_data.getId());
                swiftIn.moveToBackup(logger);

                logger.Log("SwiftIn - SwiftIn Scheduler","End","END");

                return new ResponseEntity<>("SwiftIn Success", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
            }



        }
        catch (Exception e){
            logger.Log("SwiftIn - SwiftIn Scheduler","Error","ERROR",e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
