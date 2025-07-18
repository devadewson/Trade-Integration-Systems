package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.coresystem.ProcessFacilities;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.*;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.mq.reservation.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.reservation.response.*;
import com.maybank.integratorapp.model.soap.limit.XL01.request.SoapEnvelope;
import com.maybank.integratorapp.service.EmailService;
import jakarta.jms.JMSException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LimitReservationMessageProcessor {
    private static Logger log = LoggerFactory.getLogger(LimitReservationMessageProcessor.class);
    @Autowired
    LimitUtilizationMessageProcessor utilizationMessageProcessor;
    @Autowired
    LogInterfaceProcessService logger;
    @Autowired
    MsParameterService parameterService;
    @Autowired
    MsCurrencyRepository msCurrencyRepository;
    @Autowired
    MsFacilityRepository msFacilityRepository;

    @Autowired
    EmailService emailService;
    @Autowired
    MsBranchService msBranchService;

    @Autowired
    MsMapClsProductTypeRepository msMapClsProductTypeRepository;

    @Autowired
    MsUtilizeRunningNumberRepository msUtilizeRunningNumberRepository;

    ServiceResponse response = new ServiceResponse();

    private final String ProcessName = "LimitReservationProcess";
    @Autowired
    FtiTransactionDetailService ftiTransactionDetailService;
    @Autowired
    FtiTransactionService ftiTransactionService;
    @Autowired
    private LimitFacilitiesMessageProcessor processFacilities;
    private long LoggerId;
    @Autowired
    private LimitFacilitiesMessageProcessor facilitiesMessageProcessor;

//    public String processMessage(String message,Long loggerId) {
//        String responseXml = "";
//        logger.SetLogParent(loggerId);
//
//        try {
//            // step 1. Parse request message
//            // step 2. Set initial response
//            // step 3. Map external request to core system request
//            // step 4. Request data from core system
//            // step 5. Map core system data to external Response
//            // step 6. return response message as string
//
//            // step 1.
//            ServiceRequest request = parseRequest(message);
//
//            if (request != null) {
//
//                // step 2.
//                setInitialResponseHeader(request);
//
//                //From requset to maping response FTI
//                String reservedReservationIdentifier = request.getReservationsRequest().getReservationRequestDetails().getAccountIdentifier();
//
//                String facilityIdentifier = request.getReservationsRequest().getReservationRequestDetails().getFacilityIdentifier();
//                String customerRes = request.getReservationsRequest().getReservationRequestDetails().getCustomer();
//                String masterReference= request.getReservationsRequest().getReservationRequestDetails().getMasterReference();
//                String lineOfBusiness = request.getReservationsRequest().getReservationRequestDetails().getCustomerType();
//                String debitCreditFlag = request.getReservationsRequest().getReservationRequestDetails().getDebitCreditFlag();
//                String branch = request.getReservationsRequest().getReservationRequestDetails().getBranch();
//                String FtiProductCode = request.getReservationsRequest().getReservationRequestDetails().getProduct();
//                String eventCode = request.getReservationsRequest().getReservationRequestDetails().getEventReference();
//                String _eventCode = eventCode.substring(0,3);
//                String startdateRes = request.getReservationsRequest().getReservationRequestDetails().getTenorStartDate();
//                String expireDateRes =  request.getReservationsRequest().getReservationRequestDetails().getTenorEndDate();
//                String transDateRes = request.getReservationsRequest().getReservationRequestDetails().getValueDate();
//                String exposureAmmount =  request.getReservationsRequest().getReservationRequestDetails().getPostingAmount().getAmount();
//
//                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("ddMMyy");
//                LocalDate _startDate = LocalDate.parse(startdateRes, inputFormatter);
////            String startDate = _startDate.format(outputFormatter);
//                String startDate = "311024";
//
//                LocalDate _expiryDate = LocalDate.parse(expireDateRes, inputFormatter);
//                String expiryDate = _expiryDate.format(outputFormatter);
////            String expiryDate = "291224";
//
//                LocalDate _transDate = LocalDate.parse(transDateRes, inputFormatter);
////            String transactionDate = _transDate.format(outputFormatter);
//                String transactionDate = "311024";
//
//                // Ambil semua MsFacility dengan keyLoanAcc yang sesuai
//                MsFacility facilities = msFacilityRepository.findByKeyLoanAcc(facilityIdentifier);
//
//                // Ambil ID dari MsFacility
//                Long facilityId = facilities.getId();
//                String facilitySequence = facilities.getKeyDigitNote();
//                String currency = facilities.getLoanCurrencyCode();
//
//                String limitAmount =  facilities.getPrincipalBalance().replace(".00", "");
//                String reservedAmount =  facilities.getCommitmentBalance().replace(".00", "");
//                String availableAmount =  facilities.getCommitmentBalance().replace(".00", "");
//
//                String productType  = facilities.getNoteType();
//                String newKeyLoanAcc = null;
//                String acctReqXL01 = null;
//
//                log.info("Facility ID: " + facilityId);
//                log.info("LineOfBusiness: " + lineOfBusiness);
//
//                // cek dulu di table referensi transaksinya
//
//                // Ambil MsUtilizeRunningNumber berdasarkan facilityId
//                MsUtilizeRunningNumber runningNumberEntry = msUtilizeRunningNumberRepository.findByFacilityId(facilityId);
//
//                if (runningNumberEntry == null) {
//                    MsUtilizeRunningNumber newRunning = new MsUtilizeRunningNumber();
//                    newRunning.setCompanyLimitId(facilities.getCompanyLimitId());
//                    newRunning.setFacilityId(facilityId);
//                    newRunning.setRunningNumber(0);
//                    msUtilizeRunningNumberRepository.save(newRunning);
//                    runningNumberEntry = newRunning;
//                    log.info("No Running Number found for Facility ID: " + facilityId);
//                }
//                // Ambil Running Number dan pastikan format 3 digit
//                int runningNumber = runningNumberEntry.getRunningNumber();
//                String formattedRunningNumber = String.format("%03d", runningNumber + 1);
//                log.info("Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber);
//
//                // Buat keyLoanAcc baru dengan mengganti bagian draw
//                String customFacilityIdentifier = buildCustomFacilityIdentifier(facilityIdentifier);
//                log.info("Facility Identifier: " + customFacilityIdentifier);
//
//                // Buat keyLoanAcc baru dengan mengganti bagian draw
//                newKeyLoanAcc = buildNewKey(facilityIdentifier, formattedRunningNumber);
//                log.info("New KeyLoanAcc: " + newKeyLoanAcc);
//
//                // Buat formatted key untuk sistem proses
//                acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);
//                log.info("New Formatted Key: " + acctReqXL01);
//
//                // treat amend as issue for mapping purpose
//                if(_eventCode.equals("AMD") || _eventCode.equals("ADJ"))
//                    _eventCode = "ISS";
//
//                if(!lineOfBusiness.equals("01"))
//                    lineOfBusiness= "00";
//                if(productType.equals("515")) // bank limits
//                    lineOfBusiness= "07";
//
//                String cls001ProductType= "";
//                if(productType.startsWith("7")) // islamic limits
//                {
//                    List<MsMapClsProductType> productTypeList = msMapClsProductTypeRepository.findDraw001Products(productType);
//
//                    //special case 710
//                    if(productType.equals("710")){
//                        if(_eventCode.equals("ISS"))
//                            cls001ProductType= productTypeList.stream().filter(x->
//                                            x.getEventCode().equals("ISS") &&
//                                                    x.getLiabilityCode().equals("IGT"))
//                                    .findFirst().get().getProductType001();
//                    }
//
//                }else{
//                    cls001ProductType = msMapClsProductTypeRepository.findDraw001Product(productType, lineOfBusiness,_eventCode);
//
//                }
//                logger.Log(this.LoggerId,ProcessName, "CLS Product Type search criteria "+productType+"|"+lineOfBusiness+"|"+_eventCode, "DEBUG");
//
//                log.info("CLS Product Type : " + cls001ProductType);
//                logger.Log(this.LoggerId,ProcessName, "CLS Product Type : "+cls001ProductType, "DEBUG");
//
//                String xl01responseCode = "";
//                String xl31responseCode = "";
//                String xl01responseMessage = "";
//                String xl31responseMessage = "";
//                // new reservation logic
//
//                FtiTransaction _header = ftiTransactionService.findByMasterRefNo(masterReference).get();
//                List<FtiTransactionDetail> _listTransactionDetail = ftiTransactionDetailService.getDetailsByHeaderId(_header.getId());
//                _listTransactionDetail = _listTransactionDetail.stream().filter(x->x.getCoreSysName().startsWith("CLS"))
//                        .sorted(Comparator.comparingLong(FtiTransactionDetail::getId))
//                        .collect(Collectors.toList());
//
//                FtiTransactionDetail _lastLimitAction = null;
//                String _lastLimitStatus;
//                String _lastLimitReservationId;
//                if(_listTransactionDetail.size()>0){
//                    _lastLimitAction = _listTransactionDetail.get(_listTransactionDetail.size()-1);
//                    _lastLimitReservationId = _lastLimitAction.getAdditionalInfo1();
//                    _lastLimitStatus = _lastLimitAction.getAdditionalInfo4();
//                } else {
//                    _lastLimitStatus = "";
//                    _lastLimitReservationId = "";
//                }
//
//                if(eventCode.startsWith("ISS")){
//                    String canNext = "Y";
//                    if(
//                        (_lastLimitAction == null) ||
//                        (_lastLimitStatus.equals("DEL")
//                                && _listTransactionDetail.stream().noneMatch(
//                                        x->x.getAdditionalInfo1()
//                                                .startsWith(customFacilityIdentifier)))){
//                        //draw 001
//                        // step 3.
//                        SoapEnvelope msgRequestXL01 = mapCoreSystemXl01Request(masterReference,acctReqXL01,newKeyLoanAcc,currency,startDate,expiryDate,transactionDate,cls001ProductType,branch);
//
//                        // step 4.
//                        com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope msgResponseXL01 = getXl01MsgBodyResponse(msgRequestXL01,masterReference,eventCode,formattedRunningNumber,newKeyLoanAcc);
//                        xl01responseCode = msgResponseXL01
//                                .getBody().getXl01Draw001Response().
//                                getCmsXL01Draw001Response().getResponsecode();
//                        xl01responseMessage = msgResponseXL01
//                                .getBody().getXl01Draw001Response().
//                                getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
//
//                        if(xl01responseCode.equals("00")){
//                            processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
//
//                            FtiTransaction ftiTransaction = new FtiTransaction();
//                            ftiTransaction.setMasterRefNo(masterReference);
//                            ftiTransaction.setDrawNumber(formattedRunningNumber);
//                            ftiTransaction.setReservationId(newKeyLoanAcc);
//                            ftiTransactionService.createOrUpdateFtiTransaction(ftiTransaction);
//
//                        }else{
//                            canNext = "N";
//                            formattedRunningNumber="-";
//                            newKeyLoanAcc="-";
//                            // step 5.
//                            mapExternalResponse(xl01responseCode,xl01responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
//
//                        }
//                    }
//
//                    if(canNext.equals("Y")){
//                        String debit_credit = "62";
//                        if(debitCreditFlag.equals("C"))
//                            debit_credit = "67";
//
//                        //draw 31
//                        com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope msgRequestXL31= mapCoreSystemXl31Request(masterReference,exposureAmmount,newKeyLoanAcc,currency,transactionDate,debit_credit,branch,eventCode);
//
//                        // step 4.
//                        com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope msgResponseXL31 = getXl31MsgBodyResponse(msgRequestXL31,masterReference,eventCode);
//
//                        xl31responseCode = msgResponseXL31
//                                .getBody().getXl31Response().
//                                getCmsXl31Response().getResponsecode();
//                        xl31responseMessage = msgResponseXL31
//                                .getBody().getXl31Response().
//                                getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
//
//                        formattedRunningNumber = splitKey(newKeyLoanAcc)[5];
//                        if(xl31responseCode.equals("00")) {
//                            processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
//                        }
//                        // step 5.
//                        mapExternalResponse(xl31responseCode,xl31responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
//
//                    }
//
//
//                }else {
//                    newKeyLoanAcc =reservedReservationIdentifier;
//
////                    check facility difference
//                    if(!_lastLimitReservationId.startsWith(customFacilityIdentifier)){
//                        // find all previous XL31, then tally all the amount, 67 is minus, 62 is plus
//                        int _finalAmount = 0;
//                        for (FtiTransactionDetail XL31req: _listTransactionDetail.stream().filter(x->x.getCoreSysName().equals("CLS-XL31")).toList()) {
//                            if(XL31req.getAdditionalInfo2().equals("62"))
//                                _finalAmount = _finalAmount+ Integer.parseInt(XL31req.getAdditionalInfo3());
//                            else if(XL31req.getAdditionalInfo2().equals("67"))
//                                _finalAmount = _finalAmount- Integer.parseInt(XL31req.getAdditionalInfo3());
//
//                        }
//
//                        // reverse the old facility
//                        String debit_credit = "67";
//
//                        //draw 31
//                        com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope msgRequestXL31= mapCoreSystemXl31Request(masterReference,exposureAmmount,newKeyLoanAcc,currency,transactionDate,debit_credit,branch,eventCode);
//
//                        // step 4.
//                        com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope msgResponseXL31 = getXl31MsgBodyResponse(msgRequestXL31,masterReference,eventCode);
//
//                        xl31responseCode = msgResponseXL31
//                                .getBody().getXl31Response().
//                                getCmsXl31Response().getResponsecode();
//                        xl31responseMessage = msgResponseXL31
//                                .getBody().getXl31Response().
//                                getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
//
//                        formattedRunningNumber = splitKey(newKeyLoanAcc)[5];
//                        if(xl31responseCode.equals("00")) {
//                            processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
//                        }
//                        // step 5.
//                        mapExternalResponse(xl31responseCode,xl31responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
//
//                        // release the reversal
//                        utilizationMessageProcessor.logger = logger;
//                        com.maybank.integratorapp.model.soap.limit.XL41.request.SoapEnvelope XL41Request = utilizationMessageProcessor.mapXL41Request(_lastLimitReservationId,masterReference);
//
//                        com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope XL41Response = utilizationMessageProcessor.getXL41Response(XL41Request,eventCode,masterReference,_lastLimitReservationId);
//
//
//                    }
//
//                    String debit_credit = "62";
//                    if(debitCreditFlag.equals("C"))
//                        debit_credit = "67";
//
//                    //draw 31
//                    com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope msgRequestXL31= mapCoreSystemXl31Request(masterReference,exposureAmmount,newKeyLoanAcc,currency,transactionDate,debit_credit,branch,eventCode);
//
//                    // step 4.
//                    com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope msgResponseXL31 = getXl31MsgBodyResponse(msgRequestXL31,masterReference,eventCode);
//
//                    xl31responseCode = msgResponseXL31
//                            .getBody().getXl31Response().
//                            getCmsXl31Response().getResponsecode();
//                    xl31responseMessage = msgResponseXL31
//                            .getBody().getXl31Response().
//                            getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
//
//                    formattedRunningNumber = splitKey(newKeyLoanAcc)[5];
//                    if(xl31responseCode.equals("00")) {
//                        processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
//                    }
//                    // step 5.
//                    mapExternalResponse(xl31responseCode,xl31responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
//                }
//
//
//
//            }
//
//            // step 6.
//            XmlMapper xmlMapper = new XmlMapper();
//            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//            responseXml = xmlMapper.writeValueAsString(response);
//
//        } catch (Exception e) {
//            logger.Log(this.LoggerId,ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());
//
//        }
//        return responseXml;
//    }

    public String processMessage(String message,Long loggerId) {
        String responseXml = "";
        this.LoggerId = loggerId;
//        logger.SetLogParent(loggerId);

        try {
            // step 1. Parse request message
            // step 2. Set initial response
            // step 3. Map external request to core system request
            // step 4. Request data from core system
            // step 5. Map core system data to external Response
            // step 6. return response message as string

            // step 1.
            ServiceRequest request = parseRequest(message);

            if (request != null) {

                // step 2.
                setInitialResponseHeader(request);

                //From requset to maping response FTI
                String reservedReservationIdentifier = request.getReservationsRequest().getReservationRequestDetails().getAccountIdentifier();

                String facilityIdentifier = request.getReservationsRequest().getReservationRequestDetails().getFacilityIdentifier();
                String customerRes = request.getReservationsRequest().getReservationRequestDetails().getCustomer();
                String masterReference= request.getReservationsRequest().getReservationRequestDetails().getMasterReference();
                String lineOfBusiness = request.getReservationsRequest().getReservationRequestDetails().getCustomerType();
                String debitCreditFlag = request.getReservationsRequest().getReservationRequestDetails().getDebitCreditFlag();
                String branch = request.getReservationsRequest().getReservationRequestDetails().getBranch();
                String FtiProductCode = request.getReservationsRequest().getReservationRequestDetails().getProduct();
                String eventCode = request.getReservationsRequest().getReservationRequestDetails().getEventReference();
                String _eventCode = eventCode.substring(0,3);
                String startdateRes = request.getReservationsRequest().getReservationRequestDetails().getTenorStartDate();
                String expireDateRes =  request.getReservationsRequest().getReservationRequestDetails().getTenorEndDate();
                String transDateRes = request.getReservationsRequest().getReservationRequestDetails().getValueDate();
                String exposureAmmount =  request.getReservationsRequest().getReservationRequestDetails().getPostingAmount().getAmount();

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("ddMMyy");

                if(startdateRes == null)
                    startdateRes=transDateRes;
                if(expireDateRes==null)
                    expireDateRes=transDateRes;


                LocalDate _startDate = LocalDate.parse(startdateRes, inputFormatter);
                String startDate = _startDate.format(outputFormatter);
//                String startDate = "051124";

                LocalDate _expiryDate = LocalDate.parse(expireDateRes, inputFormatter);
                String expiryDate = _expiryDate.format(outputFormatter);
//            String expiryDate = "291224";

                LocalDate _transDate = LocalDate.parse(transDateRes, inputFormatter);
                String transactionDate = _transDate.format(outputFormatter);
//                String transactionDate = "051124";

                String clsStaticDate = parameterService.findValueByPrmKey("CLSStaticDate");
                if(!clsStaticDate.equals("-")){
                    startDate = clsStaticDate;
                    transactionDate = clsStaticDate;
                }

                // Ambil semua MsFacility dengan keyLoanAcc yang sesuai
                MsFacility facilities = msFacilityRepository.findByKeyLoanAcc(facilityIdentifier);

                // Ambil ID dari MsFacility
                Long facilityId = facilities.getId();
                String facilitySequence = facilities.getKeyDigitNote();
                String currency = facilities.getLoanCurrencyCode();

                String limitAmount =  facilities.getPrincipalBalance().replace(".00", "");
                String reservedAmount =  facilities.getCommitmentBalance().replace(".00", "");
                String availableAmount =  facilities.getCommitmentBalance().replace(".00", "");

                String productType  = facilities.getNoteType();
                String newKeyLoanAcc = null;
                String acctReqXL01 = null;
                String formattedRunningNumber = "";

                String xl01responseCode = "";
                String xl31responseCode = "";
                String xl01responseMessage = "";
                String xl31responseMessage = "";

                boolean needXL01 = false;
                boolean needXL2B = false;
                boolean needXL31 = false;

//                log.info("Facility ID: " + facilityId);
//                log.info("LineOfBusiness: " + lineOfBusiness);



//                if(newKeyLoanAcc != null){
//
//
//                }
                // Ambil MsUtilizeRunningNumber berdasarkan facilityId
                MsUtilizeRunningNumber runningNumberEntry = msUtilizeRunningNumberRepository.findByFacilityId(facilityId);

                if (runningNumberEntry == null) {
                    MsUtilizeRunningNumber newRunning = new MsUtilizeRunningNumber();
                    newRunning.setCompanyLimitId(facilities.getCompanyLimitId());
                    newRunning.setFacilityId(facilityId);
                    newRunning.setRunningNumber(0);
                    msUtilizeRunningNumberRepository.save(newRunning);
                    runningNumberEntry = newRunning;
                    log.info("No Running Number found for Facility ID: " + facilityId);
                }
                // Ambil Running Number dan pastikan format 3 digit
                int runningNumber = runningNumberEntry.getRunningNumber();
                formattedRunningNumber = String.format("%03d", runningNumber + 1);
//                log.info("Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber);
                logger.Log(this.LoggerId,ProcessName, "Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber, "DEBUG");

                // Buat keyLoanAcc baru dengan mengganti bagian draw
                newKeyLoanAcc = buildNewKey(facilityIdentifier, formattedRunningNumber);
//                log.info("New KeyLoanAcc: " + newKeyLoanAcc);
                logger.Log(this.LoggerId,ProcessName, "New KeyLoanAcc: " + newKeyLoanAcc, "DEBUG");

                // Buat formatted key untuk sistem proses
                acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);
//                log.info("New Formatted Key: " + acctReqXL01);
                logger.Log(this.LoggerId,ProcessName, "New Formatted Key: " + acctReqXL01, "DEBUG");

                // cek dulu di table referensi transaksinya
                // timpa & pakai yang lama jika ada
                Optional<FtiTransaction> ftiTransactionData = ftiTransactionService.findByMasterRefNo(masterReference);

                if(ftiTransactionData.isPresent()){
                    FtiTransaction transaction = ftiTransactionData.get();
                    String _facNew = splitKey(facilityIdentifier)[4];
                    List<FtiTransactionDetail> transactionDetails = ftiTransactionDetailService.getDetailsByHeaderId(transaction.getId());
                    if(transactionDetails.stream().anyMatch(x->x.getFtiEvent().equals("ISS001"))){

                        FtiTransactionDetail transactionDetails1 = transactionDetails.stream().filter(x -> x.getAdditionalInfo4().equals("DRW")).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                        logger.Log(this.LoggerId,ProcessName, "Get Old : " + transactionDetails1.getId(), "DEBUG");

                        if(transactionDetails1.getCoreSysStatus().equals("00")){
                            String _facOld = splitKey(transactionDetails1.getAdditionalInfo1())[4];

                            if(_facOld.equals(_facNew)){
                                logger.Log(this.LoggerId,ProcessName, "Previous KeyLoanAcc: " + transactionDetails1.getAdditionalInfo1(), "DEBUG");

                                newKeyLoanAcc = transactionDetails1.getAdditionalInfo1();
//                            xl01responseCode = "00";
                                needXL31 = true;
                                formattedRunningNumber = splitKey(newKeyLoanAcc)[5];
                                acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);

                                // check for XL2B
                                String _dateOld = transactionDetails1.getAdditionalInfo5();
                                String _dateNew = startDate+"#"+expiryDate;
                                String _dateFromReq = request.getReservationsRequest().getReservationRequestDetails().getTenorStartDate();

                                if(_dateFromReq != null){
                                    transactionDetails1 = transactionDetails.stream().filter(x ->
                                            x.getCoreSysName().equals("CLS-XL01Draw001") || x.getCoreSysName().equals("CLS-XL2B")
                                                    && x.getFtiEvent().equals(eventCode)
                                    ).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                                    // sementara XL2B belum bisa ganti start date, maka hanya compare expiry nya saja
                                    if(transactionDetails1.getAdditionalInfo5() != null){
//                                        _dateOld = transactionDetails1.getAdditionalInfo5();
                                        _dateOld = transactionDetails1.getAdditionalInfo5().split("#")[1];
                                    }


//                                    if(!_dateOld.equals(_dateNew)){
                                    if(!_dateOld.equals(expiryDate)){
                                        needXL2B = true;
                                    }
                                }

                                // jika cancel, bikin XL2B jadiin maturitydate nya jadi tanggal event cancel
                                if(_eventCode.equals("CAN") || _eventCode.equals("BCR")){
                                    _transDate = LocalDate.parse(transDateRes, inputFormatter);
                                    expiryDate = _transDate.format(outputFormatter);
                                    transactionDetails1 = transactionDetails.stream().filter(x ->
                                            x.getCoreSysName().equals("CLS-XL01Draw001") || x.getCoreSysName().equals("CLS-XL2B")
                                                    && x.getFtiEvent().equals(eventCode)
                                    ).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                                    // sementara XL2B belum bisa ganti start date, maka hanya compare expiry nya saja
                                    if(transactionDetails1.getAdditionalInfo5() != null){
//                                        _dateOld = transactionDetails1.getAdditionalInfo5();
                                        _dateOld = transactionDetails1.getAdditionalInfo5().split("#")[1];
                                    }


//                                    if(!_dateOld.equals(_dateNew)){
                                    if(!_dateOld.equals(expiryDate)){
                                        needXL2B = true;
                                    }
                                }

                                if(exposureAmmount.equals("0")){
                                    needXL31 = false;
                                }
//                            String _amountOld = transactionDetails1.getAdditionalInfo2();
//                            if(!_amountOld.equals(exposureAmmount)){
//                                needXL31 = true;
//                            }

                            }else {
                                // handle facility change
                                // need to make new draw on new facility
                                needXL01 = true;
                            }
//                        check whether its the same facility
                        }
                        else{
                            needXL01= true;
                        }

                    }
                }

                // treat anything except claim as issue for mapping purpose
                if (!(_eventCode.equals("CLM") || _eventCode.equals("POC"))) {
                    _eventCode = "ISS";
                }

                if(!lineOfBusiness.equals("07") && !lineOfBusiness.equals("01"))
                    lineOfBusiness= "00";
//                if(productType.equals("515") || productType.equals("525")) // bank limits
//                    lineOfBusiness= "07";

                String cls001ProductType= "";
                if(productType.startsWith("7")) // islamic limits
                {
                    List<MsMapClsProductType> productTypeList = msMapClsProductTypeRepository.findDraw001Products(productType);

                    //special case 710
                    if(productType.equals("710")){
                        if(_eventCode.equals("ISS"))
                            cls001ProductType= productTypeList.stream().filter(x->
                                            x.getEventCode().equals("ISS") &&
                                                    x.getLiabilityCode().equals("IGT"))
                                    .findFirst().get().getProductType001();
                    }

                }else{
                    cls001ProductType = msMapClsProductTypeRepository.findDraw001Product(productType, lineOfBusiness,_eventCode);

                }
                logger.Log(this.LoggerId,ProcessName, "CLS Product Type search criteria "+productType+"|"+lineOfBusiness+"|"+_eventCode, "DEBUG");

//                log.info("CLS Product Type : " + cls001ProductType);
                logger.Log(this.LoggerId,ProcessName, "CLS Product Type : "+cls001ProductType, "DEBUG");


                // new reservation logic

                FtiTransaction _header = new FtiTransaction();

                List<FtiTransactionDetail> _listTransactionDetail = new ArrayList<>();
                if(ftiTransactionService.findByMasterRefNo(masterReference).isPresent()){
                    _header = ftiTransactionService.findByMasterRefNo(masterReference).get();
                    _listTransactionDetail = ftiTransactionDetailService.getDetailsByHeaderId(_header.getId());
                    _listTransactionDetail = _listTransactionDetail.stream().filter(x->x.getCoreSysName().startsWith("CLS"))
                            .sorted(Comparator.comparingLong(FtiTransactionDetail::getId))
                            .collect(Collectors.toList());
                }

                FtiTransactionDetail _lastLimitAction = null;
                if(_listTransactionDetail.stream().count()>0){
//                    logger.Log(this.LoggerId,ProcessName, "Transaction Detail Count : "+_listTransactionDetail.stream().count(), "DEBUG");

                    _lastLimitAction = _listTransactionDetail.get((int) (_listTransactionDetail.stream().count()-1));
                }

                if(_lastLimitAction==null){
                    needXL01=true;
                }

                if(needXL01){
                    //draw 001
                    // step 3.
                    SoapEnvelope msgRequestXL01 = mapCoreSystemXl01Request(masterReference,acctReqXL01,newKeyLoanAcc,currency,startDate,expiryDate,transactionDate,cls001ProductType,branch);

                    // step 4.
//                    logger.Log(this.LoggerId,ProcessName, "CLS BEFORE HIT XL01", "DEBUG");
                    com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope msgResponseXL01 = getXl01MsgBodyResponse(msgRequestXL01,masterReference,eventCode,formattedRunningNumber,newKeyLoanAcc);
//                    logger.Log(this.LoggerId,ProcessName, "CLS AFTER HIT XL01", "DEBUG");

                    xl01responseCode = msgResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponsecode();

                    xl01responseMessage = msgResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
//                    logger.Log(this.LoggerId,ProcessName, "CLS AFTER HIT XL01 RESPONSE CODE :"+xl01responseCode, "DEBUG");

//                    logger.Log(this.LoggerId,ProcessName, "CLS AFTER HIT XL01 RESPONSE MESSAGE :"+xl01responseMessage, "DEBUG");
                    if(xl01responseCode.contains("exception") && xl01responseMessage == null)
                        xl01responseCode= "99";

                    if(xl01responseCode.equals("00")){
//                        logger.Log(this.LoggerId,ProcessName, "CLS BEFORE REFRESH FACILITIES", "DEBUG");
                        facilitiesMessageProcessor.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
//                        logger.Log(this.LoggerId,ProcessName, "CLS AFTER REFRESH FACILITIES", "DEBUG");
                        FtiTransaction ftiTransaction = new FtiTransaction();
                        ftiTransaction.setMasterRefNo(masterReference);
                        ftiTransaction.setDrawNumber(formattedRunningNumber);
                        ftiTransaction.setReservationId(newKeyLoanAcc);
                        ftiTransactionService.createOrUpdateFtiTransaction(ftiTransaction);

                        needXL31 =true;
                    }else{
                        formattedRunningNumber="-";
                        newKeyLoanAcc="-";
                        // step 5.
                        mapExternalResponse(xl01responseCode,xl01responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);

                    }
                }else if (_lastLimitAction!=null && reservedReservationIdentifier!=null){
                    // amend/adjust
                    newKeyLoanAcc =reservedReservationIdentifier;
                }

                if(needXL2B){
                    // cek dulu apakah sebelumnya masih ada XL2B yang masih gantung,

                    FtiTransaction transaction = ftiTransactionData.get();
                    List<FtiTransactionDetail> transactionDetails = ftiTransactionDetailService.getDetailsByHeaderId(transaction.getId());
                    Optional<FtiTransactionDetail> lastXL2B = transactionDetails.stream().filter(x ->
                            x.getCoreSysName().equals("CLS-XL2B") && x.getCoreSysStatus().equals("00")
                                    && x.getFtiEvent().equals(eventCode)
                    ).max(Comparator.comparing(FtiTransactionDetail::getId));

                    if(lastXL2B.isPresent()){
                        FtiTransactionDetail _lastXL2B = lastXL2B.get();

                        Optional<FtiTransactionDetail> lastXL40 = transactionDetails.stream().filter(x ->
                                x.getCoreSysName().equals("CLS-XL40") && x.getCoreSysStatus().equals("00") && x.getId()>_lastXL2B.getId()
                        ).max(Comparator.comparing(FtiTransactionDetail::getId));
                        // kalau masih ada XL2B gantung, kirim XL40 untuk XL2B yang gantung, next bikin baru

                        if(lastXL40.isEmpty()){
                            // req XL40
                            utilizationMessageProcessor.logger = logger;

                            com.maybank.integratorapp.model.soap.limit.XL40.request.SoapEnvelope msgRequest =
                                    utilizationMessageProcessor.mapXL40Request(newKeyLoanAcc,masterReference);

                            // step 4.
                            com.maybank.integratorapp.model.soap.limit.XL40.response.SoapEnvelope msgResponse =
                                    utilizationMessageProcessor.getXL40Response(msgRequest,eventCode,masterReference,newKeyLoanAcc);

                            String xl40responseCode = msgResponse
                                    .getBody().getXl40Response().
                                    getCmsXl40Response().getResponsecode();
                            String xl40responseMessage = msgResponse
                                    .getBody().getXl40Response().
                                    getCmsXl40Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();


                            if(xl40responseCode.equals("00")) {
                                processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
                            }
                            // step 5.
                            mapExternalResponse(xl40responseCode,xl40responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);

                        }

                    }

                    //req XL2B
                    com.maybank.integratorapp.model.soap.limit.XL2B.request.SoapEnvelope msgRequestXL2B=
                            mapCoreSystemXl2BRequest(masterReference,expiryDate,newKeyLoanAcc,branch);

                    // step 4.
                    com.maybank.integratorapp.model.soap.limit.XL2B.response.SoapEnvelope msgResponseXL2B =
                            getXl2BMsgBodyResponse(msgRequestXL2B,masterReference,eventCode,startDate,expiryDate);

                    String xl2BresponseCode = msgResponseXL2B
                            .getBody().getXl2BResponse().
                            getCmsXl2BResponse().getResponsecode();
                    String xl2BresponseMessage = msgResponseXL2B
                            .getBody().getXl2BResponse().
                            getCmsXl2BResponse().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    if(xl2BresponseCode.contains("exception") && xl2BresponseMessage == null)
                        xl2BresponseCode= "99";

                    if(xl2BresponseCode.equals("00")) {
                        processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
                    }
                    // step 5.
                    mapExternalResponse(xl2BresponseCode,xl2BresponseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
                }

//                if((xl01responseCode.equals("00") || !eventCode.startsWith("ISS")) || needXL31){
                if(needXL31){

                    String debit_credit = "62";
                    if(debitCreditFlag.equals("C"))
                        debit_credit = "67";

                    //draw 31
                    com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope msgRequestXL31= mapCoreSystemXl31Request(masterReference,exposureAmmount,newKeyLoanAcc,currency,transactionDate,debit_credit,branch,eventCode);

                    // step 4.
                    com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope msgResponseXL31 = getXl31MsgBodyResponse(msgRequestXL31,masterReference,eventCode);

                    xl31responseCode = msgResponseXL31
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponsecode();
                    xl31responseMessage = msgResponseXL31
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();

                    formattedRunningNumber = splitKey(newKeyLoanAcc)[5];
                    if(xl31responseMessage.contains("exception") && xl31responseCode == null)
                        xl31responseCode= "99";
                    if(xl31responseCode.equals("00")) {
                        processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
                    }
                    // step 5.
                    mapExternalResponse(xl31responseCode,xl31responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
                }

                // ketika tedeteksi tidak mengubah apa-apa
                if(!needXL01 && !needXL2B && !needXL31){


                    mapExternalResponse("00","Nothing Changed",facilityIdentifier,facilitySequence
                            ,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);

                }
            }

            // step 6.
//            sendEmailNotif();
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            responseXml = xmlMapper.writeValueAsString(response);

        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());

        }
        return responseXml;
    }

    private void handleExceptionResponse(String message) {
        response.getResponseHeader().setStatus("Error");
        response.getResponseHeader().getDetails().setError(message);
    }

    // step 1. Parse request message
    private ServiceRequest parseRequest(String message) throws JMSException {
        XmlMapper xmlMapper = new XmlMapper();
        ServiceRequest request;
        try {
            request = xmlMapper.readValue(message, ServiceRequest.class);
        } catch (JsonProcessingException e) {
            logger.Log(this.LoggerId,ProcessName, "Error Json Processing", "ERROR-JSON-PROCESS", e.getMessage());
            handleExceptionResponse(e.getMessage());
            request = null;
        }
        return request;
    }

    // step 2. Set initial response
    private void setInitialResponseHeader(ServiceRequest request) {
        response.setResponseHeader(new ResponseHeader());
        response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
        response.getResponseHeader().setService(request.getRequestHeader().getService());
        response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
        response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
        response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());
        response.getResponseHeader().setStatus("SUCCEEDED");
    }

    // step 3. Map external request to core system request
    public SoapEnvelope mapCoreSystemXl01Request(
            String referenceId,
            String acctReqXL01,
            String newKeyloanAcc,
            String currency,
            String startDate,
            String expiryDate,
            String transactionDate,
            String cls001ProductType,
            String branch
    ) {
        SoapEnvelope soapReqXL01 = new SoapEnvelope();
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
        String correlationID = "FTI";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String[] splittedKey = splitKey(newKeyloanAcc);
        String limitCurrency = splittedKey[1];
        String limitBranch = splittedKey[2];
        String limitCif = splittedKey[3];
        String limitRunningNumber = splittedKey[5];

        String currencyNumber = msCurrencyRepository.findByIsoCode(currency).getInternalCode();

        MsBranch _branch = msBranchService.getByBranchCode(branch);

        String clientUserId = "7755";
        String clientSpvUserId = "7766";

        if(_branch!=null){
            clientUserId = _branch.getUserId();
            clientSpvUserId = _branch.getSpvUserId();
            if(clientSpvUserId.equals("-"))
                clientSpvUserId = clientUserId;
        }

        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setAdditionalHeader("");
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setBranchCode(branch);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setChannelID(clsChannelId);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setClientSupervisorID(clientSpvUserId);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setClientUserID(clientUserId);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setReference(referenceId);
//        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setReversalSequenceNo(correlationID);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setTransactionDate(date);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setTransactionTime(time);

        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAcct(acctReqXL01);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAppl("XL");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setBatch(limitBranch+"01");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setBranch(limitBranch);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setChgmeth("0");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCifNo(limitCif);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCommitmentCode("3");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCommitmentType("1");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl2(limitCurrency);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl3(limitBranch);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl4("0000");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCurrency(currency);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setDepartement(limitBranch);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setIntstart(startDate);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setMatdate(expiryDate);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setNotedate(transactionDate);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setPrinamt("000000000000.00");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setProductType(cls001ProductType);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setRate("000.000010");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setRelCd("01");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setStatus("A");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setUseAcct1("2");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setUserCode(clientUserId);


        return soapReqXL01;
    }

    // step 4. Request data from core system
    public com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope getXl01MsgBodyResponse(SoapEnvelope soapReqXL01,String referenceId,String eventCode,String limitRunningNumber,String limitReservationId) {
        com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope cmsResponseXL01
                = new com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope();
        String soapUrl = parameterService.findValueByPrmKey("XL01Request");
        try {
            XmlMapper mapper = new XmlMapper();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlString = null;
            try {
                xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReqXL01);

//                log.info(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(this.LoggerId,ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

//            com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope cmsResponseXL01 = null;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xmlString, ContentType.TEXT_XML));
                String _response = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();
                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);
                    _response = outputResponse;
                    logger.Log(this.LoggerId,ProcessName, "Response ESB Message", "ESB-MESSAGE", _response);

                    if (_response.contains("Fault")) {
                        log.info(_response);

                    }
                    cmsResponseXL01 = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope.class);


                    String xmlResponseXL01 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cmsResponseXL01);
