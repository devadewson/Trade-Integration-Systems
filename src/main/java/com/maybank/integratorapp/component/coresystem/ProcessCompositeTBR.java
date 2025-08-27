package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.component.system.messageprocessor.LimitUtilizationMessageProcessor;
import com.maybank.integratorapp.data.entity.*;
import com.maybank.integratorapp.data.repository.VwTbrMappingRepository;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.mq.batchposting.request.ExtraData;
import com.maybank.integratorapp.model.mq.batchposting.request.Posting;
import com.maybank.integratorapp.model.restv2.CompositeTbr.response.MsgWrapper;
import com.maybank.integratorapp.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestTemplate;
import com.maybank.integratorapp.model.rest.compositetbr.requestv2.*;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
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

        try{
            this.LoggerId =idLogParent;
//        this.logger.SetLogParent(idLogParent);

            String referenceID = data.stream().findFirst().get().getMasterReference();
            String eventCode = data.stream().findFirst().get().getEventReference();

            // remove the 999 vs 07 posting
            List<Posting> removed = data.stream().filter(x->x.getBackOfficeAccountNo().startsWith("07") || x.getBackOfficeAccountNo().startsWith("999")).toList();
            data.removeAll(removed);

            // RTGS Logic Block
//        if(data.stream().anyMatch(x->x.getAccountType().equals("RPKP")))
//            data = rtgsLogic(data);

            if(data.size()>0){
                // group the posting
                logger.Log(this.LoggerId,"Posting - Group Posting Data","Group posting into pair of debit credit","START");
                List<PostingGroup> finalData = groupPosting(data);
                logger.Log(this.LoggerId,"Posting - Group Posting Data","Group posting into pair of debit credit","END");

                // find case debit ca - credit nostro
                List<PostingGroup> _additionalGroup = new ArrayList<>();
                Iterator<PostingGroup> iterator = finalData.iterator();
                while (iterator.hasNext()) {
                    PostingGroup postingGroup = iterator.next();
                    if(postingGroup.getTbrCode() ==null && postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("NOSTRO"))){
//                        List<Posting> _additionalPosting = ccaNostroLogic(postingGroup);
                        List<Posting> _additionalPosting = ccaNostroLogicNew(postingGroup);
                        if (_additionalPosting.size() > 0) {
                            iterator.remove();  // Safe removal using iterator
                            _additionalGroup = groupPosting(_additionalPosting);
                            logger.Log(this.LoggerId, "CA-NOSTRO LOGIC", "Finished", "DEBUG");
                        }
                    }

                }
//            logger.Log(this.LoggerId,"Posting", "Add to final data", "DEBUG");

                if(_additionalGroup.size()>0){
                    finalData.addAll(_additionalGroup);

                }
//            logger.Log(this.LoggerId,"Posting", "Add to final data2", "DEBUG");


                PostingGroupSorter.sortPostingGroups(finalData);

                for (PostingGroup postingGroup:finalData) {
//                logger.Log(this.LoggerId,"Posting", "Write to log table1", "DEBUG");

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
//                logger.Log(this.LoggerId,"Posting", "Write to log table2", "DEBUG");

                    FtiTransactionDetailPostingGroup _group = new FtiTransactionDetailPostingGroup();
                    _group.setDetailId(ftiTransactionDetail.getId());
                    _group.setGroupId(String.valueOf(postingGroup.getGroupId()));
                    _group.setTbrCode(postingGroup.getTbrCode());
                    _group.setFlagCrossValas(postingGroup.getFlagCrossValas());
                    _group.setFlagMdmc(postingGroup.getFlagMdmc());
                    _group.setMappingType(postingGroup.getMappingType());

                    _group = ftiTransactionDetailPostingGroupService.save(_group);
//                logger.Log(this.LoggerId,"Posting", "Write to log table3", "DEBUG");

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
//                logger.Log(this.LoggerId,"Posting", "Write to log table4", "DEBUG");

                    // check if its cross valas
                    if (postingGroup.getFlagCrossValas().equals("N"))
                    {
                        logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting the data into ESB", "START");
                        postTbr(referenceID,postingGroup, _group.getId(),ftiTransactionDetail.getId());
                        logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting the data into ESB", "END");

                    }
                    else{
                        // do cross valas logic here
                        logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting Cross Valas Data the data into ESB", "START");

                        logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "Map and Posting Cross Valas Data the data into ESB", "END");

                    }

                    // check & post to RTGS
//                    if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("RPKP")));{
//                        postRtgs(postingGroup);
//                    }

                }

            }else{
                FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                ftiTransactionDetail.setTransMessageLogId(LoggerId);
                ftiTransactionDetail.setFtiEvent(eventCode);
                ftiTransactionDetail.setCoreSysName("FMS-CompositeTBR");
                ftiTransactionDetail.setTransName("Posting");
                ftiTransactionDetail.setAdditionalInfo1("Empty");
                ftiTransactionDetail.setAdditionalInfo2("-");
                ftiTransactionDetail.setAdditionalInfo3("-");
                ftiTransactionDetail.setAdditionalInfo4("-");
                ftiTransactionDetail.setAdditionalInfo5("-");
                ftiTransactionDetail = ftiTransactionDetailService.createDetailByMasterRefNo(referenceID, ftiTransactionDetail);
//                logger.Log(this.LoggerId,"Posting", "Write to log table2", "DEBUG");

            }

        }
        catch (Exception e){
            e.printStackTrace();
            logger.Log(this.LoggerId,"Posting", "Error :"+e.getMessage(), "ERROR");

        }


    }
    private List<Posting> ccaNostroLogicNew(PostingGroup postingGroup) {
        List<Posting> data = new ArrayList<>();
        String groupId = "";
        String branch = "";
        String account = "";
        String PS_Account = parameterService.findValueByPrmKey("PS_Account");
        String TripHO_Account = parameterService.findValueByPrmKey("TripHO_Account");
        String TripBranch_Account = parameterService.findValueByPrmKey("TripBranch_Account");

        try{
            String _postingCurrency = postingGroup.getPostings().stream().findFirst().get().getPostingCcy();
            if (postingGroup.getPostings().stream().allMatch(x->x.getPostingCcy().equals(_postingCurrency))) {
                /*      Case1
                CA vs NOSTRO (Pair)
                Menjadi
                CA vs CA TRIP Cabang
                GL TRIP HO vs NOSTRO
                */
                if(postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")&& x.getDebitCreditFlag().equals("D")).count()==1
                        && postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("NOSTRO")&& x.getDebitCreditFlag().equals("C")).count()==1
                        && postingGroup.getPostings().size() == 2){
                        PostingExtender Debit_CA = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")
                            && x.getDebitCreditFlag().equals("D")).findFirst().get();
                        groupId = Debit_CA.getExtraData().getGroupID();
                        account = PS_Account;
                        branch = findBranchPosting(postingGroup);
                        PostingExtender Debit_GL_0 = makeShadowLeg(Debit_CA,"A1166","C",groupId,branch,account);
                        data.add(Debit_GL_0);

//                    groupId = "999991";
//                    branch = findBranchPosting(postingGroup);
//                    account = TripBranch_Account.replace("xxx",branch);
//                    PostingExtender Debit_CA = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")
//                            && x.getDebitCreditFlag().equals("D")).findFirst().get();
//                    Debit_CA.getExtraData().setGroupID(groupId);
//                    PostingExtender Credit_CA_Trip_Cabang = makeShadowLeg(Debit_CA,"CCA","C",groupId,branch,account);
//                    data.add(Debit_CA);
//                    data.add(Credit_CA_Trip_Cabang);
//
//                    groupId = "999992";
////                branch = findBranchPosting(postingGroup);
//                    account = TripHO_Account;
//                    branch = "999";
//                    PostingExtender Credit_Nostro = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("NOSTRO")
//                            && x.getDebitCreditFlag().equals("C")).findFirst().get();
//                    Credit_Nostro.getExtraData().setGroupID(groupId);
//                    Credit_Nostro.setAccountType("A1165");
//                    Credit_Nostro.getExtraData().setCustBranchFacility(branch);
//                    PostingExtender Debit_CA_Trip_HO = makeShadowLeg(Credit_Nostro,"CCA","D",groupId,branch,account);
//                    data.add(Debit_CA_Trip_HO);
//                    data.add(Credit_Nostro);


                }
                /*      Case2
                CA vs NOSTRO (Pair)
                Menjadi
                CA vs GL PS
                (Case2 Extra GL vs GL PS)
                GL PS vs CA TRIP Cabang
                GL TRIP HO vs NOSTRO
                (Case2 Extra GL PS vs GL)
                */
                else if(postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")&& x.getDebitCreditFlag().equals("D")).count()==1
                        && postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("NOSTRO")&& x.getDebitCreditFlag().equals("C")).count()==1
                        && postingGroup.getPostings().size() > 2){
                    // CA vs GL PS
                    groupId = "999991";
                    branch = findBranchPosting(postingGroup);
                    account = PS_Account;
                    PostingExtender Debit_CA = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")
                            && x.getDebitCreditFlag().equals("D")).findFirst().get();
                    Debit_CA.getExtraData().setGroupID(groupId);
                    PostingExtender Credit_GL_PS = makeShadowLeg(Debit_CA,"A1166","C",groupId,branch,account);
                    data.add(Debit_CA);
                    data.add(Credit_GL_PS);
                    // jika ada Debit GL
                    if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("GL")&& x.getDebitCreditFlag().equals("D"))){
                        groupId = "99996";
//                        branch = findBranchPosting(postingGroup);
                        account = PS_Account;
                        int i = 1;
                        for (PostingExtender posting:
                                postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("GL")
                                        && x.getDebitCreditFlag().equals("D")).toList()) {
                            groupId = groupId+(String.valueOf(i));
                            PostingExtender Debit_GL = posting;
                            Debit_GL.getExtraData().setGroupID(groupId);
                            PostingExtender Credit_PS = makeShadowLeg(Debit_GL,"A1166","C",groupId,branch,account);
                            data.add(Debit_GL);
                            data.add(Credit_PS);
                            i++;
                        }

                    }

                    // GL PS vs CA Trip Cabang
                    groupId = "999992";
                    branch = findBranchPosting(postingGroup);
                    account = PS_Account;
                    PostingExtender Credit_Nostro = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("NOSTRO")
                            && x.getDebitCreditFlag().equals("C")).findFirst().get();


                    PostingExtender Debit_GL_PS = makeShadowLeg(Credit_Nostro,"A1166","D",groupId,branch,account);
                    account = TripBranch_Account.replace("xxx",branch);
                    PostingExtender Credit_CA_Trip_Cabang = makeShadowLeg(Credit_Nostro,"CCA","C",groupId,branch,account);
                    data.add(Debit_GL_PS);
                    data.add(Credit_CA_Trip_Cabang);

                    // CA Trip HO vs Nostro
                    groupId = "999993";
