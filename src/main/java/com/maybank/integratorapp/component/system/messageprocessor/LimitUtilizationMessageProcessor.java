package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import com.maybank.integratorapp.data.entity.MsBranch;
import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.mq.limitutilization.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.limitutilization.request.ServiceRequestChild;

import com.maybank.integratorapp.model.soap.limit.XL41.request.SoapEnvelope;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LimitUtilizationMessageProcessor {
    private static Logger log = LoggerFactory.getLogger(LimitUtilizationMessageProcessor.class);
    @Autowired
    LogInterfaceProcessService logger;
    @Autowired
    MsParameterRepository parameterRepository;
    @Autowired
    private MsParameterService parameterService;
    @Autowired
    private FtiTransactionDetailService ftiTransactionDetailService;
    @Autowired
    MsCurrencyService msCurrencyService;
    @Autowired
    FtiTransactionService ftiTransactionService;
//    ServiceResponse response = new ServiceResponse();

    private final String ProcessName = "LimitUtilizationProcess";
    @Autowired
    private MsCompanyLimitService msCompanyLimitService;
    @Autowired
    private MsBranchService msBranchService;

    @Autowired
    EmailService emailService;
    private Long LoggerId;
    private long XL2BTransId;
    private long XL31TransId;
    private FtiTransactionDetail LastLimitAction;

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
                for (ServiceRequestChild utilizationItem:
                    request.getBatchRequest().getServiceRequestChild()) {
                    String accountNo = utilizationItem.getExposure().getAccountNumber();
//                String utilizationID = utilizationItem.getExposure().getReservationIdentifier();
                    String utilizationID = utilizationItem.getExposure().getFacilityExposureIdentifier();
                    if(!utilizationID.isEmpty()){
                        String correlationID = request.getRequestHeader().getCorrelationID();
                        String masterReference = utilizationItem.getExposure().getMasterReference();
                        String eventCode = utilizationItem.getExposure().getEventReference();

                        FtiTransaction _header = new FtiTransaction();
                        List<FtiTransactionDetail> _listTransactionDetail = new ArrayList<>();
                        if(ftiTransactionService.findByMasterRefNo(masterReference).isPresent()){
                            _header = ftiTransactionService.findByMasterRefNo(masterReference).get();
                            _listTransactionDetail = ftiTransactionDetailService.getDetailsByHeaderId(_header.getId());
                            _listTransactionDetail = _listTransactionDetail.stream().filter(x->
                                            x.getCoreSysName().startsWith("CLS")
                                                    && x.getFtiEvent().equals(eventCode)
                                                    && x.getAdditionalInfo1().equals(utilizationID)
                                    )
                                    .sorted(Comparator.comparingLong(FtiTransactionDetail::getId))
                                    .collect(Collectors.toList());
                        }

                        boolean needXL40 = false;
                        boolean needXL41 = false;
                        boolean recreateTrans = false;



                        if(_listTransactionDetail.stream().count()>0){
//                    logger.Log(this.LoggerId,ProcessName, "Transaction Detail Count : "+_listTransactionDetail.stream().count(), "DEBUG");

                            LastLimitAction = _listTransactionDetail.get((int) (_listTransactionDetail.stream().count()-1));
                            // cek dulu tanggal reservasinya
                            // kalau bukan hari ini maka bikin ulang
                            LocalDateTime today = LocalDateTime.now();
                            LocalDateTime inputDate = LocalDateTime.ofInstant(
                                    LastLimitAction.getCreatedDate().toInstant(), ZoneId.systemDefault());

                            if(!(inputDate.getYear() == today.getYear() &&
                                    inputDate.getMonth() == today.getMonth() &&
                                    inputDate.getDayOfMonth() == today.getDayOfMonth())){

                                // bikin ulang last action nya
                                // karena di cls sudah ilang
                                recreateTrans = true;
                                logger.Log(this.LoggerId,ProcessName, "Recreate Trans", "DEBUG");

                            }

                            if(LastLimitAction.getCoreSysName().contains("XL2B")){
                                XL2BTransId = LastLimitAction.getId();
                                needXL40 =true;
                            }else if(LastLimitAction.getCoreSysName().contains("XL31")){
                                XL31TransId = LastLimitAction.getId();
                                needXL41 = true;
                                // check whether before XL31 there is XL2B
                                List<FtiTransactionDetail> _listTransaction = ftiTransactionDetailService.getDetailsByHeaderId(LastLimitAction.getHeaderId());
                                Optional<FtiTransactionDetail> _beforeLastLimitAction = _listTransaction.stream().filter(x ->
                                        x.getCoreSysName().equals("CLS-XL2B") && x.getCoreSysStatus().equals("00") && x.getFtiEvent().equals(LastLimitAction.getFtiEvent()) && x.getId()< LastLimitAction.getId()
                                ).max(Comparator.comparing(FtiTransactionDetail::getId));
//                        FtiTransactionDetail _beforeLastLimitAction = ftiTransactionDetailService.getById(_lastLimitAction.getId()-1);
                                if(_beforeLastLimitAction.isPresent()){
                                    XL2BTransId = _beforeLastLimitAction.get().getId();
                                    needXL40 = true;
                                }
                            }


                        } else {
                            LastLimitAction = null;
                        }
                        if(needXL40){
                            if(recreateTrans){
                                LastLimitAction = _listTransactionDetail.stream().filter(x->x.getId().equals(XL2BTransId)).findFirst().get();
//                        how??
                            }
                            // step 3.
                            com.maybank.integratorapp.model.soap.limit.XL40.request.SoapEnvelope msgRequest = mapXL40Request(utilizationID,masterReference);

                            // step 4.
                            com.maybank.integratorapp.model.soap.limit.XL40.response.SoapEnvelope msgResponse = getXL40Response(msgRequest,eventCode,masterReference,utilizationID);

                        }

                        if(needXL41){
                            if(recreateTrans){
                                LastLimitAction = _listTransactionDetail.stream().filter(x->x.getId().equals(XL31TransId)).findFirst().get();
//                        how??
                            }
                            // step 3.
                            com.maybank.integratorapp.model.soap.limit.XL41.request.SoapEnvelope msgRequest = mapXL41Request(utilizationID,masterReference);

                            // step 4.
                            com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope msgResponse = getXL41Response(msgRequest,eventCode,masterReference,utilizationID);

                        }
                    }
                    else{
                        logger.Log(this.LoggerId,ProcessName, "Empty FacilityExposureIdentifier", "ERROR-PROCESS-MESSAGE");

                    }

                }

                // step 5.
//                mapExternalResponse(msgResponse,msgRequest);

            }

            // step 6.
