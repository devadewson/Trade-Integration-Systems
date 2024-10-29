package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.data.entity.MsAccountType;
import com.maybank.integratorapp.data.entity.MsCurrency;
import com.maybank.integratorapp.data.entity.MsTBRField;
import com.maybank.integratorapp.data.entity.vw_tbr_mapping;
import com.maybank.integratorapp.data.repository.FtiAccountTypeRepository;
import com.maybank.integratorapp.data.repository.VwTbrMappingRepository;
import com.maybank.integratorapp.data.service.FtiAccountTypeService;
import com.maybank.integratorapp.data.service.MsCurrencyService;
import com.maybank.integratorapp.data.service.MsTBRFieldService;
import com.maybank.integratorapp.model.mq.batchposting.request.Posting;
import com.maybank.integratorapp.model.rest.compositetbr.request.RestEnvelope;
import com.maybank.integratorapp.util.DynamicClassGenerator;
import com.maybank.integratorapp.util.DynamicClassPropertyMap;
import com.maybank.integratorapp.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.util.StringUtils.capitalize;

@Component
public class ProcessCompositeTBR {

    @Autowired
    private MsTBRFieldService tbrFieldService;
    @Autowired
    private FtiAccountTypeService ftiAccountTypeService;
    @Autowired
    private MsCurrencyService msCurrencyService;
    @Autowired
    private VwTbrMappingRepository dataDTO;
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

    public void doPosting(List<Posting> data){

        List<PostingGroup> finalData = groupPosting(data);

        // post to TBR
        for (PostingGroup postingGroup:finalData) {
            postTbr(postingGroup);
        }

        // check if its cross valas
        // post to RTGS
    }
    public void postTbr(PostingGroup data){
        try{

            String tbrNumber = data.TbrCode;
            List<PostingExtender> postings = data.getPostings();
            List<vw_tbr_mapping> mappings = data.getMappings();
            List<MsTBRField> fieldsList = tbrFieldService.findFieldsByTbrCode(tbrNumber);

            // transform the currency into IDR/FCY1/FCY2

//            List<String> propertyNames = new ArrayList<>();
//            fieldsList.forEach(s-> propertyNames.add(s.getDestinationField()));

//            Object finalTbr = DynamicClassGenerator.generateClass("TBRData",propertyNames);
//            propertyNames.add("TBRNumber");
//            propertyNames.add("TBRDesc");

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
            instance = mapFieldTBR(instance,dynamicClass,postings,fieldsList);

//            dynamicClass.getMethod("setSourceAccountNo", String.class).invoke(instance, "1002031");
//            String sourceAccountNo = (String) dynamicClass.getMethod("getSourceAccountNo").invoke(instance);

            String url = "http://10.235.66.95:7804/transactionservicesapi/v1/CompositeTBR";
            List<Object> tbrData = new ArrayList<>();
            tbrData.add(instance);
            RestEnvelope envelope = new RestEnvelope();

            envelope.getCompositeTBR().getChannelHeader().setChannelID("COOLPAY");
            envelope.getCompositeTBR().getChannelHeader().setBranchCode("001");

            envelope.getCompositeTBR().getExecuteCompositeTransactionRequest().setTransactionName("Testing New FTI TBR");
            envelope.getCompositeTBR().getExecuteCompositeTransactionRequest().setTBRData(tbrData);

            // do posting to ESB
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper objectMapper = new ObjectMapper();

            String jsonPayload = objectMapper.writerWithDefaultPrettyPrinter() // enable pretty print
                                    .writeValueAsString(envelope);

            System.out.println("Serialized JSON Payload: " + jsonPayload);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            String response = restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();

            System.out.println("Response from API: " + response);
        }catch (Exception e){
            System.out.println(e.getMessage());
//            throw e;
        }
    }
    public List<PostingGroup> groupPosting(List<Posting> listPosting){
//        List<Posting> listPosting = new ArrayList<>();

        // for sample only
        listPosting = populateSamplePosting2();

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
                data.setPostingCcyAlias(currency.getInternalCode());
            }

            finalListPosting.add(data);
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
        List<PostingGroup> groupedPostings = new ArrayList<>();

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

        // printline

        for (PostingGroup group : groupedPostings) {
            System.out.println("TbrCode: " + group.getTbrCode());
            System.out.println("MappingType: " + group.getMappingType());

            System.out.println("Postings:");
            for (Posting posting : group.getPostings()) {
                System.out.println(" - Sequence: " + posting.getPostingSeqNo() +
                        ", AccountType: " + posting.getAccountType() +
                        ", Currency: " + posting.getPostingCcy() +
                        ", DebitCredit: " + posting.getDebitCreditFlag());
            }

        }

        return groupedPostings;

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

                    // Generate method names for the source's getter and the destination's setter
                    String sourceGetterName = "get" + capitalize(sourcePropertyName);

                    // Get Posting Object for mapping
                    PostingExtender _postingData = new PostingExtender();
                    List<PostingExtender> posting = postings.stream().filter(p ->
                            p.getAccountTypeAlias().equals(mapping.getMappingAccountType())
                            && p.getPostingCcy().equals(mapping.getMappingCurrency())
                            && p.getDebitCreditFlag().equals(mapping.getMappingDebitCredit())
                            ).toList();

                    // jika multiple debit/credit found
                    if(posting.stream().count() > 1){

                    }else{
                        _postingData = posting.get(0);
                    }

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
                    !posting.getPostingCcy().equals(condition.getCurrency_code()) ||
                    !posting.getDebitCreditFlag().equals(condition.getDebit_credit())) {
                return false;
            }
        }

        return true;
    }
    public class PostingExtender extends Posting{
        private String PostingCcyAlias;
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
    }
    public class PostingGroup{
        public int GroupId;
        public String TbrCode;
        public String MappingType;
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
    }
}