//                    branch = findBranchPosting(postingGroup);
                    account = TripHO_Account;
                    branch = "999";
                    Credit_Nostro.getExtraData().setGroupID(groupId);
                    Credit_Nostro.setAccountType("A1165");
                    PostingExtender Debit_Trip_HO = makeShadowLeg(Credit_Nostro,"CCA","D",groupId,branch,account);
                    data.add(Debit_Trip_HO);
                    data.add(Credit_Nostro);


                    // jika ada Credit GL
                    if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("GL")
                            && x.getDebitCreditFlag().equals("C"))){
                        groupId = "99997";
                        branch = findBranchPosting(postingGroup);
                        account = PS_Account;
                        int i = 1;
                        for (PostingExtender posting:
                        postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("GL")
                                && x.getDebitCreditFlag().equals("C")).toList()) {
                            groupId = groupId+(String.valueOf(i));
                            PostingExtender Credit_GL = posting;
                            Credit_GL.getExtraData().setGroupID(groupId);
                            PostingExtender Debit_PS = makeShadowLeg(Credit_GL,"A1166","D",groupId,branch,account);
                            data.add(Debit_PS);
                            data.add(Credit_GL);
                            i++;
                        }

                    }
                }
                else if(postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("NOSTRO")&& x.getDebitCreditFlag().equals("D")).count()==1
                        && postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")&& x.getDebitCreditFlag().equals("C")).count()==1
                        && postingGroup.getPostings().size() > 2){
                    // NOSTRO vs GL TRIP
                    groupId = "999991";
                    branch = "999";
                    account = TripHO_Account;
                    PostingExtender Debit_NOSTRO = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("NOSTRO")
                        && x.getDebitCreditFlag().equals("D")).findFirst().get();
                    Debit_NOSTRO.getExtraData().setGroupID(groupId);
                    PostingExtender Credit_Trip_HO = makeShadowLeg(Debit_NOSTRO,"CCA","C",groupId,branch,account);
                    data.add(Debit_NOSTRO);
                    data.add(Credit_Trip_HO);
                    // jika ada Debit GL
                    if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("GL")
                            && x.getDebitCreditFlag().equals("D"))){
                        groupId = "99996";
                        branch = findBranchPosting(postingGroup);
                        account = PS_Account;
                        int i = 1;
                        for (PostingExtender posting:
                                postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("GL")
                                        && x.getDebitCreditFlag().equals("D")).toList()) {
                            groupId = groupId+(String.valueOf(i));
                            PostingExtender Debit_GL = posting;
                            Debit_GL.getExtraData().setGroupID(groupId);
                            PostingExtender Credit_PS = makeShadowLeg(Debit_GL,"A1166","C",groupId,branch,account);
                            data.add(Debit_GL);
                            data.add(Credit_PS);
                            i++;
                        }

                    }

                    // CA TRIP CABANG vs GL PS
                    groupId = "999992";
                    branch = findBranchPosting(postingGroup);
                    account = PS_Account;
                    PostingExtender Credit_GL_PS = makeShadowLeg(Debit_NOSTRO,"A1166","C",groupId,branch,account);
                    PostingExtender Debit_Trip_CABANG = makeShadowLeg(Debit_NOSTRO,"CCA","D",groupId,branch,account);
                    data.add(Debit_Trip_CABANG);
                    data.add(Credit_GL_PS);

                    // Trip Cabang vs Nostro
                    PostingExtender Credit_CA = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")
                            && x.getDebitCreditFlag().equals("C")).findFirst().get();
                    Credit_CA.getExtraData().setGroupID(groupId);
                    groupId = "999993";
                    branch = findBranchPosting(postingGroup);
                    account = PS_Account;
                    PostingExtender Debit_GL_PS = makeShadowLeg(Credit_CA,"A1166","D",groupId,branch,account);
                    data.add(Debit_GL_PS);
                    data.add(Credit_CA);

                    // jika ada Credit GL
                    if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("GL")
                            && x.getDebitCreditFlag().equals("C"))){
                        groupId = "99997";
                        branch = findBranchPosting(postingGroup);
                        account = PS_Account;
                        int i = 1;
                        for (PostingExtender posting:
                                postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("GL")
                                        && x.getDebitCreditFlag().equals("C")).toList()) {
                            groupId = groupId+(String.valueOf(i));
                            PostingExtender Credit_GL = posting;
                            Credit_GL.getExtraData().setGroupID(groupId);
                            PostingExtender Debit_PS = makeShadowLeg(Credit_GL,"A1166","D",groupId,branch,account);
                            data.add(Debit_PS);
                            data.add(Credit_GL);
                            i++;
                        }

                    }
                }
            }
        }catch (Exception ex){
            logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Error :"+ex.getMessage(), "ERROR");

        }



