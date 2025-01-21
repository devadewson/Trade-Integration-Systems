package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessCostumerSearch;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.customersearch.response.Details;
import com.maybank.integratorapp.model.mq.customersearch.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
import com.maybank.integratorapp.model.mq.customersearch.response.ServiceResponse;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
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

    private void forwardMessage(TextMessage message){
        MsQueueConfig config = queueConfigService.findByServiceName("CustomerDetails");
        MessagePublisher publisher = new MessagePublisher(
                config.getRequest_Queue_Address(),
                Integer.parseInt(config.getRequest_Queue_Port()),
                config.getRequest_Queue_Manager(),
                config.getRequest_Queue_Channel(),
                config.getRequest_Queue_Username(),
                config.getRequest_Queue_Password(),
                config.getRequest_Queue_Name());
        try {
            publisher.PublishMessage(message.getText(), message.getJMSCorrelationID());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    private void processMessage(TextMessage message) {
        ServiceResponse response = new ServiceResponse();
        LogQueueData logData = new LogQueueData();

        try {
            initializeLogData(logData, message);
            System.out.println("Received 1 Message With CorrelationID : " + message.getJMSCorrelationID());

            String correlationId = message.getJMSCorrelationID();
            if(dataDTO.findByCorrelationId(correlationId)!= null){
                message.acknowledge();
                return;
            }
            //new logic, if the Operation Tag is CustomerDetails, forward the message to another queues
            if(message.getText().contains("<Operation>CustomerDetails</Operation>")){
                forwardMessage(message);
                message.acknowledge();
                return;
            }
            ServiceRequest request = parseRequest(message);

            String customerNumber = request.getCustomerSearchRequest().getCustomerNumber();
            if(customerNumber == null){
                Details detailsResponse = new Details();
                detailsResponse.setError("GCIF Is Empty");
                response.getResponseHeader().setDetails(detailsResponse);
                response.getResponseHeader().setStatus("ERROR");

            }else{
                String tagCustomer = request.getCustomerSearchRequest().getIncludeCustomers();
                String tagBank = request.getCustomerSearchRequest().getIncludeBanks();

                //check 2 tag ini, hanya 1 yang boleh Y
//            <ns2:IncludeCustomers>Y</ns2:IncludeCustomers>
//            <ns2:IncludeBanks>Y</ns2:IncludeBanks>
                // save 2 tag ini ke db
                if(!tagCustomer.equals(tagBank)){

//            String customerNumber = request.getCustomerSearchRequest().getCustomerMnemonic()
                    CustomerSearchResult customerSearchResultResponse = processCustomerSearch.getCustomerSearchResult(customerNumber,tagCustomer,tagBank);
                    // Set CustomerSearchResult ke dalam CustomerSearchResults
                    List<CustomerSearchResult> results = new ArrayList<>();
                    results.add(customerSearchResultResponse);
                    response.getCustomerSearchResponse().getCustomerSearchResults().setCustomerSearchResult(results);
                    response.getResponseHeader().setStatus("SUCCEEDED");

                }else{
                    Details detailsResponse = new Details();
                    detailsResponse.setError("Please Check Only One, Bank=Y or Corporate=Y");
                    response.getResponseHeader().setDetails(detailsResponse);
                    response.getResponseHeader().setStatus("ERROR");
                }
            }

            setInitialResponseHeader(response, request);

            //Send Response To QUEUE Response
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String responseXml = xmlMapper.writeValueAsString(response);

            publisher.PublishMessage(responseXml, message.getJMSCorrelationID());


            logData.setStatus("Success");
            logData.setResMessage(responseXml);
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());

            message.acknowledge();

        } catch (JMSException | JsonProcessingException e) {
            handleException(e, response, logData);
        }

        dataDTO.save(logData);

    }

    private void initializeLogData(LogQueueData logData, TextMessage message)  {
        Queue sourceQueue = null;
        try {
            sourceQueue = (Queue) message.getJMSDestination();
            logData.setOrigin("MQ_"+sourceQueue.getQueueName());
            logData.setMessageUID(new MQUtil().getMessageUID());
            logData.setReqMessage(message.getText());
            logData.setCreated_date(new Date());
            logData.setCorrelationID(message.getJMSCorrelationID());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }


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
            System.out.println(e.getMessage());
        } else if (e instanceof JsonMappingException) {
            System.out.println(e.getMessage());
        } else if (e instanceof JsonProcessingException) {
            System.out.println(e.getMessage());
        } else {
            System.out.println(e.getMessage());
        }

        response.getResponseHeader().setStatus("Error");
        response.getResponseHeader().getDetails().setError(e.getMessage());

        logData.setStatus("Error");
        logData.setDelivery_date(new Date());
        logData.setUpdated_date(new Date());

    }

}