//            sendEmailNotif();
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            responseXml = xmlMapper.writeValueAsString("");

        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());

        }
        return responseXml;
    }

    private void handleExceptionResponse(String message) {
//        response.getResponseHeader().setStatus("Error");
//        response.getResponseHeader().getDetails().setError(message);
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
//        response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
//        response.getResponseHeader().setService(request.getRequestHeader().getService());
//        response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
//        response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
//        response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());
//        response.getResponseHeader().setStatus("SUCCEEDED");
    }

    // step 3. Map external request to core system request
    public SoapEnvelope mapXL41Request(String utilizationID, String correlationID) {
        String soapUrl = parameterService.findValueByPrmKey("XL41Request");
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
//        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
//        String correlationID = "ServiceRequest.getRequestHeader().getCorrelationID();";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        SoapEnvelope soapEnvelopeXL41 = new SoapEnvelope();

        String[] splittedKey = splitKey(utilizationID);
        String limitCurrency = splittedKey[1];
        String limitBranch = splittedKey[2];
        String limitCif = splittedKey[3];
        String limitNoteKey = splittedKey[4];
        String limitDraw = splittedKey[5];
        String dateNow = new SimpleDateFormat("ddMMyy").format(new Date());

        MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(limitCif);
        MsBranch _branch = msBranchService.getByBranchCode(limitBranch);

        String clientUserId = "7755";
        String clientSpvUserId = "7766";

        if(_branch!=null){
            clientUserId = _branch.getUserId();
            clientSpvUserId = _branch.getSpvUserId();
        }

        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setAdditionalHeader("");
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setBranchCode(limitBranch);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setChannelID(clsChannelId);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setClientUserID(clientUserId);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setReference(correlationID);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setTransactionDate(date);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setTransactionTime(time);

        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCtl2(limitCurrency);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCtl3(limitBranch);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCust(limitCif);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setDraw(limitDraw);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setFlag("P");
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setNote(limitNoteKey);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setPart("99");

        return soapEnvelopeXL41;
    }
    public com.maybank.integratorapp.model.soap.limit.XL40.request.SoapEnvelope mapXL40Request(String utilizationID,String correlationID) {
        String soapUrl = parameterService.findValueByPrmKey("XL41Request");
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
//        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
//        String correlationID = "ServiceRequest.getRequestHeader().getCorrelationID();";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        com.maybank.integratorapp.model.soap.limit.XL40.request.SoapEnvelope soapEnvelope = new com.maybank.integratorapp.model.soap.limit.XL40.request.SoapEnvelope();

        String[] splittedKey = splitKey(utilizationID);
        String limitCurrency = splittedKey[1];
        String limitBranch = splittedKey[2];
        String limitCif = splittedKey[3];
        String limitNoteKey = splittedKey[4];
        String limitDraw = splittedKey[5];
        String dateNow = new SimpleDateFormat("ddMMyy").format(new Date());

        MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(limitCif);
        MsBranch _branch = msBranchService.getByBranchCode(limitBranch);

        String clientUserId = "7755";
        String clientSpvUserId = "7766";

        if(_branch!=null){
            clientUserId = _branch.getUserId();
            clientSpvUserId = _branch.getSpvUserId();
            if(clientSpvUserId.equals("-"))
                clientSpvUserId = clientUserId;
        }

        soapEnvelope.getBody().getXl40().getChannelHeader().setAdditionalHeader("");
        soapEnvelope.getBody().getXl40().getChannelHeader().setBranchCode(limitBranch);
        soapEnvelope.getBody().getXl40().getChannelHeader().setChannelID(clsChannelId);
        soapEnvelope.getBody().getXl40().getChannelHeader().setClientUserID(clientSpvUserId);
        soapEnvelope.getBody().getXl40().getChannelHeader().setReference(correlationID);
        soapEnvelope.getBody().getXl40().getChannelHeader().setTransactionDate(date);
        soapEnvelope.getBody().getXl40().getChannelHeader().setTransactionTime(time);

        soapEnvelope.getBody().getXl40().getCmsXl40Request().setLoanNumber(buildXL40Key(utilizationID));
        soapEnvelope.getBody().getXl40().getCmsXl40Request().setScreenid("XL2B");
        return soapEnvelope;
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
    private String buildXL40Key(String key) {
        String bank = key.substring(0, 2);
        String currency = key.substring(2, 5);
        String branchCode = key.substring(5, 8);
        String cif = key.substring(8, 18);
        String note = key.substring(18, 26);
        String draw = key.substring(26, 29);
        String seq = key.substring(29, 31);

        return branchCode+"-"+cif+"."+note
                +" "+draw+"-"+seq;
    }
    // step 4. Request data from core system
    public com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope getXL41Response(SoapEnvelope soapEnvelopeXL41, String eventCode, String referenceId, String reservationId) {
        com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope res = new com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope();
        String soapUrl = parameterService.findValueByPrmKey("XL41Request");

        String dcType = soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().getFlag();
        String amount = "-";
        String noteNumber = reservationId;

        try {
            XmlMapper mapper = new XmlMapper();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlString = null;
            try {
                xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapEnvelopeXL41);

//                log.info(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(this.LoggerId,ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

            com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope cmsResponseXL41 = null;

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

                    cmsResponseXL41 = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope.class);
                    res = cmsResponseXL41;
                    String xmlResponseXL41 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cmsResponseXL41);
//
                    String responseCode = cmsResponseXL41
                            .getBody().getXl41Response().
                            getCmsXl41Response().getResponsecode();
                    String responseMessage = cmsResponseXL41
                            .getBody().getXl41Response().
                            getCmsXl41Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    String responseMessage2 = cmsResponseXL41
                            .getBody().getXl41Response().
                            getCmsXl41Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("status1")).findFirst().get().getValue();

                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(LoggerId);
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL41");
                    ftiTransactionDetail.setTransName("Utilization");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage2);
                    ftiTransactionDetail.setAdditionalInfo1(noteNumber);
                    ftiTransactionDetail.setAdditionalInfo2(dcType);
                    ftiTransactionDetail.setAdditionalInfo3(amount);
                    ftiTransactionDetail.setAdditionalInfo4("REL");
                    ftiTransactionDetail.setReqMessage(xmlString);
                    ftiTransactionDetail.setResMessage(xmlResponseXL41);
                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);


                }
            } catch (Exception e) {
                logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }
        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return res;
    }
    public com.maybank.integratorapp.model.soap.limit.XL40.response.SoapEnvelope getXL40Response(com.maybank.integratorapp.model.soap.limit.XL40.request.SoapEnvelope soapEnvelopeXL40, String eventCode, String referenceId, String reservationId) {
        com.maybank.integratorapp.model.soap.limit.XL40.response.SoapEnvelope res = new com.maybank.integratorapp.model.soap.limit.XL40.response.SoapEnvelope();
        String soapUrl = parameterService.findValueByPrmKey("XL41Request");

        String dcType = "-";
        String amount = "-";
        String noteNumber = reservationId;

        try {
            XmlMapper mapper = new XmlMapper();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlString = null;
            try {
                xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapEnvelopeXL40);

//                log.info(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(this.LoggerId,ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

            com.maybank.integratorapp.model.soap.limit.XL40.response.SoapEnvelope cmsResponseXL40 = null;

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

                    cmsResponseXL40 = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL40.response.SoapEnvelope.class);
                    res = cmsResponseXL40;
                    String xmlResponseXL40 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cmsResponseXL40);
