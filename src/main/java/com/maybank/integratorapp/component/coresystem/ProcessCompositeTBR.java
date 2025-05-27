package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.component.system.messageprocessor.LimitUtilizationMessageProcessor;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.VwTbrMappingRepository;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.mq.batchposting.request.Posting;
import com.maybank.integratorapp.model.restv2.CompositeTbr.response.MsgWrapper;
import com.maybank.integratorapp.util.DynamicClassGenerator;
import com.maybank.integratorapp.util.DynamicClassPropertyMap;
import com.maybank.integratorapp.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.maybank.integratorapp.model.rest.compositetbr.requestv2.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.util.StringUtils.capitalize;

@Component
public class ProcessCompositeTBR {
    private static Logger log = LoggerFactory.getLogger(ProcessCompositeTBR.class);
    @Autowired
    FtiTransactionDetailPostingService ftiTransactionDetailPostingService;
    @Autowired
    FtiTransactionDetailPostingGroupService ftiTransactionDetailPostingGroupService;
    @Autowired
    FtiTransactionDetailService ftiTransactionDetailService;
    @Autowired
    private MsCompanyLimitService msCompanyLimitService;
    @Autowired
    private MsBranchService msBranchService;
    @Autowired
    private MsTBRFieldService tbrFieldService;
    @Autowired
    private FtiAccountTypeService ftiAccountTypeService;
    @Autowired
    private MsCurrencyService msCurrencyService;
    @Autowired
    private VwTbrMappingRepository dataDTO;

    @Autowired
    private MsParameterService parameterService;

    @Autowired
    LogInterfaceProcessService logger;


    public List<Posting> populateSamplePosting(){
        List<Posting> listPosting = new ArrayList<>();

        Posting postItem = new Posting();

        postItem.setPostingSeqNo("1");
        postItem.setAccountType("CA");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("D");
        listPosting.add(postItem);

        postItem = new Posting();
        postItem.setPostingSeqNo("2");
        postItem.setAccountType("GL");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("C");
        listPosting.add(postItem);

        postItem = new Posting();
        postItem.setPostingSeqNo("3");
        postItem.setAccountType("CA");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("D");
        listPosting.add(postItem);

        postItem = new Posting();
        postItem.setPostingSeqNo("4");
        postItem.setAccountType("GL");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("C");
        listPosting.add(postItem);

        postItem = new Posting();
        postItem.setPostingSeqNo("5");
        postItem.setAccountType("CA");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("D");
        listPosting.add(postItem);

        postItem = new Posting();
        postItem.setPostingSeqNo("6");
        postItem.setAccountType("GL");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("C");
        listPosting.add(postItem);

        postItem = new Posting();
        postItem.setPostingSeqNo("7");
        postItem.setAccountType("GL");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("C");
        listPosting.add(postItem);

        return listPosting;
    }
    public List<Posting> populateSamplePosting2(){
        List<Posting> listPosting = new ArrayList<>();

        Posting postItem = new Posting();

        postItem.setPostingSeqNo("1");
        postItem.setAccountType("CCA");
        postItem.setBackOfficeAccountNo("2003037791");
        postItem.setPostingNarrative1("Testing Post");
        postItem.setPostingAmount("2000000");
        postItem.setMasterReference("R3311331133");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("D");
        listPosting.add(postItem);

        postItem = new Posting();
        postItem.setPostingSeqNo("2");
        postItem.setAccountType("I4610");
        postItem.setBackOfficeAccountNo("0460500208");
        postItem.setPostingNarrative1("Testing Post");
        postItem.setPostingAmount("2000000");
        postItem.setMasterReference("R3311331133");
        postItem.setPostingCcy("IDR");
        postItem.setDebitCreditFlag("C");
        listPosting.add(postItem);

        return listPosting;
    }

