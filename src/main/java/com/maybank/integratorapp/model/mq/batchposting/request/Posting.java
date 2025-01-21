package com.maybank.integratorapp.model.mq.batchposting.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Posting", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
public class Posting {
    @JacksonXmlProperty(localName = "TransactionId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String transactionID;
    @JacksonXmlProperty(localName = "TransactionSeqNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")

    private String transactionSeqNo;
    @JacksonXmlProperty(localName = "MasterKey", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")

    private String masterKey;
    @JacksonXmlProperty(localName = "EventKey", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String eventKey;
    @JacksonXmlProperty(localName = "PostingBranch", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingBranch;
    @JacksonXmlProperty(localName = "InputBranch", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String inputBranch;
    @JacksonXmlProperty(localName = "ProductReference", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String productReference;
    @JacksonXmlProperty(localName = "MasterReference", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String masterReference;
    @JacksonXmlProperty(localName = "EventReference", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String eventReference;
    @JacksonXmlProperty(localName = "InternalRecnRef", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String internalRecnRef;
    @JacksonXmlProperty(localName = "PostingSeqNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingSeqNo;
    @JacksonXmlProperty(localName = "AccountNumber", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String accountNumber;
    @JacksonXmlProperty(localName = "BackOfficeAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String backOfficeAccountNo;
    @JacksonXmlProperty(localName = "ExternalAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String externalAccountNo;
    @JacksonXmlProperty(localName = "OtherAccountNumber", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String otherAccountNumber;
    @JacksonXmlProperty(localName = "IBAN", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String iban;
    @JacksonXmlProperty(localName = "AccountIdentifier", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String accountIdentifier;
    @JacksonXmlProperty(localName = "CustomerMnemonic", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customerMnemonic;
    @JacksonXmlProperty(localName = "AccountType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String accountType;
    @JacksonXmlProperty(localName = "SPSKMnemonic", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String spskMnemonic;
    @JacksonXmlProperty(localName = "SPSKCategoryCode", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String spskCategoryCode;
    @JacksonXmlProperty(localName = "Application", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String application;
    @JacksonXmlProperty(localName = "DebitCreditFlag", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String debitCreditFlag;
    @JacksonXmlProperty(localName = "TransactionCode", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String transactionCode;
    @JacksonXmlProperty(localName = "PostingAmount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingAmount;
    @JacksonXmlProperty(localName = "PostingCcy", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingCcy;
    @JacksonXmlProperty(localName = "ValueDate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String valueDate;
    @JacksonXmlProperty(localName = "AgainstCcy", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String againstCcy;
    @JacksonXmlProperty(localName = "PostingNarrative1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingNarrative1;
    @JacksonXmlProperty(localName = "PostingNarrative2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingNarrative2;
    @JacksonXmlProperty(localName = "PostingNarrative3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingNarrative3;
    @JacksonXmlProperty(localName = "PostingNarrative4", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String postingNarrative4;
    @JacksonXmlProperty(localName = "SundryReferenceCode", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String sundryReferenceCode;
    @JacksonXmlProperty(localName = "UserCode1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String userCode1;
    @JacksonXmlProperty(localName = "UserCode2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String userCode2;
    @JacksonXmlProperty(localName = "ChargeCategorisationCode", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeCategorisationCode;
    @JacksonXmlProperty(localName = "RelatedParty", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String relatedParty;
    @JacksonXmlProperty(localName = "AnalysisCode", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String analysisCode;
    @JacksonXmlProperty(localName = "ParentCountry", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String parentCountry;
    @JacksonXmlProperty(localName = "CustomerType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customerType;
    @JacksonXmlProperty(localName = "Team", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String team;
    @JacksonXmlProperty(localName = "BeneficiaryName", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String beneficiaryName;
    @JacksonXmlProperty(localName = "OriginalCcy", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String originalCcy;
    @JacksonXmlProperty(localName = "OriginalAmount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String originalAmount;
    @JacksonXmlProperty(localName = "IssueOrContractDate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String issueOrContractDate;
    @JacksonXmlProperty(localName = "OtherPartyRef", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String otherPartyRef;
    @JacksonXmlProperty(localName = "BankCode1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode1;
    @JacksonXmlProperty(localName = "BankCode2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode2;
    @JacksonXmlProperty(localName = "BankCode3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode3;
    @JacksonXmlProperty(localName = "BankCode4", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode4;
    @JacksonXmlProperty(localName = "BankCode5", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String bankCode5;
    @JacksonXmlProperty(localName = "TenorStart", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String tenorStart;
    @JacksonXmlProperty(localName = "TenorEnd", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String tenorEnd;
    @JacksonXmlProperty(localName = "PayReceiveFlag", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String payReceiveFlag;
    @JacksonXmlProperty(localName = "SettlementTypeVia", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementTypeVia;
    @JacksonXmlProperty(localName = "SettlementAccountUsed", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementAccountUsed;
    @JacksonXmlProperty(localName = "SettlementPartyAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementPartyAccountNo;
    @JacksonXmlProperty(localName = "SettlementPartyCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementPartyCustID;
    @JacksonXmlProperty(localName = "SettlementPartyBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementPartyBIC;
    @JacksonXmlProperty(localName = "SettlementPartyAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementPartyAddress;
    @JacksonXmlProperty(localName = "SettlementAccountPartyAccount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementAccountPartyAccount;
    @JacksonXmlProperty(localName = "SettlementAccountPartyCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementAccountPartyCustID;
    @JacksonXmlProperty(localName = "SettlementAccountPartyBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementAccountPartyBIC;
    @JacksonXmlProperty(localName = "SettlementAccountPartyAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementAccountPartyAddress;
    @JacksonXmlProperty(localName = "SettlementAccountAlternativeAccNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementAccountAlternativeAccNo;
    @JacksonXmlProperty(localName = "PaymentSystem", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String paymentSystem;
    @JacksonXmlProperty(localName = "ClearingNumber", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String clearingNumber;
    @JacksonXmlProperty(localName = "SettlementSundryReferenceCode", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementSundryReferenceCode;
    @JacksonXmlProperty(localName = "SettlementUserCode1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementUserCode1;
    @JacksonXmlProperty(localName = "SettlementUserCode2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementUserCode2;
    @JacksonXmlProperty(localName = "SettlementNarrative", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String settlementNarrative;
    @JacksonXmlProperty(localName = "NostroMnemonic", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String nostroMnemonic;
    @JacksonXmlProperty(localName = "MainTransferMethod", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String mainTransferMethod;
    @JacksonXmlProperty(localName = "PayingBankTransliterate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String payingBankTransliterate;
    @JacksonXmlProperty(localName = "PayingBankAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String payingBankAccountNo;
    @JacksonXmlProperty(localName = "PayingBankCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String payingBankCustID;
    @JacksonXmlProperty(localName = "PayingBankBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String payingBankBIC;
    @JacksonXmlProperty(localName = "PayingBankAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String payingBankAddress;
    @JacksonXmlProperty(localName = "IntermediaryBankAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String intermediaryBankAccountNo;
    @JacksonXmlProperty(localName = "IntermediaryBankCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String intermediaryBankCustID;
    @JacksonXmlProperty(localName = "IntermediaryBankBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String intermediaryBankBIC;
    @JacksonXmlProperty(localName = "IntermediaryBankAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String intermediaryBankAddress;
    @JacksonXmlProperty(localName = "CounterpartyBankAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyBankAccountNo;
    @JacksonXmlProperty(localName = "CounterpartyBankCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyBankCustID;
    @JacksonXmlProperty(localName = "CounterpartyBankBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyBankBIC;
    @JacksonXmlProperty(localName = "CounterpartyBankAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyBankAddress;
    @JacksonXmlProperty(localName = "CounterpartyAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyAccountNo;
    @JacksonXmlProperty(localName = "CounterpartyCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyCustID;
    @JacksonXmlProperty(localName = "CounterpartyBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyBIC;
    @JacksonXmlProperty(localName = "CounterpartyAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyAddress;
    @JacksonXmlProperty(localName = "CounterpartyIBAN", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String counterpartyIBAN;
    @JacksonXmlProperty(localName = "SWIFTmessageType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String swifTmessageType;
    @JacksonXmlProperty(localName = "RegulatoryReporting", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String regulatoryReporting;
    @JacksonXmlProperty(localName = "TransactionType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String transactionType;
    @JacksonXmlProperty(localName = "ChargeAmt1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeAmt1;
    @JacksonXmlProperty(localName = "ChargeCcy1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeCcy1;
    @JacksonXmlProperty(localName = "ChargeAmt2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeAmt2;
    @JacksonXmlProperty(localName = "ChargeCcy2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeCcy2;
    @JacksonXmlProperty(localName = "ChargeAmt3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeAmt3;
    @JacksonXmlProperty(localName = "ChargeCcy3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeCcy3;
    @JacksonXmlProperty(localName = "ChargeAmt4", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeAmt4;
    @JacksonXmlProperty(localName = "ChargeCcy4", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeCcy4;
    @JacksonXmlProperty(localName = "ChargeAmt5", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeAmt5;
    @JacksonXmlProperty(localName = "ChargeCcy5", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeCcy5;
    @JacksonXmlProperty(localName = "ChargeAmt6", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeAmt6;
    @JacksonXmlProperty(localName = "ChargeCcy6", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chargeCcy6;
    @JacksonXmlProperty(localName = "ExchangeRate", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String exchangeRate;
    @JacksonXmlProperty(localName = "InstructionText1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionText1;
    @JacksonXmlProperty(localName = "InstructionCode1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionCode1;
    @JacksonXmlProperty(localName = "InstructionText2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionText2;
    @JacksonXmlProperty(localName = "InstructionCode2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionCode2;
    @JacksonXmlProperty(localName = "InstructionText3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionText3;
    @JacksonXmlProperty(localName = "InstructionCode3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionCode3;
    @JacksonXmlProperty(localName = "InstructionText4", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionText4;
    @JacksonXmlProperty(localName = "InstructionCode4", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionCode4;
    @JacksonXmlProperty(localName = "InstructionText5", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionText5;
    @JacksonXmlProperty(localName = "InstructionCode5", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionCode5;
    @JacksonXmlProperty(localName = "InstructionText6", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionText6;
    @JacksonXmlProperty(localName = "InstructionCode6", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructionCode6;
    @JacksonXmlProperty(localName = "InstructedAmount", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructedAmount;
    @JacksonXmlProperty(localName = "InstructedCcy", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String instructedCcy;
    @JacksonXmlProperty(localName = "ServiceLevel", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String serviceLevel;
    @JacksonXmlProperty(localName = "SWIFTChargesFor", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String swiftChargesFor;
    @JacksonXmlProperty(localName = "PaymentDetails", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String paymentDetails;
    @JacksonXmlProperty(localName = "TimeDetails1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String timeDetails1;
    @JacksonXmlProperty(localName = "TimeCode1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String timeCode1;
    @JacksonXmlProperty(localName = "TimeDetails2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String timeDetails2;
    @JacksonXmlProperty(localName = "TimeCode2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String timeCode2;
    @JacksonXmlProperty(localName = "TimeDetails3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String timeDetails3;
    @JacksonXmlProperty(localName = "TimeCode3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String timeCode3;
    @JacksonXmlProperty(localName = "SenderToReceiverInfo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String senderToReceiverInfo;
    @JacksonXmlProperty(localName = "OrderingCustomerAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String orderingCustomerAccountNo;
    @JacksonXmlProperty(localName = "OrderingCustomerCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String orderingCustomerCustID;
    @JacksonXmlProperty(localName = "OrderingCustomerBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String orderingCustomerBIC;
    @JacksonXmlProperty(localName = "OrderingCustomerAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String orderingCustomerAddress;
    @JacksonXmlProperty(localName = "ChequeNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String chequeNo;
    @JacksonXmlProperty(localName = "CoverTransferMethod", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverTransferMethod;
    @JacksonXmlProperty(localName = "ReceiversCorrespondentAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String receiversCorrespondentAccountNo;
    @JacksonXmlProperty(localName = "ReceiversCorrespondentCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String receiversCorrespondentCustID;
    @JacksonXmlProperty(localName = "ReceiversCorrespondentBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String receiversCorrespondentBIC;
    @JacksonXmlProperty(localName = "ReceiversCorrespondentAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String receiversCorrespondentAddress;
    @JacksonXmlProperty(localName = "ThirdReimbursingBankAccountNo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String thirdReimbursingBankAccountNo;
    @JacksonXmlProperty(localName = "ThirdReimbursingBankCustId", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String thirdReimbursingBankCustID;
    @JacksonXmlProperty(localName = "ThirdReimbursingBankBIC", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String thirdReimbursingBankBIC;
    @JacksonXmlProperty(localName = "ThirdReimbursingBankAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String thirdReimbursingBankAddress;
    @JacksonXmlProperty(localName = "CoverSenderToReceiverInfo", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverSenderToReceiverInfo;
    @JacksonXmlProperty(localName = "CoverTimeDetails1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverTimeDetails1;
    @JacksonXmlProperty(localName = "CoverTimeCode1", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverTimeCode1;
    @JacksonXmlProperty(localName = "CoverTimeDetails2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverTimeDetails2;
    @JacksonXmlProperty(localName = "CoverTimeCode2", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverTimeCode2;
    @JacksonXmlProperty(localName = "CoverTimeDetails3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverTimeDetails3;
    @JacksonXmlProperty(localName = "CoverTimeCode3", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String coverTimeCode3;
    @JacksonXmlProperty(localName = "AddMntDelFlag", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String addMntDelFlag;
    @JacksonXmlProperty(localName = "ExtraData", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private ExtraData extraData;

    public ExtraData getExtraData() {
        return extraData;
    }

    public void setExtraData(ExtraData extraData) {
        this.extraData = extraData;
    }

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

    public String getAccountIdentifier() { return accountIdentifier; }
    public void setAccountIdentifier(String value) { this.accountIdentifier = value; }

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
