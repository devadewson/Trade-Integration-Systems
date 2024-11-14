package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessAccountInquiry;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.accountinquiry.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;
import com.maybank.integratorapp.model.mq.accountinquiry.response.Details;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ResponseHeader;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ServiceResponse;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.*;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import java.util.Date;

@Component
public class AccountInquiryMessageListener implements CustomMessageListener {
    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    MsQueueConfigService queueConfigService;

    @Autowired
    ProcessAccountInquiry processAccountInquiry;
    @Autowired
    private Environment env;
    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {}
        String responseXml = "";
        LogQueueData _data = new LogQueueData();
        try {
            System.out.println("Received 1 Message With CorrelationID : "+ message.getJMSCorrelationID());
            String _message = message.getBody(String.class);
            System.out.println("===========================xmlRequest=================================");
            System.out.println(_message);
            System.out.println("============================================================\n");

            Queue sourceQueue = (Queue) message.getJMSDestination();
            _data.setOrigin("MQ_"+sourceQueue.getQueueName());
            _data.setMessageUID(new MQUtil().getMessageUID());
            _data.setReqMessage(_message);
            _data.setCreated_date(new Date());
            _data.setCorrelationID(message.getJMSCorrelationID());

            _data = dataDTO.save(_data);
            message.acknowledge();

            XmlMapper xmlMapper = new XmlMapper();
            ServiceRequest request = xmlMapper.readValue(_message, ServiceRequest.class);
            String accNo = request.getAvailBALRequest().getBackOfficeAccount();
            String branch = request.getAvailBALRequest().getExternalAccount();
            String currency = request.getAvailBALRequest().getPostingCurrency();

            AccountInquiryResponse accountInquiryResponse = processAccountInquiry.getAccInqWithVar(accNo, branch, currency);

            ServiceResponse serviceResponse = new ServiceResponse();
            ResponseHeader responseHeader = new ResponseHeader();
            Details detailsResponse = new Details();
            AvailBalResponse availBalResponse = new AvailBalResponse();

            detailsResponse.setInfo(accountInquiryResponse.getResponseDetail().getResponse_data());
            responseHeader.setCorrelationID(message.getJMSCorrelationID());
            responseHeader.setService("AccountInquiry");
            responseHeader.setOperation("AccountInquiry");
            responseHeader.setStatus(accountInquiryResponse.getResponseDetail().getResponse_data() + "_" +
                    accountInquiryResponse.getResponseDetail().getError_origin() +"_"+
                    accountInquiryResponse.getResponseDetail().getResponse_code());
            availBalResponse.setBalance(accountInquiryResponse.getAccountInquiryResponseData().getBalance());

            serviceResponse.setAvailBalResponse(availBalResponse);
            serviceResponse.setResponseHeader(responseHeader);

            responseXml = xmlMapper.writeValueAsString(serviceResponse);;
            System.out.println("===========================responseXml=================================");
            System.out.println(responseXml);
            System.out.println("============================================================\n");
            _data.setStatus("Success");
            _data.setDelivery_date(new Date());
            _data.setUpdated_date(new Date());

        } catch (JMSException e) {
            _data.setStatus("Error");
            _data.setDelivery_date(new Date());
            _data.setUpdated_date(new Date());

            System.out.println(e.getMessage());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

        dataDTO.save(_data);
        MsQueueConfig config = queueConfigService.findByServiceName("AccountInquiry");
        if (config != null && config.getEnableStatus() == 1) {
            String correlationId = null;
            try {
                correlationId = message.getJMSCorrelationID();
            } catch (JMSException e) {
                System.out.println(e.getMessage());
            }

            MessagePublisher publisher = new MessagePublisher(
                    config.getResponse_Queue_Address(), Integer.parseInt(config.getResponse_Queue_Port()), config.getResponse_Queue_Manager(),
                    config.getResponse_Queue_Channel(), config.getResponse_Queue_Username(), config.getResponse_Queue_Password(),
                    config.getResponse_Queue_Name());

            publisher.PublishMessage(responseXml, correlationId);
        } else {
            System.out.println("MsQueueConfig 'AccountInquiry' is Null");
        }
    }
}
