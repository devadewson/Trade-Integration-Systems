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
import jakarta.jms.TextMessage;
import org.apache.activemq.command.ActiveMQDestination;
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


                //set the response header early for static value
//                response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
//                response.getResponseHeader().setService(request.getRequestHeader().getService());
//                response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
//                response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
//                response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());
//            xmlMapper.readValue(_message, ServiceRequest.class);
//            ObjectMapper mapper = new ObjectMapper();
//            RequestModel requestModel = mapper.readValue(_message, RequestModel.class);
//            SystemMessage messageReceipt = new ObjectMapper().reader().readValue(_message);


//            System.out.println("Account Balance : "+request.getData().getBalance());
//            messageReceipt.data.setSource("MQReply");

                process.putFileContent(request.getSwiftOut().getMessages().getMessage(), message.getJMSCorrelationID());
//            DCIFInquiryServices process = new DCIFInquiryServices();

//            BodyData data = requestModel.getData();
//            data.setBalance(process.doSomething());
//            double balance = ;
//            data.setBalance(process.getAccountBalance(requestModel.getData().getAccount()));
//                CustomerSearchResponse _CustomerSearchResponse = response.getCustomerSearchResponse();
//                CustomerSearchResults _CustomerSearchResults = _CustomerSearchResponse.getCustomerSearchResults();

//                List<CustomerSearchResult> searchResults = process.getCustomerSearch(request.getCustomerSearchRequest().getCustomerMnemonic());
//                _CustomerSearchResults.setCustomerSearchResult(searchResults);
//                response.getCustomerSearchResponse().getCustomerSearchResults().setCustomerSearchResult(searchResults);
//            AvailBalResponse _availBalResponse = response.getAvailBalResponse();
//            _availBalResponse.setBalance(process.getAccountBalance(request.getAvailBALRequest().getBackOfficeAccount()));
//                response.getAvailBalResponse().setBalance(process.getAccountBalanceNew(request.getAvailBALRequest().getBackOfficeAccount()));
//                response.getResponseHeader().setStatus("Success");

                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

//
//            ResponseModel responseModel = new ResponseModel();
//            responseModel.setIdtrans(requestModel.getIdtrans());
//            responseModel.setActiontype("Response");
//            responseModel.setData(data);
//            responseModel.setStatus("DONE");

                message.acknowledge();

//            System.out.println("Account Balance : "+responseModel.getData().getBalance());


//            producer.PublishMessage(responseModel);

            } catch (JMSException e) {
//                response.getResponseHeader().setStatus("Error");
//                response.getResponseHeader().getDetails().setError("MessageQueue Error");

                _data.setStatus("Error");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

                throw new RuntimeException(e);
            } catch (JsonMappingException e) {
//                response.getResponseHeader().setStatus("Error");
//                response.getResponseHeader().getDetails().setError("Mapping Error");

                _data.setStatus("Error");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());


                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
//                response.getResponseHeader().setStatus("Error");
//                response.getResponseHeader().getDetails().setError("Parse Mapping Error");

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