//
                    String responseCode = cmsResponseXL40
                            .getBody().getXl40Response().
                            getCmsXl40Response().getResponsecode();
                    String responseMessage = cmsResponseXL40
                            .getBody().getXl40Response().
                            getCmsXl40Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    String responseMessage2 = cmsResponseXL40
                            .getBody().getXl40Response().
                            getCmsXl40Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message_1")).findFirst().get().getValue();
                    if(responseMessage.contains("exception") && responseCode == null)
                        responseCode= "99";
                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(LoggerId);
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL40");
                    ftiTransactionDetail.setTransName("Utilization");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage);
                    ftiTransactionDetail.setAdditionalInfo1(noteNumber);
                    ftiTransactionDetail.setAdditionalInfo2(dcType);
                    ftiTransactionDetail.setAdditionalInfo3(amount);
                    ftiTransactionDetail.setAdditionalInfo4("REL");
                    ftiTransactionDetail.setReqMessage(xmlString);
                    ftiTransactionDetail.setResMessage(xmlResponseXL40);
                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                }
            } catch (Exception e) {
                logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }
        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return res;
    }
    private void sendEmailNotif(){
        List<FtiTransactionDetail> _transDetail = ftiTransactionDetailService.getDetailsByTransMessageLogId(LoggerId);
        FtiTransaction _transaction = ftiTransactionService.getFtiTransactionById(_transDetail.get(0).getHeaderId());

        emailService.sendTransactionNotification(_transaction,_transDetail);
    }
    // step 5. Map core system data to external Response