/*
        Case2
        CA vs NOSTRO (With Extra GL Leg(s))
        Menjadi
        CA vs GL PS
        GL PS vs CA TRIP CABANG
        GL TRIP HO vs NOSTRO
        GL PS vs GL Leg(s)

        Case3 (Normal)
        Nostro vs CA (Pair)

        Case4
        Nostro vs CA (With extra GL Leg(s))
        Menjadi
        Nostro vs GL Trip HO
        CA Trip Cabang vs GL PS
        GL PS vs CA
        GL PS vs GL Leg(s)

        Case5
        Nostro & CA vs GL

*/

        return data;
    }
    private List<Posting> ccaNostroLogic(PostingGroup postingGroup) {
        List<Posting> data = new ArrayList<>();
/*      Case1
        CA vs NOSTRO (Pair)
        Menjadi
        CA vs CA TRIP Cabang
        GL TRIP HO vs NOSTRO

        Case2
        CA vs NOSTRO (With Extra GL Leg(s))
        Menjadi
        CA vs GL PS
        GL PS vs CA TRIP CABANG
        GL TRIP HO vs NOSTRO
        GL PS vs GL Leg(s)

        Case3 (Normal)
        Nostro vs CA (Pair)

        Case4
        Nostro vs CA (With extra GL Leg(s))
        Menjadi
        Nostro vs GL Trip HO
        CA Trip Cabang vs GL PS
        GL PS vs CA
        GL PS vs GL Leg(s)

        Case5
        Nostro & CA vs GL

*/

        try{
            String _postingCurrency = postingGroup.getPostings().stream().findFirst().get().getPostingCcy();
//                logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Currency : "+_postingCurrency, "DEBUG");
            if (postingGroup.getPostings().stream().allMatch(x->x.getPostingCcy().equals(_postingCurrency))){
//                    logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Con1 : "+postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("NOSTRO")), "DEBUG");
                if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("NOSTRO")&& x.getDebitCreditFlag().equals("C"))){
//                        logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Con2 : "+postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("CA")), "DEBUG");

                    if(postingGroup.getPostings().stream().anyMatch(x->x.getAccountTypeAlias().equals("CA")&& x.getDebitCreditFlag().equals("D"))){
//                            logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Pair1 Start", "DEBUG");
                        String _groupId = "99999";

                        String cifno = postingGroup.getPostings().stream().findFirst().get().getCustomerMnemonic();
                        if (postingGroup.getPostings().stream().findFirst().get().getCustomerMnemonic() == null){
                            cifno = postingGroup.getPostings().stream().findFirst().get().getRelatedParty();
                        }
                        String postingBranch = postingGroup.getPostings().stream().findFirst().get().getPostingBranch();
//            String postingBranch = data.getPostings().stream().findFirst().get().getExtraData().getCustBranchFacility();
//                        String _branch = _newDebit.getExtraData().getCustBranchFacility();

                        String _branch = "003";

                        if(postingBranch.startsWith("9"))
                        {
                            MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(cifno);
                            if(postingBranch.equals("906"))
                                _branch = _company.getCbranch();
                            else
                                _branch = _company.getIbranch();

                        }

                        // CA v GL Penampungan Sementara
                        Posting _newDebit = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("CA")).findFirst().get();
                        _newDebit.getExtraData().setGroupID("91"+_groupId);
                        PostingExtender _newCreditPair = new PostingExtender();
                        ReflectionUtils.copyProperties(_newDebit,_newCreditPair);
                        _newCreditPair.setPostingCcy(_newDebit.getPostingCcy());
                        _newCreditPair.setPostingAmount(_newDebit.getPostingAmount());
                        _newCreditPair.setAccountType("A1166");
//                            _newCreditPair.setBackOfficeAccountNo("299999"+_branch+"0");
                        _newCreditPair.setBackOfficeAccountNo("0116600001");
                        if(_newDebit.getDebitCreditFlag().equals("C")){
                            _newCreditPair.setDebitCreditFlag("D");
                        }
                        else{
                            _newCreditPair.setDebitCreditFlag("C");

                        }
                        _newCreditPair.setExtraData(new ExtraData());
                        _newCreditPair.getExtraData().setGroupID("91"+_groupId);
                        _newCreditPair.getExtraData().setCustBranchFacility(_branch);
//                            logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Pair1 End", "DEBUG");

                        // GL v CA Trip
                        PostingExtender _newGhostPair = new PostingExtender();
                        ReflectionUtils.copyProperties(_newCreditPair,_newGhostPair);
                        _newGhostPair.setPostingCcy(_newDebit.getPostingCcy());
                        _newGhostPair.setPostingAmount(_newDebit.getPostingAmount());
                        _newGhostPair.setAccountType("A1166");
//                            _newCreditPair.setBackOfficeAccountNo("299999"+_branch+"0");
                        _newGhostPair.setBackOfficeAccountNo("0116600001");
                        _newGhostPair.setDebitCreditFlag("D");
                        _newGhostPair.setExtraData(new ExtraData());
                        _newGhostPair.getExtraData().setGroupID("92"+_groupId);
                        _newGhostPair.getExtraData().setCustBranchFacility(_branch);

                        PostingExtender _newGhostPair2 = new PostingExtender();
                        ReflectionUtils.copyProperties(_newGhostPair,_newGhostPair2);
                        _newGhostPair2.setPostingCcy(_newDebit.getPostingCcy());
                        _newGhostPair2.setPostingAmount(_newDebit.getPostingAmount());
                        _newGhostPair2.setAccountType("A1115");
                        _newGhostPair2.setBackOfficeAccountNo("299999"+_branch+"0");
//                            _newGhostPair2.setBackOfficeAccountNo("0116600001");
                        _newGhostPair2.setDebitCreditFlag("C");
                        _newGhostPair2.setExtraData(new ExtraData());
                        _newGhostPair2.getExtraData().setGroupID("92"+_groupId);
                        _newGhostPair2.getExtraData().setCustBranchFacility(_branch);

//                            logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Pair2 Start", "DEBUG");

                        // GL Trip v Nostro
                        Posting _newCredit = postingGroup.getPostings().stream().filter(x->x.getAccountTypeAlias().equals("NOSTRO")).findFirst().get();
                        _newCredit.getExtraData().setGroupID("93"+_groupId);
                        PostingExtender _newDebitPair = new PostingExtender();
                        ReflectionUtils.copyProperties(_newCredit,_newDebitPair);

                        _newDebitPair.setAccountType("A1166");
                        _newDebitPair.setBackOfficeAccountNo("2999998880");
//                            _newDebitPair.setBackOfficeAccountNo("0116600001");
                        if(_newCredit.getDebitCreditFlag().equals("C")){
                            _newDebitPair.setDebitCreditFlag("D");
                        }
                        else{
                            _newDebitPair.setDebitCreditFlag("C");

                        }
                        _newDebitPair.setExtraData(new ExtraData());
                        _newDebitPair.getExtraData().setGroupID("93"+_groupId);
                        _newDebitPair.getExtraData().setCustBranchFacility(_branch);
//                            logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Pair2 End", "DEBUG");

                        data.add(_newDebit);
                        data.add(_newCreditPair);
                        data.add(_newGhostPair);
                        data.add(_newGhostPair2);
                        data.add(_newCredit);
                        data.add(_newDebitPair);
                    }
                }
            }

        }catch (Exception e){
            logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Error :"+e.getMessage(), "ERROR");

        }