//                    log.info(xmlResponseXL01);

                    // Extract  response code
                    String responseCode = cmsResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponsecode();
                    String responseMessage = cmsResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    if(outputResponse.contains("exception") && responseCode == null)
                        responseCode= "99";

                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(LoggerId);
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL01Draw001");
                    ftiTransactionDetail.setTransName("Reservation");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage);
                    ftiTransactionDetail.setAdditionalInfo1(limitReservationId);
                    ftiTransactionDetail.setAdditionalInfo2("-");
                    ftiTransactionDetail.setAdditionalInfo3("-");
                    ftiTransactionDetail.setAdditionalInfo4("DRW");
                    String _StartAndMaturity =
                            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().getIntstart()
                                    +"#"+
                                    soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().getMatdate();
                    ftiTransactionDetail.setAdditionalInfo5(_StartAndMaturity);
                    ftiTransactionDetail.setReqMessage(xmlString);
                    ftiTransactionDetail.setResMessage(xmlResponseXL01);
                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                }
            } catch (Exception e) {

                logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }

        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return cmsResponseXL01;
    }
    public com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope getXl31MsgBodyResponse(
            com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope soapReqXL31,String referenceId,String eventCode) {
        com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope serviceResponse
                = new com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope();
        String soapUrl = parameterService.findValueByPrmKey("XL31Request");
        try {
            XmlMapper mapper = new XmlMapper();

            String dcType = soapReqXL31.getBody().getXl31().getCmsXl31Request().getTran();
            String amount = soapReqXL31.getBody().getXl31().getCmsXl31Request().getAmount();
            String noteNumber = soapReqXL31.getBody().getXl31().getCmsXl31Request().getNotenumber();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlString = null;
            try {
                xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReqXL31);

//                log.info(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(this.LoggerId,ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

//            com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope cmsResponseXL31 = null;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xmlString, ContentType.TEXT_XML));
                String _response = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();
                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);
                    _response = outputResponse;
                    logger.Log(this.LoggerId,ProcessName, "Response ESB Message", "ESB-MESSAGE", _response);
                    if (_response.contains("Fault")) {
                        log.info(_response);
                    }
                    serviceResponse = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope.class);


                    String xmlResponseXL31 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(serviceResponse);
