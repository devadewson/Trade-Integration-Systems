package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.SystemProcess;
import com.maybank.integratorapp.component.coresystem.ProcessSwiftOut;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.swiftout.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
//import com.maybank.integratorapp.model.mq.swiftout.response.ServiceResponse;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SwiftOutMessageListener implements CustomMessageListener {
    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    ProcessSwiftOut process;
//    @Autowired private MessagePublisher publisher;
    @Autowired
    private Environment env;
    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
//            ServiceResponse response = new ServiceResponse();
            String responseText = "";

            LogQueueData _data = new LogQueueData();

            try {
                System.out.println("Received 1 Message With CorrelationID : "+message.getJMSCorrelationID());

                String _message = message.getBody(String.class);

                Queue sourceQueue = (Queue) message.getJMSDestination();
                _data.setOrigin("MQ_"+sourceQueue.getQueueName());
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setReqMessage(_message);
                _data.setCreated_date(new Date());
                _data.setCorrelationID(message.getJMSCorrelationID());

                _data = dataDTO.save(_data);

                XmlMapper xmlMapper = new XmlMapper();
                ServiceRequest request = xmlMapper.readValue(_message, ServiceRequest.class);

                process.putFileContent(request.getSwiftOut().getMessages().getMessage(), message.getJMSCorrelationID(), _data.getId());

                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

                message.acknowledge();

            } catch (JMSException e) {
                _data.setStatus("Error");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

                throw new RuntimeException(e);
            } catch (JsonMappingException e) {

                _data.setStatus("Error");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());


                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {

                _data.setStatus("Error");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());


                throw new RuntimeException(e);
            }
//            MessageProducer producer = new MessageProducer();
                dataDTO.save(_data);

//            XmlMapper xmlMapper = new XmlMapper();
//            // Serialize the object to XML
//            String xml = null;
//            try {
//                xml = xmlMapper.writeValueAsString(response);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }

//            publisher.PublishMessage(xml,response.getResponseHeader().getCorrelationID());
        }
    }
}
