package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessCostumerSearch;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.customersearch.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
import com.maybank.integratorapp.model.mq.customersearch.response.ServiceResponse;
import com.maybank.integratorapp.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class CustomerSearchMessageListener implements CustomMessageListener {

    @Autowired
    private LogQueueDataRepository dataDTO;

    //    @Autowired private MessagePublisher publisher;
    @Autowired
    private Environment env;
    @Autowired
    MsQueueConfigService queueConfigService;

    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;

    @Autowired
    private ProcessCostumerSearch processCustomerSearch;

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

            ServiceRequest request = parseRequest(message);

            String customerNumber = request.getCustomerSearchRequest().getCustomerNumber();

            CustomerSearchResult customerSearchResultResponse = customerSearchResultResponse(customerNumber);

            // Set CustomerSearchResult ke dalam CustomerSearchResults
            List<CustomerSearchResult> results = new ArrayList<>();
            results.add(customerSearchResultResponse);
            response.getCustomerSearchResponse().getCustomerSearchResults().setCustomerSearchResult(results);

            setInitialResponseHeader(response, request);

            //Send Response To QUEUE Response
            String responseXml = new XmlMapper().writeValueAsString(response);
            MsQueueConfig config = queueConfigService.findByServiceName("CustomerSearch");
            if(config != null && config.getEnableStatus() == 1){
                String correlationId = message.getJMSCorrelationID();

                MessagePublisher publisher = new MessagePublisher(config.getResponse_Queue_Address(),
                        config.getResponse_Queue_Username(),config.getResponse_Queue_Password(), config.getResponse_Queue_Name());
                publisher.PublishMessage(responseXml, correlationId);
            }else{
                System.out.println("MsQueueConfig 'CustomerDetail' is Null");
            }

            response.getResponseHeader().setStatus("Success");

            logData.setStatus("Success");
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());

            message.acknowledge();

        } catch (JMSException | JsonProcessingException e) {
            handleException(e, response, logData);
        }

        dataDTO.save(logData);

    }
    private CustomerSearchResult customerSearchResultResponse(String customerNumber) {
        return processCustomerSearch.getCustomerSearchResult(customerNumber);
    }

    private void initializeLogData(LogQueueData logData, TextMessage message) throws JMSException  {
        ActiveMQDestination sourceQueue = (ActiveMQDestination) message.getJMSDestination();
        logData.setOrigin("MQ_" + sourceQueue.getPhysicalName());
        logData.setMessageUID(new MQUtil().getMessageUID());
        logData.setReqMessage(message.getText());
        logData.setCreated_date(new Date());
        logData.setCorrelationID(message.getJMSCorrelationID());

    }
    private void setInitialResponseHeader(ServiceResponse response, ServiceRequest request) {
        response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
        response.getResponseHeader().setService(request.getRequestHeader().getService());
        response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
        response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
        response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());
    }

    private ServiceRequest parseRequest(TextMessage message) throws JsonProcessingException, JMSException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(message.getText(), ServiceRequest.class);
    }

    private void handleException(Exception e, ServiceResponse response, LogQueueData logData) {
        String errorMsg;

        if (e instanceof JMSException) {
            errorMsg = "MessageQueue Error";
        } else if (e instanceof JsonMappingException) {
            errorMsg = "Mapping Error";
        } else if (e instanceof JsonProcessingException) {
            errorMsg = "Parse Mapping Error";
        } else {
            errorMsg = "Unknown Error";
        }

        response.getResponseHeader().setStatus("Error");
        response.getResponseHeader().getDetails().setError(errorMsg);

        logData.setStatus("Error");
        logData.setDelivery_date(new Date());
        logData.setUpdated_date(new Date());

    }

}