//                    log.info(xmlResponseXL01);

                    // Extract  response code

                    String responseMessage = serviceResponse
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    String responseCode = serviceResponse
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponsecode() ;

                    if(responseMessage.contains("exception") && responseCode == null)
                        responseCode= "99";

                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(LoggerId);
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL31");
                    ftiTransactionDetail.setTransName("Reservation");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage);
                    ftiTransactionDetail.setAdditionalInfo1(noteNumber);
                    ftiTransactionDetail.setAdditionalInfo2(dcType);
                    ftiTransactionDetail.setAdditionalInfo3(amount);
                    ftiTransactionDetail.setAdditionalInfo4("NRY");
                    ftiTransactionDetail.setReqMessage(xmlString);
                    ftiTransactionDetail.setResMessage(xmlResponseXL31);
                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                }
            } catch (Exception e) {

                logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }

        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return serviceResponse;
    }

    public com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope mapCoreSystemXl31Request(
            String referenceId,
            String exposureAmmount,
            String newKeyloanAcc,
            String currency,
            String transactionDate,
            String debit_credit,
            String branch,
            String eventCode
    ){
// Call XL31 request
        com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope soapReqXL31 =
                new com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope();
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
        String correlationID = "FTI";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String[] splittedKey = splitKey(newKeyloanAcc);
        String limitBranch = splittedKey[2];


//                REQUEST REF NO DI CLS SCREEN (XL41). MAX 11 DIGIT
//                        - REF BARU 16 DIGIT (UID906C1234567ID-AMD001 >>> D1234567AMD)
//                        - REF LAMA 13 DIGIT (PYBB123456000-ISS001 >>> B123456ISS)
        String clsCustomReference = "";
        if(referenceId.length() == 16){
            clsCustomReference = referenceId.substring(2,3) +referenceId.substring(7,14)+eventCode.substring(0,3);
        }
        if(referenceId.length() == 13){
            clsCustomReference = referenceId.substring(3,4) +referenceId.substring(4,10)+eventCode.substring(0,3);

        }

        MsBranch _branch = msBranchService.getByBranchCode(branch);

        String clientUserId = "7755";
        String clientSpvUserId = "7766";

        if(_branch!=null){
            clientUserId = _branch.getUserId();
            clientSpvUserId = _branch.getSpvUserId();
            if(clientSpvUserId.equals("-"))
                clientSpvUserId = clientUserId;
        }

        soapReqXL31.getBody().getXl31().getChannelHeader().setAdditionalHeader("");
        soapReqXL31.getBody().getXl31().getChannelHeader().setBranchCode(branch);
        soapReqXL31.getBody().getXl31().getChannelHeader().setChannelID(clsChannelId);
        soapReqXL31.getBody().getXl31().getChannelHeader().setClientSupervisorID(clientSpvUserId);
        soapReqXL31.getBody().getXl31().getChannelHeader().setClientUserID(clientUserId);
        soapReqXL31.getBody().getXl31().getChannelHeader().setReference(referenceId);
        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionDate(date);
        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionTime(time);

        soapReqXL31.getBody().getXl31().getCmsXl31Request().setAmount(exposureAmmount);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBatch(limitBranch+"01");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBd("");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setCurrency(currency);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDepartement(limitBranch);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDescription(clsCustomReference);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setNotenumber(newKeyloanAcc);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setQual("0");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTran(debit_credit);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTransactiondate(transactionDate);

        return soapReqXL31;
    }

    public com.maybank.integratorapp.model.soap.limit.XL2B.request.SoapEnvelope mapCoreSystemXl2BRequest(
            String referenceId,
            String maturityDate,
            String newKeyloanAcc,
            String branch
    ){

        com.maybank.integratorapp.model.soap.limit.XL2B.request.SoapEnvelope soapReqXL2B =
                new com.maybank.integratorapp.model.soap.limit.XL2B.request.SoapEnvelope();
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
        String correlationID = "FTI";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String[] splittedKey = splitKey(newKeyloanAcc);
        String limitBranch = splittedKey[2];

        MsBranch _branch = msBranchService.getByBranchCode(branch);

        String clientUserId = "7755";
        String clientSpvUserId = "7766";

        if(_branch!=null){
            clientUserId = _branch.getUserId();
            clientSpvUserId = _branch.getSpvUserId();
            if(clientSpvUserId.equals("-"))
                clientSpvUserId = clientUserId;
        }

        soapReqXL2B.getBody().getXl2B().getChannelHeader().setAdditionalHeader("");
        soapReqXL2B.getBody().getXl2B().getChannelHeader().setBranchCode(branch);
        soapReqXL2B.getBody().getXl2B().getChannelHeader().setChannelID(clsChannelId);
        soapReqXL2B.getBody().getXl2B().getChannelHeader().setClientSupervisorID(clientSpvUserId);
        soapReqXL2B.getBody().getXl2B().getChannelHeader().setClientUserID(clientUserId);
        soapReqXL2B.getBody().getXl2B().getChannelHeader().setReference(referenceId);
        soapReqXL2B.getBody().getXl2B().getChannelHeader().setTransactionDate(date);
        soapReqXL2B.getBody().getXl2B().getChannelHeader().setTransactionTime(time);

        soapReqXL2B.getBody().getXl2B().getCmsXl2BRequest().setMaturity(maturityDate);
        soapReqXL2B.getBody().getXl2B().getCmsXl2BRequest().setNoteNo(newKeyloanAcc);
        soapReqXL2B.getBody().getXl2B().getCmsXl2BRequest().setOfficerCode(clsChannelId);


        return soapReqXL2B;
    }

    public com.maybank.integratorapp.model.soap.limit.XL2B.response.SoapEnvelope getXl2BMsgBodyResponse(
            com.maybank.integratorapp.model.soap.limit.XL2B.request.SoapEnvelope soapReqXL2B,String referenceId,String eventCode,String startDate,String maturityDate) {
        com.maybank.integratorapp.model.soap.limit.XL2B.response.SoapEnvelope serviceResponse
                = new com.maybank.integratorapp.model.soap.limit.XL2B.response.SoapEnvelope();
        String soapUrl = parameterService.findValueByPrmKey("XL31Request");
        try {
            XmlMapper mapper = new XmlMapper();

            String dcType = "-";
            String amount = "-";
            String noteNumber = soapReqXL2B.getBody().getXl2B().getCmsXl2BRequest().getNoteNo();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlString = null;
            try {
                xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReqXL2B);

//                log.info(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(this.LoggerId,ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

//            com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope cmsResponseXL31 = null;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xmlString, ContentType.TEXT_XML));
                String _response = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();
                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);
                    _response = outputResponse;
                    logger.Log(this.LoggerId,ProcessName, "Response ESB Message", "ESB-MESSAGE", _response);
                    if (_response.contains("Fault")) {
                        log.info(_response);
                    }
                    serviceResponse = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL2B.response.SoapEnvelope.class);


                    String xmlResponseXL2B = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(serviceResponse);
