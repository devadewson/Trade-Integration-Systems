package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessReservationReversal;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.reservationsreversal.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.reservationsreversal.response.ServiceResponse;
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
public class ReservationReversalListener implements CustomMessageListener {
    @Autowired
    ProcessReservationReversal processReservationReversal;
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
        ServiceResponse response = new ServiceResponse();
        LogQueueData logData = new LogQueueData();

        try {
            initializeLogData(logData, message);

            String correlationId = message.getJMSCorrelationID();
            if(dataDTO.findByCorrelationId(correlationId)!= null){
                message.acknowledge();
                return;
            }
            logData = dataDTO.save(logData);
            message.acknowledge();
            ServiceRequest request = parseRequest(message);

            String customerRes = request.getReservationsReversalRequest().getCustomer();
//            String customerRes = "0002794045";
            String masterReference= request.getReservationsReversalRequest().getMasterReference();
            String facilityIdentifier = request.getReservationsReversalRequest().getFacilityIdentifier();
            String reservationIdentifierNumber  = request.getReservationsReversalRequest().getReservationIdentifier();
            String lineOfBusiness = "01";
            String eventCode = request.getReservationsReversalRequest().getEventReference().substring(0, 3);

            String transDateRes = "291024";

            processReservationReversal.getReversalReservation(reservationIdentifierNumber,masterReference,facilityIdentifier,transDateRes);
//            String ReversalResponse = responseReservationReversal(noteNumber);

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String responseXml = xmlMapper.writeValueAsString(response);

            response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
            response.getResponseHeader().setService(request.getRequestHeader().getService());
            response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
            response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
            response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());
            response.getResponseHeader().setStatus("SUCCEEDED");

            System.out.println(responseXml);
            publisher.PublishMessage(responseXml, message.getJMSCorrelationID());

            logData.setStatus("Success");
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());
            logData.setResMessage(responseXml);
            dataDTO.save(logData);

        } catch (JMSException | JsonProcessingException e) {
            handleException(e, logData);
        }
    }

    private void initializeLogData(LogQueueData logData, TextMessage message) throws JMSException  {
        Queue sourceQueue = (Queue) message.getJMSDestination();
        logData.setOrigin("MQ_"+sourceQueue.getQueueName());
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
