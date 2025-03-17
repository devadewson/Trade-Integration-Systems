package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessFacilities;
import com.maybank.integratorapp.component.system.messageprocessor.LimitFacilitiesMessageProcessor;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.repository.MsCurrencyRepository;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import com.maybank.integratorapp.data.repository.MscompanylimitRepository;
import com.maybank.integratorapp.data.service.MsCurrencyService;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.facilities.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.facilities.response.*;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FacilitiesMessageListener implements CustomMessageListener {
    @Autowired
    private LogQueueDataRepository dataDTO;
    @Autowired
    MsFacilityRepository msFacilityRepository;
    @Autowired
    MsQueueConfigService queueConfigService;
    @Autowired
    MscompanylimitRepository mscompanylimitRepository;
    @Autowired
    private Environment env;
    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }
    private MessagePublisher publisher;
    @Autowired
    ProcessFacilities processFacilities;

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
                System.out.println("Facility Enquiry Listener Received : "+message.getJMSCorrelationID());
                System.out.println(_message);
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

                String xml = messageProcessor.processMessage(_message,_data.getId());
                publisher.PublishMessage(xml,message.getJMSCorrelationID());

                _data.setResMessage(xml);
                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());
                dataDTO.save(_data);


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void onMessageOld(Message message){
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
                System.out.println("Facility Enquiry Listener Received : "+message.getJMSCorrelationID());
                System.out.println(_message);
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

                ServiceResponse response = new ServiceResponse();
                response.setFacilitiesResponse(new FacilitiesResponse());

                ResponseHeader responseHeader = new ResponseHeader();
                responseHeader.setCorrelationID(request.getRequestHeader().getCorrelationID());
                responseHeader.setService(request.getRequestHeader().getService());
                responseHeader.setOperation(request.getRequestHeader().getOperation());
                responseHeader.setSourceSystem(request.getRequestHeader().getTargetSystem());
                responseHeader.setTargetSystem(request.getRequestHeader().getSourceSystem());

                String cifno = request.getFacilitiesRequest().getFacilityRequestDetails().getCustomer().trim();
//                String cifno = "0002794045";

                // Cek apakah cifno ada di MsCompanyLimit
                if (!mscompanylimitRepository.existsByCifno(cifno)) {
                    // Jika CIF tidak ada, insert data ke tabel MsCompanyLimit
                    System.out.println("CIF " + cifno + " tidak ditemukan di tabel MsCompanyLimit. Menambahkan data baru.");
                    MsCompanyLimit newLimit = new MsCompanyLimit();
                    // set nilai CIF
                    newLimit.setCifno(cifno);
                    MsCompanyLimit savedcompanyLimit = mscompanylimitRepository.save(newLimit);
                    System.out.println("Data disimpan dengan ID: " + savedcompanyLimit.getId() + "CifNo" + savedcompanyLimit.getCifno());

                    //melakukan Process Crate data facility pada database
                    var resultSoap = processFacilities.getFacilities(cifno, savedcompanyLimit.getId());
                }

                // Ambil data pada database MsFacility
                List<MsFacility> facilities = msFacilityRepository.findByCompanyLimitId(
                        mscompanylimitRepository.findByCifno(cifno).getId());

                List<MsCurrency> currencies = (List<MsCurrency>) msCurrencyRepository.findAll();
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                if (!facilities.isEmpty()) {
                    responseXml = xmlMapper.writeValueAsString(facilities);

                    List<FacilityDetails> facilityDetails = new ArrayList<>();
                    facilities.forEach(s->{
                        try {
                            FacilityDetails fac = new FacilityDetails();
                            fac.setIdentifier(s.getKeyLoanAcc());
                            fac.setFacilityCode(s.getKeyDigitNote());
                            fac.setCustomer(cifno);

                            Date NoteDate = inputFormat.parse(s.getNoteDate());
                            Date ExpiryDate = inputFormat.parse(s.getMaturityDate());
                            String FormattedNoteDate = outputFormat.format(NoteDate);
                            String FormattedExpiryDate = outputFormat.format(ExpiryDate);

                            fac.setStartDate(FormattedNoteDate);
                            fac.setExpiryDate(FormattedExpiryDate);
                            fac.setCurrency(s.getLoanCurrencyCode());
//                            fac.setCurrency(currencies.stream().filter(x->x.getInternalCode().equals(s.getCurrency())).findFirst().get().getIsoCode());
                            String balance = s.getPrincipalBalance().split("\\.")[0];
                            String utilizedBalance = s.getCommitmentBalance().split("\\.")[0];

                            fac.setLimitAmount(balance);
                            fac.setAvailableAmount(balance);
                            fac.setMultiCurrency("N");

                            fac.setDisplayField1(s.getKeyLoanAcc());
                            fac.setDisplayField2(s.getDescription());
                            fac.setDisplayField3("-");
                            fac.setDisplayField4(s.getNoteType());
                            fac.setDisplayField5(balance);
                            fac.setDisplayField6(balance);
                            fac.setDisplayField7(utilizedBalance);
                            fac.setDisplayField8(utilizedBalance);
                            fac.setDisplayField9(FormattedExpiryDate);
                            fac.setDisplayField10(s.getStatus());

                            facilityDetails.add(fac);
//                        fac.setCurrency(s.get);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    responseHeader.setStatus("SUCCEEDED");
                    response.getFacilitiesResponse().setFacilityDetailss(new FacilityDetailss());
                    response.getFacilitiesResponse().getFacilityDetailss().setFacilityDetails(facilityDetails);

                } else {
//                        responseXml = "<response>Data fasilitas tidak ditemukan</response>";
//                        System.out.println("Tidak ada data fasilitas untuk CIF " + cifno);
                    responseHeader.setStatus("FAILED");
                    responseHeader.setDetails(new Details());
                    responseHeader.getDetails().setError("Facilities Not Found For CIF: "+cifno);
                }

                response.setResponseHeader(responseHeader);

                xmlMapper = new XmlMapper();
                // Serialize the object to XML
                String xml = null;
                try {
                    xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    xml = xmlMapper.writeValueAsString(response);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("===========================responseXml=================================");
                System.out.println(xml);
                System.out.println("============================================================\n");
                publisher.PublishMessage(xml,message.getJMSCorrelationID());

                _data.setResMessage(xml);
                _data.setStatus("Success");
                _data.setDelivery_date(new Date());
                _data.setUpdated_date(new Date());
                dataDTO.save(_data);


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