//                    log.info(xmlResponseXL2B);

                    // Extract  response code
                    String responseCode = serviceResponse
                            .getBody().getXl2BResponse().
                            getCmsXl2BResponse().getResponsecode();
                    String responseMessage = serviceResponse
                            .getBody().getXl2BResponse().
                            getCmsXl2BResponse().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();

                    if(responseMessage.contains("exception") && responseCode == null)
                        responseCode= "99";
                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(LoggerId);
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL2B");
                    ftiTransactionDetail.setTransName("Reservation");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage);
                    ftiTransactionDetail.setAdditionalInfo1(noteNumber);
                    ftiTransactionDetail.setAdditionalInfo2(dcType);
                    ftiTransactionDetail.setAdditionalInfo3(amount);
                    ftiTransactionDetail.setAdditionalInfo4("NRY");
                    ftiTransactionDetail.setReqMessage(xmlString);
                    ftiTransactionDetail.setResMessage(xmlResponseXL2B);
                    String _StartAndMaturity =startDate+"#"+maturityDate;
                    ftiTransactionDetail.setAdditionalInfo5(_StartAndMaturity);
                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                }
            } catch (Exception e) {

                logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }

        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return serviceResponse;
    }


    // step 5. Map core system data to external Response
    private void mapExternalResponse(
            String clsResponseCode,
            String clsResponseMessage,
            String facilityIdentifier,
            String facilitySequence,
            String newKeyLoanAcc,
            String formattedRunningNumber,
            String customerRes,
            String startdateRes,
            String expireDateRes,
            String currency,
            String limitAmount,
            String exposureAmount,
            String reservedAmount,
            String availableAmount
    ) {
        ResponseHeader responseHeader = response.getResponseHeader();
//set reservation response
        ReservationsResponse reservationsResponse =  new ReservationsResponse();
        reservationsResponse.setFacilityIdentifier(facilityIdentifier);
        reservationsResponse.setFacilitySequence(facilitySequence);
        reservationsResponse.setReservationIdentifier(newKeyLoanAcc);
        reservationsResponse.setReservationSequence(formattedRunningNumber);
        reservationsResponse.setCustomer(customerRes);
        reservationsResponse.setFacilityExposureIdentifier(newKeyLoanAcc);

//        limitAmount = limitAmount.replaceAll("\\.","");
        exposureAmount = exposureAmount.replaceAll("\\.","");
//        reservedAmount = reservedAmount.replaceAll("\\.","");
//        availableAmount = availableAmount.replaceAll("\\.","");
        String balance = limitAmount.split("\\.")[0];
        String utilizedBalance = reservedAmount.split("\\.")[0];

        //set Reservation Details
        ReservationResponseDetails reservationResponseDetails = new ReservationResponseDetails();
        reservationResponseDetails.setStartDate(startdateRes);
        reservationResponseDetails.setExpiryDate(expireDateRes);
        reservationResponseDetails.setCurrency(currency);
        reservationResponseDetails.setLimitAmount(balance+"00");
        reservationResponseDetails.setAvailableAmount(utilizedBalance+"00");
//        reservationResponseDetails.setLimitAmount(limitAmount);
        reservationResponseDetails.setExposureAmount(exposureAmount);
//        reservationResponseDetails.setReservedAmount(reservedAmount);
//        reservationResponseDetails.setAvailableAmount(availableAmount);
        reservationResponseDetails.setLimitCheckStatus("S");

        if(!clsResponseCode.equals("00")){
            reservationResponseDetails.setLimitCheckStatus("E");

            responseHeader.setStatus("FAILED");
            Details _details = new Details();
            _details.setError("[CLS ERROR] "+clsResponseMessage);
            responseHeader.setDetails(_details);
        }


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



        response.setReservationsResponse(reservationsResponse);
        response.setResponseHeader(responseHeader);
    }
    private String buildNewKey(String originalKey, String formattedRunningNumber) {
        String[] parts = splitKey(originalKey);
        parts[5] = formattedRunningNumber;
        return String.join("", parts);
    }

    private String buildCustomFacilityIdentifier(String key) {
        String[] parts = splitKey(key);
        parts[5] = "";
        parts[6] = "";

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

    private void sendEmailNotif(){
        List<FtiTransactionDetail> _transDetail = ftiTransactionDetailService.getDetailsByTransMessageLogId(LoggerId);
        FtiTransaction _transaction = ftiTransactionService.getFtiTransactionById(_transDetail.get(0).getHeaderId());

        emailService.sendTransactionNotification(_transaction,_transDetail);
    }

}
