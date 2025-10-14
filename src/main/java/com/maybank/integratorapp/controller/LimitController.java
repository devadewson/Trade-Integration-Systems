package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.system.messageprocessor.AccountInquiryMessageProcessor;
import com.maybank.integratorapp.component.system.messageprocessor.LimitFacilitiesMessageProcessor;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.LogQueueDataRepository;
import com.maybank.integratorapp.data.repository.MsCurrencyRepository;
import com.maybank.integratorapp.data.repository.MsFacilityRepository;
import com.maybank.integratorapp.data.repository.MsCompanyLimitRepository;
import com.maybank.integratorapp.model.mq.facilities.response.FacilityDetails;
import com.maybank.integratorapp.model.soap.fcclimit.request.OFA;
import com.maybank.integratorapp.model.soap.fcclimit.response.Limit;
import com.maybank.integratorapp.model.soap.fcclimit.response.OFAResponse;
import com.maybank.integratorapp.util.MQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Tag(name = "Limit API", description = "APIs for Limit Query")
public class LimitController {
    @Autowired
    MsFacilityRepository msFacilityRepository;
    @Autowired
    MsCompanyLimitRepository mscompanylimitRepository;
    private static Logger log = LoggerFactory.getLogger(LimitController.class);
    @Autowired
    private LogQueueDataRepository dataDTO;
    @Autowired
    private MsCurrencyRepository msCurrencyRepository;

    @Autowired
    LimitFacilitiesMessageProcessor processFacilities;

