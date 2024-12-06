package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessFacilities;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import com.maybank.integratorapp.data.repository.MscompanylimitRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.facilities.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.facilities.response.*;
import com.maybank.integratorapp.util.MQUtil;
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

    @Override
    public void onMessage(Message message){
        if (message instanceof TextMessage){
            LogQueueData _data = new LogQueueData();
            String responseXml = "";
            String correlationId = "";
            try {
                System.out.println("Received 1 Message With CorrelationID : " + message.getJMSCorrelationID());
                String _message = message.getBody(String.class);

                System.out.println("===========================xmlRequest=================================");
                System.out.println(_message);
                System.out.println("============================================================\n");

                correlationId = message.getJMSCorrelationID();

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
                request.getRequestHeader().setCorrelationID(message.getJMSCorrelationID());

                String cifno = request.getFacilitiesRequest().getFacilityRequestDetails().getCustomer().trim();

                // Cek apakah cifno ada di MsCompanyLimit
                if (mscompanylimitRepository.existsByCifno(cifno)) {
                    System.out.println("CIF " + cifno + " ditemukan di tabel MsCompanyLimit.");

                    // Ambil data pada database MsFacility
                    List<MsFacility> facilities = msFacilityRepository.findByCompanyLimitId(
                            mscompanylimitRepository.findByCifno(cifno).getId()
                    );
                    if (!facilities.isEmpty()) {
                        responseXml = xmlMapper.writeValueAsString(facilities);
                    } else {
                        responseXml = "<response>Data fasilitas tidak ditemukan</response>";
                        System.out.println("Tidak ada data fasilitas untuk CIF " + cifno);
                    }
                } else {
                    // Jika CIF tidak ada, insert data ke tabel MsCompanyLimit
                    System.out.println("CIF " + cifno + " tidak ditemukan di tabel MsCompanyLimit. Menambahkan data baru.");
                    MsCompanyLimit newLimit = new MsCompanyLimit();
                    // set nilai CIF
                    newLimit.setCifno(cifno);
                    MsCompanyLimit savedcompanyLimit = mscompanylimitRepository.save(newLimit);
                    System.out.println("Data disimpan dengan ID: " + savedcompanyLimit.getId() + "CifNo" + savedcompanyLimit.getCifno());

                    //melakukan Process Crate data facility pada database
                    var resultSoap = processFacilities.getFacilities(cifno, savedcompanyLimit.getId());

                    // Ambil data pada database MsFacility
                    List<MsFacility> facilities = msFacilityRepository.findByCompanyLimitId(
                            mscompanylimitRepository.findByCifno(cifno).getId());
                    if (!facilities.isEmpty()) {
                        responseXml = xmlMapper.writeValueAsString(facilities);
                    } else {
                        responseXml = "<response>Data fasilitas tidak ditemukan</response>";
                        System.out.println("Tidak ada data fasilitas untuk CIF " + cifno);
                    }

                }
                System.out.println("===========================responseXml=================================");
                System.out.println(responseXml);
                System.out.println("============================================================\n");


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
