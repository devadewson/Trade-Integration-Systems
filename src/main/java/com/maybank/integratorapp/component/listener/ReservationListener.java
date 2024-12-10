package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.CustomMessageListener;
import com.maybank.integratorapp.component.MessagePublisher;
import com.maybank.integratorapp.component.coresystem.ProcessReservation;
import com.maybank.integratorapp.data.entity.LogQueueData;
import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.entity.MsUtilizeRunningNumber;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import com.maybank.integratorapp.data.repository.MsUtilizeRunningNumberRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.reservation.request.ServiceRequest;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ReservationListener implements CustomMessageListener {

    @Autowired
    ProcessReservation processReservation;

    @Autowired
    private LogQueueDataRepository dataDTO;

    @Autowired
    private Environment env;

    @Autowired
    MsQueueConfigService queueConfigService;

    @Autowired
    MsFacilityRepository msFacilityRepository;

    @Autowired
    MsUtilizeRunningNumberRepository msUtilizeRunningNumberRepository;

    public void setPublisher(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    private MessagePublisher publisher;

    @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    processMessage((TextMessage) message);
                }
            }
            private void processMessage(TextMessage message) {
                LogQueueData logData = new LogQueueData();
                String newKeyLoanAcc = null;
                String acctReqXL01 = null;

                try {
                    initializeLogData(logData, message);
                    dataDTO.save(logData);
                    message.acknowledge();

                    ServiceRequest request = parseRequest(message);
            String keyLoanAcc = request.getReservationsRequest().getReservationRequestDetails().getFacilityIdentifier();

            // Ambil semua MsFacility dengan keyLoanAcc yang sesuai
            MsFacility facilities = msFacilityRepository.findByKeyLoanAcc(keyLoanAcc);

            // Hentikan proses jika tidak ditemukan
            if (facilities == null) {
                System.out.println("No Facility found for KeyLoanAcc: " + keyLoanAcc);
                return;
            }
            // Ambil ID dari MsFacilit
            Long facilityId = facilities.getId();
            System.out.println("Facility ID: " + facilityId);

            // Ambil MsUtilizeRunningNumber berdasarkan facilityId
            MsUtilizeRunningNumber runningNumberEntry = msUtilizeRunningNumberRepository.findByFacilityId(facilityId);

                if (runningNumberEntry == null) {
                    System.out.println("No Running Number found for Facility ID: " + facilityId);
                } else {
                    // Ambil Running Number dan pastikan format 3 digit
                    int runningNumber = runningNumberEntry.getRunningNumber();
                    String formattedRunningNumber = String.format("%03d", runningNumber + 1);
                    System.out.println("Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber);

                    // Buat keyLoanAcc baru dengan mengganti bagian draw
                    newKeyLoanAcc = buildNewKey(keyLoanAcc, formattedRunningNumber);
                    System.out.println("New KeyLoanAcc: " + newKeyLoanAcc);

                    // Buat formatted key untuk sistem proses
                    acctReqXL01 = buildFormattedKey(keyLoanAcc, formattedRunningNumber);
                    System.out.println("New Formatted Key: " + acctReqXL01);

                }

            String CMSxl01Draw001Response = cmsXl01Draw001Response(newKeyLoanAcc,acctReqXL01);

            logData.setStatus("Success");
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());

        } catch (JMSException | JsonProcessingException e) {
            handleException(e, logData);
        }
    }

    private void initializeLogData(LogQueueData logData, TextMessage message) throws JMSException {
        Queue sourceQueue = (Queue) message.getJMSDestination();
        logData.setOrigin("MQ_" + sourceQueue.getQueueName());
        logData.setMessageUID(new MQUtil().getMessageUID());
        logData.setReqMessage(message.getText());
        logData.setCreated_date(new Date());
        logData.setCorrelationID(message.getJMSCorrelationID());
    }

    private ServiceRequest parseRequest(TextMessage message) throws JsonProcessingException, JMSException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(message.getText(), ServiceRequest.class);
    }
    // Pecah originalKey menggunakan splitKey
    private String buildNewKey(String originalKey, String formattedRunningNumber) {
        String[] parts = splitKey(originalKey);
        parts[5] = formattedRunningNumber;
        return String.join("", parts);
    }

    private String[] splitKey(String key) {
        String bank = key.substring(0, 2);
        String currency = key.substring(2, 5);
        String branchCode = key.substring(5, 8);
        String cif = key.substring(8, 18);
        String note = key.substring(18, 26);
        String draw = key.substring(26, 29);
        String seq = key.substring(29, 31);

        return new String[]{bank, currency, branchCode, cif, note, draw, seq};
    }

    //  key dengan format yang dimodifikasi
    private String buildFormattedKey(String originalKey, String formattedRunningNumber) {
        // Ambil bagian yang diperlukan dari originalKey
        String cif = originalKey.substring(8, 18);
        String note = originalKey.substring(18, 26);
        String seq = originalKey.substring(29, 31);
        String formattedDraw = formattedRunningNumber;

        return cif + "." + note + "." + formattedDraw + "." + seq;
    }

    private String cmsXl01Draw001Response(String newKeyLoanAcc,String acctReqXL01) {
        return processReservation.getReversal(newKeyLoanAcc,acctReqXL01);
    }

    private void handleException(Exception e, LogQueueData logData) {
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

        logData.setStatus("Error");
        logData.setDelivery_date(new Date());
        logData.setUpdated_date(new Date());
    }
}
