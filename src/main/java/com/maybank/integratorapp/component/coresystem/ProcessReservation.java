package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.MsCurrency;
import com.maybank.integratorapp.data.entity.MsFacility;
import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import com.maybank.integratorapp.data.entity.MsMapClsProductType;
import com.maybank.integratorapp.data.repository.MsCurrencyRepository;
import com.maybank.integratorapp.data.repository.MsMapClsProductTypeRepository;
import com.maybank.integratorapp.model.mq.reservation.response.ReservationResponseDetails;
import com.maybank.integratorapp.model.mq.reservation.response.ReservationResponseDetailss;
import com.maybank.integratorapp.model.mq.reservation.response.ReservationsResponse;
import com.maybank.integratorapp.model.soap.XL01.request.SoapEnvelope;
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
import java.util.Date;
import java.util.List;

@Component
public class ProcessReservation {
    @Autowired
    ProcessFacilities processFacilities;
    @Autowired
    private MsCurrencyRepository msCurrencyRepository;
    @Autowired
    MsMapClsProductTypeRepository msMapClsProductTypeRepository;
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
    public String getReservation(String referenceId, String newKeyloanAcc, String acctReqXL01, String keyLoanAcc, String customerRes, String transDate, String startdateRes, String expireDateRes
            , String exposureAmmount, String currency, String limitAmount, String reservedAmount, String productType, String lineOfBusiness, String eventCode, MsFacility facility,String FTIProductCode) {

        try{
            String soapUrl = "http://10.230.83.57:65085/services/CMSService";
            SoapEnvelope soapReqXL01 = new SoapEnvelope();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

            String[] splittedKey = splitKey(newKeyloanAcc);
            String limitCurrency = splittedKey[1];
            String limitBranch = splittedKey[2];
            String limitCif = splittedKey[3];
            String limitNoteKey = splittedKey[4];
            String limitDraw = splittedKey[5];
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("ddMMyy");

            LocalDate _startDate = LocalDate.parse(startdateRes, inputFormatter);
//            String startDate = _startDate.format(outputFormatter);
            String startDate = "291024";

            LocalDate _expiryDate = LocalDate.parse(expireDateRes, inputFormatter);
            String expiryDate = _expiryDate.format(outputFormatter);
//            String expiryDate = "291224";

            LocalDate _transDate = LocalDate.parse(transDate, inputFormatter);
//            String transactionDate = _transDate.format(outputFormatter);
            String transactionDate = "291024";

            String dateNow = new SimpleDateFormat("ddMMyy").format(new Date());

            // treat amend as issue for mapping purpose
            if(eventCode.equals("AMD"))
                eventCode = eventCode.replace("AMD","ISS");
            
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
                    if(eventCode.equals("ISS"))
                        cls001ProductType= productTypeList.stream().filter(x->
                                x.getEventCode().equals("ISS") &&
                                x.getLiabilityCode().equals("IGT"))
                            .findFirst().get().getProductType001();
                }

            }else{
                cls001ProductType = msMapClsProductTypeRepository.findDraw001Product(productType, lineOfBusiness,eventCode);

            }
            System.out.println("CLS Product Type : " + cls001ProductType);
            List<MsCurrency> currencies = (List<MsCurrency>) msCurrencyRepository.findAll();
            String ISOcurrency = currencies.stream().filter(x->x.getInternalCode().equals(limitCurrency)).findFirst().get().getIsoCode();

            // Request To CLS XL01DRAW001
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setAdditionalHeader("");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setBranchCode("003");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setChannelID("BT");
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
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCurrency("IDR");
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

            XmlMapper mapper = new XmlMapper();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlXL01 = null;
            try {
                xmlXL01 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReqXL01);

                System.out.println(xmlXL01);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            com.maybank.integratorapp.model.soap.XL01.responseComplete.SoapEnvelope cmsResponseXL01 = null;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xmlXL01, ContentType.TEXT_XML));
                String _response = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();
                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);
                    _response = outputResponse;
                    cmsResponseXL01 = mapper.readValue(_response, com.maybank.integratorapp.model.soap.XL01.responseComplete.SoapEnvelope.class);

                    String xmlResponseXL01 = mapper.writeValueAsString(cmsResponseXL01);
                    System.out.println(xmlResponseXL01);

                    // Extract  response code
                    String responseCode = cmsResponseXL01
                            .getBody().getXl01Draw001Response().
                            getCmsXL01Draw001Response().getResponsecode();
                    //    //Get Note Number From Response XL01
