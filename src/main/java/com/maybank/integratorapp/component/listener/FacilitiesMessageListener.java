package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.system.messageprocessor.LimitFacilitiesMessageProcessor;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.repository.MsCurrencyRepository;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import com.maybank.integratorapp.data.repository.MsCompanyLimitRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.facilities.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.facilities.response.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FacilitiesMessageListener implements CustomMessageListener {
    private static Logger log = LoggerFactory.getLogger(FacilitiesMessageListener.class);
    @Autowired
    private LogQueueDataRepository dataDTO;
    @Autowired
    MsFacilityRepository msFacilityRepository;
    @Autowired
    MsQueueConfigService queueConfigService;
    @Autowired
    MsCompanyLimitRepository mscompanylimitRepository;
    @Autowired
    private Environment env;
    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }
    private MessagePublisher publisher;

    @Autowired
    LimitFacilitiesMessageProcessor messageProcessor;
    @Autowired
    private MsCurrencyRepository msCurrencyRepository;
    private void forwardMessage(TextMessage message,String serviceName){
        MsQueueConfig config = queueConfigService.findByServiceName(serviceName);
        //reuse existing connection instead of creating new one
        MessagePublisher publisher = new MessagePublisher(this.publisher.getConnection(),this.publisher.getSession(), config.getRequest_Queue_Name());
//        MessagePublisher publisher = new MessagePublisher(
//                config.getRequest_Queue_Address(),
//                Integer.parseInt(config.getRequest_Queue_Port()),
//                config.getRequest_Queue_Manager(),
//                config.getRequest_Queue_Channel(),
//                config.getRequest_Queue_Username(),
//                config.getRequest_Queue_Password(),
//                config.getRequest_Queue_Name());
        try {
            publisher.PublishMessage(message.getText(), message.getJMSCorrelationID());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onMessage(Message message){
        if (message instanceof TextMessage){
            LogQueueData _data = new LogQueueData();
            String responseXml = "";
            String correlationId = "";
            try {

                String _message = message.getBody(String.class);

                if(((TextMessage) message).getText().contains("<Operation>Reservations</Operation>")){
                    forwardMessage((TextMessage) message,"FacilityReservation");
                    message.acknowledge();
                    return;
                }else if(((TextMessage) message).getText().contains("<Operation>Exposure</Operation>")){
                    forwardMessage((TextMessage) message,"FacilityUtilization");
                    message.acknowledge();
                    return;
                }else if(((TextMessage) message).getText().contains("<Operation>ReservationsReversal</Operation>")){
                    forwardMessage((TextMessage) message,"ReservationReversal");
                    message.acknowledge();
                    return;
                }
                log.info("Facility Enquiry Listener Received : "+message.getJMSCorrelationID());
//                System.out.println(_message);
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
                _data.setRelatedTransRef(MQUtil.findTransRef(_data.getOrigin(),_message));
                _data.setCorrelationID(correlationId);
                _data = dataDTO.save(_data);
                message.acknowledge();

                String xml = messageProcessor.processMessage(_message,_data.getId());
                publisher.PublishMessage(xml,message.getJMSCorrelationID());

                _data.setResMessage(xml);
                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());
                dataDTO.save(_data);


            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
