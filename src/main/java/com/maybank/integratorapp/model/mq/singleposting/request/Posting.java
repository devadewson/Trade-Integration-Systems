package com.maybank.integratorapp.model.mq.singleposting.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Posting {
    @JacksonXmlProperty(localName = "TransactionID", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
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
    private String accountIdetifier;
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
//    private ExtraData extraData;
//    private Prefix prefix;

    public String getTransactionID() { return transactionID; }
    public void setTransactionID(String value) { this.transactionID = value; }

    public String getTransactionSeqNo() { return transactionSeqNo; }
    public void setTransactionSeqNo(String value) { this.transactionSeqNo = value; }

    public String getMasterKey() { return masterKey; }
    public void setMasterKey(String value) { this.masterKey = value; }

    public String getEventKey() { return eventKey; }
    public void setEventKey(String value) { this.eventKey = value; }

    public String getPostingBranch() { return postingBranch; }
    public void setPostingBranch(String value) { this.postingBranch = value; }

    public String getInputBranch() { return inputBranch; }
    public void setInputBranch(String value) { this.inputBranch = value; }

    public String getProductReference() { return productReference; }
    public void setProductReference(String value) { this.productReference = value; }

    public String getMasterReference() { return masterReference; }
    public void setMasterReference(String value) { this.masterReference = value; }

    public String getEventReference() { return eventReference; }
    public void setEventReference(String value) { this.eventReference = value; }

    public String getInternalRecnRef() { return internalRecnRef; }
    public void setInternalRecnRef(String value) { this.internalRecnRef = value; }

    public String getPostingSeqNo() { return postingSeqNo; }
    public void setPostingSeqNo(String value) { this.postingSeqNo = value; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String value) { this.accountNumber = value; }

    public String getBackOfficeAccountNo() { return backOfficeAccountNo; }
    public void setBackOfficeAccountNo(String value) { this.backOfficeAccountNo = value; }

    public String getExternalAccountNo() { return externalAccountNo; }
    public void setExternalAccountNo(String value) { this.externalAccountNo = value; }

    public String getOtherAccountNumber() { return otherAccountNumber; }
    public void setOtherAccountNumber(String value) { this.otherAccountNumber = value; }

    public String getIban() { return iban; }
    public void setIban(String value) { this.iban = value; }

    public String getAccountIdetifier() { return accountIdetifier; }
    public void setAccountIdetifier(String value) { this.accountIdetifier = value; }

    public String getCustomerMnemonic() { return customerMnemonic; }
    public void setCustomerMnemonic(String value) { this.customerMnemonic = value; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String value) { this.accountType = value; }

    public String getSpskMnemonic() { return spskMnemonic; }
    public void setSpskMnemonic(String value) { this.spskMnemonic = value; }

    public String getSpskCategoryCode() { return spskCategoryCode; }
    public void setSpskCategoryCode(String value) { this.spskCategoryCode = value; }

    public String getApplication() { return application; }
    public void setApplication(String value) { this.application = value; }

    public String getDebitCreditFlag() { return debitCreditFlag; }
    public void setDebitCreditFlag(String value) { this.debitCreditFlag = value; }

    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String value) { this.transactionCode = value; }

    public String getPostingAmount() { return postingAmount; }
    public void setPostingAmount(String value) { this.postingAmount = value; }

    public String getPostingCcy() { return postingCcy; }
    public void setPostingCcy(String value) { this.postingCcy = value; }

    public String getValueDate() { return valueDate; }
    public void setValueDate(String value) { this.valueDate = value; }

    public String getAgainstCcy() { return againstCcy; }
    public void setAgainstCcy(String value) { this.againstCcy = value; }

    public String getPostingNarrative1() { return postingNarrative1; }
    public void setPostingNarrative1(String value) { this.postingNarrative1 = value; }

    public String getPostingNarrative2() { return postingNarrative2; }
    public void setPostingNarrative2(String value) { this.postingNarrative2 = value; }

    public String getPostingNarrative3() { return postingNarrative3; }
    public void setPostingNarrative3(String value) { this.postingNarrative3 = value; }

    public String getPostingNarrative4() { return postingNarrative4; }
    public void setPostingNarrative4(String value) { this.postingNarrative4 = value; }

    public String getSundryReferenceCode() { return sundryReferenceCode; }
    public void setSundryReferenceCode(String value) { this.sundryReferenceCode = value; }

    public String getUserCode1() { return userCode1; }
    public void setUserCode1(String value) { this.userCode1 = value; }

    public String getUserCode2() { return userCode2; }
    public void setUserCode2(String value) { this.userCode2 = value; }

    public String getChargeCategorisationCode() { return chargeCategorisationCode; }
    public void setChargeCategorisationCode(String value) { this.chargeCategorisationCode = value; }

    public String getRelatedParty() { return relatedParty; }
    public void setRelatedParty(String value) { this.relatedParty = value; }

    public String getAnalysisCode() { return analysisCode; }
    public void setAnalysisCode(String value) { this.analysisCode = value; }

    public String getParentCountry() { return parentCountry; }
    public void setParentCountry(String value) { this.parentCountry = value; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String value) { this.customerType = value; }

    public String getTeam() { return team; }
    public void setTeam(String value) { this.team = value; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String value) { this.beneficiaryName = value; }

    public String getOriginalCcy() { return originalCcy; }
    public void setOriginalCcy(String value) { this.originalCcy = value; }

    public String getOriginalAmount() { return originalAmount; }
    public void setOriginalAmount(String value) { this.originalAmount = value; }

    public String getIssueOrContractDate() { return issueOrContractDate; }
    public void setIssueOrContractDate(String value) { this.issueOrContractDate = value; }

    public String getOtherPartyRef() { return otherPartyRef; }
    public void setOtherPartyRef(String value) { this.otherPartyRef = value; }

    public String getBankCode1() { return bankCode1; }
    public void setBankCode1(String value) { this.bankCode1 = value; }

    public String getBankCode2() { return bankCode2; }
    public void setBankCode2(String value) { this.bankCode2 = value; }

    public String getBankCode3() { return bankCode3; }
    public void setBankCode3(String value) { this.bankCode3 = value; }

    public String getBankCode4() { return bankCode4; }
    public void setBankCode4(String value) { this.bankCode4 = value; }

    public String getBankCode5() { return bankCode5; }
    public void setBankCode5(String value) { this.bankCode5 = value; }

    public String getTenorStart() { return tenorStart; }
    public void setTenorStart(String value) { this.tenorStart = value; }

    public String getTenorEnd() { return tenorEnd; }
    public void setTenorEnd(String value) { this.tenorEnd = value; }

    public String getPayReceiveFlag() { return payReceiveFlag; }
    public void setPayReceiveFlag(String value) { this.payReceiveFlag = value; }

    public String getSettlementTypeVia() { return settlementTypeVia; }
    public void setSettlementTypeVia(String value) { this.settlementTypeVia = value; }

    public String getSettlementAccountUsed() { return settlementAccountUsed; }
    public void setSettlementAccountUsed(String value) { this.settlementAccountUsed = value; }

    public String getSettlementPartyAccountNo() { return settlementPartyAccountNo; }
    public void setSettlementPartyAccountNo(String value) { this.settlementPartyAccountNo = value; }

    public String getSettlementPartyCustID() { return settlementPartyCustID; }
    public void setSettlementPartyCustID(String value) { this.settlementPartyCustID = value; }

    public String getSettlementPartyBIC() { return settlementPartyBIC; }
    public void setSettlementPartyBIC(String value) { this.settlementPartyBIC = value; }

    public String getSettlementPartyAddress() { return settlementPartyAddress; }
    public void setSettlementPartyAddress(String value) { this.settlementPartyAddress = value; }

    public String getSettlementAccountPartyAccount() { return settlementAccountPartyAccount; }
    public void setSettlementAccountPartyAccount(String value) { this.settlementAccountPartyAccount = value; }

    public String getSettlementAccountPartyCustID() { return settlementAccountPartyCustID; }
    public void setSettlementAccountPartyCustID(String value) { this.settlementAccountPartyCustID = value; }

    public String getSettlementAccountPartyBIC() { return settlementAccountPartyBIC; }
    public void setSettlementAccountPartyBIC(String value) { this.settlementAccountPartyBIC = value; }

    public String getSettlementAccountPartyAddress() { return settlementAccountPartyAddress; }
    public void setSettlementAccountPartyAddress(String value) { this.settlementAccountPartyAddress = value; }

    public String getSettlementAccountAlternativeAccNo() { return settlementAccountAlternativeAccNo; }
    public void setSettlementAccountAlternativeAccNo(String value) { this.settlementAccountAlternativeAccNo = value; }

    public String getPaymentSystem() { return paymentSystem; }
    public void setPaymentSystem(String value) { this.paymentSystem = value; }

    public String getClearingNumber() { return clearingNumber; }
    public void setClearingNumber(String value) { this.clearingNumber = value; }

    public String getSettlementSundryReferenceCode() { return settlementSundryReferenceCode; }
    public void setSettlementSundryReferenceCode(String value) { this.settlementSundryReferenceCode = value; }

    public String getSettlementUserCode1() { return settlementUserCode1; }
    public void setSettlementUserCode1(String value) { this.settlementUserCode1 = value; }

    public String getSettlementUserCode2() { return settlementUserCode2; }
    public void setSettlementUserCode2(String value) { this.settlementUserCode2 = value; }

    public String getSettlementNarrative() { return settlementNarrative; }
    public void setSettlementNarrative(String value) { this.settlementNarrative = value; }

    public String getNostroMnemonic() { return nostroMnemonic; }
    public void setNostroMnemonic(String value) { this.nostroMnemonic = value; }

    public String getMainTransferMethod() { return mainTransferMethod; }
    public void setMainTransferMethod(String value) { this.mainTransferMethod = value; }

    public String getPayingBankTransliterate() { return payingBankTransliterate; }
    public void setPayingBankTransliterate(String value) { this.payingBankTransliterate = value; }

    public String getPayingBankAccountNo() { return payingBankAccountNo; }
    public void setPayingBankAccountNo(String value) { this.payingBankAccountNo = value; }

    public String getPayingBankCustID() { return payingBankCustID; }
    public void setPayingBankCustID(String value) { this.payingBankCustID = value; }

    public String getPayingBankBIC() { return payingBankBIC; }
    public void setPayingBankBIC(String value) { this.payingBankBIC = value; }

    public String getPayingBankAddress() { return payingBankAddress; }
    public void setPayingBankAddress(String value) { this.payingBankAddress = value; }

    public String getIntermediaryBankAccountNo() { return intermediaryBankAccountNo; }
    public void setIntermediaryBankAccountNo(String value) { this.intermediaryBankAccountNo = value; }

    public String getIntermediaryBankCustID() { return intermediaryBankCustID; }
    public void setIntermediaryBankCustID(String value) { this.intermediaryBankCustID = value; }

    public String getIntermediaryBankBIC() { return intermediaryBankBIC; }
    public void setIntermediaryBankBIC(String value) { this.intermediaryBankBIC = value; }

    public String getIntermediaryBankAddress() { return intermediaryBankAddress; }
    public void setIntermediaryBankAddress(String value) { this.intermediaryBankAddress = value; }

    public String getCounterpartyBankAccountNo() { return counterpartyBankAccountNo; }
    public void setCounterpartyBankAccountNo(String value) { this.counterpartyBankAccountNo = value; }

    public String getCounterpartyBankCustID() { return counterpartyBankCustID; }
    public void setCounterpartyBankCustID(String value) { this.counterpartyBankCustID = value; }

    public String getCounterpartyBankBIC() { return counterpartyBankBIC; }
    public void setCounterpartyBankBIC(String value) { this.counterpartyBankBIC = value; }

    public String getCounterpartyBankAddress() { return counterpartyBankAddress; }
    public void setCounterpartyBankAddress(String value) { this.counterpartyBankAddress = value; }

    public String getCounterpartyAccountNo() { return counterpartyAccountNo; }
    public void setCounterpartyAccountNo(String value) { this.counterpartyAccountNo = value; }

    public String getCounterpartyCustID() { return counterpartyCustID; }
    public void setCounterpartyCustID(String value) { this.counterpartyCustID = value; }

    public String getCounterpartyBIC() { return counterpartyBIC; }
    public void setCounterpartyBIC(String value) { this.counterpartyBIC = value; }

    public String getCounterpartyAddress() { return counterpartyAddress; }
    public void setCounterpartyAddress(String value) { this.counterpartyAddress = value; }

    public String getCounterpartyIBAN() { return counterpartyIBAN; }
    public void setCounterpartyIBAN(String value) { this.counterpartyIBAN = value; }

    public String getSwifTmessageType() { return swifTmessageType; }
    public void setSwifTmessageType(String value) { this.swifTmessageType = value; }

    public String getRegulatoryReporting() { return regulatoryReporting; }
    public void setRegulatoryReporting(String value) { this.regulatoryReporting = value; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String value) { this.transactionType = value; }

    public String getChargeAmt1() { return chargeAmt1; }
    public void setChargeAmt1(String value) { this.chargeAmt1 = value; }

    public String getChargeCcy1() { return chargeCcy1; }
    public void setChargeCcy1(String value) { this.chargeCcy1 = value; }

    public String getChargeAmt2() { return chargeAmt2; }
    public void setChargeAmt2(String value) { this.chargeAmt2 = value; }

    public String getChargeCcy2() { return chargeCcy2; }
    public void setChargeCcy2(String value) { this.chargeCcy2 = value; }

    public String getChargeAmt3() { return chargeAmt3; }
    public void setChargeAmt3(String value) { this.chargeAmt3 = value; }

    public String getChargeCcy3() { return chargeCcy3; }
    public void setChargeCcy3(String value) { this.chargeCcy3 = value; }

    public String getChargeAmt4() { return chargeAmt4; }
    public void setChargeAmt4(String value) { this.chargeAmt4 = value; }

    public String getChargeCcy4() { return chargeCcy4; }
    public void setChargeCcy4(String value) { this.chargeCcy4 = value; }

    public String getChargeAmt5() { return chargeAmt5; }
    public void setChargeAmt5(String value) { this.chargeAmt5 = value; }

    public String getChargeCcy5() { return chargeCcy5; }
    public void setChargeCcy5(String value) { this.chargeCcy5 = value; }

    public String getChargeAmt6() { return chargeAmt6; }
    public void setChargeAmt6(String value) { this.chargeAmt6 = value; }

    public String getChargeCcy6() { return chargeCcy6; }
    public void setChargeCcy6(String value) { this.chargeCcy6 = value; }

    public String getExchangeRate() { return exchangeRate; }
    public void setExchangeRate(String value) { this.exchangeRate = value; }

    public String getInstructionText1() { return instructionText1; }
    public void setInstructionText1(String value) { this.instructionText1 = value; }

    public String getInstructionCode1() { return instructionCode1; }
    public void setInstructionCode1(String value) { this.instructionCode1 = value; }

    public String getInstructionText2() { return instructionText2; }
    public void setInstructionText2(String value) { this.instructionText2 = value; }

    public String getInstructionCode2() { return instructionCode2; }
    public void setInstructionCode2(String value) { this.instructionCode2 = value; }

    public String getInstructionText3() { return instructionText3; }
    public void setInstructionText3(String value) { this.instructionText3 = value; }

    public String getInstructionCode3() { return instructionCode3; }
    public void setInstructionCode3(String value) { this.instructionCode3 = value; }

    public String getInstructionText4() { return instructionText4; }
    public void setInstructionText4(String value) { this.instructionText4 = value; }

    public String getInstructionCode4() { return instructionCode4; }
    public void setInstructionCode4(String value) { this.instructionCode4 = value; }

    public String getInstructionText5() { return instructionText5; }
    public void setInstructionText5(String value) { this.instructionText5 = value; }

    public String getInstructionCode5() { return instructionCode5; }
    public void setInstructionCode5(String value) { this.instructionCode5 = value; }

    public String getInstructionText6() { return instructionText6; }
    public void setInstructionText6(String value) { this.instructionText6 = value; }

    public String getInstructionCode6() { return instructionCode6; }
    public void setInstructionCode6(String value) { this.instructionCode6 = value; }

    public String getInstructedAmount() { return instructedAmount; }
    public void setInstructedAmount(String value) { this.instructedAmount = value; }

    public String getInstructedCcy() { return instructedCcy; }
    public void setInstructedCcy(String value) { this.instructedCcy = value; }

    public String getServiceLevel() { return serviceLevel; }
    public void setServiceLevel(String value) { this.serviceLevel = value; }

    public String getSwiftChargesFor() { return swiftChargesFor; }
    public void setSwiftChargesFor(String value) { this.swiftChargesFor = value; }

    public String getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(String value) { this.paymentDetails = value; }

    public String getTimeDetails1() { return timeDetails1; }
    public void setTimeDetails1(String value) { this.timeDetails1 = value; }

    public String getTimeCode1() { return timeCode1; }
    public void setTimeCode1(String value) { this.timeCode1 = value; }

    public String getTimeDetails2() { return timeDetails2; }
    public void setTimeDetails2(String value) { this.timeDetails2 = value; }

    public String getTimeCode2() { return timeCode2; }
    public void setTimeCode2(String value) { this.timeCode2 = value; }

    public String getTimeDetails3() { return timeDetails3; }
    public void setTimeDetails3(String value) { this.timeDetails3 = value; }

    public String getTimeCode3() { return timeCode3; }
    public void setTimeCode3(String value) { this.timeCode3 = value; }

    public String getSenderToReceiverInfo() { return senderToReceiverInfo; }
    public void setSenderToReceiverInfo(String value) { this.senderToReceiverInfo = value; }

    public String getOrderingCustomerAccountNo() { return orderingCustomerAccountNo; }
    public void setOrderingCustomerAccountNo(String value) { this.orderingCustomerAccountNo = value; }

    public String getOrderingCustomerCustID() { return orderingCustomerCustID; }
    public void setOrderingCustomerCustID(String value) { this.orderingCustomerCustID = value; }

    public String getOrderingCustomerBIC() { return orderingCustomerBIC; }
    public void setOrderingCustomerBIC(String value) { this.orderingCustomerBIC = value; }

    public String getOrderingCustomerAddress() { return orderingCustomerAddress; }
    public void setOrderingCustomerAddress(String value) { this.orderingCustomerAddress = value; }

    public String getChequeNo() { return chequeNo; }
    public void setChequeNo(String value) { this.chequeNo = value; }

    public String getCoverTransferMethod() { return coverTransferMethod; }
    public void setCoverTransferMethod(String value) { this.coverTransferMethod = value; }

    public String getReceiversCorrespondentAccountNo() { return receiversCorrespondentAccountNo; }
    public void setReceiversCorrespondentAccountNo(String value) { this.receiversCorrespondentAccountNo = value; }

    public String getReceiversCorrespondentCustID() { return receiversCorrespondentCustID; }
    public void setReceiversCorrespondentCustID(String value) { this.receiversCorrespondentCustID = value; }

    public String getReceiversCorrespondentBIC() { return receiversCorrespondentBIC; }
    public void setReceiversCorrespondentBIC(String value) { this.receiversCorrespondentBIC = value; }

    public String getReceiversCorrespondentAddress() { return receiversCorrespondentAddress; }
    public void setReceiversCorrespondentAddress(String value) { this.receiversCorrespondentAddress = value; }

    public String getThirdReimbursingBankAccountNo() { return thirdReimbursingBankAccountNo; }
    public void setThirdReimbursingBankAccountNo(String value) { this.thirdReimbursingBankAccountNo = value; }

    public String getThirdReimbursingBankCustID() { return thirdReimbursingBankCustID; }
    public void setThirdReimbursingBankCustID(String value) { this.thirdReimbursingBankCustID = value; }

    public String getThirdReimbursingBankBIC() { return thirdReimbursingBankBIC; }
    public void setThirdReimbursingBankBIC(String value) { this.thirdReimbursingBankBIC = value; }

    public String getThirdReimbursingBankAddress() { return thirdReimbursingBankAddress; }
    public void setThirdReimbursingBankAddress(String value) { this.thirdReimbursingBankAddress = value; }

    public String getCoverSenderToReceiverInfo() { return coverSenderToReceiverInfo; }
    public void setCoverSenderToReceiverInfo(String value) { this.coverSenderToReceiverInfo = value; }

    public String getCoverTimeDetails1() { return coverTimeDetails1; }
    public void setCoverTimeDetails1(String value) { this.coverTimeDetails1 = value; }

    public String getCoverTimeCode1() { return coverTimeCode1; }
    public void setCoverTimeCode1(String value) { this.coverTimeCode1 = value; }

    public String getCoverTimeDetails2() { return coverTimeDetails2; }
    public void setCoverTimeDetails2(String value) { this.coverTimeDetails2 = value; }

    public String getCoverTimeCode2() { return coverTimeCode2; }
    public void setCoverTimeCode2(String value) { this.coverTimeCode2 = value; }

    public String getCoverTimeDetails3() { return coverTimeDetails3; }
    public void setCoverTimeDetails3(String value) { this.coverTimeDetails3 = value; }

    public String getCoverTimeCode3() { return coverTimeCode3; }
    public void setCoverTimeCode3(String value) { this.coverTimeCode3 = value; }

    public String getAddMntDelFlag() { return addMntDelFlag; }
    public void setAddMntDelFlag(String value) { this.addMntDelFlag = value; }

//    public ExtraData getExtraData() { return extraData; }
//    public void setExtraData(ExtraData value) { this.extraData = value; }

}
