package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.system.messageprocessor.LimitReservationMessageProcessor;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.entity.MsUtilizeRunningNumber;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import com.maybank.integratorapp.data.repository.MsMapClsProductTypeRepository;
import com.maybank.integratorapp.data.repository.MsUtilizeRunningNumberRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.reservation.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.reservation.response.*;
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

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;

@Component
public class ReservationListener implements CustomMessageListener {
    private static Logger log = LoggerFactory.getLogger(ReservationListener.class);

    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    private Environment env;

    @Autowired
    MsQueueConfigService queueConfigService;

    @Autowired
    MsFacilityRepository msFacilityRepository;

    @Autowired
    LimitReservationMessageProcessor messageProcessor;

    @Autowired
    MsUtilizeRunningNumberRepository msUtilizeRunningNumberRepository;

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
        ServiceResponse response = new ServiceResponse();
        LogQueueData logData = new LogQueueData();
        String newKeyLoanAcc = null;
        String acctReqXL01 = null;

        try {
            initializeLogData(logData, message);
            String correlationId = message.getJMSCorrelationID();
            if(dataDTO.findByCorrelationId(correlationId)!= null){
                message.acknowledge();
                return;
            }
            String _message = message.getBody(String.class);
            log.info("Reservation Listener Received : "+message.getJMSCorrelationID());
//            System.out.println(message.getBody(String.class));
            logData = dataDTO.save(logData);
            message.acknowledge();

            String xml = messageProcessor.processMessage(_message,logData.getId());
            publisher.PublishMessage(xml, message.getJMSCorrelationID());

            logData.setStatus("Success");
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());
            logData.setResMessage(xml);
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

    private void setInitialResponseHeader(ResponseHeader responseHeader, ServiceRequest request) {
        if (request != null && request.getRequestHeader() != null) {
            responseHeader.setCorrelationID(request.getRequestHeader().getCorrelationID());
            responseHeader.setService(request.getRequestHeader().getService());
            responseHeader.setOperation(request.getRequestHeader().getOperation());
            responseHeader.setSourceSystem(request.getRequestHeader().getTargetSystem());
            responseHeader.setTargetSystem(request.getRequestHeader().getSourceSystem());
            responseHeader.setStatus("SUCCEEDED");
        } else {
            responseHeader.setStatus("FAILED");
        }
    }

    private ServiceRequest parseRequest(TextMessage message) throws JsonProcessingException, JMSException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(message.getText(), ServiceRequest.class);
    }
    // Pecah originalKey menggunakan splitKey
    private String buildNewKey(String originalKey, String formattedRunningNumber) {
        String[] parts = splitKey(originalKey);
        parts[5] = formattedRunningNumber;
        return String.join("", parts);
    }

    private String[] splitKey(String key) {
        String bank = key.substring(0, 2);
        String currency = key.substring(2, 5);
        String branchCode = key.substring(5, 8);
        String cif = key.substring(8, 18);
        String note = key.substring(18, 26);
        String draw = key.substring(26, 29);
        String seq = key.substring(29, 31);

        return new String[]{bank, currency, branchCode, cif, note, draw, seq};
    }

    //  key dengan format yang dimodifikasi
    private String buildFormattedKey(String originalKey, String formattedRunningNumber) {
        // Ambil bagian yang diperlukan dari originalKey
        String cif = originalKey.substring(8, 18);
        String note = originalKey.substring(18, 26);
        String seq = originalKey.substring(29, 31);
        String formattedDraw = formattedRunningNumber;

        return cif + "." + note + "." + formattedDraw + "." + seq;
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