    private Long LoggerId;
    public void doPosting(List<Posting> data, Long idLogParent){

        this.LoggerId =idLogParent;
//        this.logger.SetLogParent(idLogParent);

        String referenceID = data.stream().findFirst().get().getMasterReference();
        String eventCode = data.stream().findFirst().get().getEventReference();






        // remove the 999 vs 07 posting
        List<Posting> removed = data.stream().filter(x->x.getBackOfficeAccountNo().startsWith("07") || x.getBackOfficeAccountNo().startsWith("999")).toList();
        data.removeAll(removed);

        // group the posting
        logger.Log(this.LoggerId,"Posting - Group Posting Data","Group posting into pair of debit credit","START");
        List<PostingGroup> finalData = groupPosting(data);
        logger.Log(this.LoggerId,"Posting - Group Posting Data","Group posting into pair of debit credit","END");
        // condition check if there is cross valas


        for (PostingGroup postingGroup:finalData) {
            FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
            ftiTransactionDetail.setTransMessageLogId(LoggerId);
            ftiTransactionDetail.setFtiEvent(eventCode);
            ftiTransactionDetail.setCoreSysName("FMS-CompositeTBR");
            ftiTransactionDetail.setTransName("Posting");
            ftiTransactionDetail.setAdditionalInfo1("TBR-"+postingGroup.getTbrCode());
            ftiTransactionDetail.setAdditionalInfo2(String.valueOf(postingGroup.getGroupId()));
            ftiTransactionDetail.setAdditionalInfo3("-");
            ftiTransactionDetail.setAdditionalInfo4("-");
            ftiTransactionDetail.setAdditionalInfo5("-");
            ftiTransactionDetail = ftiTransactionDetailService.createDetailByMasterRefNo(referenceID, ftiTransactionDetail);

            FtiTransactionDetailPostingGroup _group = new FtiTransactionDetailPostingGroup();
            _group.setDetailId(ftiTransactionDetail.getId());
            _group.setGroupId(String.valueOf(postingGroup.getGroupId()));
            _group.setTbrCode(postingGroup.getTbrCode());
            _group.setFlagCrossValas(postingGroup.getFlagCrossValas());
            _group.setFlagMdmc(postingGroup.getFlagMdmc());
            _group.setMappingType(postingGroup.getMappingType());

            _group = ftiTransactionDetailPostingGroupService.save(_group);

            for(PostingExtender posting:postingGroup.getPostings()){
                FtiTransactionDetailPosting _posting = new FtiTransactionDetailPosting();
                _posting.setIdGroup(_group.getId());
                _posting.setAccount(posting.getBackOfficeAccountNo());
                _posting.setPostingSeqNo(posting.getPostingSeqNo());
                _posting.setAccountType(posting.getAccountType());
                _posting.setAccountTypeAlias(posting.getAccountTypeAlias());
                _posting.setAmount(posting.getPostingAmount());
                _posting.setCcyAlias(posting.getPostingCcyAlias());
                _posting.setCcy(posting.getPostingCcy());
                _posting.setCcyNumber(posting.getPostingCcyNumber());
                _posting.setValueDate(posting.getValueDate());
                _posting.setDebitCredit(posting.getDebitCreditFlag());

                ftiTransactionDetailPostingService.save(_posting);
            }

            // check if its cross valas
            if (postingGroup.getFlagCrossValas().equals("N"))
            {
                logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting the data into ESB", "START");
                postTbr(referenceID,postingGroup, Long.valueOf(postingGroup.getGroupId()),ftiTransactionDetail.getId());
                logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting the data into ESB", "END");

            }
            else{
                // do cross valas logic here
                logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting Cross Valas Data the data into ESB", "START");
                logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting Cross Valas Data the data into ESB", "END");

            }

            // check & post to RTGS
            if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("RPKP")));{
                postRtgs(postingGroup);
            }

        }


    }

    private void postRtgs(PostingGroup postingGroup) {
        try{
            PostingExtender rpkpPosting = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("RPKP")).findFirst().get();

        }
        catch (Exception e){
            
        }

    }

    public void postTbr(String referenceID,PostingGroup data, Long groupId, Long idtransactiondetail){
        try{
            String cifno = data.getPostings().stream().findFirst().get().getCustomerMnemonic();
            if (data.getPostings().stream().findFirst().get().getCustomerMnemonic() == null){
                cifno = data.getPostings().stream().findFirst().get().getRelatedParty();
            }
            String postingBranch = data.getPostings().stream().findFirst().get().getPostingBranch();

            String branch = "003";
            String clientUserId = "7755";
            String clientSpvUserId = "7766";

            if(postingBranch.startsWith("9"))
            {
                MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(cifno);
                if(postingBranch.equals("906"))
                    branch = _company.getCbranch();
                else
                    branch = _company.getIbranch();

            }

            MsBranch _branch = msBranchService.getByBranchCode(branch);

            if(_branch!=null){
                clientUserId = _branch.getUserId();
                clientSpvUserId = _branch.getSpvUserId();
            }

            String tbrNumber = data.TbrCode;
            List<PostingExtender> postings = data.getPostings();
            List<vw_tbr_mapping> mappings = data.getMappings();
            List<MsTBRField> fieldsList = tbrFieldService.findFieldsByTbrCode(tbrNumber);

            List<DynamicClassPropertyMap> propertyMapList = new ArrayList<>();
            propertyMapList.add(new DynamicClassPropertyMap("TBRNumber","String"));
            propertyMapList.add(new DynamicClassPropertyMap("TBRDesc","String"));

            fieldsList.forEach(s->{
                propertyMapList.add(new DynamicClassPropertyMap(
                        s.getDestinationField(),
                        s.getDestinationFieldDataType() == null?"String":s.getDestinationFieldDataType()));
            });

            Class<?> dynamicClass = DynamicClassGenerator.generateClass("TBRData", propertyMapList);

            Object instance = dynamicClass.getDeclaredConstructor().newInstance();

            dynamicClass.getMethod("setTBRNumber", String.class).invoke(instance, tbrNumber);
            dynamicClass.getMethod("setTBRDesc", String.class).invoke(instance, "FTI_"+tbrNumber);


            // do the field-value mapping
//            instance = mapFieldTBR(instance,dynamicClass,postings,fieldsList);
            instance = mapFieldTBRNew(instance,dynamicClass,postings,fieldsList);

//            dynamicClass.getMethod("setSourceAccountNo", String.class).invoke(instance, "1002031");
//            String sourceAccountNo = (String) dynamicClass.getMethod("getSourceAccountNo").invoke(instance);
            String url = parameterService.findValueByPrmKey("CompositeTBRRequest");
            String ChannelHeaderBranchCode = parameterService.findValueByPrmKey("ChannelHeaderBranchCode");
            String ChannelHeaderChannelId = parameterService.findValueByPrmKey("ChannelHeaderChannelId");

//            String url = "http://10.235.66.95:7804/transactionservicesapi/v1/CompositeTBR";
            List<Object> tbrData = new ArrayList<>();
            tbrData.add(instance);
//            RestEnvelope envelope = new RestEnvelope();
//
//            envelope.getCompositeTBR().getChannelHeader().setChannelID(ChannelHeaderChannelId);
//            envelope.getCompositeTBR().getChannelHeader().setBranchCode(ChannelHeaderBranchCode);
//
//            envelope.getCompositeTBR().getExecuteCompositeTransactionRequest().setTransactionName("Testing New FTI TBR");
//            envelope.getCompositeTBR().getExecuteCompositeTransactionRequest().setTBRData(tbrData);


            // do posting to ESB
            Message _msgWrapper = new Message();
            Msg _msg = new Msg();
            MsgBody _msgBody = new MsgBody();
            MsgHeader _msgHeader = new MsgHeader();
            _msgHeader.setMsgID(referenceID);
            _msgHeader.setVer("01");
            _msgHeader.setSvcID("IDUPDACCTTRX001");
            _msgHeader.setEnv("S");
            _msgHeader.setBranchCode(branch);
            if(!clientSpvUserId.isEmpty()){
                if(!clientSpvUserId.equals("-")){
                    _msgHeader.setSpvOverride("true");
                    _msgHeader.setClientSpvID(clientSpvUserId);
                }
            }
            _msg.setMsgHeader(_msgHeader);
            _msgBody.setTbrData(tbrData);
            _msg.setMsgBody(_msgBody);
            _msgWrapper.setMsg(_msg);


            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
            );
//            String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter() // enable pretty print
//                                    .writeValueAsString(envelope);
            String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter() // enable pretty print
                    .writeValueAsString(_msgWrapper);
            logger.Log(this.LoggerId,"Posting "+groupId, "Request ESB", "DATA-REQ",jsonPayload);

//            log.info("Serialized JSON Payload: " + jsonPayload);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            String response = "";
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();
            logger.Log(this.LoggerId,"Posting "+groupId, "Response ESB", "DATA-RES",response);


            com.maybank.integratorapp.model.restv2.CompositeTbr.response.MsgWrapper res = new MsgWrapper();
            res = objectMapper.readValue(response, com.maybank.integratorapp.model.restv2.CompositeTbr.response.MsgWrapper.class);

            String responseCode = res.getMsg().getMsgHeader().getStatusCode();
            String responseMessage = res.getMsg().getMsgHeader().getStatusDesc();

            String hostResponseCode = res.getMsg().getMsgHeader().getAdditionalStatusCodes()[0].getHostStatusCode();
            String hostResponseMessage = res.getMsg().getMsgHeader().getAdditionalStatusCodes()[0].getHostStatusDesc();

            FtiTransactionDetail _detail = ftiTransactionDetailService.getById(idtransactiondetail);
            _detail.setCoreSysStatus(responseCode + " | "+hostResponseCode);
            _detail.setCoreSysMessage(responseMessage + " | "+hostResponseMessage);
            ftiTransactionDetailService.createDetailByMasterRefNo(referenceID,_detail);
//            ResponseEntity<String> _response = restTemplate.postForEntity(url,_msgWrapper,String.class);

//            if(_response.hasBody()){
//                response = _response.getBody();
//                logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting the data into ESB", "DATA-RES",response);
//
//            }

//            log.info("Response from API: " + response);
        }catch (Exception e){
            logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting the data into ESB", "ERROR",e.getMessage());

//            throw e;
        }
    }
    public List<PostingGroup> groupPosting(List<Posting> listPosting){
//        List<Posting> listPosting = new ArrayList<>();
        List<PostingGroup> groupedPostings = new ArrayList<>();

        try{
// for sample only
//            listPosting = populateSamplePosting2();

            List<PostingExtender> finalListPosting = new ArrayList<>();
            for (Posting posting : listPosting) {
                PostingExtender data = new PostingExtender();
                ReflectionUtils.copyProperties(posting,data);

                MsAccountType accountType = ftiAccountTypeService.findByFtiAccountType(data.getAccountType());

                if(accountType!=null){
                    data.setAccountTypeAlias(accountType.getAccountType());
                }

                MsCurrency currency = msCurrencyService.findByIsoCode(data.getPostingCcy());

                if(currency!=null){
                    data.setPostingCcyNumber(currency.getInternalCode());
                }

                finalListPosting.add(data);
            }

            // check cross valas
            if(finalListPosting.stream().allMatch(x->!x.getPostingCcy().equals("IDR"))){
                String firstPostingCcy = finalListPosting.get(0).getPostingCcy();

                boolean allSameCurrencies = finalListPosting.stream()
                        .map(PostingExtender::getPostingCcy) // Extract PostingCcy from each object
                        .allMatch(ccy -> Objects.equals(ccy, firstPostingCcy)) ;// Compare with original list size

                if (!allSameCurrencies) {
                    // Logic for when all postings have different currencies

                    Map<String, Integer> currencyCountMap = new HashMap<>();

                    finalListPosting.forEach(posting -> {
                        String currency = posting.getPostingCcy();
                        // Get the current count for this currency, or start from 1 if it's the first occurrence
                        int count = currencyCountMap.getOrDefault(currency, 0) + 1;
                        currencyCountMap.put(currency, count);

                        // Set PostingCcyAlias based on the count
                        posting.setPostingCcyAlias("FCY" + count);
                    });
                }else{
                    finalListPosting.forEach(posting -> {
                        posting.setPostingCcyAlias("FCY");
                    });
                }
            }else{
                // Mixed currency (with IDR and non-IDR)
                finalListPosting.forEach(posting -> {
                    if (posting.getPostingCcy().equals("IDR")) {
                        posting.setPostingCcyAlias("IDR");
                    } else {
                        posting.setPostingCcyAlias("FCY");
                    }
                });
            }

            List<vw_tbr_mapping> listMapping = (List<vw_tbr_mapping>)dataDTO.findAll();

            // Group the list by TbrCode and MappingType
            Map<String, Map<String, List<vw_tbr_mapping>>> groupedMapping = listMapping.stream()
                    .collect(Collectors.groupingBy(
                            vw_tbr_mapping::getTbrcode,
                            Collectors.groupingBy(vw_tbr_mapping::getMapping_type)
//                        Collectors.groupingBy(vw_tbr_mapping::getMapping_type,
//                                Collectors.collectingAndThen(
//                                        Collectors.toMap(
//                                                vw_tbr_mapping::g,   // Use the `id` as a key to ensure distinct values
//                                                Function.identity(),     // Map to the `vw_tbr_mapping` itself
//                                                (existing, replacement) -> existing // If duplicate, keep the existing one
//                                        ),
//                                        map -> new ArrayList<>(map.values()) // Convert the map values back to a list
//                                )
//                        )
                    ));
            List<PostingGroup> finalGroupedMapping = new ArrayList<>();

            for (Map.Entry<String, Map<String, List<vw_tbr_mapping>>> tbrEntry : groupedMapping.entrySet()) {
                String tbrCode = tbrEntry.getKey();
                for (Map.Entry<String, List<vw_tbr_mapping>> mappingEntry : tbrEntry.getValue().entrySet()) {
                    PostingGroup group = new PostingGroup();
                    group.setTbrCode(tbrCode);
                    group.setMappingType(mappingEntry.getKey());
                    group.setMappings(mappingEntry.getValue());
                    group.setPostings(new ArrayList<>()); // Initialize the postings list

                    finalGroupedMapping.add(group);
                }
            }

            List<PostingGroup> finalData = new ArrayList<>();

            int groupId = 1;

            // grouping posting
            Map<String, List<PostingExtender>> grouped = finalListPosting.stream()
                    .collect(Collectors.groupingBy(
                            parent -> parent.getExtraData().getGroupID()
                    ));

            grouped.forEach((key, value) -> {
                PostingGroup group = new PostingGroup();
                group.setGroupId(Integer.parseInt(key));

                value.sort((row1, row2) -> {
                    if (row1.getDebitCreditFlag().equals("D") && row2.getDebitCreditFlag().equals("C") ) return -1;
                    if (row1.getDebitCreditFlag().equals("C") && row2.getDebitCreditFlag().equals("D")) return 1;
                    return 0;
                });

                group.getPostings().addAll(value);
                groupedPostings.add(group);

            });

//            for (int i = 0; i < finalListPosting.size(); i++) {
//                if (finalListPosting.get(i).getExtraData().getGroupID().) {
//                    PostingExtender found = finalListPosting.get(i);
//
//                    boolean alreadyGrouped = groupedPostings.stream()
//                            .anyMatch(pg -> pg.getPostings().contains(found));
//
//                    if (!alreadyGrouped) {
//                        PostingGroup group = new PostingGroup();
//                        group.setGroupId(groupId++);
//                        group.getPostings().add(found);
//
//                        // continue to look for subsequent C postings until the next D is encountered
//                        for (int j = i + 1; j < finalListPosting.size(); j++) {
//                            group.getPostings().add(finalListPosting.get(j));
//
//                            if ("C".equals(finalListPosting.get(j).getDebitCreditFlag()) &&
//                                    (j + 1 < finalListPosting.size() && "D".equals(finalListPosting.get(j + 1).getDebitCreditFlag()))) {
//                                break;
//                            }
//                        }
//
//                        groupedPostings.add(group);
//                    }
//                }
//            }


            // check & set cross valas flag logic
            groupedPostings.forEach(x->
            {
                if(x.getPostings().stream().anyMatch(s->s.getPostingCcyAlias().equals("FCY1"))){
                    x.setFlagCrossValas("Y");
                }else{
                    x.setFlagCrossValas("N");
                }
            });

            // find respective TBR grouping
            for (PostingGroup group:groupedPostings) {
                for (PostingGroup groupCondition:finalGroupedMapping) {
                    if(isGroupConditionMet(group,groupCondition)){
                        group.setTbrCode(groupCondition.getTbrCode());
                        group.setMappingType(groupCondition.getMappingType());
                        group.setMappings(groupCondition.getMappings());
                        break;
                    }
                }
            }

            //check mdmc flag
            for (PostingGroup group : groupedPostings) {
                long debitCount = group.getPostings().stream().filter(x->x.getDebitCreditFlag().equals("D")).count();
                long creditCount = group.getPostings().stream().filter(x->x.getDebitCreditFlag().equals("C")).count();

                if(debitCount> 1 || creditCount>1)
                    group.setFlagMdmc("Y");
                else
                    group.setFlagMdmc("N");
            }

            for (PostingGroup group : groupedPostings) {
                logger.Log(this.LoggerId,"Posting - Group Posting Data", "TbrCode: " + group.getTbrCode(), "PROCESS");
                logger.Log(this.LoggerId,"Posting - Group Posting Data", "MappingType: " + group.getMappingType(), "PROCESS");
                logger.Log(this.LoggerId,"Posting - Group Posting Data", "MDMC: " + group.getFlagMdmc(), "PROCESS");

                for (PostingExtender posting : group.getPostings()) {
                    logger.Log(this.LoggerId,"Posting - Group Posting Data", " - Sequence: " + posting.getPostingSeqNo() +
                            ", Account: " + posting.getBackOfficeAccountNo() +
                            ", Type: " + posting.getAccountTypeAlias() +
                            ", Currency: " + posting.getPostingCcy() +
                            ", DebitCredit: " + posting.getDebitCreditFlag(), "PROCESS");

                }

            }
        } catch (Exception e){
            logger.Log(this.LoggerId,"Posting - Group Posting Data","Grouped posting into pair of debit credit","ERROR",e.getMessage());
        }


        return groupedPostings;

    }
    public List<PostingGroup> groupPostingOld(List<Posting> listPosting){
//        List<Posting> listPosting = new ArrayList<>();
        List<PostingGroup> groupedPostings = new ArrayList<>();

        try{
// for sample only
//            listPosting = populateSamplePosting2();

            List<PostingExtender> finalListPosting = new ArrayList<>();
            for (Posting posting : listPosting) {
                PostingExtender data = new PostingExtender();
                ReflectionUtils.copyProperties(posting,data);

                MsAccountType accountType = ftiAccountTypeService.findByFtiAccountType(data.getAccountType());

                if(accountType!=null){
                    data.setAccountTypeAlias(accountType.getAccountType());
                }

                MsCurrency currency = msCurrencyService.findByIsoCode(data.getPostingCcy());

                if(currency!=null){
                    data.setPostingCcyNumber(currency.getInternalCode());
                }

                finalListPosting.add(data);
            }

            // check cross valas
            if(finalListPosting.stream().allMatch(x->!x.getPostingCcy().equals("IDR"))){
                String firstPostingCcy = finalListPosting.get(0).getPostingCcy();

                boolean allSameCurrencies = finalListPosting.stream()
                        .map(PostingExtender::getPostingCcy) // Extract PostingCcy from each object
                        .allMatch(ccy -> Objects.equals(ccy, firstPostingCcy)) ;// Compare with original list size

                if (!allSameCurrencies) {
                    // Logic for when all postings have different currencies

                    Map<String, Integer> currencyCountMap = new HashMap<>();

                    finalListPosting.forEach(posting -> {
                        String currency = posting.getPostingCcy();
                        // Get the current count for this currency, or start from 1 if it's the first occurrence
                        int count = currencyCountMap.getOrDefault(currency, 0) + 1;
                        currencyCountMap.put(currency, count);

                        // Set PostingCcyAlias based on the count
                        posting.setPostingCcyAlias("FCY" + count);
                    });
                }else{
                    finalListPosting.forEach(posting -> {
                        posting.setPostingCcyAlias("FCY");
                    });
                }
            }else{
                // Mixed currency (with IDR and non-IDR)
                finalListPosting.forEach(posting -> {
                    if (posting.getPostingCcy().equals("IDR")) {
                        posting.setPostingCcyAlias("IDR");
                    } else {
                        posting.setPostingCcyAlias("FCY");
                    }
                });
            }

            List<vw_tbr_mapping> listMapping = (List<vw_tbr_mapping>)dataDTO.findAll();

            // Group the list by TbrCode and MappingType
            Map<String, Map<String, List<vw_tbr_mapping>>> groupedMapping = listMapping.stream()
                    .collect(Collectors.groupingBy(
                            vw_tbr_mapping::getTbrcode,
                            Collectors.groupingBy(vw_tbr_mapping::getMapping_type)
//                        Collectors.groupingBy(vw_tbr_mapping::getMapping_type,
//                                Collectors.collectingAndThen(
//                                        Collectors.toMap(
//                                                vw_tbr_mapping::g,   // Use the `id` as a key to ensure distinct values
//                                                Function.identity(),     // Map to the `vw_tbr_mapping` itself
//                                                (existing, replacement) -> existing // If duplicate, keep the existing one
//                                        ),
//                                        map -> new ArrayList<>(map.values()) // Convert the map values back to a list
//                                )
//                        )
                    ));
            List<PostingGroup> finalGroupedMapping = new ArrayList<>();

            for (Map.Entry<String, Map<String, List<vw_tbr_mapping>>> tbrEntry : groupedMapping.entrySet()) {
                String tbrCode = tbrEntry.getKey();
                for (Map.Entry<String, List<vw_tbr_mapping>> mappingEntry : tbrEntry.getValue().entrySet()) {
                    PostingGroup group = new PostingGroup();
                    group.setTbrCode(tbrCode);
                    group.setMappingType(mappingEntry.getKey());
                    group.setMappings(mappingEntry.getValue());
                    group.setPostings(new ArrayList<>()); // Initialize the postings list

                    finalGroupedMapping.add(group);
                }
            }

            List<PostingGroup> finalData = new ArrayList<>();

            int groupId = 1;

            // grouping posting
            for (int i = 0; i < finalListPosting.size(); i++) {
                if ("D".equals(finalListPosting.get(i).getDebitCreditFlag())) {
                    PostingExtender found = finalListPosting.get(i);

                    boolean alreadyGrouped = groupedPostings.stream()
                            .anyMatch(pg -> pg.getPostings().contains(found));

                    if (!alreadyGrouped) {
                        PostingGroup group = new PostingGroup();
                        group.setGroupId(groupId++);
                        group.getPostings().add(found);

                        // continue to look for subsequent C postings until the next D is encountered
                        for (int j = i + 1; j < finalListPosting.size(); j++) {
                            group.getPostings().add(finalListPosting.get(j));

                            if ("C".equals(finalListPosting.get(j).getDebitCreditFlag()) &&
                                    (j + 1 < finalListPosting.size() && "D".equals(finalListPosting.get(j + 1).getDebitCreditFlag()))) {
                                break;
                            }
                        }

                        groupedPostings.add(group);
                    }
                }
            }


            // check & set cross valas flag logic
            groupedPostings.forEach(x->
            {
                if(x.getPostings().stream().anyMatch(s->s.getPostingCcyAlias().equals("FCY1"))){
                    x.setFlagCrossValas("Y");
                }else{
                    x.setFlagCrossValas("N");
                }
            });

            // find respective TBR grouping
            for (PostingGroup group:groupedPostings) {
                for (PostingGroup groupCondition:finalGroupedMapping) {
                    if(isGroupConditionMet(group,groupCondition)){
                        group.setTbrCode(groupCondition.getTbrCode());
                        group.setMappingType(groupCondition.getMappingType());
                        group.setMappings(groupCondition.getMappings());
                        break;
                    }
                }
            }

            //check mdmc flag
            for (PostingGroup group : groupedPostings) {
                long debitCount = group.getPostings().stream().filter(x->x.getDebitCreditFlag().equals("D")).count();
                long creditCount = group.getPostings().stream().filter(x->x.getDebitCreditFlag().equals("C")).count();

                if(debitCount> 1 || creditCount>1)
                    group.setFlagMdmc("Y");
                else
                    group.setFlagMdmc("N");
            }

            for (PostingGroup group : groupedPostings) {
                logger.Log(this.LoggerId,"Posting - Group Posting Data", "TbrCode: " + group.getTbrCode(), "PROCESS");
                logger.Log(this.LoggerId,"Posting - Group Posting Data", "MappingType: " + group.getMappingType(), "PROCESS");
                logger.Log(this.LoggerId,"Posting - Group Posting Data", "MDMC: " + group.getFlagMdmc(), "PROCESS");

                for (PostingExtender posting : group.getPostings()) {
                    logger.Log(this.LoggerId,"Posting - Group Posting Data", " - Sequence: " + posting.getPostingSeqNo() +
                            ", Account: " + posting.getBackOfficeAccountNo() +
                            ", Type: " + posting.getAccountTypeAlias() +
                            ", Currency: " + posting.getPostingCcy() +
                            ", DebitCredit: " + posting.getDebitCreditFlag(), "PROCESS");

                }

            }
        } catch (Exception e){
            logger.Log(this.LoggerId,"Posting - Group Posting Data","Grouped posting into pair of debit credit","ERROR",e.getMessage());
        }


        return groupedPostings;

    }
    public Object mapFieldTBRNew(Object instance, Class<?> dynamicClass,List<PostingExtender> postings, List<MsTBRField> listMapping){

        // Get the source's getter method and the destination's setter method
        try {
            List<String> mappedFields = new ArrayList<String>();
            // debit legs
            int seq = 1;

            List<PostingExtender> filteredPostings = postings.stream()
                    .filter(x -> x.getDebitCreditFlag().equals("D"))
                    .toList();

            for (int i = 0; i < filteredPostings.size(); i++) {
                PostingExtender post = filteredPostings.get(i);
                // find all fields for this posting
                int finalSeq = seq;
                logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map seq "+String.valueOf(finalSeq), "DEBUG");

                List<MsTBRField> listField = listMapping.stream().filter(x->
                        x.getMappingDebitCredit().equals(post.getDebitCreditFlag())
                        && x.getMappingAccountType().equals(post.getAccountTypeAlias())
                        && x.getMappingPosition().equals(String.valueOf(finalSeq))
                ).toList();
                for (MsTBRField field :listField){
                    String destinationPropertyName = field.getDestinationField();
                    String destinationSetterName = "set" + capitalize(destinationPropertyName);
                    String sourcePropertyName = field.getSourceField();
                    String sourceGetterName = "get" + capitalize(sourcePropertyName);
                    if(!mappedFields.contains(destinationPropertyName)){

                        mappedFields.add(destinationPropertyName);
                    }
                    if(field.getDefaultValue() != null && !field.getDefaultValue().isEmpty()){

                        Object value = field.getDefaultValue();
                        logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map default value "+String.valueOf(value)+" into "+destinationPropertyName+" ESB", "DEBUG");

                        if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                            if(field.getDestinationFieldDataType().equals("Integer")){
                                dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class).invoke(instance, Integer.parseInt(value.toString()));

                            }
                        }else{
                            dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                        }
                        continue;
                    }
                    logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map "+sourcePropertyName+" data into "+destinationPropertyName+" ESB", "DEBUG");
                    Class<?> sourceClass = post.getClass();
                    Class<?> destinationClass = dynamicClass;

                    Method sourceGetter = sourceClass.getMethod(sourceGetterName);
//                    Method destinationSetter = destinationClass.getMethod(destinationSetterName, mapping.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class);

                    // Invoke the source getter method to get the value
                    Object value = sourceGetter.invoke(post);

                    if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                        if(field.getDestinationFieldDataType().equals("Integer")){
                            dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class).invoke(instance, Integer.parseInt(value.toString()));

                        }
                    }else{
                        dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                    }

                }
                if (i + 1 < filteredPostings.size()) {
                    PostingExtender nextPost = filteredPostings.get(i + 1);
                    if (post.getAccountTypeAlias().equals(nextPost.getAccountTypeAlias())) {
                        seq++;
                    }
                }

            }
            // credit legs
            seq = 1;
            filteredPostings = postings.stream()
                    .filter(x -> x.getDebitCreditFlag().equals("C"))
                    .toList();

            for (int i = 0; i < filteredPostings.size(); i++) {
                PostingExtender post = filteredPostings.get(i);
                // find all fields for this posting
                int finalSeq = seq;
                logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map seq "+String.valueOf(finalSeq), "DEBUG");

                List<MsTBRField> listField = listMapping.stream().filter(x->
                        x.getMappingDebitCredit().equals(post.getDebitCreditFlag())
                        && x.getMappingAccountType().equals(post.getAccountTypeAlias())
                        && x.getMappingPosition().equals(String.valueOf(finalSeq))
                ).toList();
                for (MsTBRField field :listField){
                    String destinationPropertyName = field.getDestinationField();
                    String destinationSetterName = "set" + capitalize(destinationPropertyName);
                    String sourcePropertyName = field.getSourceField();
                    String sourceGetterName = "get" + capitalize(sourcePropertyName);
                    if(!mappedFields.contains(destinationPropertyName)){

                        mappedFields.add(destinationPropertyName);
                    }

                    if(field.getDefaultValue() != null && !field.getDefaultValue().isEmpty()){

                        Object value = field.getDefaultValue();
                        logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map default value "+String.valueOf(value)+" into "+destinationPropertyName+" ESB", "DEBUG");

                        if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                            if(field.getDestinationFieldDataType().equals("Integer")){
                                dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class).invoke(instance, Integer.parseInt(value.toString()));

                            }
                        }else{
                            dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                        }
                        continue;
                    }
                    logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map "+sourcePropertyName+" data into "+destinationPropertyName+" ESB", "DEBUG");
                    Class<?> sourceClass = post.getClass();
                    Class<?> destinationClass = dynamicClass;

                    Method sourceGetter = sourceClass.getMethod(sourceGetterName);
