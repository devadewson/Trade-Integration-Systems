package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.coresystem.ProcessFacilities;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.*;
import com.maybank.integratorapp.data.service.FtiTransactionDetailService;
import com.maybank.integratorapp.data.service.FtiTransactionService;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.MsParameterService;
import com.maybank.integratorapp.model.mq.reservation.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.reservation.response.*;
import com.maybank.integratorapp.model.soap.limit.XL01.request.SoapEnvelope;
import jakarta.jms.JMSException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
    private ProcessFacilities processFacilities;

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
//                System.out.println("Facility ID: " + facilityId);
//                System.out.println("LineOfBusiness: " + lineOfBusiness);
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
//                    System.out.println("No Running Number found for Facility ID: " + facilityId);
//                }
//                // Ambil Running Number dan pastikan format 3 digit
//                int runningNumber = runningNumberEntry.getRunningNumber();
//                String formattedRunningNumber = String.format("%03d", runningNumber + 1);
//                System.out.println("Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber);
//
//                // Buat keyLoanAcc baru dengan mengganti bagian draw
//                String customFacilityIdentifier = buildCustomFacilityIdentifier(facilityIdentifier);
//                System.out.println("Facility Identifier: " + customFacilityIdentifier);
//
//                // Buat keyLoanAcc baru dengan mengganti bagian draw
//                newKeyLoanAcc = buildNewKey(facilityIdentifier, formattedRunningNumber);
//                System.out.println("New KeyLoanAcc: " + newKeyLoanAcc);
//
//                // Buat formatted key untuk sistem proses
//                acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);
//                System.out.println("New Formatted Key: " + acctReqXL01);
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
//                logger.Log(ProcessName, "CLS Product Type search criteria "+productType+"|"+lineOfBusiness+"|"+_eventCode, "DEBUG");
//
//                System.out.println("CLS Product Type : " + cls001ProductType);
//                logger.Log(ProcessName, "CLS Product Type : "+cls001ProductType, "DEBUG");
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
//                        com.maybank.integratorapp.model.soap.limit.XL41.request.SoapEnvelope XL41Request = utilizationMessageProcessor.mapCoreSystemRequest(_lastLimitReservationId,masterReference);
//
//                        com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope XL41Response = utilizationMessageProcessor.getMsgBodyResponse(XL41Request,eventCode,masterReference,_lastLimitReservationId);
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
//            logger.Log(ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());
//
//        }
//        return responseXml;
//    }

    public String processMessage(String message,Long loggerId) {
        String responseXml = "";
        logger.SetLogParent(loggerId);

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
                LocalDate _startDate = LocalDate.parse(startdateRes, inputFormatter);
//            String startDate = _startDate.format(outputFormatter);
                String startDate = "311024";

                LocalDate _expiryDate = LocalDate.parse(expireDateRes, inputFormatter);
                String expiryDate = _expiryDate.format(outputFormatter);
//            String expiryDate = "291224";

                LocalDate _transDate = LocalDate.parse(transDateRes, inputFormatter);
//            String transactionDate = _transDate.format(outputFormatter);
                String transactionDate = "311024";

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

                System.out.println("Facility ID: " + facilityId);
                System.out.println("LineOfBusiness: " + lineOfBusiness);



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
                    System.out.println("No Running Number found for Facility ID: " + facilityId);
                }
                // Ambil Running Number dan pastikan format 3 digit
                int runningNumber = runningNumberEntry.getRunningNumber();
                formattedRunningNumber = String.format("%03d", runningNumber + 1);
                System.out.println("Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber);
                logger.Log(ProcessName, "Running Number for Facility ID " + facilityId + ": " + formattedRunningNumber, "DEBUG");

                // Buat keyLoanAcc baru dengan mengganti bagian draw
                newKeyLoanAcc = buildNewKey(facilityIdentifier, formattedRunningNumber);
                System.out.println("New KeyLoanAcc: " + newKeyLoanAcc);
                logger.Log(ProcessName, "New KeyLoanAcc: " + newKeyLoanAcc, "DEBUG");

                // Buat formatted key untuk sistem proses
                acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);
                System.out.println("New Formatted Key: " + acctReqXL01);
                logger.Log(ProcessName, "New Formatted Key: " + acctReqXL01, "DEBUG");

                // cek dulu di table referensi transaksinya
                // timpa & pakai yang lama jika ada
                Optional<FtiTransaction> ftiTransactionData = ftiTransactionService.findByMasterRefNo(masterReference);

                if(!ftiTransactionData.isEmpty()){
                    FtiTransaction transaction = ftiTransactionData.get();
                    String _customFacilityIdentifier = facilityIdentifier.substring(0,(facilityIdentifier.length()-6));
                    String _facNew = splitKey(facilityIdentifier)[4];
                    List<FtiTransactionDetail> transactionDetails = ftiTransactionDetailService.getDetailsByHeaderId(transaction.getId());
                    if(transactionDetails.stream().anyMatch(x->x.getFtiEvent().equals("ISS001"))){
//                        FtiTransactionDetail transactionDetails1;
//                        transactionDetails.forEach(x->{
//                            if(x.getAdditionalInfo1()!=null){
//                                transactionDetails1 = x;
//                                break;
//                            }
//                        });
                        FtiTransactionDetail transactionDetails1 = transactionDetails.stream().filter(x->x.getFtiEvent().equals("ISS001")&&x.getAdditionalInfo1()!=null).findFirst().get();
                        logger.Log(ProcessName, "Get Old : " + transactionDetails1.getId(), "DEBUG");

//                        check whether its the same facility
                        String _facOld = splitKey(transactionDetails1.getAdditionalInfo1())[4];

                        if(_facOld.equals(_facNew)){
                            logger.Log(ProcessName, "Previous KeyLoanAcc: " + transactionDetails1.getAdditionalInfo1(), "DEBUG");

                            newKeyLoanAcc = transactionDetails1.getAdditionalInfo1();
                            xl01responseCode = "00";
                            formattedRunningNumber = splitKey(newKeyLoanAcc)[5];
                            acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);
                        }

                    }
                }

                // treat amend as issue for mapping purpose
                if(_eventCode.equals("AMD") || _eventCode.equals("ADJ"))
                    _eventCode = "ISS";

                if(!lineOfBusiness.equals("01"))
                    lineOfBusiness= "00";
                if(productType.equals("515")) // bank limits
                    lineOfBusiness= "07";

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
                logger.Log(ProcessName, "CLS Product Type search criteria "+productType+"|"+lineOfBusiness+"|"+_eventCode, "DEBUG");

                System.out.println("CLS Product Type : " + cls001ProductType);
                logger.Log(ProcessName, "CLS Product Type : "+cls001ProductType, "DEBUG");


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
                    logger.Log(ProcessName, "Transaction Detail Count : "+_listTransactionDetail.stream().count(), "DEBUG");

                    _lastLimitAction = _listTransactionDetail.get((int) (_listTransactionDetail.stream().count()-1));
                }

                if(eventCode.startsWith("ISS") && _lastLimitAction==null){
                    //draw 001
                    // step 3.
                    SoapEnvelope msgRequestXL01 = mapCoreSystemXl01Request(masterReference,acctReqXL01,newKeyLoanAcc,currency,startDate,expiryDate,transactionDate,cls001ProductType,branch);

                    // step 4.
//                    logger.Log(ProcessName, "CLS BEFORE HIT XL01", "DEBUG");
                    com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope msgResponseXL01 = getXl01MsgBodyResponse(msgRequestXL01,masterReference,eventCode,formattedRunningNumber,newKeyLoanAcc);
//                    logger.Log(ProcessName, "CLS AFTER HIT XL01", "DEBUG");

                    xl01responseCode = msgResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponsecode();
                    xl01responseMessage = msgResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
//                    logger.Log(ProcessName, "CLS AFTER HIT XL01 RESPONSE CODE :"+xl01responseCode, "DEBUG");

//                    logger.Log(ProcessName, "CLS AFTER HIT XL01 RESPONSE MESSAGE :"+xl01responseMessage, "DEBUG");
                    if(xl01responseCode.equals("00")){
//                        logger.Log(ProcessName, "CLS BEFORE REFRESH FACILITIES", "DEBUG");
                        facilitiesMessageProcessor.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
//                        logger.Log(ProcessName, "CLS AFTER REFRESH FACILITIES", "DEBUG");
                        FtiTransaction ftiTransaction = new FtiTransaction();
                        ftiTransaction.setMasterRefNo(masterReference);
                        ftiTransaction.setDrawNumber(formattedRunningNumber);
                        ftiTransaction.setReservationId(newKeyLoanAcc);
                        ftiTransactionService.createOrUpdateFtiTransaction(ftiTransaction);
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

                if(xl01responseCode.equals("00") || !eventCode.startsWith("ISS")){
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
                    if(xl31responseCode.equals("00")) {
                        processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
                    }
                    // step 5.
                    mapExternalResponse(xl31responseCode,xl31responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
                }



            }

            // step 6.
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            responseXml = xmlMapper.writeValueAsString(response);

        } catch (Exception e) {
            logger.Log(ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());

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
            logger.Log(ProcessName, "Error Json Processing", "ERROR-JSON-PROCESS", e.getMessage());
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

        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setAdditionalHeader("");
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setBranchCode(branch);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setChannelID(clsChannelId);
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setClientSupervisorID("7766");
        soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setClientUserID("7755");
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
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setUserCode("7755");


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

                System.out.println(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

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
                    logger.Log(ProcessName, "Response ESB Message", "ESB-MESSAGE", _response);

                    if (_response.contains("Fault")) {
                        System.out.println(_response);

                    }
                    cmsResponseXL01 = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL01.responseComplete.SoapEnvelope.class);


                    String xmlResponseXL01 = mapper.writeValueAsString(cmsResponseXL01);
                    System.out.println(xmlResponseXL01);

                    // Extract  response code
                    String responseCode = cmsResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponsecode();
                    String responseMessage = cmsResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(logger.getIdLogParent());
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL01Draw001");
                    ftiTransactionDetail.setTransName("Reservation");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage);
                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                }
            } catch (Exception e) {

                logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }

        } catch (Exception e) {
            logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

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

                System.out.println(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

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
                    logger.Log(ProcessName, "Response ESB Message", "ESB-MESSAGE", _response);
                    if (_response.contains("Fault")) {
                        System.out.println(_response);
                    }
                    serviceResponse = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope.class);


                    String xmlResponseXL01 = mapper.writeValueAsString(serviceResponse);
                    System.out.println(xmlResponseXL01);

                    // Extract  response code
                    String responseCode = serviceResponse
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponsecode();
                    String responseMessage = serviceResponse
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(logger.getIdLogParent());
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL31");
                    ftiTransactionDetail.setTransName("Reservation");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage);
                    if(responseCode.equals("00")){
                        ftiTransactionDetail.setAdditionalInfo1(noteNumber);
                        ftiTransactionDetail.setAdditionalInfo2(dcType);
                        ftiTransactionDetail.setAdditionalInfo3(amount);
                        ftiTransactionDetail.setAdditionalInfo4("NRY");
                    }

                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                }
            } catch (Exception e) {

                logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }

        } catch (Exception e) {
            logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

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

        soapReqXL31.getBody().getXl31().getChannelHeader().setAdditionalHeader("");
        soapReqXL31.getBody().getXl31().getChannelHeader().setBranchCode(branch);
        soapReqXL31.getBody().getXl31().getChannelHeader().setChannelID(clsChannelId);
        soapReqXL31.getBody().getXl31().getChannelHeader().setClientSupervisorID("7766");
        soapReqXL31.getBody().getXl31().getChannelHeader().setClientUserID("7755");
        soapReqXL31.getBody().getXl31().getChannelHeader().setReference(referenceId);
        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionDate(date);
        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionTime(time);

        soapReqXL31.getBody().getXl31().getCmsXl31Request().setAmount(exposureAmmount);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBatch(limitBranch+"01");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBd("");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setCurrency(currency);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDepartement(limitBranch);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDescription("FTI");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setNotenumber(newKeyloanAcc);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setQual("0");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTran(debit_credit);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTransactiondate(transactionDate);

        return soapReqXL31;
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
            String exposureAmmount,
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

        //set Reservation Details
        ReservationResponseDetails reservationResponseDetails = new ReservationResponseDetails();
        reservationResponseDetails.setStartDate(startdateRes);
        reservationResponseDetails.setExpiryDate(expireDateRes);
        reservationResponseDetails.setCurrency(currency);
        reservationResponseDetails.setLimitAmount(limitAmount);
        reservationResponseDetails.setExposureAmount(exposureAmmount);
        reservationResponseDetails.setReservedAmount(reservedAmount);
        reservationResponseDetails.setAvailableAmount(availableAmount);
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


}
