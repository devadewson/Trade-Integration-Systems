package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.SystemProcess;
import com.maybank.integratorapp.component.coresystem.ProcessAccountBalance;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.accountinquiry.request.Credentials;
import com.maybank.integratorapp.model.mq.accountinquiry.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ServiceResponse;
//import com.maybank.integratorapp.service.JwtService;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AccountBalanceMessageListener implements CustomMessageListener {

    @Autowired
    private LogQueueDataRepository dataDTO;


// Test Create new branch
//    @Autowired
//    private JwtService jwtService;

    @Autowired
    private Environment env;

    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            ServiceResponse response = new ServiceResponse();
            LogQueueData _data = new LogQueueData();

            try {
                System.out.println("Received 1 Message With CorrelationID : "+message.getJMSCorrelationID());

//            String _message = message.getBody(String.class);
//            String _message = message.getStringProperty("data");
                String _message = message.getBody(String.class);


                ActiveMQDestination sourceQueue = (ActiveMQDestination) message.getJMSDestination();
                _data.setOrigin("MQ_"+sourceQueue.getPhysicalName());
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setReqMessage(_message);
                _data.setCreated_date(new Date());
                _data.setCorrelationID(message.getJMSCorrelationID());

//                LogQueueDataDTO dataDTO = new LogQueueDataDTO();
                _data = dataDTO.save(_data);

                XmlMapper xmlMapper = new XmlMapper();
                ServiceRequest request = xmlMapper.readValue(_message, ServiceRequest.class);

                //set the response header early
                response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
                response.getResponseHeader().setService(request.getRequestHeader().getService());
                response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
                response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
                response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());

                ProcessAccountBalance process = new ProcessAccountBalance();

//                    response.getAvailBalResponse().setBalance(process.getAccountBalance(request.getAvailBALRequest().getBackOfficeAccount()));
                response.getAvailBalResponse().setBalance(process.getAccountBalanceNew(request.getAvailBALRequest().getBackOfficeAccount()));

                response.getResponseHeader().setStatus("Success");
                response.getResponseHeader().getDetails().setInfo("Success");
                // Serialize the object to XML
                String xmlResponse = xmlMapper.writeValueAsString(response);

                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());
                _data.setResMessage(xmlResponse);
                _data.setDestination(this.publisher.getDestinationQueue());


                message.acknowledge();
//                if(jwtService.validateToken(request.getRequestHeader().getCredentials().getCertificate())){
//
//                    SystemProcess process = new SystemProcess();
//
////                    response.getAvailBalResponse().setBalance(process.getAccountBalance(request.getAvailBALRequest().getBackOfficeAccount()));
//                    response.getAvailBalResponse().setBalance(process.getAccountBalanceNew(request.getAvailBALRequest().getBackOfficeAccount()));
//
//                    response.getResponseHeader().setStatus("Success");
//                    response.getResponseHeader().getDetails().setInfo("Success");
//                    // Serialize the object to XML
//                    String xmlResponse = xmlMapper.writeValueAsString(response);
//
//                    _data.setStatus("Success");
//                    _data.setDelivery_date(new Date());
//                    _data.setUpdated_date(new Date());
//                    _data.setResMessage(xmlResponse);
//
//                    message.acknowledge();
//
//
//                }else{
//                    response.getResponseHeader().setStatus("Error");
//                    response.getResponseHeader().getDetails().setError("Unauthorized");
//
//                    _data.setStatus("Error");
//                    _data.setStatus_info("Unauthorized");
//                    _data.setDelivery_date(new Date());
//                    _data.setUpdated_date(new Date());
//                }

            } catch (JMSException e) {
                response.getResponseHeader().setStatus("Error");
                response.getResponseHeader().getDetails().setError("MessageQueue Error");

                _data.setStatus("Error");
                _data.setStatus_info(e.getMessage());
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

                throw new RuntimeException(e);
            } catch (JsonMappingException e) {
                response.getResponseHeader().setStatus("Error");
                response.getResponseHeader().getDetails().setError("Mapping Error");

                _data.setStatus("Error");
                _data.setStatus_info(e.getMessage());
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());


                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                response.getResponseHeader().setStatus("Error");
                response.getResponseHeader().getDetails().setError("Parse Mapping Error");

                _data.setStatus("Error");
                _data.setStatus_info(e.getMessage());
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());


                throw new RuntimeException(e);
            }
//            MessageProducer producer = new MessageProducer();

            dataDTO.save(_data);

            XmlMapper xmlMapper = new XmlMapper();
            // Serialize the object to XML
            String xml = null;
            try {
                xml = xmlMapper.writeValueAsString(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            publisher.PublishMessage(xml,response.getResponseHeader().getCorrelationID());
        }
    }

    public Boolean isAuthenticated(Credentials cred){
        Boolean ret = false;



        return ret;
    }
}