//                    Method destinationSetter = destinationClass.getMethod(destinationSetterName, mapping.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class);

                    // Invoke the source getter method to get the value
                    Object value = sourceGetter.invoke(post);

                    if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                        if(field.getDestinationFieldDataType().equals("Integer")){
                            dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class).invoke(instance, Integer.parseInt(value.toString()));

                        }
                    }else{
                        dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                    }

                }
                if ((i + 1) < filteredPostings.size()) {
                    PostingExtender nextPost = filteredPostings.get(i + 1);
                    if (post.getAccountTypeAlias().equals(nextPost.getAccountTypeAlias())) {
                        seq++;
                    }
                }


            }
            // finalize object
            for (MsTBRField field :listMapping){
                String destinationPropertyName = field.getDestinationField();
                String destinationSetterName = "set" + capitalize(destinationPropertyName);
                if(!mappedFields.contains(destinationPropertyName)){
                    logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map empty data into "+destinationPropertyName+" ESB", "DEBUG");

                    dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, "");
                }
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return instance;
    }
    public Object mapFieldTBR(Object instance, Class<?> dynamicClass,List<PostingExtender> postings, List<MsTBRField> listMapping){

        // Get the source's getter method and the destination's setter method
        try {
            for (MsTBRField mapping : listMapping) {
                String destinationPropertyName = mapping.getDestinationField();
                String destinationSetterName = "set" + capitalize(destinationPropertyName);

                // Get source property name and destination property name from mapping
                if(mapping.getSourceField() != null && !mapping.getSourceField().isEmpty()){
                    String sourcePropertyName = mapping.getSourceField();
                    logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map "+sourcePropertyName+" data into "+destinationPropertyName+" ESB", "PROCESS");

                    // Generate method names for the source's getter and the destination's setter
                    String sourceGetterName = "get" + capitalize(sourcePropertyName);

                    // Get Posting Object for mapping
                    PostingExtender _postingData = new PostingExtender();
                    _postingData = null;

                    List<PostingExtender> posting = postings.stream().filter(p ->
                            p.getDebitCreditFlag().equals(mapping.getMappingDebitCredit())
                    ).toList();
                    long legCount = postings.stream().count();

                    if(legCount>=Long.parseLong(mapping.getMappingPosition())){
                        posting = postings.stream().filter(p ->
                                p.getAccountTypeAlias().equals(mapping.getMappingAccountType())
                        ).toList();
                        if(posting.stream().count()>=Long.parseLong(mapping.getMappingPosition())){
                            _postingData = posting.get(Integer.parseInt( mapping.getMappingPosition())-1);

                        }

                    }


//                    List<PostingExtender> posting = postings.stream().filter(p ->
//                            p.getAccountTypeAlias().equals(mapping.getMappingAccountType())
////                            && p.getPostingCcy().equals(mapping.getMappingCurrency())
//                            && p.getDebitCreditFlag().equals(mapping.getMappingDebitCredit())
//                            ).toList();
//                    logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map Posting data count : "+posting.stream().count(), "PROCESS");
//
//                    // jika multiple debit/credit found
//                    if(posting.stream().count() > 0){
//                        if(posting.stream().count() == 1
//                                && posting.stream().count() > (Integer.parseInt( mapping.getMappingPosition())-1)){
//                            _postingData = posting.get(0);
//                        }else if(posting.stream().count() > 1
//                                && posting.stream().count() > (Integer.parseInt( mapping.getMappingPosition())-1)){
//                            _postingData = posting.get(Integer.parseInt( mapping.getMappingPosition())-1);
//
//                        }else{
//                            _postingData = null;
//                        }
//                    }else{
//                        _postingData = null;
//                    }


                    if(_postingData!= null){
                        logger.Log(this.LoggerId,"Posting - Map Data to ESB", destinationPropertyName +" mapped to Posting Data :"
                                        +_postingData.getPostingSeqNo()+"|"
                                        +_postingData.getPostingCcyAlias()+"|"
                                        +_postingData.getDebitCreditFlag()+"|"
                                        +_postingData.getAccountTypeAlias()+"|"
                                , "PROCESS");

                        Class<?> sourceClass = _postingData.getClass();
                        Class<?> destinationClass = dynamicClass;

                        Method sourceGetter = sourceClass.getMethod(sourceGetterName);
//                    Method destinationSetter = destinationClass.getMethod(destinationSetterName, mapping.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class);

                        // Invoke the source getter method to get the value
                        Object value = sourceGetter.invoke(_postingData);

                        if((mapping.getDestinationFieldDataType() != null && !mapping.getDestinationFieldDataType().isEmpty())){
                            if(mapping.getDestinationFieldDataType().equals("Integer")){
                                dynamicClass.getMethod(destinationSetterName, mapping.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class).invoke(instance, Integer.parseInt(value.toString()));

                            }
                        }else{
                            dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                        }
                    }else{
                        logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Posting data not found for "+destinationPropertyName, "PROCESS");
                        dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, "");

                    }



                    // Invoke the destination setter method to set the value
//                    destinationSetter.invoke(dynamicClass, value);
                }else{
                    Object value = mapping.getDefaultValue();
//                    set the default value
                    if((mapping.getDestinationFieldDataType() != null && !mapping.getDestinationFieldDataType().isEmpty())){
                        if(mapping.getDestinationFieldDataType().equals("Integer")){
                            dynamicClass.getMethod(destinationSetterName, mapping.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class).invoke(instance, Integer.parseInt(value.toString()));

                        }
                    }else{
                        dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                    }
                }

            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return instance;
    }
    public boolean isGroupConditionMet(PostingGroup group, PostingGroup groupCondition) {
        if (group.getPostings().size() != groupCondition.getMappings().size()) {
            return false;
        }

        for (int i = 0; i < groupCondition.getMappings().size(); i++) {
            vw_tbr_mapping condition = groupCondition.getMappings().get(i);
            PostingExtender posting = group.getPostings().get(i);

            if (!posting.getAccountTypeAlias().equals(condition.getAccount_type()) ||
                    !posting.getPostingCcyAlias().equals(condition.getCurrency_code()) ||
                    !posting.getDebitCreditFlag().equals(condition.getDebit_credit())) {
                return false;
            }
        }

        return true;
    }
    public class PostingExtender extends Posting{
        private String PostingCcyAlias;
        private String PostingCcyNumber;
        private String AccountTypeAlias;

        public String getPostingCcyAlias() {
            return PostingCcyAlias;
        }

        public void setPostingCcyAlias(String postingCcyAlias) {
            PostingCcyAlias = postingCcyAlias;
        }

        public String getAccountTypeAlias() {
            return AccountTypeAlias;
        }

        public void setAccountTypeAlias(String accountTypeAlias) {
            AccountTypeAlias = accountTypeAlias;
        }

        public String getPostingCcyNumber() {
            return PostingCcyNumber;
        }

        public void setPostingCcyNumber(String postingCcyNumber) {
            PostingCcyNumber = postingCcyNumber;
        }
    }
    public class PostingGroup{
        public int GroupId;
        public String TbrCode;
        public String MappingType;
        public String FlagCrossValas;
        public String FlagMdmc;
        public Long MappingId;
        public List<vw_tbr_mapping> Mappings = new ArrayList<>();
        public List<PostingExtender> Postings = new ArrayList<>();

        public int getGroupId() {
            return GroupId;
        }

        public void setGroupId(int groupId) {
            GroupId = groupId;
        }

        public String getTbrCode() {
            return TbrCode;
        }

        public void setTbrCode(String tbrCode) {
            TbrCode = tbrCode;
        }

        public String getMappingType() {
            return MappingType;
        }

        public void setMappingType(String mappingType) {
            MappingType = mappingType;
        }

        public List<vw_tbr_mapping> getMappings() {
            return Mappings;
        }

        public void setMappings(List<vw_tbr_mapping> mappings) {
            Mappings = mappings;
        }

        public List<PostingExtender> getPostings() {
            return Postings;
        }

        public void setPostings(List<PostingExtender> postings) {
            Postings = postings;
        }

        public String getFlagCrossValas() {
            return FlagCrossValas;
        }

        public void setFlagCrossValas(String flagCrossValas) {
            FlagCrossValas = flagCrossValas;
        }

        public String getFlagMdmc() {
            return FlagMdmc;
        }

        public void setFlagMdmc(String flagMdmc) {
            FlagMdmc = flagMdmc;
        }

        public Long getMappingId() {
            return MappingId;
        }

        public void setMappingId(Long mappingId) {
            MappingId = mappingId;
        }
    }
}
