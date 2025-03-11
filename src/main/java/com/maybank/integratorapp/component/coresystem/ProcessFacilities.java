package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import com.maybank.integratorapp.data.entity.MsUtilizeRunningNumber;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import com.maybank.integratorapp.data.repository.MsFacilityUtilizeRepository;
import com.maybank.integratorapp.data.repository.MsUtilizeRunningNumberRepository;
import com.maybank.integratorapp.data.repository.MscompanylimitRepository;
import com.maybank.integratorapp.data.service.MsParameterService;
import com.maybank.integratorapp.model.mq.facilities.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.facilities.response.*;
import com.maybank.integratorapp.model.soap.limit.XLBT.request.AdditionalHeader;
import com.maybank.integratorapp.model.soap.limit.XLBT.request.SoapEnvelope;
import com.maybank.integratorapp.model.soap.limit.XLBT.response.LoanAccounts;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class ProcessFacilities {
    @Autowired
    MsFacilityRepository msFacilityRepository;

    @Autowired
    MsFacilityUtilizeRepository msFacilityUtilizeRepository;

    @Autowired
    MsUtilizeRunningNumberRepository msUtilizeRunningNumberRepository;

    @Autowired
    ProcessCustomerDetail customerDetail;
    @Autowired
    private MsParameterService parameterService;
    public ServiceResponse getFacilities(String cifno, Long idcompanyLimit) {

        String soapUrl = parameterService.findValueByPrmKey("XLBTRequest");
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
//        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
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

            System.out.println(xml);
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

                    if(!res.getBody().getXlbtResponse().getCmsXlbtResponse().getResponsecode().equals("00")){
                        return serviceResponseMq;

                    }

                    String asd = mapper.writeValueAsString(res);
                    System.out.println("===========================asd=================================");
                    System.out.println(asd.substring(0,100)+"...");
                    System.out.println("============================================================\n");

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

                            MsFacility msFacility = listFacilityfinal.stream().filter(z->z.getKeyDigitNote().equals(noteNumber)).findFirst().get();

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
                        });
                            msFacilityUtilizeRepository.saveAll(listFacilityUtilize);

                            // Simpan draw terakhir ke MsRunningNumber
                            saveLatestDrawNumber(listFacilityUtilize);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serviceResponseMq;
    }
    public ServiceResponse refreshFacilities(String cifno, Long idcompanyLimit) {

        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
        String correlationID = "serviceRequest.getRequestHeader().getCorrelationID();";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        SoapEnvelope soapReq = new SoapEnvelope();
        soapReq.getBody().getxLBT().getChannelHeader().setBranchCode("003");
        soapReq.getBody().getxLBT().getChannelHeader().setChannelID("BT");
        soapReq.getBody().getxLBT().getChannelHeader().setClientSupervisorID("LKE");
        soapReq.getBody().getxLBT().getChannelHeader().setClientUserID("B027950");
        soapReq.getBody().getxLBT().getChannelHeader().setReference("B001395000");
        soapReq.getBody().getxLBT().getChannelHeader().setReversalSequenceNo(correlationID);
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

            System.out.println(xml);
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

                    if(!res.getBody().getXlbtResponse().getCmsXlbtResponse().getResponsecode().equals("00")){
                        return serviceResponseMq;

                    }

                    String asd = mapper.writeValueAsString(res);
                    System.out.println("===========================asd=================================");
                    System.out.println(asd.substring(0,100)+"...");
                    System.out.println("============================================================\n");

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

                            MsFacility msFacility = existingFacility.stream().filter(z->z.getKeyDigitNote().equals(noteNumber)).findFirst().get();

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


                            if(existingUtilized.stream().filter(x->x.getKeyLoanAcc().equals(utilize.getKeyLoanAcc())).findAny().isEmpty()){
                                listFacilityUtilize.add(utilize);
                            }
                        });
                        msFacilityUtilizeRepository.saveAll(listFacilityUtilize);
                        existingUtilized.addAll(listFacilityUtilize);
                        
                        // Simpan draw terakhir ke MsRunningNumber
                        updateLatestDrawNumber(existingUtilized);

                        System.out.println("Successfully Refreshing Limit : "+cifno);


                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serviceResponseMq;
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
        highestByGroup.forEach((id, facility) ->
                System.out.println("Facility ID: " + id + ", Highest KeyLoanAcc: " + facility.getKeyLoanAcc()));

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
                runningNumber.setRunningNumber(Integer.parseInt(latestDraw.getKeyLoanAcc().substring(26, 29)));
