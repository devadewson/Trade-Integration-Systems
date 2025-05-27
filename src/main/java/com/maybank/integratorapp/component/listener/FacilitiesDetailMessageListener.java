package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessFacilitiesDetails;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.facilitiesdetails.request.ServiceRequest;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class FacilitiesDetailMessageListener implements CustomMessageListener {
    @Autowired
    private LogQueueDataRepository dataDTO;
    @Autowired
    MsQueueConfigService queueConfigService;
    @Autowired
    private Environment env;
    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }
    private MessagePublisher publisher;

    @Autowired
    private ProcessFacilitiesDetails processFacilitiesDetails;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            LogQueueData _data = new LogQueueData();
            String responseXml = "";
            try {
                System.out.println("Facilities Received 1 Message With CorrelationID : " + message.getJMSCorrelationID());
                String _message = message.getBody(String.class);

//                System.out.println("===========================xmlRequest=================================");
//                System.out.println(_message);
//                System.out.println("============================================================\n");

                Queue sourceQueue = (Queue) message.getJMSDestination();
                _data.setOrigin("MQ_" + sourceQueue.getQueueName());
                _data.setOrigin("MQ_" + sourceQueue.getQueueName());
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setReqMessage(_message);
                _data.setCreated_date(new Date());
                _data.setCorrelationID(message.getJMSCorrelationID());

                _data = dataDTO.save(_data);
//                message.acknowledge();

                XmlMapper xmlMapper = new XmlMapper();
                ServiceRequest request = xmlMapper.readValue(_message, ServiceRequest.class);
                request.getRequestHeader().setCorrelationID(message.getJMSCorrelationID());
                var resultSoap = processFacilitiesDetails.getFacilitiesDetails(request);

                responseXml = xmlMapper.writeValueAsString(request);
//                System.out.println("===========================responseXml=================================");
//                System.out.println(responseXml);
//                System.out.println("============================================================\n");

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