//        logger.Log(this.LoggerId,"CA-NOSTRO LOGIC", "Test", "END");

        return data;
    }
    private String findBranchPosting(PostingGroup postingGroup){
        String _branch = "003";
        if(postingGroup.getPostings().stream().findFirst().get().getExtraData() !=null
                && postingGroup.getPostings().stream().findFirst().get().getExtraData().getCustBranchFacility() !=null
        ){
            _branch = postingGroup.getPostings().stream().findFirst().get().getExtraData().getCustBranchFacility();


        }else{
            String cifno = postingGroup.getPostings().stream().findFirst().get().getCustomerMnemonic();
            if (postingGroup.getPostings().stream().findFirst().get().getCustomerMnemonic() == null){
                cifno = postingGroup.getPostings().stream().findFirst().get().getRelatedParty();
            }
            String postingBranch = postingGroup.getPostings().stream().findFirst().get().getPostingBranch();

            if(postingBranch.startsWith("9"))
            {
                MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(cifno);
                if(_company!=null){
                    if(postingBranch.equals("906"))
                        _branch = _company.getCbranch();
                    else
                        _branch = _company.getIbranch();

                }

            }
        }


        return _branch;
    }
    private PostingExtender makeShadowLeg(PostingExtender source,String accType,String dcFlag,String groupId,String branch,String account ){

        PostingExtender _newShadowLeg = new PostingExtender();
        ReflectionUtils.copyProperties(source,_newShadowLeg);
        _newShadowLeg.setAccountType(accType);
        _newShadowLeg.setBackOfficeAccountNo(account);
        _newShadowLeg.setDebitCreditFlag(dcFlag);
        _newShadowLeg.setExtraData(new ExtraData());
        _newShadowLeg.getExtraData().setGroupID(groupId);
        _newShadowLeg.getExtraData().setCustBranchFacility(branch);

        return _newShadowLeg;
    }
    private List<Posting> rtgsLogic(List<Posting> data) {
        List<Posting> finalPosting = new ArrayList<>();

        try {

            String product = data.stream().findFirst().get().getProductReference();
            // ambil semua RPKP
            List<Posting> allRPKP = data.stream().filter(x->x.getAccountType().equals("RPKP")).toList();

            //

            // case IFB
            if(product.equals("IFB")){



                // takeout Debit GL
                // takeout Credit GL
            }else if(product.equals("IBS")){
                // ambil semua RPKP
                // takeout Debit GL
                // takeout Credit CA
            }else{
                // normal posting
            }

        }
        catch(Exception ex){
            logger.Log(this.LoggerId,"Posting - Posting Data to ESB", "RTGS Logic Block", "ERROR",ex.getMessage());

        }

        return finalPosting;
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

            String TripHO_Account = parameterService.findValueByPrmKey("TripHO_Account");
            String TripBranch_Account = parameterService.findValueByPrmKey("TripBranch_Account");
            String branch = data.getPostings().get(0).getCoreSystemBranch();
            String clientUserId = data.getPostings().get(0).getCoreUid();
            String clientSpvUserId = data.getPostings().get(0).getCoreSpvUid();
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

            //rtgs logic block
            if(postings.stream().filter(x -> x.getPaymentSystem() != null).anyMatch(x->x.getPaymentSystem().contains("RTGS") && x.getDebitCreditFlag().equals("C"))){
                logger.Log(this.LoggerId,"Posting "+groupId, "RTGS Posting", "DATA-REQ");

                tbrNumber = "EFTD";
                boolean isSKN = !postings.stream().filter(x -> x.getPaymentSystem() != null
                        && x.getPaymentSystem().contains("RTGS")
                        && x.getDebitCreditFlag().equals("C")).findFirst().get().getMainTransferMethod().equals("RS");
                String tbrName = "TBR EFTD-"+(isSKN?"SKN":"RTGS");
                fieldsList = tbrFieldService.findFieldsByTbrName(tbrName).stream().filter(s->s.getSourceField()!=null || s.getDefaultValue()!=null).toList();

                for (Posting p:
                     postings) {
                    if(p.getBackOfficeAccountNo().equals(TripHO_Account)){

                        String _newAcc = TripBranch_Account.replace("xxx",branch);
                        _newAcc = _newAcc.replace("2999",("2"+branch));

                        p.setBackOfficeAccountNo(_newAcc);
                    }
                }

                List<DynamicClassPropertyMap> finalPropertyMapList = new ArrayList<>();
                finalPropertyMapList.add(new DynamicClassPropertyMap("TBRNumber","String"));
                finalPropertyMapList.add(new DynamicClassPropertyMap("TBRDesc","String"));

                fieldsList.forEach(s->{
                    if(s.getSourceField()==null && s.getDefaultValue()==null){
                        //dont add to property maplist

                    }else{
                        finalPropertyMapList.add(new DynamicClassPropertyMap(
                                s.getDestinationField(),
                                s.getDestinationFieldDataType() == null?"String":s.getDestinationFieldDataType()));
                    }

                });
                dynamicClass = DynamicClassGenerator.generateClass("TBRData", finalPropertyMapList);

                instance = dynamicClass.getDeclaredConstructor().newInstance();

                dynamicClass.getMethod("setTBRNumber", String.class).invoke(instance, tbrNumber);
                dynamicClass.getMethod("setTBRDesc", String.class).invoke(instance, "FTI_"+tbrName);


                // do the field-value mapping
                instance = mapFieldTBRNew(instance,dynamicClass,postings,fieldsList);
                tbrData.add(instance);

            }
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

            // Get current date and time
            Date now = new Date();

            // Create formatters
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

            // Format the date and time
            String formattedDate = dateFormatter.format(now);
            String formattedTime = timeFormatter.format(now);

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

                String cifno = posting.getCustomerMnemonic();
                if(posting.getCustomerMnemonic() == null){
                    cifno = posting.getRelatedParty();
                }
                String postingBranch = posting.getPostingBranch();

                String branch = "003";
                String clientUserId = "7755";
                String clientSpvUserId = "7766";


                if(posting.getExtraData()!=null){
                    if(!posting.getExtraData().getCustBranchFacility().isEmpty()){

                        postingBranch = posting.getExtraData().getCustBranchFacility();
                        branch = postingBranch;

                    }
                }else{
                    if(postingBranch.startsWith("9"))
                    {
                        MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(cifno);
                        if(_company!=null){
                            if(postingBranch.equals("906"))
                                branch = _company.getCbranch();
                            else
                                branch = _company.getIbranch();

                        }

                    }
                }


                MsBranch _branch = msBranchService.getByBranchCode(branch);

                if(_branch!=null){
                    clientUserId = _branch.getUserId();
                    clientSpvUserId = _branch.getSpvUserId();
                    if(clientSpvUserId.equals("-"))
                        clientSpvUserId = clientUserId;
                }

                //case RTGS
                if(data.getSettlementAccountPartyCustId()!=null){
                    if(data.getSettlementAccountPartyCustId().startsWith("LCUS-R"))
                        data.setRtgsBankCode(data.getSettlementAccountPartyCustId().replace("LCUS-R",""));
                }

                data.setCoreSystemBranch(branch);
                data.setCoreUid(clientUserId);
                data.setCoreSpvUid(clientSpvUserId);
                data.setRtgsTransDate(formattedDate);
                data.setRtgsTransTime(formattedTime);

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
                logger.Log(this.LoggerId,"Posting - Group Posting Data",
                        "TbrCode: " + group.getTbrCode()
                        +" | "+
                        "GroupID : " + group.getGroupId()
                        +" | "+
                        "MappingType : " + group.getMappingType()
                        +" | "+
                        "MDMC : " + group.getFlagMdmc()
                        +" | "+
                        "Cross Valas : " + group.getFlagCrossValas()
                        , "PROCESS");
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
                if (i>0) {
                    PostingExtender prevPost = filteredPostings.get(i -1);
                    if (post.getAccountTypeAlias().equals(prevPost.getAccountTypeAlias())) {
                        seq++;
                    }
                }
                // find all fields for this posting
                int finalSeq = seq;
                logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map "
                        +post.getDebitCreditFlag()
                        +" "
                        +post.getAccountTypeAlias()
                        +" seq "+String.valueOf(finalSeq), "DEBUG");

                List<MsTBRField> listField = listMapping.stream().filter(x->
                        x.getMappingDebitCredit().equals(post.getDebitCreditFlag())
                                && x.getMappingAccountType().equals(post.getAccountTypeAlias())
                                && Arrays.stream(x.getMappingPosition().split(",")).anyMatch(z->z.equals(String.valueOf(finalSeq)))
                ).toList();
                for (MsTBRField field :listField){
                    String destinationPropertyName = field.getDestinationField();
                    String destinationSetterName = "set" + capitalize(destinationPropertyName);
                    String sourcePropertyName = field.getSourceField();
                    String sourceGetterName = "get" + capitalize(sourcePropertyName);
                    int fieldLength = field.getFieldLength();
                    if(!mappedFields.contains(destinationPropertyName)){

                        mappedFields.add(destinationPropertyName);
                    }
                    if(field.getDefaultValue() != null && !field.getDefaultValue().isEmpty()){

                        Object value = field.getDefaultValue();
                        logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map default value "+String.valueOf(value)+" into "+destinationPropertyName+" ESB", "DEBUG");

                        if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                            dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class)
                                    .invoke(instance,
                                            field.getDestinationFieldDataType().equals("Integer")?Integer.parseInt(value.toString()):String.valueOf(value)
                                    );
                        }else{
                            dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                        }
                        continue;
                    }
                    Class<?> sourceClass = post.getClass();
                    Class<?> destinationClass = dynamicClass;

                    Method sourceGetter = sourceClass.getMethod(sourceGetterName);