//                runningNumber.setCompanyLimitId(latestDraw.getCompanyLimitId());

//                runningNumberList.add(runningNumber);
                msUtilizeRunningNumberRepository.save(runningNumber);
            }
        });

//        msUtilizeRunningNumberRepository.saveAll(runningNumberList);
    }
}



//                    responseHeaderMq.setCorrelationID("serviceRequest.getRequestHeader().getCorrelationID()");
//                    responseHeaderMq.setService("LIMIT");
//                    responseHeaderMq.setOperation("FACILITIES");
//                    responseHeaderMq.setStatus("SUCCEEDED");
//
//                    detailsResponseMq.setInfo(String.valueOf(response.getStatusLine().getStatusCode()));
//                    responseHeaderMq.setDetails(detailsResponseMq);
//
//                    int total = res.getBody().getXlbtResponse().getCmsXlbtResponse()
//                            .getLoanAccounts().size();
//                    List<FacilityDetails> facilityDetailsList = new ArrayList<>(total);
//                    for (int i = 0; i < total; i++) {
//                        FacilityDetails details = new FacilityDetails();
//
//                        details.setDescription(res.getBody().getXlbtResponse().getCmsXlbtResponse().
//                                getLoanAccounts().get(i).getDescription());
//                        details.setStatus(res.getBody().getXlbtResponse().getCmsXlbtResponse().
//                                getLoanAccounts().get(i).getStatus());
//                        details.setCurrency(res.getBody().getXlbtResponse().getCmsXlbtResponse().
//                                getLoanAccounts().get(i).getLoancurrencycode());
//                        details.setExtraDataKey(res.getBody().getXlbtResponse().getCmsXlbtResponse().
//                                getLoanAccounts().get(i).getKey());
//                        details.setStartDate(res.getBody().getXlbtResponse().getCmsXlbtResponse().
//                                getLoanAccounts().get(i).getNotedate());
//                        details.setExpiryDate(res.getBody().getXlbtResponse().getCmsXlbtResponse().
//                                getLoanAccounts().get(i).getMaturitydate());
//
//                        facilityDetailsList.add(details);
//                    }
//                    FacilityDetailss facilityDetailssHead = new FacilityDetailss();
//                    facilityDetailssHead.setFacilityDetails(facilityDetailsList);
//
//                    FacilityResponseExtraDetails responseExtraDetails = new FacilityResponseExtraDetails();
//                    responseExtraDetails.setExtraDataKey("ExtraDataKey");
//                    responseExtraDetails.setFieldName("FieldName");
//                    responseExtraDetails.setFieldValue("FieldValue");
//
//                    FacilityResponseExtraDetailss responseExtraDetailssHead = new FacilityResponseExtraDetailss();
//                    responseExtraDetailssHead.setFacilityResponseExtraDetails(responseExtraDetails);
//
//                    facilitiesResponseMq.setFacilityDetailss(facilityDetailssHead);
//                    facilitiesResponseMq.setFacilityResponseExtraDetailss(responseExtraDetailssHead);
//
//                    serviceResponseMq.setResponseHeader(responseHeaderMq);
//                    serviceResponseMq.setFacilitiesResponse(facilitiesResponseMq);
//                }
//
//                XmlMapper xmlMapper = new XmlMapper();
//                String responseXml = xmlMapper.writeValueAsString(serviceResponseMq);
//                System.out.println(responseXml);
//
//
//
//            } catch (ClientProtocolException e) {
//                System.out.println(e);
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        return serviceResponseMq;

