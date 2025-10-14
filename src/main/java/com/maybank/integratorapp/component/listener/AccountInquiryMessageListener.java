package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.system.messageprocessor.AccountInquiryMessageProcessor;
import com.maybank.integratorapp.controller.DashboardController;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.service.MsCurrencyService;
import com.maybank.integratorapp.model.mq.accountinquiry.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;
import com.maybank.integratorapp.model.mq.accountinquiry.response.Details;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ResponseHeader;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ServiceResponse;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.*;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;

import java.math.BigInteger;
import java.util.Date;

@Component
public class AccountInquiryMessageListener implements CustomMessageListener {
    private static Logger log = LoggerFactory.getLogger(AccountInquiryMessageListener.class);
    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    MsQueueConfigService queueConfigService;

    @Autowired
    AccountInquiryMessageProcessor accountInquiryMessageProcessor;

    @Autowired
    MsCurrencyService msCurrencyService;

    @Autowired
    private Environment env;
    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            String responseXml = "";
            String correlationId = "";
            LogQueueData _data = new LogQueueData();
            try {

                String _message = message.getBody(String.class);
                log.info("Account Inquiry Listener Received : "+message.getJMSCorrelationID());
//                System.out.println(message.getBody(String.class));

                correlationId = message.getJMSCorrelationID();
                if(dataDTO.findByCorrelationId(correlationId)!= null){
                    message.acknowledge();
                    return;
                }

                Queue sourceQueue = (Queue) message.getJMSDestination();
                _data.setOrigin("MQ_"+sourceQueue.getQueueName());
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setReqMessage(_message);
                _data.setCreated_date(new Date());
                _data.setCorrelationID(correlationId);

                _data = dataDTO.save(_data);

                message.acknowledge();
                responseXml = accountInquiryMessageProcessor.processMessage(_message, _data.getId());
//                System.out.println("===========================xmlResponse=================================");
//                System.out.println(responseXml);
//                System.out.println("============================================================\n");
                _data.setDestination("MQ_"+publisher.getDestinationQueue());
                _data.setResMessage(responseXml);
                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

            } catch (JMSException e) {
                log.error(e.getMessage());
            }

            dataDTO.save(_data);

            publisher.PublishMessage(responseXml, correlationId);

        }

    }

}
