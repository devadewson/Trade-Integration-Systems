package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessLimitUtilization;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.limitutilization.request.ServiceRequest;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component

public class LimitUtilizationListener implements CustomMessageListener {
    @Autowired
    ProcessLimitUtilization processLimitUtilization;
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
            dataDTO.save(logData);
            message.acknowledge();

            ServiceRequest request = parseRequest(message);

            String accountNo = request.getBatchRequest().getServiceRequestChild().get(0).getExposure().getAccountNumber();
            String utilizationID = request.getBatchRequest().getServiceRequestChild().get(0).getExposure().getFacilityExposureIdentifier();
            String correlationID = request.getRequestHeader().getCorrelationID();

            String responseLimit = processLimitUtilization.getLimitUtilization(accountNo,utilizationID,correlationID);

            logData.setStatus("Success");
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());

        } catch (JMSException | JsonProcessingException e) {
            handleException(e, logData);
        }
    }

    private void initializeLogData(LogQueueData logData, TextMessage message) throws JMSException {
        Queue sourceQueue = (Queue) message.getJMSDestination();
        logData.setOrigin("MQ_" + sourceQueue.getQueueName());
        logData.setMessageUID(new MQUtil().getMessageUID());
        logData.setReqMessage(message.getText());
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
            System.out.println(e.getMessage());
        } else if (e instanceof JsonMappingException) {
            System.out.println(e.getMessage());
        } else if (e instanceof JsonProcessingException) {
            System.out.println(e.getMessage());
        } else {
            System.out.println(e.getMessage());
        }

        logData.setStatus("Error");
        logData.setDelivery_date(new Date());
        logData.setUpdated_date(new Date());
    }
}
