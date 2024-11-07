package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessCustomerDetail;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsQueueConfig;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.model.mq.customerdetail.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.customerdetail.response.*;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponse;
import com.maybank.integratorapp.model.rest.CustomerDetail.response.CustomerInformationResponse;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CustomerDetailMessageListener implements CustomMessageListener {
    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    ProcessCustomerDetail processCustomerDetail;

    @Autowired
    MsQueueConfigService queueConfigService;
    @Autowired
    private Environment env;

    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            LogQueueData _data = new LogQueueData();
            String responseXml = "";
            try {
                System.out.println("Received 1 Message With CorrelationID : " + message.getJMSCorrelationID());
                String _message = message.getBody(String.class);

                System.out.println("===========================xmlRequest=================================");
                System.out.println(_message);
                System.out.println("============================================================\n");

                Queue sourceQueue = (Queue) message.getJMSDestination();
                _data.setOrigin("MQ_" + sourceQueue.getQueueName());
                _data.setOrigin("MQ_" + sourceQueue.getQueueName());
                _data.setMessageUID(new MQUtil().getMessageUID());
                _data.setReqMessage(_message);
                _data.setCreated_date(new Date());
                _data.setCorrelationID(message.getJMSCorrelationID());

                _data = dataDTO.save(_data);

                XmlMapper xmlMapper = new XmlMapper();
                ServiceRequest request = xmlMapper.readValue(_message, ServiceRequest.class);
                String gcifNo = request.getCustomerDetailsRequest().getCustomerId();

                CustomerInformationResponse informationResponse = new CustomerInformationResponse();
                informationResponse = processCustomerDetail.getCustomerDetail(gcifNo);

                ServiceResponse serviceResponse = new ServiceResponse();
                ResponseHeader responseHeader = new ResponseHeader();
                Details detailsResponse = new Details();
                CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();

                detailsResponse.setInfo(informationResponse.getResponseDetail().getResponseData());
                responseHeader.setCorrelationID(message.getJMSCorrelationID());
                responseHeader.setService("Customer");
                responseHeader.setOperation("CustomerDetails");
                responseHeader.setStatus(informationResponse.getResponseDetail().getResponseData() + "_" +
                        informationResponse.getResponseDetail().getErrorOrigin() + "_" +
                        informationResponse.getResponseDetail().getResponseCode());
                customerDetailsResponse.setFullName(informationResponse.getCustomerInformationResponseData().getFullName());
                customerDetailsResponse.setCustomerNumber(informationResponse.getCustomerInformationResponseData().getGCIFNo());
                AddressDetails addressDetails = new AddressDetails();
                AddressDetail detailAddress = new AddressDetail();
                String fullAddress = informationResponse.getCustomerInformationResponseData().getAddressLine1() + " " +
                        informationResponse.getCustomerInformationResponseData().getAddressLine2() + " " +
                        informationResponse.getCustomerInformationResponseData().getAddressLine3();
                detailAddress.setAddressType(fullAddress);
                addressDetails.setAddressDetail(detailAddress);
                customerDetailsResponse.setAddressDetails(addressDetails);

                serviceResponse.setResponseHeader(responseHeader);
                serviceResponse.setCustomerDetailsResponse(customerDetailsResponse);

                AccountListResponse accountListResponse = processCustomerDetail.getAccListByGcifNo(gcifNo);
                String cifNo = accountListResponse.getAccountListResponseData().getAccountData().get(0).getCifNo();
                String taxId = informationResponse.getCustomerInformationResponseData().getNPWP();
                String lineOfBussiness = informationResponse.getCustomerInformationResponseData().getLineOfBusiness();
                String national = informationResponse.getCustomerInformationResponseData().getNationality();
                String zipCode = accountListResponse.getAccountListResponseData().getZipcode();
                String custType = informationResponse.getCustomerInformationResponseData().getCustomerType();

                customerDetailsResponse.setCustomerExtraData(new CustometExtraData());
                customerDetailsResponse.getCustomerExtraData().setCifNumber(cifNo);
                customerDetailsResponse.getCustomerExtraData().setTaxId(taxId);
                customerDetailsResponse.getCustomerExtraData().setLineOfBusiness(lineOfBussiness);

                detailAddress.setZipCode(zipCode);
                customerDetailsResponse.setResidenceCountry(national);
                customerDetailsResponse.setCustomerType(custType);

                responseXml = xmlMapper.writeValueAsString(serviceResponse);
                System.out.println("===========================responseXml=================================");
                System.out.println(responseXml);
                System.out.println("============================================================\n");
                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

                message.acknowledge();

            } catch (JMSException e) {
                _data.setStatus("Error");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());

                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            dataDTO.save(_data);
            MsQueueConfig config = queueConfigService.findByServiceName("CustomerDetails");
            if (config != null && config.getEnableStatus() == 1) {
                String correlationId = null;
                try {
                    correlationId = message.getJMSCorrelationID();
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }

                MessagePublisher publisher = new MessagePublisher(
                        config.getResponse_Queue_Address(), Integer.parseInt(config.getResponse_Queue_Port()), config.getResponse_Queue_Manager(),
                        config.getResponse_Queue_Channel(), config.getResponse_Queue_Username(), config.getResponse_Queue_Password(),
                        config.getResponse_Queue_Name());

                publisher.PublishMessage(responseXml, correlationId);
            } else {
                System.out.println("MsQueueConfig 'CustomerDetail' is Null");
            }
        }
    }
}
