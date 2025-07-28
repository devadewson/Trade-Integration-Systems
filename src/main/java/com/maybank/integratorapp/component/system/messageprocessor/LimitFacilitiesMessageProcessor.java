package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.*;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.mq.facilities.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.facilities.response.*;
import com.maybank.integratorapp.model.soap.limit.XLBT.request.SoapEnvelope;
import com.maybank.integratorapp.model.soap.limit.XLBT.response.LoanAccounts;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class LimitFacilitiesMessageProcessor {
    private static Logger log = LoggerFactory.getLogger(LimitFacilitiesMessageProcessor.class);
    @Autowired
    LogInterfaceProcessService logger;
    @Autowired
    MsParameterService parameterService;
    @Autowired
    MsCurrencyRepository msCurrencyRepository;
    @Autowired
    MsCompanyLimitRepository mscompanylimitRepository;
    @Autowired
    MsFacilityRepository msFacilityRepository;

    @Autowired
    MsCompanyLimitService msCompanyLimitService;

    @Autowired
    MsBranchService msBranchService;

    @Autowired
    MsFacilityUtilizeRepository msFacilityUtilizeRepository;

    @Autowired
    MsUtilizeRunningNumberRepository msUtilizeRunningNumberRepository;
    @Autowired
    MsCurrencyService msCurrencyService;

    ServiceResponse response = new ServiceResponse();

    private final String ProcessName = "LimitFacilitiesProcess";

    private long LoggerId;
    public void setLoggerId(long _loggerId){
        this.LoggerId= _loggerId;
    }
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
                String cifno = request.getFacilitiesRequest().getFacilityRequestDetails().getCustomer().trim();
                String branch = request.getFacilitiesRequest().getFacilityRequestDetails().getBranch().trim();
                String islamicFlag = request.getFacilitiesRequest().getFacilityRequestDetails().getProductSubType().trim().equals("ISL")?"Y":"N";
                String currency = request.getFacilitiesRequest().getFacilityRequestDetails().getPostingAmount().getCurrency();
                //                String cifno = "0002794045";

                // step 3.
                SoapEnvelope msgRequest = mapCoreSystemRequest(cifno,branch);

                MsCompanyLimit companyLimit;

                // Cek apakah cifno ada di MsCompanyLimit
                if (!mscompanylimitRepository.existsByCifno(cifno)) {
                    // Jika CIF tidak ada, insert data ke tabel MsCompanyLimit
                    MsCompanyLimit newLimit = new MsCompanyLimit();
                    // set nilai CIF
                    newLimit.setCifno(cifno);
                    if(branch.startsWith("7"))
                        newLimit.setIbranch(branch);
                    else
                        newLimit.setCbranch(branch);
                    MsCompanyLimit savedcompanyLimit = mscompanylimitRepository.save(newLimit);
                    companyLimit = savedcompanyLimit;

                    // step 4.
                    ServiceResponse msgResponse = getMsgBodyResponse(msgRequest, savedcompanyLimit.getId());

                }else{
                    MsCompanyLimit _companyLimit = mscompanylimitRepository.findByCifno(cifno);

                    if(branch.startsWith("7"))
                        _companyLimit.setIbranch(branch);
                    else
                        _companyLimit.setCbranch(branch);

                    companyLimit = _companyLimit;
                    MsCompanyLimit savedcompanyLimit = mscompanylimitRepository.save(_companyLimit);
                }

                this.refreshFacilities(cifno,companyLimit.getId());

                // Ambil data pada database MsFacility
                List<MsFacility> facilities = msFacilityRepository.findByCompanyLimitId(
                        mscompanylimitRepository.findByCifno(cifno).getId());

//                // filter islamic facilities
//                if(islamicFlag.equals("Y"))
//                    facilities = facilities.stream().filter(x->x.getNoteType().startsWith("7")).toList();
//                else
//                    facilities = facilities.stream().filter(x->!x.getNoteType().startsWith("7")).toList();
                // filter branch facilities
                facilities = facilities.stream().filter(x->x.getBranchCode().equals(branch)).toList();

                // filter currency
                facilities = facilities.stream().filter(x->x.getLoanCurrencyCode().equals(currency)).toList();

                // step 5.
                mapExternalResponse(facilities,cifno);

            }

            // step 6.
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
    public SoapEnvelope mapCoreSystemRequest(String cifno,String branch) {

        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
        String correlationID = "FTI";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
//        MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(cifno);
        MsBranch _branch = msBranchService.getByBranchCode(branch);

        String clientUserId = "7755";
        String clientSpvUserId = "7766";

        if(_branch!=null){
            clientUserId = _branch.getUserId();
            clientSpvUserId = _branch.getSpvUserId();
        }
//        String cifno = externalRequest.getFacilitiesRequest().getFacilityRequestDetails().getCustomer();
        // Buat request CustomerInformation
        SoapEnvelope soapReq = new SoapEnvelope();
        soapReq.getBody().getxLBT().getChannelHeader().setBranchCode(branch);
        soapReq.getBody().getxLBT().getChannelHeader().setChannelID(clsChannelId);
        soapReq.getBody().getxLBT().getChannelHeader().setClientSupervisorID(clientSpvUserId);
        soapReq.getBody().getxLBT().getChannelHeader().setClientUserID(clientUserId);
        soapReq.getBody().getxLBT().getChannelHeader().setReference("FTI");
        soapReq.getBody().getxLBT().getChannelHeader().setTransactionDate(date);
        soapReq.getBody().getxLBT().getChannelHeader().setTransactionTime(time);

        soapReq.getBody().getxLBT().getcMS_XLBTRequest().setCifno(cifno);
        soapReq.getBody().getxLBT().getcMS_XLBTRequest().setAid("XLBT");

        return soapReq;
    }

    // step 4. Request data from core system
    public ServiceResponse getMsgBodyResponse(SoapEnvelope soapReq,Long idcompanyLimit) {
        ServiceResponse serviceResponseMq = new ServiceResponse();
        String soapUrl = parameterService.findValueByPrmKey("XLBTRequest");
        try {
            XmlMapper mapper = new XmlMapper();

            mapper.setDefaultUseWrapper(false); // Avoid unnecessary wrapping
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xml = null;
            com.maybank.integratorapp.model.soap.
                    limit.XLBT.response.SoapEnvelope res = new com.maybank.integratorapp.model.
                    soap.limit.XLBT.response.SoapEnvelope();
            try {
                xml = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReq);

//                log.info(xml);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            logger.Log(this.LoggerId,ProcessName, "ESB-REQUEST ","DEBUG", xml);

            ResponseHeader responseHeaderMq = new ResponseHeader();
            FacilitiesResponse facilitiesResponseMq = new FacilitiesResponse();
            Details detailsResponseMq = new Details();

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xml, ContentType.TEXT_XML));
                String _response = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        // Handle response if needed
                        var _res = response.getEntity();
                        var _resStream = _res.getContent();

                        var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);
                        logger.Log(this.LoggerId,ProcessName, "ESB-RESPONSE ","DEBUG", outputResponse);

                        _response = outputResponse;
                        res = mapper.readValue(_response, com.maybank.integratorapp.model.soap.
                                limit.XLBT.response.SoapEnvelope.class);

                        if(!res.getBody().getXlbtResponse().getCmsXlbtResponse().getResponsecode().equals("00")){
                            return serviceResponseMq;

                        }

                        // Proses dan pecah key
                        List<LoanAccounts> loanAccountsList = res.getBody().getXlbtResponse().getCmsXlbtResponse().getLoanAccounts();
                        if (loanAccountsList != null) {

                            List<MsFacility> listFacility = new ArrayList<>();
                            List<MsFacilityUtilize> listFacilityUtilize = new ArrayList<>();

                            List<LoanAccounts> loanAccountsList99 = loanAccountsList.stream().filter(x->splitKey(x.getKey())[5].equals("999")).toList();
                            //List<LoanAccounts> loanAccountsListUtilize = loanAccountsList.stream().filter(x->!splitKey(x.getKey())[5].equals("999")).toList();
                            loanAccountsList99.forEach(s->{
                                String[] splitKey = splitKey(s.getKey());
                                String companyLimitValue = splitKey[3];
                                String draw = splitKey[5];


                                MsFacility facility = new MsFacility();

                                //Add To database FacilityUtilize
                                facility.setCompanyLimitId(idcompanyLimit);

                                facility.setKeyDigitNote(splitKey[4]);
                                facility.setCurrency(splitKey[1]);
                                facility.setBranchCode(splitKey[2]);
                                facility.setCifNo(splitKey[3]);
                                facility.setCommitmentBalance(s.getCommitmentbalance());
                                facility.setCommitmentBalanceSign(s.getCommitmentbalancesign());
                                facility.setDescription(s.getDescription());
                                facility.setKeyLoanAcc(s.getKey());
                                facility.setLoanCurrencyCode(s.getLoancurrencycode());
                                facility.setMaturityDate(s.getMaturitydate());
                                facility.setNoteDate(s.getNotedate());
                                facility.setNoteType(s.getNotetype());
                                facility.setPrincipalBalance(s.getPrincipalbalance());
                                facility.setPrincipalBalanceSign(s.getPrincipalbalancesign());
                                facility.setStatus(s.getStatus());

                                listFacility.add(facility);
                            });
                            List <MsFacility> listFacilityfinal = (List<MsFacility>) msFacilityRepository.saveAll(listFacility);

                            //Add To database FacilityUtilize
                            List<LoanAccounts> loanAccountsListUtilize = loanAccountsList.stream().filter(x->!splitKey(x.getKey())[5].equals("999")).toList();
                            loanAccountsListUtilize.forEach(s->{
                                String[] splitKey = splitKey(s.getKey());
                                String companyLimitValue = splitKey[3];
                                String noteNumber = splitKey[4];
                                String draw = splitKey[5];

                                MsFacilityUtilize utilize = new MsFacilityUtilize();


                                // Mencari MsCompanyLimit berdasarkan cifno

                                MsFacility msFacility = listFacilityfinal.stream().filter(z->z.getKeyDigitNote().equals(noteNumber)).findFirst().orElse(null);

                                if(msFacility != null){
                                    utilize.setFacilityId(msFacility.getId());
                                    utilize.setKeyDigitNote(splitKey[4]);
                                    utilize.setCurrency(splitKey[1]);
                                    utilize.setBranchCode(splitKey[2]);
                                    utilize.setCifNo(splitKey[3]);
                                    utilize.setCompanyLimitId(idcompanyLimit);
                                    utilize.setCommitmentBalance(s.getCommitmentbalance());
                                    utilize.setCommitmentBalanceSign(s.getCommitmentbalancesign());
                                    utilize.setDescription(s.getDescription());
                                    utilize.setKeyLoanAcc(s.getKey());
                                    utilize.setLoanCurrencyCode(s.getLoancurrencycode());
                                    utilize.setMaturityDate(s.getMaturitydate());
                                    utilize.setNoteDate(s.getNotedate());
                                    utilize.setNoteType(s.getNotetype());
                                    utilize.setPrincipalBalance(s.getPrincipalbalance());
                                    utilize.setPrincipalBalanceSign(s.getPrincipalbalancesign());
                                    utilize.setStatus(s.getStatus());

                                    listFacilityUtilize.add(utilize);
                                }

                            });
                            List<MsFacilityUtilize> _listFacilityUtilize = (List<MsFacilityUtilize>) msFacilityUtilizeRepository.saveAll(listFacilityUtilize);

                            // Simpan draw terakhir ke MsRunningNumber
                            saveLatestDrawNumber(_listFacilityUtilize);

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.Log(this.LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return serviceResponseMq;
    }

    public void refreshFacilities (String cifno,Long idcompanyLimit){

//        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
        String soapUrl = parameterService.findValueByPrmKey("XLBTRequest");
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
        String correlationID = "serviceRequest.getRequestHeader().getCorrelationID();";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        SoapEnvelope soapReq = new SoapEnvelope();
        soapReq.getBody().getxLBT().getChannelHeader().setBranchCode("003");
        soapReq.getBody().getxLBT().getChannelHeader().setChannelID(clsChannelId);
        soapReq.getBody().getxLBT().getChannelHeader().setClientSupervisorID("7766");
        soapReq.getBody().getxLBT().getChannelHeader().setClientUserID("7755");
        soapReq.getBody().getxLBT().getChannelHeader().setReference("FTI");
        soapReq.getBody().getxLBT().getChannelHeader().setTransactionDate(date);
        soapReq.getBody().getxLBT().getChannelHeader().setTransactionTime(time);

        soapReq.getBody().getxLBT().getcMS_XLBTRequest().setCifno(cifno);
        soapReq.getBody().getxLBT().getcMS_XLBTRequest().setAid("XLBT");

        XmlMapper mapper = new XmlMapper();

        mapper.setDefaultUseWrapper(false); // Avoid unnecessary wrapping
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

        String xml = null;
        com.maybank.integratorapp.model.soap.
                limit.XLBT.response.SoapEnvelope res = new com.maybank.integratorapp.model.
                soap.limit.XLBT.response.SoapEnvelope();
        try {
            xml = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReq);

//            log.info(xml);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ServiceResponse serviceResponseMq = new ServiceResponse();
        ResponseHeader responseHeaderMq = new ResponseHeader();
        FacilitiesResponse facilitiesResponseMq = new FacilitiesResponse();
        Details detailsResponseMq = new Details();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(soapUrl);
            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setEntity(new StringEntity(xml, ContentType.TEXT_XML));
            String _response = "";

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();

                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);

                    _response = outputResponse;
                    res = mapper.readValue(_response, com.maybank.integratorapp.model.soap.
                            limit.XLBT.response.SoapEnvelope.class);

                    String asd = mapper.writeValueAsString(res);
//                    log.info("===========================XLBT=================================");
//                    log.info(asd.substring(0,100)+"...");
//                    log.info("============================================================\n");

                    // Proses dan pecah key
                    List<LoanAccounts> loanAccountsList = res.getBody().getXlbtResponse().getCmsXlbtResponse().getLoanAccounts();
                    if (loanAccountsList != null) {

                        List<MsFacility> listFacility = new ArrayList<>();
                        List<MsFacilityUtilize> listFacilityUtilize = new ArrayList<>();

                        List<LoanAccounts> loanAccountsList99 = loanAccountsList.stream().filter(x->splitKey(x.getKey())[5].equals("999")).toList();
                        List<MsFacility> existingFacility = msFacilityRepository.findAllFacilitiesByCif(cifno);
                        //List<LoanAccounts> loanAccountsListUtilize = loanAccountsList.stream().filter(x->!splitKey(x.getKey())[5].equals("999")).toList();
                        loanAccountsList99.forEach(s->{
                            String[] splitKey = splitKey(s.getKey());
                            String companyLimitValue = splitKey[3];
                            String draw = splitKey[5];


                            MsFacility facility = new MsFacility();

                            //Add To database FacilityUtilize
                            facility.setCompanyLimitId(idcompanyLimit);

                            facility.setKeyDigitNote(splitKey[4]);
                            facility.setCurrency(splitKey[1]);
                            facility.setBranchCode(splitKey[2]);
                            facility.setCifNo(splitKey[3]);
                            facility.setCommitmentBalance(s.getCommitmentbalance());
                            facility.setCommitmentBalanceSign(s.getCommitmentbalancesign());
                            facility.setDescription(s.getDescription());
                            facility.setKeyLoanAcc(s.getKey());
                            facility.setLoanCurrencyCode(s.getLoancurrencycode());
                            facility.setMaturityDate(s.getMaturitydate());
                            facility.setNoteDate(s.getNotedate());
                            facility.setNoteType(s.getNotetype());
                            facility.setPrincipalBalance(s.getPrincipalbalance());
                            facility.setPrincipalBalanceSign(s.getPrincipalbalancesign());
                            facility.setStatus(s.getStatus());
                            if(existingFacility.stream().filter(x->x.getKeyLoanAcc().equals(facility.getKeyLoanAcc())).findAny().isEmpty()){
                                listFacility.add(facility);
                            }else{
                                MsFacility _existing = existingFacility.stream().filter(x->x.getKeyLoanAcc().equals(facility.getKeyLoanAcc())).findAny().get();
                                _existing.setKeyDigitNote(splitKey[4]);
                                _existing.setCurrency(splitKey[1]);
                                _existing.setBranchCode(splitKey[2]);
                                _existing.setCifNo(splitKey[3]);
                                _existing.setCommitmentBalance(facility.getCommitmentBalance());
                                _existing.setCommitmentBalanceSign(facility.getCommitmentBalanceSign());
                                _existing.setDescription(facility.getDescription());
                                _existing.setLoanCurrencyCode(facility.getLoanCurrencyCode());
                                _existing.setMaturityDate(facility.getMaturityDate());
                                _existing.setNoteDate(facility.getNoteDate());
                                _existing.setNoteType(facility.getNoteType());
                                _existing.setPrincipalBalance(facility.getPrincipalBalance());
                                _existing.setPrincipalBalanceSign(facility.getPrincipalBalanceSign());
                                _existing.setStatus(facility.getStatus());
                                _existing =msFacilityRepository.save(_existing);

                            }

                        });
                        List <MsFacility> listFacilityfinal = (List<MsFacility>) msFacilityRepository.saveAll(listFacility);
                        existingFacility.addAll(listFacilityfinal);

//                        List<String> existingUtilized = msFacilityUtilizeRepository.findAllKeyLoanAcc();
                        List<MsFacilityUtilize> existingUtilized = msFacilityUtilizeRepository.findAllFacilityUtilizeByCif(cifno);
                        //Add To database FacilityUtilize
                        List<LoanAccounts> loanAccountsListUtilize = loanAccountsList.stream().filter(x->!splitKey(x.getKey())[5].equals("999")).toList();
                        loanAccountsListUtilize.forEach(s->{
                            String[] splitKey = splitKey(s.getKey());
                            String companyLimitValue = splitKey[3];
                            String noteNumber = splitKey[4];
                            String draw = splitKey[5];

                            MsFacilityUtilize utilize = new MsFacilityUtilize();


                            // Mencari MsCompanyLimit berdasarkan cifno

                            MsFacility msFacility = existingFacility.stream().filter(z->z.getKeyDigitNote().equals(noteNumber)).findFirst().orElse(null);

                            if(msFacility != null) {
                                utilize.setFacilityId(msFacility.getId());
                                utilize.setKeyDigitNote(splitKey[4]);
                                utilize.setCurrency(splitKey[1]);
                                utilize.setBranchCode(splitKey[2]);
                                utilize.setCifNo(splitKey[3]);
                                utilize.setCompanyLimitId(idcompanyLimit);
                                utilize.setCommitmentBalance(s.getCommitmentbalance());
                                utilize.setCommitmentBalanceSign(s.getCommitmentbalancesign());
                                utilize.setDescription(s.getDescription());
                                utilize.setKeyLoanAcc(s.getKey());
                                utilize.setLoanCurrencyCode(s.getLoancurrencycode());
                                utilize.setMaturityDate(s.getMaturitydate());
                                utilize.setNoteDate(s.getNotedate());
                                utilize.setNoteType(s.getNotetype());
                                utilize.setPrincipalBalance(s.getPrincipalbalance());
                                utilize.setPrincipalBalanceSign(s.getPrincipalbalancesign());
                                utilize.setStatus(s.getStatus());


                                if (existingUtilized.stream().filter(x -> x.getKeyLoanAcc().equals(utilize.getKeyLoanAcc())).findAny().isEmpty()) {
                                    listFacilityUtilize.add(utilize);
                                }
                            }
                        });
                        msFacilityUtilizeRepository.saveAll(listFacilityUtilize);
                        existingUtilized.addAll(listFacilityUtilize);

                        // Simpan draw terakhir ke MsRunningNumber
                        updateLatestDrawNumber(existingUtilized);

                        log.info("Successfully Refreshing Limit : "+cifno);


                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

//        return serviceResponseMq;
    }
    // step 5. Map core system data to external Response
    private void mapExternalResponse(List<MsFacility> facilities,String cifno) throws JsonProcessingException {
        ResponseHeader responseHeader = response.getResponseHeader();
        XmlMapper xmlMapper = new XmlMapper();
        String responseXml;
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
                    String remainingBalance = String.valueOf((Long.parseLong(balance) - Long.parseLong(utilizedBalance)));

                    fac.setStatus(s.getNoteType());
                    fac.setDescription(s.getDescription());
                    fac.setLimitAmount(balance+"00");
                    fac.setAvailableAmount(utilizedBalance+"00");
                    fac.setMultiCurrency("N");

                    fac.setDisplayField1(s.getKeyLoanAcc());
                    fac.setDisplayField2(s.getDescription());
                    fac.setDisplayField3("-");
                    fac.setDisplayField4(s.getNoteType());
                    fac.setDisplayField5(s.getPrincipalBalance());
                    fac.setDisplayField6(s.getCommitmentBalance());
                    fac.setDisplayField7("-");
                    fac.setDisplayField8("-");
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
//                        log.info("Tidak ada data fasilitas untuk CIF " + cifno);
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
    private void saveLatestDrawNumber(List<MsFacilityUtilize> listFacilityUtilize) {
        Map<Long, List<MsFacilityUtilize>> facilityGroupedById = listFacilityUtilize.stream()
                .collect(Collectors.groupingBy(MsFacilityUtilize::getFacilityId));

        List<MsUtilizeRunningNumber> runningNumberList = new ArrayList<>();

        facilityGroupedById.forEach((facilityId, utilizes) -> {
            MsFacilityUtilize latestDraw = utilizes.stream()
                    .max(Comparator.comparing(utilize -> {
                        String draw = utilize.getKeyLoanAcc().substring(26, 29);
                        return Integer.parseInt(draw);
                    })).orElse(null);

            if (latestDraw != null) {
                MsUtilizeRunningNumber runningNumber = new MsUtilizeRunningNumber();
                runningNumber.setFacilityId(facilityId);
                runningNumber.setRunningNumber(Integer.parseInt(latestDraw.getKeyLoanAcc().substring(26, 29)));
                runningNumber.setCompanyLimitId(latestDraw.getCompanyLimitId());

                runningNumberList.add(runningNumber);
            }
        });

        msUtilizeRunningNumberRepository.saveAll(runningNumberList);
    }

    private void updateLatestDrawNumber(List<MsFacilityUtilize> listFacilityUtilize) {
        Map<Long, List<MsFacilityUtilize>> facilityGroupedById = listFacilityUtilize.stream()
                .collect(Collectors.groupingBy(MsFacilityUtilize::getFacilityId));

//        List<MsUtilizeRunningNumber> runningNumberList = new ArrayList<>();
// Group by facilityId and get the highest substring value in each group
        Map<Long, MsFacilityUtilize> highestByGroup = listFacilityUtilize.stream()
                .collect(Collectors.groupingBy(
                        MsFacilityUtilize::getFacilityId,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(m ->
                                        Integer.parseInt(m.getKeyLoanAcc().substring(26, 29)))),
                                Optional::get
                        )
                ));

        // Print result
//        highestByGroup.forEach((id, facility) ->
//                logger.Log(this.LoggerId,ProcessName, "Facility ID: " + id + ", Highest KeyLoanAcc: " + facility.getKeyLoanAcc(), "DEBUG"));

        facilityGroupedById.forEach((facilityId, utilizes) -> {
            MsFacilityUtilize latestDraw = utilizes.stream()
                    .max(Comparator.comparing(utilize -> {
                        String draw = utilize.getKeyLoanAcc().substring(26, 29);
                        return Integer.parseInt(draw);
                    })).orElse(null);

            if (latestDraw != null) {
                MsUtilizeRunningNumber runningNumber = msUtilizeRunningNumberRepository.findByFacilityId(facilityId);
                if(runningNumber == null){
                    runningNumber = new MsUtilizeRunningNumber();
                    runningNumber.setFacilityId(facilityId);
                    runningNumber.setCompanyLimitId(latestDraw.getCompanyLimitId());
                }
//                runningNumber.setFacilityId(facilityId);
                int newRunningNumber = Integer.parseInt(latestDraw.getKeyLoanAcc().substring(26, 29));
                if(newRunningNumber != runningNumber.getRunningNumber()){
                    logger.Log(this.LoggerId,ProcessName,
                            "Facility ID: " + runningNumber.getFacilityId()
                                    + ", Running Number : " + runningNumber.getRunningNumber()
                            +" to "+newRunningNumber, "DEBUG");
                    runningNumber.setRunningNumber(newRunningNumber);
                    msUtilizeRunningNumberRepository.save(runningNumber);

                }
//                runningNumber.setRunningNumber();
//                runningNumber.setCompanyLimitId(latestDraw.getCompanyLimitId());

//                runningNumberList.add(runningNumber);

            }
        });

//        msUtilizeRunningNumberRepository.saveAll(runningNumberList);
    }

}