//                    Method destinationSetter = destinationClass.getMethod(destinationSetterName, mapping.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class);

                    // Invoke the source getter method to get the value
                    Object value = sourceGetter.invoke(post);
                    logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map "+sourcePropertyName+" data into "+destinationPropertyName+" ESB", "DEBUG",String.valueOf(value));

                    if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                        dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class)
                                .invoke(instance,
                                        field.getDestinationFieldDataType().equals("Integer")?Integer.parseInt(value.toString()):String.valueOf(value)
                                );
                    }else{
                        String _value = String.valueOf(value);
                        if(_value.length()>fieldLength)
                            value = _value.substring(0,fieldLength);

                        dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

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
                if (i>0) {
                    PostingExtender prevPost = filteredPostings.get(i -1);
                    if (post.getAccountTypeAlias().equals(prevPost.getAccountTypeAlias())) {
                        seq++;
                    }
                }
                // find all fields for this posting
                int finalSeq = seq;
                logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map "
                        +post.getDebitCreditFlag()
                        +" "
                        +post.getAccountTypeAlias()
                        +" seq "+String.valueOf(finalSeq), "DEBUG");

                List<MsTBRField> listField = listMapping.stream().filter(x->
                        x.getMappingDebitCredit().equals(post.getDebitCreditFlag())
                        && x.getMappingAccountType().equals(post.getAccountTypeAlias())
                        && Arrays.stream(x.getMappingPosition().split(",")).anyMatch(z->z.equals(String.valueOf(finalSeq)))
                ).toList();
                for (MsTBRField field :listField){
                    String destinationPropertyName = field.getDestinationField();
                    String destinationSetterName = "set" + capitalize(destinationPropertyName);
                    String sourcePropertyName = field.getSourceField();
                    String sourceGetterName = "get" + capitalize(sourcePropertyName);
                    int fieldLength = field.getFieldLength();
                    if(!mappedFields.contains(destinationPropertyName)){

                        mappedFields.add(destinationPropertyName);
                    }

                    if(field.getDefaultValue() != null && !field.getDefaultValue().isEmpty()){

                        Object value = field.getDefaultValue();
                        logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map default value "+String.valueOf(value)+" into "+destinationPropertyName+" ESB", "DEBUG");

                        if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                            dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class)
                                    .invoke(instance,
                                    field.getDestinationFieldDataType().equals("Integer")?Integer.parseInt(value.toString()):String.valueOf(value)
                                    );

                        }else{
                            dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

                        }
                        continue;
                    }

                    Class<?> sourceClass = post.getClass();
                    Class<?> destinationClass = dynamicClass;

                    Method sourceGetter = sourceClass.getMethod(sourceGetterName);
