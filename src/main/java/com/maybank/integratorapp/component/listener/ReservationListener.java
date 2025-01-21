package com.maybank.integratorapp.component.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
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
import com.maybank.integratorapp.data.repository.MsMapClsProductTypeRepository;
import com.maybank.integratorapp.data.repository.MsUtilizeRunningNumberRepository;
import com.maybank.integratorapp.data.service.MsQueueConfigService;
import com.maybank.integratorapp.model.mq.reservation.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.reservation.response.*;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Date;

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
        ServiceResponse response = new ServiceResponse();
        LogQueueData logData = new LogQueueData();
        String newKeyLoanAcc = null;
        String acctReqXL01 = null;

        try {
            initializeLogData(logData, message);
            dataDTO.save(logData);
            message.acknowledge();

            ServiceRequest request = parseRequest(message);

            //From requset to maping response FTI
            String facilityIdentifier = request.getReservationsRequest().getReservationRequestDetails().getFacilityIdentifier();
            String customerRes = request.getReservationsRequest().getReservationRequestDetails().getCustomer();
//            String customerRes = "0002794045";
            String masterReference= request.getReservationsRequest().getReservationRequestDetails().getMasterReference();
            String lineOfBusiness = "01";
            String eventCode = request.getReservationsRequest().getReservationRequestDetails().getEventReference().substring(0, 3);
            String startdateRes = request.getReservationsRequest().getReservationRequestDetails().getTenorStartDate();
            String expireDateRes =  request.getReservationsRequest().getReservationRequestDetails().getTenorEndDate();
            String transDateRes = request.getReservationsRequest().getReservationRequestDetails().getValueDate();
            String exposureAmmount =  request.getReservationsRequest().getReservationRequestDetails().getPostingAmount().getAmount();


            // Ambil semua MsFacility dengan keyLoanAcc yang sesuai
            MsFacility facilities = msFacilityRepository.findByKeyLoanAcc(facilityIdentifier);
            if (facilities == null) {
                System.out.println("No Facility found for KeyLoanAcc: " + facilityIdentifier);
                return;
            }
            // Ambil ID dari MsFacilit
            Long facilityId = facilities.getId();
            String facilitySequence = facilities.getKeyDigitNote();
            String currency = facilities.getLoanCurrencyCode();

            String limitAmount =  facilities.getPrincipalBalance().replace(".00", "");
            String reservedAmount =  facilities.getCommitmentBalance().replace(".00", "");
            String availableAmount =  facilities.getCommitmentBalance().replace(".00", "");

//            String limitAmount = decimalFormat.format( facilities.getPrincipalBalance());
//            String reservedAmount = decimalFormat.format( facilities.getPrincipalBalance());
//            String availableAmount = decimalFormat.format(Double.parseDouble(limitAmount) - Double.parseDouble(reservedAmount));
            String productType  = facilities.getNoteType();


            System.out.println("Facility ID: " + facilityId);

            // Ambil MsUtilizeRunningNumber berdasarkan facilityId
            MsUtilizeRunningNumber runningNumberEntry = msUtilizeRunningNumberRepository.findByFacilityId(facilityId);

            if (runningNumberEntry == null) {
                MsUtilizeRunningNumber newRunning = new MsUtilizeRunningNumber();
                newRunning.setCompanyLimitId(facilities.getCompanyLimitId());
                newRunning.setFacilityId(facilityId);
                newRunning.setRunningNumber(0);
                msUtilizeRunningNumberRepository.save(newRunning);
                runningNumberEntry = newRunning;
                System.out.println("No Running Number found for Facility ID: " + facilityId);
            }
            // Ambil Running Number dan pastikan format 3 digit
            int runningNumber = runningNumberEntry.getRunningNumber();
            String formattedRunningNumber = String.format("%03d", runningNumber + 1);
            System.out.println("Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber);

            // Buat keyLoanAcc baru dengan mengganti bagian draw
            newKeyLoanAcc = buildNewKey(facilityIdentifier, formattedRunningNumber);
            System.out.println("New KeyLoanAcc: " + newKeyLoanAcc);

            // Buat formatted key untuk sistem proses
            acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);
            System.out.println("New Formatted Key: " + acctReqXL01);



            String CMSxl01Draw001Response = processReservation.getReservation(masterReference, newKeyLoanAcc, acctReqXL01, facilityIdentifier,customerRes, transDateRes,startdateRes,expireDateRes
            ,exposureAmmount,currency,limitAmount,reservedAmount,productType,lineOfBusiness,eventCode,facilities);

            //set reservation response
            ReservationsResponse reservationsResponse =  new ReservationsResponse();
            reservationsResponse.setFacilityIdentifier(facilityIdentifier);
            reservationsResponse.setFacilitySequence(facilitySequence);
            reservationsResponse.setReservationIdentifier(newKeyLoanAcc);
            reservationsResponse.setReservationSequence(formattedRunningNumber);
            reservationsResponse.setCustomer(customerRes);
            reservationsResponse.setFacilityExposureIdentifier(newKeyLoanAcc);

            //set Reservation Details
            ReservationResponseDetails reservationResponseDetails = new ReservationResponseDetails();
            reservationResponseDetails.setStartDate(startdateRes);
            reservationResponseDetails.setExpiryDate(expireDateRes);
            reservationResponseDetails.setCurrency(currency);
            reservationResponseDetails.setLimitAmount(limitAmount);
            reservationResponseDetails.setExposureAmount(exposureAmmount);
            reservationResponseDetails.setReservedAmount(reservedAmount);
            reservationResponseDetails.setAvailableAmount(String.valueOf(availableAmount));
            reservationResponseDetails.setLimitCheckStatus("S");

            // Set ReservationResponseDetailss
            ReservationResponseDetailss reservationResponseDetailss = new ReservationResponseDetailss();
            reservationResponseDetailss.setReservationResponseDetails(reservationResponseDetails);

            ReservationResponseExtraDetails reservationResponseExtraDetails = new ReservationResponseExtraDetails();
            ReservationResponseExtraDetailss reservationResponseExtraDetailss = new ReservationResponseExtraDetailss();
            reservationResponseExtraDetails.setName("Name");
            reservationResponseExtraDetails.setValue("value");

            reservationResponseExtraDetailss.getReservationResponseExtraDetails().add(reservationResponseExtraDetails);

            // Set ke dalam ReservationsResponse
            reservationsResponse.setReservationResponseDetailss(reservationResponseDetailss);
            reservationsResponse.setReservationResponseExtraDetailss(reservationResponseExtraDetailss);

            ResponseHeader responseHeader = new ResponseHeader();

            setInitialResponseHeader(responseHeader, request);

            response.setReservationsResponse(reservationsResponse);
            response.setResponseHeader(responseHeader);

            //Send Response To QUEUE Response
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String responseXml = xmlMapper.writeValueAsString(response);

            System.out.println(responseXml);

            publisher.PublishMessage(responseXml, message.getJMSCorrelationID());

            logData.setStatus("Success");
            logData.setDelivery_date(new Date());
            logData.setUpdated_date(new Date());
            logData.setResMessage(responseXml);
            dataDTO.save(logData);

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

    private void setInitialResponseHeader(ResponseHeader responseHeader, ServiceRequest request) {
        if (request != null && request.getRequestHeader() != null) {
            responseHeader.setCorrelationID(request.getRequestHeader().getCorrelationID());
            responseHeader.setService(request.getRequestHeader().getService());
            responseHeader.setOperation(request.getRequestHeader().getOperation());
            responseHeader.setSourceSystem(request.getRequestHeader().getTargetSystem());
            responseHeader.setTargetSystem(request.getRequestHeader().getSourceSystem());
            responseHeader.setStatus("SUCCEEDED");
        } else {
            responseHeader.setStatus("FAILED");
        }
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
