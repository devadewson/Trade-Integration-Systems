package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.system.messageprocessor.LimitUtilizationMessageProcessor;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.limitutilization.request.ServiceRequest;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component

public class LimitUtilizationListener implements CustomMessageListener {
    private static Logger log = LoggerFactory.getLogger(LimitUtilizationListener.class);
    @Autowired
    LimitUtilizationMessageProcessor messageProcessor;
    @Autowired
    private LogQueueDataRepository dataDTO;
    @Autowired
    private Environment env;
    @Autowired
    MsQueueConfigService queueConfigService;

    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            processMessage((TextMessage) message);
        }
    }
    private void processMessage(TextMessage message) {
        LogQueueData logData = new LogQueueData();

        try {
            initializeLogData(logData, message);
            String correlationId = message.getJMSCorrelationID();
            if(dataDTO.findByCorrelationId(correlationId)!= null){
                message.acknowledge();
                return;
            }
            String _message = message.getBody(String.class);
            log.info("Utilization Listener Received : "+message.getJMSCorrelationID());
//            System.out.println(message.getBody(String.class));
            logData = dataDTO.save(logData);
            message.acknowledge();

            String responseLimit = messageProcessor.processMessage(_message,logData.getId());

            logData.setStatus("Success");
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());
            dataDTO.save(logData);

        } catch (JMSException e) {
            handleException(e, logData);
        }
    }

    private void initializeLogData(LogQueueData logData, TextMessage message) throws JMSException {
        Queue sourceQueue = (Queue) message.getJMSDestination();
        logData.setOrigin("MQ_" + sourceQueue.getQueueName());
        logData.setMessageUID(new MQUtil().getMessageUID());
        logData.setReqMessage(message.getText());
        logData.setRelatedTransRef(MQUtil.findTransRef(logData.getOrigin(),message.getText()));
        logData.setCreated_date(new Date());
        logData.setCorrelationID(message.getJMSCorrelationID());
    }

    private ServiceRequest parseRequest(TextMessage message) throws JsonProcessingException, JMSException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(message.getText(), ServiceRequest.class);
    }

    private void handleException(Exception e, LogQueueData logData) {
        String errorMsg;

        if (e instanceof JMSException) {
            log.error(e.getMessage());
        } else if (e instanceof JsonMappingException) {
            log.error(e.getMessage());
        } else if (e instanceof JsonProcessingException) {
            log.error(e.getMessage());
        } else {
            log.error(e.getMessage());
        }

        logData.setStatus("Error");
        logData.setDelivery_date(new Date());
        logData.setUpdated_date(new Date());
    }
}