//                    Method destinationSetter = destinationClass.getMethod(destinationSetterName, mapping.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class);

                    // Invoke the source getter method to get the value
                    Object value = sourceGetter.invoke(post);
                    logger.Log(this.LoggerId,"Posting - Map Data to ESB", "Map "+sourcePropertyName+" data into "+destinationPropertyName+" ESB", "DEBUG",String.valueOf(value));
                    if((field.getDestinationFieldDataType() != null && !field.getDestinationFieldDataType().isEmpty())){
                        dynamicClass.getMethod(destinationSetterName, field.getDestinationFieldDataType().equals("Integer")?Integer.class:String.class)
                                .invoke(instance,
                                        field.getDestinationFieldDataType().equals("Integer")?Integer.parseInt(value.toString()):String.valueOf(value)
                                );
                    }else{
                        String _value = String.valueOf(value);
                        if(_value.length()>fieldLength)
                            value = _value.substring(0,fieldLength);
                        dynamicClass.getMethod(destinationSetterName, String.class).invoke(instance, value);

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
    public class PostingExtender extends Posting {
        private String PostingCcyAlias;
        private String PostingCcyNumber;
        private String AccountTypeAlias;
        private String CoreSystemBranch;
        private String CoreUid;
        private String CoreSpvUid;
        private String RtgsReference;
        private String RtgsTransDate;
        private String RtgsTransTime;
        private String RtgsBankCode;

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

        public String getCoreSystemBranch() {
            return CoreSystemBranch;
        }

        public void setCoreSystemBranch(String coreSystemBranch) {
            CoreSystemBranch = coreSystemBranch;
        }

        public String getCoreUid() {
            return CoreUid;
        }

        public void setCoreUid(String coreUid) {
            CoreUid = coreUid;
        }

        public String getRtgsReference() {
            return RtgsReference;
        }

        public void setRtgsReference(String rtgsReference) {
            RtgsReference = rtgsReference;
        }

        public String getRtgsTransDate() {
            return RtgsTransDate;
        }

        public void setRtgsTransDate(String rtgsTransDate) {
            RtgsTransDate = rtgsTransDate;
        }

        public String getRtgsTransTime() {
            return RtgsTransTime;
        }

        public void setRtgsTransTime(String rtgsTransTime) {
            RtgsTransTime = rtgsTransTime;
        }

        public String getCoreSpvUid() {
            return CoreSpvUid;
        }

        public void setCoreSpvUid(String coreSpvUid) {
            CoreSpvUid = coreSpvUid;
        }

        public String getRtgsBankCode() {
            return RtgsBankCode;
        }

        public void setRtgsBankCode(String rtgsBankCode) {
            RtgsBankCode = rtgsBankCode;
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