//                    String noteNumber = "" ;
//                            if (cmsResponseXL01 != null && cmsResponseXL01.getBody().getXl01Draw001Response()
//                                    .getCmsXL01Draw001Response().getResponseDetail().getAdditionalData()!= null) {
//                                    List<additionalData> additionalDataList = cmsResponseXL01.getBody().getXl01Draw001Response()
//                        .getCmsXL01Draw001Response().getResponseDetail().getAdditionalData();
//                        if (additionalDataList != null) {
//                        for (additionalData data : additionalDataList) {
//                        if ("noteNumber".equals(data.getParam())) {
//                        // Return nilai noteNumber
//                        noteNumber = data.getValue();
//                        }
//                        }

                    if ("00".equals(responseCode)) {
                        System.out.println("Response code is 00. Continuing to XL31...");

                        // Call XL31 request
                        com.maybank.integratorapp.model.soap.XL31.request.SoapEnvelope soapReqXL31 =
                                new com.maybank.integratorapp.model.soap.XL31.request.SoapEnvelope();

                        soapReqXL31.getBody().getXl31().getChannelHeader().setAdditionalHeader("");
                        soapReqXL31.getBody().getXl31().getChannelHeader().setBranchCode("003");
                        soapReqXL31.getBody().getXl31().getChannelHeader().setChannelID("BT");
                        soapReqXL31.getBody().getXl31().getChannelHeader().setClientSupervisorID("7766");
                        soapReqXL31.getBody().getXl31().getChannelHeader().setClientUserID("7755");
                        soapReqXL31.getBody().getXl31().getChannelHeader().setReference(referenceId);
//                    soapReqXL31.getBody().getXl31().getChannelHeader().setReversalSequenceNo(correlationID);
                        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionDate(date);
                        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionTime(time);

                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setAmount(exposureAmmount);
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBatch(limitBranch+"01");
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBd("");
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setCurrency(ISOcurrency);
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDepartement(limitBranch);
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDescription("FTI");
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setNotenumber(newKeyloanAcc);
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setQual("0");
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTran("62");
                        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTransactiondate(transactionDate);

                        XmlMapper mapperXL31 = new XmlMapper();

                        // Avoid unnecessary wrapping
                        mapperXL31.setDefaultUseWrapper(false);
                        mapperXL31.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

                        String xmlXL31 = null;
                        try {
                            xmlXL31 = mapperXL31.writerWithDefaultPrettyPrinter().writeValueAsString(soapReqXL31);
                            System.out.println(xmlXL31);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        com.maybank.integratorapp.model.soap.XL31.response.SoapEnvelope cmsResponseXL31 = null;
                        try (CloseableHttpClient httpClientXL31 = HttpClients.createDefault()) {
                            HttpPost httpPostXL31 = new HttpPost(soapUrl);
                            httpPostXL31.setHeader("Content-Type", "text/xml");
                            httpPostXL31.setEntity(new StringEntity(xmlXL31, ContentType.TEXT_XML));
                            String _responseXL31 = "";

                            try (CloseableHttpResponse responseXL31 = httpClientXL31.execute(httpPostXL31)) {

                                // Handle response if needed
                                var _resXL31 = responseXL31.getEntity();
                                var _resStreamXL31 = _resXL31.getContent();
                                var outputResponseXL31 = new String(_resStreamXL31.readAllBytes(), StandardCharsets.UTF_8);
                                _responseXL31 = outputResponseXL31;
                                cmsResponseXL31 = mapperXL31.readValue(_responseXL31, com.maybank.integratorapp.model.soap.XL31.response.SoapEnvelope.class);
                                String finalstatusCode = cmsResponseXL31.getBody().getXl31Response().getCmsXl31Response().getResponsecode();
                                if("00".equals(finalstatusCode)){
                                    //refresh facility so it get new running number
                                    System.out.println("Refreshing Limit : "+limitCif);
                                    processFacilities.refreshFacilities(limitCif,facility.getCompanyLimitId());

                                }

                                String xmlResponseXL31 = mapperXL31.writeValueAsString(cmsResponseXL31);
                                System.out.println(xmlResponseXL31);


                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        return "sukses";

                    } else if ("99".equals(responseCode)) {
                        System.out.println("Response code is 99. Stopping process.");
                        return "Process stopped due to response code 99.";
                    } else {
                        System.out.println("Unexpected response code: " + responseCode);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to process SOAP request", e);

            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Unknown error occurred.";
    }
}
