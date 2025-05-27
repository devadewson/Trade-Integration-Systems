package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FtiTransactionPosting",schema = "dbo")
@NoArgsConstructor
public class FtiTransactionPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idHeader;
    private String transactionID;

    private String transactionSeqNo;

    private String masterKey;
    private String eventKey;
    private String postingBranch;
    private String inputBranch;
    private String productReference;
    private String masterReference;
    private String eventReference;
    private String internalRecnRef;
    private String postingSeqNo;
    private String accountNumber;
    private String backOfficeAccountNo;
    private String externalAccountNo;
    private String otherAccountNumber;
    private String iban;
    private String accountIdentifier;
    private String customerMnemonic;
    private String accountType;
    private String spskMnemonic;
    private String spskCategoryCode;
    private String application;
    private String debitCreditFlag;
    private String transactionCode;
    private String postingAmount;
    private String postingCcy;
    private String valueDate;
    private String againstCcy;
    private String postingNarrative1;
    private String postingNarrative2;
    private String postingNarrative3;
    private String postingNarrative4;
    private String sundryReferenceCode;
    private String userCode1;
    private String userCode2;
    private String chargeCategorisationCode;
    private String relatedParty;
    private String analysisCode;
    private String parentCountry;
    private String customerType;
    private String team;
    private String beneficiaryName;
    private String originalCcy;
    private String originalAmount;
    private String issueOrContractDate;
    private String otherPartyRef;
    private String bankCode1;
    private String bankCode2;
    private String bankCode3;
    private String bankCode4;
    private String bankCode5;
    private String tenorStart;
    private String tenorEnd;
    private String payReceiveFlag;
    private String settlementTypeVia;
    private String settlementAccountUsed;
    private String settlementPartyAccountNo;
    private String settlementPartyCustID;
    private String settlementPartyBIC;
    private String settlementPartyAddress;
    private String settlementAccountPartyAccount;
    private String settlementAccountPartyCustID;
    private String settlementAccountPartyBIC;
    private String settlementAccountPartyAddress;
    private String settlementAccountAlternativeAccNo;
    private String paymentSystem;
    private String clearingNumber;
    private String settlementSundryReferenceCode;
    private String settlementUserCode1;
    private String settlementUserCode2;
    private String settlementNarrative;
    private String nostroMnemonic;
    private String mainTransferMethod;
    private String payingBankTransliterate;
    private String payingBankAccountNo;
    private String payingBankCustID;
    private String payingBankBIC;
    private String payingBankAddress;
    private String intermediaryBankAccountNo;
    private String intermediaryBankCustID;
    private String intermediaryBankBIC;
    private String intermediaryBankAddress;
    private String counterpartyBankAccountNo;
    private String counterpartyBankCustID;
    private String counterpartyBankBIC;
    private String counterpartyBankAddress;
    private String counterpartyAccountNo;
    private String counterpartyCustID;
    private String counterpartyBIC;
    private String counterpartyAddress;
    private String counterpartyIBAN;
    private String swifTmessageType;
    private String regulatoryReporting;
    private String transactionType;
    private String chargeAmt1;
    private String chargeCcy1;
    private String chargeAmt2;
    private String chargeCcy2;
    private String chargeAmt3;
    private String chargeCcy3;
    private String chargeAmt4;
    private String chargeCcy4;
    private String chargeAmt5;
    private String chargeCcy5;
    private String chargeAmt6;
    private String chargeCcy6;
    private String exchangeRate;
    private String instructionText1;
    private String instructionCode1;
    private String instructionText2;
    private String instructionCode2;
    private String instructionText3;
    private String instructionCode3;
    private String instructionText4;
    private String instructionCode4;
    private String instructionText5;
    private String instructionCode5;
    private String instructionText6;
    private String instructionCode6;
    private String instructedAmount;
    private String instructedCcy;
    private String serviceLevel;
    private String swiftChargesFor;
    private String paymentDetails;
    private String timeDetails1;
    private String timeCode1;
    private String timeDetails2;
    private String timeCode2;
    private String timeDetails3;
    private String timeCode3;
    private String senderToReceiverInfo;
    private String orderingCustomerAccountNo;
    private String orderingCustomerCustID;
    private String orderingCustomerBIC;
    private String orderingCustomerAddress;
    private String chequeNo;
    private String coverTransferMethod;
    private String receiversCorrespondentAccountNo;
    private String receiversCorrespondentCustID;
    private String receiversCorrespondentBIC;
    private String receiversCorrespondentAddress;
    private String thirdReimbursingBankAccountNo;
    private String thirdReimbursingBankCustID;
    private String thirdReimbursingBankBIC;
    private String thirdReimbursingBankAddress;
    private String coverSenderToReceiverInfo;
    private String coverTimeDetails1;
    private String coverTimeCode1;
    private String coverTimeDetails2;
    private String coverTimeCode2;
    private String coverTimeDetails3;
    private String coverTimeCode3;
    private String addMntDelFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionSeqNo() {
        return transactionSeqNo;
    }

    public void setTransactionSeqNo(String transactionSeqNo) {
        this.transactionSeqNo = transactionSeqNo;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getPostingBranch() {
        return postingBranch;
    }

    public void setPostingBranch(String postingBranch) {
        this.postingBranch = postingBranch;
    }

    public String getInputBranch() {
        return inputBranch;
    }

    public void setInputBranch(String inputBranch) {
        this.inputBranch = inputBranch;
    }

    public String getProductReference() {
        return productReference;
    }

    public void setProductReference(String productReference) {
        this.productReference = productReference;
    }

    public String getMasterReference() {
        return masterReference;
    }

    public void setMasterReference(String masterReference) {
        this.masterReference = masterReference;
    }

    public String getEventReference() {
        return eventReference;
    }

    public void setEventReference(String eventReference) {
        this.eventReference = eventReference;
    }

    public String getInternalRecnRef() {
        return internalRecnRef;
    }

    public void setInternalRecnRef(String internalRecnRef) {
        this.internalRecnRef = internalRecnRef;
    }

    public String getPostingSeqNo() {
        return postingSeqNo;
    }

    public void setPostingSeqNo(String postingSeqNo) {
        this.postingSeqNo = postingSeqNo;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBackOfficeAccountNo() {
        return backOfficeAccountNo;
    }

    public void setBackOfficeAccountNo(String backOfficeAccountNo) {
        this.backOfficeAccountNo = backOfficeAccountNo;
    }

    public String getExternalAccountNo() {
        return externalAccountNo;
    }

    public void setExternalAccountNo(String externalAccountNo) {
        this.externalAccountNo = externalAccountNo;
    }

    public String getOtherAccountNumber() {
        return otherAccountNumber;
    }

    public void setOtherAccountNumber(String otherAccountNumber) {
        this.otherAccountNumber = otherAccountNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getCustomerMnemonic() {
        return customerMnemonic;
    }

    public void setCustomerMnemonic(String customerMnemonic) {
        this.customerMnemonic = customerMnemonic;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSpskMnemonic() {
        return spskMnemonic;
    }

    public void setSpskMnemonic(String spskMnemonic) {
        this.spskMnemonic = spskMnemonic;
    }

    public String getSpskCategoryCode() {
        return spskCategoryCode;
    }

    public void setSpskCategoryCode(String spskCategoryCode) {
        this.spskCategoryCode = spskCategoryCode;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getDebitCreditFlag() {
        return debitCreditFlag;
    }

    public void setDebitCreditFlag(String debitCreditFlag) {
        this.debitCreditFlag = debitCreditFlag;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getPostingAmount() {
        return postingAmount;
    }

    public void setPostingAmount(String postingAmount) {
        this.postingAmount = postingAmount;
    }

    public String getPostingCcy() {
        return postingCcy;
    }

    public void setPostingCcy(String postingCcy) {
        this.postingCcy = postingCcy;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getAgainstCcy() {
        return againstCcy;
    }

    public void setAgainstCcy(String againstCcy) {
        this.againstCcy = againstCcy;
    }

    public String getPostingNarrative1() {
        return postingNarrative1;
    }

    public void setPostingNarrative1(String postingNarrative1) {
        this.postingNarrative1 = postingNarrative1;
    }

    public String getPostingNarrative2() {
        return postingNarrative2;
    }

    public void setPostingNarrative2(String postingNarrative2) {
        this.postingNarrative2 = postingNarrative2;
    }

    public String getPostingNarrative3() {
        return postingNarrative3;
    }

    public void setPostingNarrative3(String postingNarrative3) {
        this.postingNarrative3 = postingNarrative3;
    }

    public String getPostingNarrative4() {
        return postingNarrative4;
    }

    public void setPostingNarrative4(String postingNarrative4) {
        this.postingNarrative4 = postingNarrative4;
    }

    public String getSundryReferenceCode() {
        return sundryReferenceCode;
    }

    public void setSundryReferenceCode(String sundryReferenceCode) {
        this.sundryReferenceCode = sundryReferenceCode;
    }

    public String getUserCode1() {
        return userCode1;
    }

    public void setUserCode1(String userCode1) {
        this.userCode1 = userCode1;
    }

    public String getUserCode2() {
        return userCode2;
    }

    public void setUserCode2(String userCode2) {
        this.userCode2 = userCode2;
    }

    public String getChargeCategorisationCode() {
        return chargeCategorisationCode;
    }

    public void setChargeCategorisationCode(String chargeCategorisationCode) {
        this.chargeCategorisationCode = chargeCategorisationCode;
    }

    public String getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(String relatedParty) {
        this.relatedParty = relatedParty;
    }

    public String getAnalysisCode() {
        return analysisCode;
    }

    public void setAnalysisCode(String analysisCode) {
        this.analysisCode = analysisCode;
    }

    public String getParentCountry() {
        return parentCountry;
    }

    public void setParentCountry(String parentCountry) {
        this.parentCountry = parentCountry;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getOriginalCcy() {
        return originalCcy;
    }

    public void setOriginalCcy(String originalCcy) {
        this.originalCcy = originalCcy;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getIssueOrContractDate() {
        return issueOrContractDate;
    }

    public void setIssueOrContractDate(String issueOrContractDate) {
        this.issueOrContractDate = issueOrContractDate;
    }

    public String getOtherPartyRef() {
        return otherPartyRef;
    }

    public void setOtherPartyRef(String otherPartyRef) {
        this.otherPartyRef = otherPartyRef;
    }

    public String getBankCode1() {
        return bankCode1;
    }

    public void setBankCode1(String bankCode1) {
        this.bankCode1 = bankCode1;
    }

    public String getBankCode2() {
        return bankCode2;
    }

    public void setBankCode2(String bankCode2) {
        this.bankCode2 = bankCode2;
    }

    public String getBankCode3() {
        return bankCode3;
    }

    public void setBankCode3(String bankCode3) {
        this.bankCode3 = bankCode3;
    }

    public String getBankCode4() {
        return bankCode4;
    }

    public void setBankCode4(String bankCode4) {
        this.bankCode4 = bankCode4;
    }

    public String getBankCode5() {
        return bankCode5;
    }

    public void setBankCode5(String bankCode5) {
        this.bankCode5 = bankCode5;
    }

    public String getTenorStart() {
        return tenorStart;
    }

    public void setTenorStart(String tenorStart) {
        this.tenorStart = tenorStart;
    }

    public String getTenorEnd() {
        return tenorEnd;
    }

    public void setTenorEnd(String tenorEnd) {
        this.tenorEnd = tenorEnd;
    }

    public String getPayReceiveFlag() {
        return payReceiveFlag;
    }

    public void setPayReceiveFlag(String payReceiveFlag) {
        this.payReceiveFlag = payReceiveFlag;
    }

    public String getSettlementTypeVia() {
        return settlementTypeVia;
    }

    public void setSettlementTypeVia(String settlementTypeVia) {
        this.settlementTypeVia = settlementTypeVia;
    }

    public String getSettlementAccountUsed() {
        return settlementAccountUsed;
    }

    public void setSettlementAccountUsed(String settlementAccountUsed) {
        this.settlementAccountUsed = settlementAccountUsed;
    }

    public String getSettlementPartyAccountNo() {
        return settlementPartyAccountNo;
    }

    public void setSettlementPartyAccountNo(String settlementPartyAccountNo) {
        this.settlementPartyAccountNo = settlementPartyAccountNo;
    }

    public String getSettlementPartyCustID() {
        return settlementPartyCustID;
    }

    public void setSettlementPartyCustID(String settlementPartyCustID) {
        this.settlementPartyCustID = settlementPartyCustID;
    }

    public String getSettlementPartyBIC() {
        return settlementPartyBIC;
    }

    public void setSettlementPartyBIC(String settlementPartyBIC) {
        this.settlementPartyBIC = settlementPartyBIC;
    }

    public String getSettlementPartyAddress() {
        return settlementPartyAddress;
    }

    public void setSettlementPartyAddress(String settlementPartyAddress) {
        this.settlementPartyAddress = settlementPartyAddress;
    }

    public String getSettlementAccountPartyAccount() {
        return settlementAccountPartyAccount;
    }

    public void setSettlementAccountPartyAccount(String settlementAccountPartyAccount) {
        this.settlementAccountPartyAccount = settlementAccountPartyAccount;
    }

    public String getSettlementAccountPartyCustID() {
        return settlementAccountPartyCustID;
    }

    public void setSettlementAccountPartyCustID(String settlementAccountPartyCustID) {
        this.settlementAccountPartyCustID = settlementAccountPartyCustID;
    }

    public String getSettlementAccountPartyBIC() {
        return settlementAccountPartyBIC;
    }

    public void setSettlementAccountPartyBIC(String settlementAccountPartyBIC) {
        this.settlementAccountPartyBIC = settlementAccountPartyBIC;
    }

    public String getSettlementAccountPartyAddress() {
        return settlementAccountPartyAddress;
    }

    public void setSettlementAccountPartyAddress(String settlementAccountPartyAddress) {
        this.settlementAccountPartyAddress = settlementAccountPartyAddress;
    }

    public String getSettlementAccountAlternativeAccNo() {
        return settlementAccountAlternativeAccNo;
    }

    public void setSettlementAccountAlternativeAccNo(String settlementAccountAlternativeAccNo) {
        this.settlementAccountAlternativeAccNo = settlementAccountAlternativeAccNo;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public String getClearingNumber() {
        return clearingNumber;
    }

    public void setClearingNumber(String clearingNumber) {
        this.clearingNumber = clearingNumber;
    }

    public String getSettlementSundryReferenceCode() {
        return settlementSundryReferenceCode;
    }

    public void setSettlementSundryReferenceCode(String settlementSundryReferenceCode) {
        this.settlementSundryReferenceCode = settlementSundryReferenceCode;
    }

    public String getSettlementUserCode1() {
        return settlementUserCode1;
    }

    public void setSettlementUserCode1(String settlementUserCode1) {
        this.settlementUserCode1 = settlementUserCode1;
    }

    public String getSettlementUserCode2() {
        return settlementUserCode2;
    }

    public void setSettlementUserCode2(String settlementUserCode2) {
        this.settlementUserCode2 = settlementUserCode2;
    }

    public String getSettlementNarrative() {
        return settlementNarrative;
    }

    public void setSettlementNarrative(String settlementNarrative) {
        this.settlementNarrative = settlementNarrative;
    }

    public String getNostroMnemonic() {
        return nostroMnemonic;
    }

    public void setNostroMnemonic(String nostroMnemonic) {
        this.nostroMnemonic = nostroMnemonic;
    }

    public String getMainTransferMethod() {
        return mainTransferMethod;
    }

    public void setMainTransferMethod(String mainTransferMethod) {
        this.mainTransferMethod = mainTransferMethod;
    }

    public String getPayingBankTransliterate() {
        return payingBankTransliterate;
    }

    public void setPayingBankTransliterate(String payingBankTransliterate) {
        this.payingBankTransliterate = payingBankTransliterate;
    }

    public String getPayingBankAccountNo() {
        return payingBankAccountNo;
    }

    public void setPayingBankAccountNo(String payingBankAccountNo) {
        this.payingBankAccountNo = payingBankAccountNo;
    }

    public String getPayingBankCustID() {
        return payingBankCustID;
    }

    public void setPayingBankCustID(String payingBankCustID) {
        this.payingBankCustID = payingBankCustID;
    }

    public String getPayingBankBIC() {
        return payingBankBIC;
    }

    public void setPayingBankBIC(String payingBankBIC) {
        this.payingBankBIC = payingBankBIC;
    }

    public String getPayingBankAddress() {
        return payingBankAddress;
    }

    public void setPayingBankAddress(String payingBankAddress) {
        this.payingBankAddress = payingBankAddress;
    }

    public String getIntermediaryBankAccountNo() {
        return intermediaryBankAccountNo;
    }

    public void setIntermediaryBankAccountNo(String intermediaryBankAccountNo) {
        this.intermediaryBankAccountNo = intermediaryBankAccountNo;
    }

    public String getIntermediaryBankCustID() {
        return intermediaryBankCustID;
    }

    public void setIntermediaryBankCustID(String intermediaryBankCustID) {
        this.intermediaryBankCustID = intermediaryBankCustID;
    }

    public String getIntermediaryBankBIC() {
        return intermediaryBankBIC;
    }

    public void setIntermediaryBankBIC(String intermediaryBankBIC) {
        this.intermediaryBankBIC = intermediaryBankBIC;
    }

    public String getIntermediaryBankAddress() {
        return intermediaryBankAddress;
    }

    public void setIntermediaryBankAddress(String intermediaryBankAddress) {
        this.intermediaryBankAddress = intermediaryBankAddress;
    }

    public String getCounterpartyBankAccountNo() {
        return counterpartyBankAccountNo;
    }

    public void setCounterpartyBankAccountNo(String counterpartyBankAccountNo) {
        this.counterpartyBankAccountNo = counterpartyBankAccountNo;
    }

    public String getCounterpartyBankCustID() {
        return counterpartyBankCustID;
    }

    public void setCounterpartyBankCustID(String counterpartyBankCustID) {
        this.counterpartyBankCustID = counterpartyBankCustID;
    }

    public String getCounterpartyBankBIC() {
        return counterpartyBankBIC;
    }

    public void setCounterpartyBankBIC(String counterpartyBankBIC) {
        this.counterpartyBankBIC = counterpartyBankBIC;
    }

    public String getCounterpartyBankAddress() {
        return counterpartyBankAddress;
    }

    public void setCounterpartyBankAddress(String counterpartyBankAddress) {
        this.counterpartyBankAddress = counterpartyBankAddress;
    }

    public String getCounterpartyAccountNo() {
        return counterpartyAccountNo;
    }

    public void setCounterpartyAccountNo(String counterpartyAccountNo) {
        this.counterpartyAccountNo = counterpartyAccountNo;
    }

    public String getCounterpartyCustID() {
        return counterpartyCustID;
    }

    public void setCounterpartyCustID(String counterpartyCustID) {
        this.counterpartyCustID = counterpartyCustID;
    }

    public String getCounterpartyBIC() {
        return counterpartyBIC;
    }

    public void setCounterpartyBIC(String counterpartyBIC) {
        this.counterpartyBIC = counterpartyBIC;
    }

    public String getCounterpartyAddress() {
        return counterpartyAddress;
    }

    public void setCounterpartyAddress(String counterpartyAddress) {
        this.counterpartyAddress = counterpartyAddress;
    }

    public String getCounterpartyIBAN() {
        return counterpartyIBAN;
    }

    public void setCounterpartyIBAN(String counterpartyIBAN) {
        this.counterpartyIBAN = counterpartyIBAN;
    }

    public String getSwifTmessageType() {
        return swifTmessageType;
    }

    public void setSwifTmessageType(String swifTmessageType) {
        this.swifTmessageType = swifTmessageType;
    }

    public String getRegulatoryReporting() {
        return regulatoryReporting;
    }

    public void setRegulatoryReporting(String regulatoryReporting) {
        this.regulatoryReporting = regulatoryReporting;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getChargeAmt1() {
        return chargeAmt1;
    }

    public void setChargeAmt1(String chargeAmt1) {
        this.chargeAmt1 = chargeAmt1;
    }

    public String getChargeCcy1() {
        return chargeCcy1;
    }

    public void setChargeCcy1(String chargeCcy1) {
        this.chargeCcy1 = chargeCcy1;
    }

    public String getChargeAmt2() {
        return chargeAmt2;
    }

    public void setChargeAmt2(String chargeAmt2) {
        this.chargeAmt2 = chargeAmt2;
    }

    public String getChargeCcy2() {
        return chargeCcy2;
    }

    public void setChargeCcy2(String chargeCcy2) {
        this.chargeCcy2 = chargeCcy2;
    }

    public String getChargeAmt3() {
        return chargeAmt3;
    }

    public void setChargeAmt3(String chargeAmt3) {
        this.chargeAmt3 = chargeAmt3;
    }

    public String getChargeCcy3() {
        return chargeCcy3;
    }

    public void setChargeCcy3(String chargeCcy3) {
        this.chargeCcy3 = chargeCcy3;
    }

    public String getChargeAmt4() {
        return chargeAmt4;
    }

    public void setChargeAmt4(String chargeAmt4) {
        this.chargeAmt4 = chargeAmt4;
    }

    public String getChargeCcy4() {
        return chargeCcy4;
    }

    public void setChargeCcy4(String chargeCcy4) {
        this.chargeCcy4 = chargeCcy4;
    }

    public String getChargeAmt5() {
        return chargeAmt5;
    }

    public void setChargeAmt5(String chargeAmt5) {
        this.chargeAmt5 = chargeAmt5;
    }

    public String getChargeCcy5() {
        return chargeCcy5;
    }

    public void setChargeCcy5(String chargeCcy5) {
        this.chargeCcy5 = chargeCcy5;
    }

    public String getChargeAmt6() {
        return chargeAmt6;
    }

    public void setChargeAmt6(String chargeAmt6) {
        this.chargeAmt6 = chargeAmt6;
    }

    public String getChargeCcy6() {
        return chargeCcy6;
    }

    public void setChargeCcy6(String chargeCcy6) {
        this.chargeCcy6 = chargeCcy6;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getInstructionText1() {
        return instructionText1;
    }

    public void setInstructionText1(String instructionText1) {
        this.instructionText1 = instructionText1;
    }

    public String getInstructionCode1() {
        return instructionCode1;
    }

    public void setInstructionCode1(String instructionCode1) {
        this.instructionCode1 = instructionCode1;
    }

    public String getInstructionText2() {
        return instructionText2;
    }

    public void setInstructionText2(String instructionText2) {
        this.instructionText2 = instructionText2;
    }

    public String getInstructionCode2() {
        return instructionCode2;
    }

    public void setInstructionCode2(String instructionCode2) {
        this.instructionCode2 = instructionCode2;
    }

    public String getInstructionText3() {
        return instructionText3;
    }

    public void setInstructionText3(String instructionText3) {
        this.instructionText3 = instructionText3;
    }

    public String getInstructionCode3() {
        return instructionCode3;
    }

    public void setInstructionCode3(String instructionCode3) {
        this.instructionCode3 = instructionCode3;
    }

    public String getInstructionText4() {
        return instructionText4;
    }

    public void setInstructionText4(String instructionText4) {
        this.instructionText4 = instructionText4;
    }

    public String getInstructionCode4() {
        return instructionCode4;
    }

    public void setInstructionCode4(String instructionCode4) {
        this.instructionCode4 = instructionCode4;
    }

    public String getInstructionText5() {
        return instructionText5;
    }

    public void setInstructionText5(String instructionText5) {
        this.instructionText5 = instructionText5;
    }

    public String getInstructionCode5() {
        return instructionCode5;
    }

    public void setInstructionCode5(String instructionCode5) {
        this.instructionCode5 = instructionCode5;
    }

    public String getInstructionText6() {
        return instructionText6;
    }

    public void setInstructionText6(String instructionText6) {
        this.instructionText6 = instructionText6;
    }

    public String getInstructionCode6() {
        return instructionCode6;
    }

    public void setInstructionCode6(String instructionCode6) {
        this.instructionCode6 = instructionCode6;
    }

    public String getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(String instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getInstructedCcy() {
        return instructedCcy;
    }

    public void setInstructedCcy(String instructedCcy) {
        this.instructedCcy = instructedCcy;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getSwiftChargesFor() {
        return swiftChargesFor;
    }

    public void setSwiftChargesFor(String swiftChargesFor) {
        this.swiftChargesFor = swiftChargesFor;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getTimeDetails1() {
        return timeDetails1;
    }

    public void setTimeDetails1(String timeDetails1) {
        this.timeDetails1 = timeDetails1;
    }

    public String getTimeCode1() {
        return timeCode1;
    }

    public void setTimeCode1(String timeCode1) {
        this.timeCode1 = timeCode1;
    }

    public String getTimeDetails2() {
        return timeDetails2;
    }

    public void setTimeDetails2(String timeDetails2) {
        this.timeDetails2 = timeDetails2;
    }

    public String getTimeCode2() {
        return timeCode2;
    }

    public void setTimeCode2(String timeCode2) {
        this.timeCode2 = timeCode2;
    }

    public String getTimeDetails3() {
        return timeDetails3;
    }

    public void setTimeDetails3(String timeDetails3) {
        this.timeDetails3 = timeDetails3;
    }

    public String getTimeCode3() {
        return timeCode3;
    }

    public void setTimeCode3(String timeCode3) {
        this.timeCode3 = timeCode3;
    }

    public String getSenderToReceiverInfo() {
        return senderToReceiverInfo;
    }

    public void setSenderToReceiverInfo(String senderToReceiverInfo) {
        this.senderToReceiverInfo = senderToReceiverInfo;
    }

    public String getOrderingCustomerAccountNo() {
        return orderingCustomerAccountNo;
    }

    public void setOrderingCustomerAccountNo(String orderingCustomerAccountNo) {
        this.orderingCustomerAccountNo = orderingCustomerAccountNo;
    }

    public String getOrderingCustomerCustID() {
        return orderingCustomerCustID;
    }

    public void setOrderingCustomerCustID(String orderingCustomerCustID) {
        this.orderingCustomerCustID = orderingCustomerCustID;
    }

    public String getOrderingCustomerBIC() {
        return orderingCustomerBIC;
    }

    public void setOrderingCustomerBIC(String orderingCustomerBIC) {
        this.orderingCustomerBIC = orderingCustomerBIC;
    }

    public String getOrderingCustomerAddress() {
        return orderingCustomerAddress;
    }

    public void setOrderingCustomerAddress(String orderingCustomerAddress) {
        this.orderingCustomerAddress = orderingCustomerAddress;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getCoverTransferMethod() {
        return coverTransferMethod;
    }

    public void setCoverTransferMethod(String coverTransferMethod) {
        this.coverTransferMethod = coverTransferMethod;
    }

    public String getReceiversCorrespondentAccountNo() {
        return receiversCorrespondentAccountNo;
    }

    public void setReceiversCorrespondentAccountNo(String receiversCorrespondentAccountNo) {
        this.receiversCorrespondentAccountNo = receiversCorrespondentAccountNo;
    }

    public String getReceiversCorrespondentCustID() {
        return receiversCorrespondentCustID;
    }

    public void setReceiversCorrespondentCustID(String receiversCorrespondentCustID) {
        this.receiversCorrespondentCustID = receiversCorrespondentCustID;
    }

    public String getReceiversCorrespondentBIC() {
        return receiversCorrespondentBIC;
    }

    public void setReceiversCorrespondentBIC(String receiversCorrespondentBIC) {
        this.receiversCorrespondentBIC = receiversCorrespondentBIC;
    }

    public String getReceiversCorrespondentAddress() {
        return receiversCorrespondentAddress;
    }

    public void setReceiversCorrespondentAddress(String receiversCorrespondentAddress) {
        this.receiversCorrespondentAddress = receiversCorrespondentAddress;
    }

    public String getThirdReimbursingBankAccountNo() {
        return thirdReimbursingBankAccountNo;
    }

    public void setThirdReimbursingBankAccountNo(String thirdReimbursingBankAccountNo) {
        this.thirdReimbursingBankAccountNo = thirdReimbursingBankAccountNo;
    }

    public String getThirdReimbursingBankCustID() {
        return thirdReimbursingBankCustID;
    }

    public void setThirdReimbursingBankCustID(String thirdReimbursingBankCustID) {
        this.thirdReimbursingBankCustID = thirdReimbursingBankCustID;
    }

    public String getThirdReimbursingBankBIC() {
        return thirdReimbursingBankBIC;
    }

    public void setThirdReimbursingBankBIC(String thirdReimbursingBankBIC) {
        this.thirdReimbursingBankBIC = thirdReimbursingBankBIC;
    }

    public String getThirdReimbursingBankAddress() {
        return thirdReimbursingBankAddress;
    }

    public void setThirdReimbursingBankAddress(String thirdReimbursingBankAddress) {
        this.thirdReimbursingBankAddress = thirdReimbursingBankAddress;
    }

    public String getCoverSenderToReceiverInfo() {
        return coverSenderToReceiverInfo;
    }

    public void setCoverSenderToReceiverInfo(String coverSenderToReceiverInfo) {
        this.coverSenderToReceiverInfo = coverSenderToReceiverInfo;
    }

    public String getCoverTimeDetails1() {
        return coverTimeDetails1;
    }

    public void setCoverTimeDetails1(String coverTimeDetails1) {
        this.coverTimeDetails1 = coverTimeDetails1;
    }

    public String getCoverTimeCode1() {
        return coverTimeCode1;
    }

    public void setCoverTimeCode1(String coverTimeCode1) {
        this.coverTimeCode1 = coverTimeCode1;
    }

    public String getCoverTimeDetails2() {
        return coverTimeDetails2;
    }

    public void setCoverTimeDetails2(String coverTimeDetails2) {
        this.coverTimeDetails2 = coverTimeDetails2;
    }

    public String getCoverTimeCode2() {
        return coverTimeCode2;
    }

    public void setCoverTimeCode2(String coverTimeCode2) {
        this.coverTimeCode2 = coverTimeCode2;
    }

    public String getCoverTimeDetails3() {
        return coverTimeDetails3;
    }

    public void setCoverTimeDetails3(String coverTimeDetails3) {
        this.coverTimeDetails3 = coverTimeDetails3;
    }

    public String getCoverTimeCode3() {
        return coverTimeCode3;
    }

    public void setCoverTimeCode3(String coverTimeCode3) {
        this.coverTimeCode3 = coverTimeCode3;
    }

    public String getAddMntDelFlag() {
        return addMntDelFlag;
    }

    public void setAddMntDelFlag(String addMntDelFlag) {
        this.addMntDelFlag = addMntDelFlag;
    }
}