    @Operation(
            summary = "Limit Facility",
            description = "Accepts customer CIF in XML format and returns facility list in XML format.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = OFA.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Limit Fetched Successfully",
                            content = @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = OFAResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Limit Fetch Error",
                            content = @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = OFAResponse.class))
                    ),
            }
    )
    @PostMapping(value = "/LimitFCC", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
//    @PostMapping(value = "/LimitFCC", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<String> GetLimitFCC(@RequestBody OFA requestData){
        OFAResponse response = new OFAResponse();
        List<Limit> limitList = new ArrayList<>();
        String limitResponse = "";

        try{

            String cifno = requestData.getRequest().getCifNo();
//            String cifno = "0002794045";
            log.info("FCC Request Limit for :"+limitResponse);
            // Cek apakah cifno ada di MsCompanyLimit
            if (!mscompanylimitRepository.existsByCifno(cifno)) {
                // Jika CIF tidak ada, insert data ke tabel MsCompanyLimit
//                log.info("CIF " + cifno + " tidak ditemukan di tabel MsCompanyLimit. Menambahkan data baru.");
                MsCompanyLimit newLimit = new MsCompanyLimit();
                // set nilai CIF
                newLimit.setCifno(cifno);
                MsCompanyLimit savedcompanyLimit = mscompanylimitRepository.save(newLimit);
//                log.info("Data disimpan dengan ID: " + savedcompanyLimit.getId() + "CifNo" + savedcompanyLimit.getCifno());

                //melakukan Process Crate data facility pada database
                processFacilities.refreshFacilities(cifno, savedcompanyLimit.getId());
            }



            // Ambil data pada database MsFacility
            List<MsFacility> facilities = msFacilityRepository.findByCompanyLimitId(
                    mscompanylimitRepository.findByCifno(cifno).getId());

            List<MsCurrency> currencies = (List<MsCurrency>) msCurrencyRepository.findAll();

            if (!facilities.isEmpty()) {
                List<FacilityDetails> facilityDetails = new ArrayList<>();

                List<Limit> finalLimitList = limitList;
                facilities.forEach(s->{
                    String balance = s.getPrincipalBalance().split("\\.")[0];
//                    String balance = s.getPrincipalBalance().split("\\.")[0];
                    String remainingBalance = s.getCommitmentBalance().split("\\.")[0];
                    String utilizedBalance = String.valueOf((Long.parseLong(balance) - Long.parseLong(remainingBalance)));
                    LocalDate _maturitydate = LocalDate.parse(s.getMaturityDate(), DateTimeFormatter.BASIC_ISO_DATE);
                    String maturityDate = _maturitydate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String ISOcurrency = currencies.stream().filter(x->x.getInternalCode().equals(s.getCurrency())).findFirst().get().getIsoCode();
//                    String ISOcurrency = currencies.stream().filter(x->x.getInternalCode().equals(s.get())).findFirst().get().getIsoCode();


                    Limit limit = new Limit();
                    limit.setLimitName(s.getDescription().trim());
                    limit.setLimitNo(s.getKeyLoanAcc());
//                    limit.setParentLimitNo(s.getKeyLoanAcc());
                    limit.setProductCode(s.getNoteType());
                    limit.setLimitCurrency(s.getLoanCurrencyCode());
                    limit.setLimitAmount(balance);
                    limit.setExpiryDate(maturityDate);
                    limit.setTenorPeriod("180");
                    limit.setTenorFrequency("1");
                    limit.setAvailableLimitCurrency(ISOcurrency);
                    limit.setAvailableLimit(remainingBalance);
                    limit.setAvailableLimitTransactionCurrency(remainingBalance);
                    limit.setEarmarkedLimitCurrency(ISOcurrency);
                    limit.setEarmarkedLimit(utilizedBalance);
                    limit.setUtilisationCurrency(ISOcurrency);
                    limit.setUtilisation(utilizedBalance);

                    if(s.getNoteType().startsWith("7"))
                        limit.setIslamicFlag("I");
                    else
                        limit.setIslamicFlag("C");
                    limit.setLimitStatus("Active");
                    finalLimitList.add(limit);

                });

//                limitList = finalLimitList.subList(0,2);
                response.setLimit(finalLimitList);


            }

            XmlMapper xmlMapper = new XmlMapper();
            // Serialize the object to XML
            String xml = null;
            try {
                xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
                xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);

                limitResponse = xmlMapper.writeValueAsString(response);
                log.info(limitResponse);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());

                throw new RuntimeException(e);
            }

            // return new ResponseEntity<OFAResponse>(response, HttpStatus.OK);
            return new ResponseEntity<String>(limitResponse, HttpStatus.OK);


        }
        catch (Exception e){
            log.error(e.getMessage());

            return new ResponseEntity<String>(limitResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/RefreshLimit")
    public ResponseEntity<OFAResponse> RefreshLimit(){
        OFAResponse response = new OFAResponse();
        List<Limit> limitList = new ArrayList<>();
        LogQueueData _data = new LogQueueData();

        try{
            log.info("Refresh Limit start");
            _data.setMessageUID(new MQUtil().getMessageUID());
            _data.setOrigin("Refresh Limit Integrator Scheduler");
            _data.setCreated_date(new Date());
            _data.setStatus("Success");
            _data.setDelivery_date(new Date());
            _data.setUpdated_date(new Date());
            _data = dataDTO.save(_data);

            List<MsCompanyLimit> allCompany = (List<MsCompanyLimit>) mscompanylimitRepository.findAll();
            processFacilities.setLoggerId(_data.getId());

//            allCompany.forEach(x->{
//                processFacilities.refreshFacilities(x.getCifno(),x.getId());
//
//            });
            log.info("Refresh Limit completed");
            return new ResponseEntity<OFAResponse>(response, HttpStatus.OK);

        }
        catch (Exception e){
            _data.setResMessage(e.getMessage());
            _data.setStatus("Error");
            _data.setDelivery_date(new Date());
            _data.setUpdated_date(new Date());
            _data = dataDTO.save(_data);
            log.error(e.getMessage());

            return new ResponseEntity<OFAResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/RefreshLimit/{cif}")
    public ResponseEntity<OFAResponse> RefreshLimitByCif(@PathVariable String cif){
        OFAResponse response = new OFAResponse();
        List<Limit> limitList = new ArrayList<>();

        try{

            List<MsCompanyLimit> allCompany = (List<MsCompanyLimit>) mscompanylimitRepository.findAll();
            allCompany = allCompany.stream().filter(s->s.getCifno().equals(cif)).toList();
            log.info("Refresh Limit start");
            allCompany.forEach(x->{
                log.info("Refresh Limit for "+cif);
                processFacilities.refreshFacilities(x.getCifno(),x.getId());

            });
            log.info("Refresh Limit completed");
            return new ResponseEntity<OFAResponse>(response, HttpStatus.OK);

        }
        catch (Exception e){
            log.error(e.getMessage());

            return new ResponseEntity<OFAResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
