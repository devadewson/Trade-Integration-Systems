package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.*;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.mq.reservation.request.ExtraDataFields;
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
import java.time.Period;
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

    public String processMessage(String message,Long loggerId) {
        String responseXml = "";
        this.LoggerId = loggerId;
        processFacilities.setLoggerId(loggerId);
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
                String FtiSubProductCode = request.getReservationsRequest().getReservationRequestDetails().getProductSubType();
                String eventCode = request.getReservationsRequest().getReservationRequestDetails().getEventReference();
                String _eventCode = eventCode.substring(0,3);
                String startdateRes = request.getReservationsRequest().getReservationRequestDetails().getTenorStartDate();
                String expireDateRes =  request.getReservationsRequest().getReservationRequestDetails().getTenorEndDate();
                String transDateRes = request.getReservationsRequest().getReservationRequestDetails().getValueDate();
                String exposureAmmount =  request.getReservationsRequest().getReservationRequestDetails().getPostingAmount().getAmount();
                List<ExtraDataFields> listExtraData =  request.getReservationsRequest().getExtraDataFieldss() ==null?new ArrayList<>():request.getReservationsRequest().getExtraDataFieldss().getExtraDataFields();
                String affiliateAccount = "";
                String allInInterestRate = "";
                if(!listExtraData.isEmpty()) {
                    if(listExtraData.stream().anyMatch(x->x.getName().equals("IndexRateCode"))){
                        affiliateAccount = listExtraData.stream().filter(x->x.getName().equals("IndexRateCode")).findFirst().get().getValue();

                    }
                    if(listExtraData.stream().anyMatch(x->x.getName().equals("AllInRate"))){
                        allInInterestRate = listExtraData.stream().filter(x->x.getName().equals("AllInRate")).findFirst().get().getValue();

                    }

                }

//                if(startdateRes == null|| expireDateRes == null){
//                    mapExternalResponse("99","[Integrator]-Start Date or End Date NULL","","","","",customerRes,startdateRes,expireDateRes,"","",exposureAmmount,"","");
//                    XmlMapper xmlMapper = new XmlMapper();
//                    xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//                    responseXml = xmlMapper.writeValueAsString(response);
//
//                    return responseXml;
//
//                }

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
                boolean needXL01Accept = false;

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
//                    String _facNew = splitKey(facilityIdentifier)[4];
                    String _facNew = facilityIdentifier.substring(0,facilityIdentifier.length()-5);
                    List<FtiTransactionDetail> transactionDetails = ftiTransactionDetailService.getDetailsByHeaderId(transaction.getId());
                    if(transactionDetails.stream().anyMatch(x->
                            x.getCoreSysName().equals("CLS-XL01Draw001")&&
                            x.getCoreSysStatus().equals("00"))){

                        FtiTransactionDetail transactionDetails1 = transactionDetails.stream().filter(x -> x.getAdditionalInfo4().equals("DRW")).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                        logger.Log(this.LoggerId,ProcessName, "Get Old : " + transactionDetails1.getId(), "DEBUG");

                        if(transactionDetails1.getCoreSysStatus().equals("00")){
//                            String _facOld = splitKey(transactionDetails1.getAdditionalInfo1())[4];
                            String _facOld = transactionDetails1.getAdditionalInfo1().substring(0,transactionDetails1.getAdditionalInfo1().length()-5);

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
                                            (x.getCoreSysName().equals("CLS-XL01Draw001")|| x.getCoreSysName().equals("CLS-XL2B")) &&
                                            x.getCoreSysStatus().equals("00")
                                    ).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                                    // sementara XL2B belum bisa ganti start date, maka hanya compare expiry nya saja
                                    if(transactionDetails1.getAdditionalInfo5() != null){
//                                        _dateOld = transactionDetails1.getAdditionalInfo5();
                                        _dateOld = transactionDetails1.getAdditionalInfo5().split("#")[1];
                                    }
                                    // jika setelah XL2B Terakhir ada XL41 D, maka bikin ulang XL2B nya
                                    FtiTransactionDetail finalTransactionDetails = transactionDetails1;
                                    if(finalTransactionDetails.getCoreSysName().equals("CLS-XL2B")){
                                        FtiTransactionDetail _transDetailXL41 = transactionDetails.stream().filter(x ->
                                                x.getCoreSysName().equals("CLS-XL41")&&
                                                        x.getCoreSysStatus().equals("00") &&
                                                        x.getAdditionalInfo2().equals("D") &&
                                                        x.getFtiEvent().equals(eventCode) &&
                                                        x.getId()> finalTransactionDetails.getId()
                                        ).toList().isEmpty()?null:
                                                transactionDetails.stream().filter(x ->
                                                        x.getCoreSysName().equals("CLS-XL41")&&
                                                                x.getCoreSysStatus().equals("00") &&
                                                                x.getAdditionalInfo2().equals("D") &&
                                                                x.getFtiEvent().equals(eventCode) &&
                                                                x.getId()> finalTransactionDetails.getId()
                                                ).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                                        if(_transDetailXL41 !=null){
                                            needXL2B=true;
                                        }
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
                                            (x.getCoreSysName().equals("CLS-XL01Draw001") || x.getCoreSysName().equals("CLS-XL2B")) &&
                                                    x.getCoreSysStatus().equals("00")
                                    ).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                                    newKeyLoanAcc = reservedReservationIdentifier;
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
                                if(_eventCode.equals("EXP") || _eventCode.equals("ADJ") || _eventCode.equals("AMD")){
                                    newKeyLoanAcc = reservedReservationIdentifier;
                                }
                                if(_eventCode.equals("BNR")){
                                    newKeyLoanAcc = reservedReservationIdentifier;
                                    needXL2B = false;
                                }

                                if(exposureAmmount.equals("0")){
                                    needXL31 = false;
                                }

//


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
                    else{
                        needXL01= true;
                    }

                    //CLM Accept logic
                    if(_eventCode.equals("CLM") || _eventCode.equals("POC")){
                        if(!listExtraData.isEmpty()){
                            if(listExtraData.stream().anyMatch(x->x.getName().equals("PaymentOption"))){
                                if(listExtraData.stream().filter(x->x.getName().equals("PaymentOption")).findFirst().get().getValue().equals("Accept")
                                || listExtraData.stream().filter(x->x.getName().equals("PaymentOption")).findFirst().get().getValue().equals("Bill Settlement (mixed)")){
                                    // kalau belum ada new draw untuk akseptasi
                                    if(debitCreditFlag.equals("D")){
                                        if(transactionDetails.stream().noneMatch(x->
                                                x.getCoreSysName().equals("CLS-XL01Draw001")
                                                        && x.getFtiEvent().equals(eventCode)
                                                        && x.getCoreSysStatus().equals("00")
                                        )){
                                            needXL01=true;
                                            runningNumber = runningNumberEntry.getRunningNumber();
                                            formattedRunningNumber = String.format("%03d", runningNumber + 1);

                                            // Buat keyLoanAcc baru dengan mengganti bagian draw
                                            newKeyLoanAcc = buildNewKey(facilityIdentifier, formattedRunningNumber);

                                            // Buat formatted key untuk sistem proses
                                            acctReqXL01 = buildFormattedKey(facilityIdentifier, formattedRunningNumber);
                                            needXL2B = false;

                                        }else{
                                            FtiTransactionDetail _found = transactionDetails.stream().filter(x->
                                                    x.getCoreSysName().equals("CLS-XL01Draw001")
                                                            && x.getFtiEvent().equals(eventCode)
                                                            && x.getCoreSysStatus().equals("00")
                                            ).findFirst().get();
                                            needXL01=false;
                                            needXL2B = false;
                                            newKeyLoanAcc = _found.getAdditionalInfo1();

                                            _found = transactionDetails.stream().filter(x->
                                                    (x.getCoreSysName().equals("CLS-XL01Draw001") || x.getCoreSysName().equals("CLS-XL2B"))
                                                            && x.getFtiEvent().equals(eventCode)
                                                            && x.getCoreSysStatus().equals("00")
                                            ).max(Comparator.comparing(FtiTransactionDetail::getId)).get();
                                            String _dateOld = _found.getAdditionalInfo5().split("#")[1];
                                            if(!_dateOld.equals(expiryDate)){
                                                needXL2B = true;
                                            }



                                        }

                                        needXL31 = true;
                                    }
                                    else{
                                        if(!listExtraData.stream().filter(x -> x.getName().equals("LinkedClaimResId")).findFirst().isEmpty()){
                                            reservedReservationIdentifier =listExtraData.stream().filter(x -> x.getName().equals("LinkedClaimResId")).findFirst().get().getValue();

                                        }
                                        newKeyLoanAcc = reservedReservationIdentifier;
                                        needXL01= false;
                                        needXL31 = true;
                                    }




                                }
                                else if(listExtraData.stream().filter(x->x.getName().equals("PaymentOption")).findFirst().get().getValue().equals("Bill Settlement")){
                                    if(!listExtraData.stream().filter(x -> x.getName().equals("LinkedClaimResId")).findFirst().isEmpty()){
                                        reservedReservationIdentifier =listExtraData.stream().filter(x -> x.getName().equals("LinkedClaimResId")).findFirst().get().getValue();
                                    }
                                    if(debitCreditFlag.equals("C")){
                                        newKeyLoanAcc = reservedReservationIdentifier;
                                        needXL01 = false;
                                        needXL2B = false;
                                        needXL31 = true;
                                    }
                                }
                            }

                        }

                    }
                }

                // treat anything except claim as issue for mapping purpose
                String clsSpecialEvent = parameterService.findValueByPrmKey("CLSSpecialEvent");
                String clsProductTypeSearch = parameterService.findValueByPrmKey("CLSProductTypeSearch");

                String final_eventCode = _eventCode;
                if (Arrays.stream(clsSpecialEvent.split(",")).noneMatch(z->z.equals(final_eventCode))) {
                    _eventCode = "ISS";
                }

                if(!lineOfBusiness.equals("07") && !lineOfBusiness.equals("01"))
                    lineOfBusiness= "00";
//                if(productType.equals("515") || productType.equals("525")) // bank limits
//                    lineOfBusiness= "07";

                String cls001ProductType= "";
                logger.Log(this.LoggerId,ProcessName, "CLS Product Type search criteria "+productType+"|"+lineOfBusiness+"|"+FtiProductCode+"|"+FtiSubProductCode+"|"+_eventCode, "DEBUG");

                if(productType.startsWith("7")) // islamic limits
                {
                    List<MsMapClsProductType> productTypeList = msMapClsProductTypeRepository.findDraw001Products(productType);
                    lineOfBusiness = request.getReservationsRequest().getReservationRequestDetails().getCustomerType();
                    //special case 710
                    if(productType.equals("710")){
                        if(_eventCode.equals("ISS"))
                            cls001ProductType= productTypeList.stream().filter(x->
                                            x.getEventCode().contains("ISS") &&
                                                    x.getLiabilityCode().equals("IGT"))
                                    .findFirst().get().getProductType001();
                        else{
                            cls001ProductType = msMapClsProductTypeRepository.findDraw001ProductWithLiabCode(productType, lineOfBusiness,FtiSubProductCode,_eventCode);

                        }
                    }
                    else if(productType.equals("765")||productType.equals("790")){
                        lineOfBusiness = request.getReservationsRequest().getReservationRequestDetails().getCustomerType();
                        cls001ProductType = msMapClsProductTypeRepository.findDraw001Product(productType, lineOfBusiness,_eventCode);

                    }
                    else if(productType.equals("715")){
                        cls001ProductType = msMapClsProductTypeRepository.findDraw001ProductWithLiabCode(productType, lineOfBusiness,FtiSubProductCode,_eventCode);
                        if(Arrays.asList(clsProductTypeSearch.split(",")).contains(FtiProductCode)) {
                            if (!listExtraData.isEmpty()) {
                                if (listExtraData.stream().anyMatch(x -> x.getName().equals("PaymentOption"))) {
                                    if (listExtraData.stream().filter(x -> x.getName().equals("PaymentOption")).findFirst().get().getValue().equals("Accept")) {

                                        if (debitCreditFlag.equals("D")) {
                                            FtiSubProductCode="ACC";
                                            cls001ProductType = msMapClsProductTypeRepository.findDraw001ProductWithLiabCode(productType, lineOfBusiness,FtiSubProductCode,_eventCode);

                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                else if(Arrays.asList(clsProductTypeSearch.split(",")).contains(FtiProductCode) && !lineOfBusiness.equals("07")){
                    cls001ProductType = msMapClsProductTypeRepository.findDraw001ProductWithLiabCode(productType, lineOfBusiness,FtiSubProductCode,_eventCode);

                }
                else{

                    cls001ProductType = msMapClsProductTypeRepository.findDraw001Product(productType, lineOfBusiness,_eventCode);

                }

//                log.info("CLS Product Type : " + cls001ProductType);
                logger.Log(this.LoggerId,ProcessName, "CLS Product Type : "+cls001ProductType, "DEBUG");

                if(cls001ProductType == null){
                    mapExternalResponse("99","[Integrator]-Product Type Not Found",facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
                    XmlMapper xmlMapper = new XmlMapper();
                    xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                    responseXml = xmlMapper.writeValueAsString(response);

                    return responseXml;

                }


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

                logger.Log(this.LoggerId,ProcessName, "CLS : "+
                        "XL01:"+needXL01+"|"+
                        "XL2B:"+needXL2B+"|"+
                        "XL31:"+needXL31+"|", "DEBUG");


                if(needXL01){
                    //draw 001
                    // step 3.
                    
                    SoapEnvelope msgRequestXL01 = mapCoreSystemXl01Request(masterReference,acctReqXL01,newKeyLoanAcc,currency,startDate,expiryDate,transactionDate,cls001ProductType,branch,affiliateAccount,allInInterestRate);

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

                    if((xl01responseMessage!=null && xl01responseMessage.contains("exception")) || xl01responseCode == null)
                        xl01responseCode= "99";

                    if(xl01responseCode.equals("99") && xl01responseMessage == null)
                        xl01responseMessage = "[Integrator]-Unknown CLS Error";

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
                        needXL31 = false;
                        needXL2B = false;
                        // step 5.
                        mapExternalResponse(xl01responseCode,xl01responseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);

                    }
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
                                x.getCoreSysName().equals("CLS-XL40")
                                && x.getCoreSysStatus().equals("00")
                                && x.getFtiEvent().equals(eventCode)
                                && x.getId()>_lastXL2B.getId()
                        ).max(Comparator.comparing(FtiTransactionDetail::getId));

                        Optional<FtiTransactionDetail> lastXL41 = transactionDetails.stream().filter(x ->
                                x.getCoreSysName().equals("CLS-XL41")
                                        && x.getCoreSysStatus().equals("00")
                                        && x.getAdditionalInfo2().equals("D")
                                        && x.getFtiEvent().equals(eventCode)
                                        && x.getId()>_lastXL2B.getId()
                        ).max(Comparator.comparing(FtiTransactionDetail::getId));
                        // kalau masih ada XL2B gantung, kirim XL40 untuk XL2B yang gantung, next bikin baru
                        // dan kalau gaada reversal

                        if(lastXL41.isEmpty() && lastXL40.isEmpty()){
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

                    if((xl2BresponseMessage!= null && xl2BresponseMessage.contains("exception")) || xl2BresponseCode == null)
                        xl2BresponseCode= "99";

                    if(xl2BresponseCode.equals("99") && xl2BresponseMessage == null)
                        xl2BresponseMessage = "[Integrator]-Unknown CLS Error";



                    if(xl2BresponseCode.equals("00")) {
                        processFacilities.refreshFacilities(facilities.getCifNo(), facilities.getCompanyLimitId());
                    }
                    // step 5.
                    mapExternalResponse(xl2BresponseCode,xl2BresponseMessage,facilityIdentifier,facilitySequence,newKeyLoanAcc,formattedRunningNumber,customerRes,startdateRes,expireDateRes,currency,limitAmount,exposureAmmount,reservedAmount,availableAmount);
                }

                if(needXL31){
                    String chgMethodSpecialCase = parameterService.findValueByPrmKey("CLSChgMethodProdTypeList");
                    String trancode60ProdTypeList = parameterService.findValueByPrmKey("CLSTrancode60ProdTypeList");
                    String debit_credit = "62";
                    if(debitCreditFlag.equals("C"))
                        debit_credit = "67";

                    if(Arrays.asList(chgMethodSpecialCase.split(",")).contains(cls001ProductType)
                    && Arrays.asList(trancode60ProdTypeList.split(",")).contains(FtiSubProductCode)) {
                        if(debitCreditFlag.equals("D"))
                            debit_credit = "60";
                        if(debitCreditFlag.equals("C"))
                            debit_credit = "65";
                    }


                    String clsCustomReference = generateCLSRefCode(FtiProductCode,masterReference,_eventCode);

                    //draw 31
                    com.maybank.integratorapp.model.soap.limit.XL31.request.SoapEnvelope msgRequestXL31= mapCoreSystemXl31Request(masterReference,exposureAmmount,newKeyLoanAcc,currency,transactionDate,debit_credit,branch,eventCode,clsCustomReference);

                    // step 4.
                    com.maybank.integratorapp.model.soap.limit.XL31.response.SoapEnvelope msgResponseXL31 = getXl31MsgBodyResponse(msgRequestXL31,masterReference,eventCode);

                    xl31responseCode = msgResponseXL31
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponsecode();
                    xl31responseMessage = msgResponseXL31
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();

                    formattedRunningNumber = splitKey(newKeyLoanAcc)[5];

                    if((xl31responseMessage!= null && xl31responseMessage.contains("exception")) || xl31responseCode == null)
                        xl31responseCode= "99";

                    if(xl31responseCode.equals("99") && xl31responseMessage == null)
                        xl31responseMessage = "[Integrator]-Unknown CLS Error";

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
            String branch,
            String affiliateAcct,
            String allInInterestRate
    ) {
        SoapEnvelope soapReqXL01 = new SoapEnvelope();
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
        String chgMethodSpecialCase = parameterService.findValueByPrmKey("CLSChgMethodProdTypeList");
        String correlationID = "FTI";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String[] splittedKey = splitKey(newKeyloanAcc);
        String limitCurrency = splittedKey[1];
        String limitBranch = splittedKey[2];
        String limitCif = splittedKey[3];
        String limitRunningNumber = splittedKey[5];

        String chgMethod = "0";
        if(Arrays.asList(chgMethodSpecialCase.split(",")).contains(cls001ProductType)) {
            chgMethod = "1";

        }

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
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setChgmeth(chgMethod);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCifNo(limitCif);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCommitmentCode("3");
//        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCommitmentType("0");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl2(limitCurrency);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl3(limitBranch);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl4("0000");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCurrency(currency);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setDepartement(limitBranch);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setIntstart(startDate);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setMatdate(expiryDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
        LocalDate fromDate = LocalDate.parse(startDate, formatter);
        LocalDate toDate = LocalDate.parse(expiryDate, formatter);
        Period period = Period.between(fromDate, toDate);
        boolean lessThanOneMonth = period.getMonths() == 0 && period.getYears() == 0;

        if(lessThanOneMonth && cls001ProductType.startsWith("22")){
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setNbrpymt1("1");
        }
        if(affiliateAcct.length() == 20){

            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setNbrAcct1("16"+affiliateAcct);
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setSysAcct1("5");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setRate(reformatRateString(allInInterestRate));
        }else{
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setRate("000.000010");
        }
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setNotedate(transactionDate);
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setPrinamt("000000000000.00");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setProductType(cls001ProductType);





        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setRelCd("01");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setStatus("A");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setUseAcct1("2");
        soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setUserCode(clientUserId);


        return soapReqXL01;
    }
    public String reformatRateString(String input) {
        String[] parts = input.split("\\.");
        String integerPart = parts[0];
        String decimalPart = parts.length > 1 ? parts[1] : "";

        // Pad integer part to 3 digits and decimal part to 6 digits
        String formattedInteger = String.format("%03d", Integer.parseInt(integerPart));
        String formattedDecimal = String.format("%-6s", decimalPart).replace(' ', '0');

        return formattedInteger + "." + formattedDecimal;
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
                    String responseMessage = "";
                    if(responseCode!=null){
                        if(responseCode.equals("99")){
                            if(xmlResponseXL01.contains("xmlnsc"))
                                responseMessage = "Connection Refused";
                            else if(!cmsResponseXL01
                                    .getBody().getXl01Draw001Response().
                                    getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().isEmpty()){
                                responseMessage = cmsResponseXL01
                                        .getBody().getXl01Draw001Response().
                                        getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                                if(responseMessage == null)
                                    responseMessage = "Unknown Error";

                            }else{
                                responseMessage = "Unknown Error";
                            }
                        }else{
                            if(!cmsResponseXL01
                                    .getBody().getXl01Draw001Response().
                                    getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().isEmpty()){
                                responseMessage = cmsResponseXL01
                                        .getBody().getXl01Draw001Response().
                                        getCmsXL01Draw001Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();

                            }else{
                                responseMessage = "Unknown Error";
                            }
                        }

                    } else{
                        responseCode = "99";
                        responseMessage = "Unknown Error";
                    }


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
                    String responseCode = serviceResponse
                            .getBody().getXl31Response().
                            getCmsXl31Response().getResponsecode();
                    String responseMessage = "";
                    if(responseCode!=null){
                        if(responseCode.equals("99")){
                            if(xmlResponseXL31.contains("xmlnsc"))
                                responseMessage = "Connection Refused";
                            else if(!serviceResponse
                                    .getBody().getXl31Response().
                                    getCmsXl31Response().getResponseDetail().getAdditionalData().isEmpty()){
                                responseMessage = serviceResponse
                                        .getBody().getXl31Response().
                                        getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                                if(responseMessage == null)
                                    responseMessage = "Unknown Error";
                            }else{
                                responseMessage = "Unknown Error";
                            }
                        }else{
                            if(!serviceResponse
                                    .getBody().getXl31Response().
                                    getCmsXl31Response().getResponseDetail().getAdditionalData().isEmpty()){
                                responseMessage = serviceResponse
                                        .getBody().getXl31Response().
                                        getCmsXl31Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();

                            }else{
                                responseMessage = "Unknown Error";
                            }
                        }

                    } else{
                        responseCode = "99";
                        responseMessage = "Unknown Error";
                    }

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
            String eventCode,
            String clsCustomReference
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
//
//        if(referenceId.length() == 16){
//            clsCustomReference = referenceId.substring(2,3) +referenceId.substring(7,14)+eventCode.substring(0,3);
//        }
//        if(referenceId.length() == 13){
//            clsCustomReference = referenceId.substring(3,4) +referenceId.substring(4,10)+eventCode.substring(0,3);
//
//        }

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
        if(debit_credit.equals("62") || debit_credit.equals("67"))
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
                    String responseMessage = "";
                    if(responseCode!=null){
                        if(responseCode.equals("99")){
                            if(xmlResponseXL2B.contains("xmlnsc"))
                                responseMessage = "Connection Refused";
                            else if(!serviceResponse
                                    .getBody().getXl2BResponse().
                                    getCmsXl2BResponse().getResponseDetail().getAdditionalData().isEmpty()){
                                responseMessage = serviceResponse
                                        .getBody().getXl2BResponse().
                                        getCmsXl2BResponse().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                                if(responseMessage == null)
                                    responseMessage = "Unknown Error";
                            }else{
                                responseMessage = "Unknown Error";
                            }
                        }else{
                            if(!serviceResponse
                                    .getBody().getXl2BResponse().
                                    getCmsXl2BResponse().getResponseDetail().getAdditionalData().isEmpty()){
                                responseMessage = serviceResponse
                                        .getBody().getXl2BResponse().
                                        getCmsXl2BResponse().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();

                            }else{
                                responseMessage = "Unknown Error";
                            }
                        }

                    } else{
                        responseCode = "99";
                        responseMessage = "Unknown Error";
                    }

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

        //set Reservation Details
        ReservationResponseDetails reservationResponseDetails = new ReservationResponseDetails();
        ReservationResponseDetailss reservationResponseDetailss = new ReservationResponseDetailss();
        ReservationResponseExtraDetails reservationResponseExtraDetails = new ReservationResponseExtraDetails();
        ReservationResponseExtraDetailss reservationResponseExtraDetailss = new ReservationResponseExtraDetailss();

        if(!clsResponseCode.equals("00")){
            reservationResponseDetails.setLimitCheckStatus("E");

            responseHeader.setStatus("FAILED");
            Details _details = new Details();
            _details.setError("[CLS ERROR] "+clsResponseMessage);
            responseHeader.setDetails(_details);
        }
        else {
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

        }
        reservationResponseDetailss.setReservationResponseDetails(reservationResponseDetails);

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

    private String generateCLSRefCode(String ftiProduct,String masterRefNo,String eventCode){
        String _ref = "";
//        max char = 12
//        ILC:
//        ILC906S1234567ID >>> S1234567​+Event Code (ISS/AMD/CLM/etc)
//
//        ELC:
//        ELC906A1234567ID >>> A1234567​+Event Code (ISS/AMD/CLM/etc)
//
//        FIL:
//        I906CTR1234567ID >>> I1234567​+Event Code (ISS/AMD/CLM/etc)
//
//        FEL:
//        E906CNL1234567ID >>> E1234567​+Event Code (ISS/AMD/CLM/etc)
//
//        SG/ETD:
//        SSG906S1234567ID >>> G1234567​+Event Code (ISS/AMD/CLM/etc)
//
//        ODC/IDC:
//        CDO906C1234567ID >>> O1234567​+Event Code (ISS/AMD/CLM/etc)
//
//        FSA:
//        C906IFS1234567ID >>> C1234567​+Event Code (ISS/AMD/CLM/etc)

        if(masterRefNo.length() >= 16){
            switch (ftiProduct){
                case "ILC","ELC":
                    _ref=masterRefNo.substring(6,14)+eventCode;
                    break;
                case "FIL","FEL","FSA":
                    _ref=masterRefNo.substring(0,1)+masterRefNo.substring(7,14)+eventCode;
                    break;
                case "SHG","ETD","IGT":
                    _ref=masterRefNo.substring(2,3)+masterRefNo.substring(7,14)+eventCode;
                    break;
                case "ODC","IDC":
                    _ref="O"+masterRefNo.substring(7,14)+eventCode;
                    break;
                default:
                    _ref="CLS123123123";
                    break;
            }
        }else{
//            IFUL027655000
            _ref=masterRefNo.substring(3,10);
        }



        return _ref;
    }


}
