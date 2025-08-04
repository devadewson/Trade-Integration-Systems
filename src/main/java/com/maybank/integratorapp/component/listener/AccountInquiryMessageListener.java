package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessAccountInquiry;
import com.maybank.integratorapp.component.system.messageprocessor.AccountInquiryMessageProcessor;
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
    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    MsQueueConfigService queueConfigService;

    @Autowired
    ProcessAccountInquiry processAccountInquiry;

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
                System.out.println("Account Inquiry Listener Received : "+message.getJMSCorrelationID());
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
                System.out.println(e.getMessage());
            }

            dataDTO.save(_data);

            publisher.PublishMessage(responseXml, correlationId);

        }

    }

    public void oldonMessage(Message message) {
        if (message instanceof TextMessage) {
            String responseXml = "";
            String correlationId = "";
            LogQueueData _data = new LogQueueData();
            try {
                System.out.println("Received 1 Message With CorrelationID : "+ message.getJMSCorrelationID());
                String _message = message.getBody(String.class);
                System.out.println("===========================xmlRequest=================================");
                System.out.println(_message);
                System.out.println("============================================================\n");

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

                XmlMapper xmlMapper = new XmlMapper();
                ServiceRequest request = xmlMapper.readValue(_message, ServiceRequest.class);
                String accNo = request.getAvailBALRequest().getBackOfficeAccount();
                // ambil branch dari digit ke-2 sampai 4 dari akun
                String branch = accNo.substring(1,4);
                String currency = msCurrencyService.findByIsoCode(request.getAvailBALRequest().getPostingCurrency()).getInternalCode();
//            String branch = request.getAvailBALRequest().getExternalAccount();
//            String currency = request.getAvailBALRequest().getPostingCurrency();

                AccountInquiryResponse accountInquiryResponse = processAccountInquiry.getAccInqWithVar(accNo, branch, currency);
                if(accountInquiryResponse.getAccountInquiryResponseData()!= null){
                    ServiceResponse serviceResponse = new ServiceResponse();
                    ResponseHeader responseHeader = new ResponseHeader();
                    Details detailsResponse = new Details();
                    AvailBalResponse availBalResponse = new AvailBalResponse();

                    detailsResponse.setInfo(accountInquiryResponse.getResponseDetail().getResponse_data());
                    responseHeader.setCorrelationID(request.getRequestHeader().getCorrelationID());
                    responseHeader.setService(request.getRequestHeader().getService());
                    responseHeader.setOperation(request.getRequestHeader().getOperation());
                    responseHeader.setSourceSystem(request.getRequestHeader().getTargetSystem());
                    responseHeader.setTargetSystem(request.getRequestHeader().getSourceSystem());

                    if(accountInquiryResponse.getAccountInquiryResponseData()!= null){
                        responseHeader.setStatus("SUCCEEDED");
                        String balance = accountInquiryResponse.getAccountInquiryResponseData().getBalance();
                        String formattedBalance = balance.substring(1).replace(".", "");

                        formattedBalance = String.format("%015d",new BigInteger(formattedBalance));
                        if(balance.startsWith("+"))
                            availBalResponse.setNegative("N");
                        else
                            availBalResponse.setNegative("Y");
                        availBalResponse.setBlocked("N");
                        availBalResponse.setApplies("Y");
                        availBalResponse.setErrorOrWarning("N");
                        availBalResponse.setCheckedInBackOffice("Y");
                        availBalResponse.setErrorCode("N");

                        availBalResponse.setErrorMessage("HOLDCODE-"+accountInquiryResponse.getAccountInquiryResponseData().getAccountStatus());
                        availBalResponse.setBalance(formattedBalance);

                    }
                    else {
                        responseHeader.setStatus("ERROR");
                        responseHeader.setDetails(new Details());
                        responseHeader.getDetails().setError(accountInquiryResponse.getResponseDetail().getResponse_data());
                    }
                    serviceResponse.setAvailBalResponse(availBalResponse);
                    serviceResponse.setResponseHeader(responseHeader);

                    xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    responseXml = xmlMapper.writeValueAsString(serviceResponse);
                    System.out.println("===========================responseXml=================================");
                    System.out.println(responseXml);
                    System.out.println("============================================================\n");
                    _data.setResMessage(responseXml);
                    _data.setStatus("Success");
                    _data.setDelivery_date(new Date());
                    _data.setUpdated_date(new Date());

                }


            } catch (JMSException | JsonProcessingException e) {
                System.out.println(e.getMessage());
            }

            dataDTO.save(_data);

            publisher.PublishMessage(responseXml, correlationId);

        }

    }
}