//    private void mapExternalResponse(com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper res,MsgWraper req) {
//        AccountInquiryResponse accountInquiryResponse = new AccountInquiryResponse();
//
////            Details detailsResponse = new Details();
////            detailsResponse.setInfo(res.getMsg().getMsgHeader().getStatusDesc());
//
//        AvailBalResponse availBalResponse = new AvailBalResponse();
//
//        String balance = res.getMsg().getMsgBody().getAccountInformationResponseData().getAccountData().getcADataRecord().getAvailableBalance();
////        String formattedBalance = balance.substring(1).replace(".", "");
//        String formattedBalance = balance.substring(1);
//        String holdCode = res.getMsg().getMsgBody().getAccountInformationResponseData().getAccountStatus();
//        String cifNo = res.getMsg().getMsgBody().getAccountInformationResponseData().getCifNo();
//        String accountName = res.getMsg().getMsgBody().getAccountInformationResponseData().getAccountName();
//        if(accountName.length()>75){
//            accountName= accountName.substring(0,75);
//        }
//        String infoMessage = "#CIF:"+cifNo+" NAME:"+accountName;
////        String infoMessage = "";
//
//        formattedBalance = String.format("%015.2f",Double.parseDouble(formattedBalance));
//
//        if (balance.startsWith("+"))
//            availBalResponse.setNegative("N");
//        else
//            availBalResponse.setNegative("Y");
//        availBalResponse.setBlocked("N");
//        availBalResponse.setApplies("Y");
//        availBalResponse.setErrorOrWarning("N");
//        availBalResponse.setCheckedInBackOffice("Y");
//        availBalResponse.setErrorCode("N");
//        availBalResponse.setErrorMessage("HOLDCODE-" + holdCode+infoMessage);
//        availBalResponse.setBalance(formattedBalance);
//
//        response.setAvailBalResponse(availBalResponse);
//    }
}
