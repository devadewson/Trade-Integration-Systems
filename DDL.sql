-- DROP SCHEMA dbo;

CREATE SCHEMA dbo;
-- IntegratorUAT.dbo.ESB_TBRSpec_Import definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ESB_TBRSpec_Import;

CREATE TABLE IntegratorUAT.dbo.ESB_TBRSpec_Import (
	[Field Name] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Type] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[LENGTH] bigint NULL,
	[FIELD OCCURANCE] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	DELIMITER varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TBRNo bigint NULL
);


-- IntegratorUAT.dbo.FtiAccountType definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.FtiAccountType;

CREATE TABLE IntegratorUAT.dbo.FtiAccountType (
	id bigint IDENTITY(1,1) NOT NULL,
	AccountDescription varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	AccountType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	AccountTypeId bigint NULL,
	CONSTRAINT PK__FtiAccou__3213E83F8FBFEF65 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.FtiTransaction definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.FtiTransaction;

CREATE TABLE IntegratorUAT.dbo.FtiTransaction (
	id bigint IDENTITY(1,1) NOT NULL,
	createdDate datetime2(6) NULL,
	drawNumber varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	lastEvent varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	lastStep varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	masterRefNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reservationId varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updateDate datetime2(6) NULL,
	CONSTRAINT PK__FtiTrans__3213E83FB18B8672 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.FtiTransactionDetail definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.FtiTransactionDetail;

CREATE TABLE IntegratorUAT.dbo.FtiTransactionDetail (
	id bigint IDENTITY(1,1) NOT NULL,
	additionalInfo1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additionalInfo2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additionalInfo3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additionalInfo4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additionalInfo5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coreSysMessage varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coreSysName varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coreSysStatus varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	createdDate datetime2(6) NULL,
	ftiEvent varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	headerId bigint NULL,
	reqMessage text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	resMessage text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transMessageLogId bigint NULL,
	transName varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__FtiTrans__3213E83F40016AEF PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.FtiTransactionDetailPosting definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.FtiTransactionDetailPosting;

CREATE TABLE IntegratorUAT.dbo.FtiTransactionDetailPosting (
	id bigint IDENTITY(1,1) NOT NULL,
	Account varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	AccountType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	AccountTypeAlias varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Amount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Ccy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CcyAlias varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CcyNumber varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	DebitCredit varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	PostingSeqNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ValueDate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	idGroup bigint NULL,
	CONSTRAINT PK__FtiTrans__3213E83F15B0164C PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.FtiTransactionDetailPostingGroup definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.FtiTransactionDetailPostingGroup;

CREATE TABLE IntegratorUAT.dbo.FtiTransactionDetailPostingGroup (
	id bigint IDENTITY(1,1) NOT NULL,
	DetailId bigint NULL,
	FlagCrossValas varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	FlagMdmc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	GroupId varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MappingType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TbrCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__FtiTrans__3213E83FAB052FDF PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.FtiTransactionPosting definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.FtiTransactionPosting;

CREATE TABLE IntegratorUAT.dbo.FtiTransactionPosting (
	id bigint IDENTITY(1,1) NOT NULL,
	accountIdentifier varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	accountNumber varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	accountType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	addMntDelFlag varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	againstCcy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	analysisCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	application varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	backOfficeAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankCode1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankCode2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankCode3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankCode4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankCode5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	beneficiaryName varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeAmt1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeAmt2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeAmt3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeAmt4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeAmt5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeAmt6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeCategorisationCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeCcy1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeCcy2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeCcy3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeCcy4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeCcy5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chargeCcy6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	chequeNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	clearingNumber varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyBankAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyBankAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyBankBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyBankCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyIBAN varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverSenderToReceiverInfo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverTimeCode1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverTimeCode2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverTimeCode3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverTimeDetails1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverTimeDetails2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverTimeDetails3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	coverTransferMethod varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	customerMnemonic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	customerType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	debitCreditFlag varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	eventKey varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	eventReference varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	exchangeRate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	externalAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	iban varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	idHeader bigint NULL,
	inputBranch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructedAmount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructedCcy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionCode1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionCode2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionCode3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionCode4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionCode5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionCode6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionText1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionText2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionText3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionText4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionText5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructionText6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediaryBankAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediaryBankAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediaryBankBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediaryBankCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	internalRecnRef varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	issueOrContractDate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	mainTransferMethod varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	masterKey varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	masterReference varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nostroMnemonic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	orderingCustomerAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	orderingCustomerAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	orderingCustomerBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	orderingCustomerCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	originalAmount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	originalCcy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	otherAccountNumber varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	otherPartyRef varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	parentCountry varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payReceiveFlag varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payingBankAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payingBankAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payingBankBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payingBankCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payingBankTransliterate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	paymentDetails varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	paymentSystem varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingAmount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingBranch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingCcy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingNarrative1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingNarrative2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingNarrative3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingNarrative4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	postingSeqNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	productReference varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receiversCorrespondentAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receiversCorrespondentAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receiversCorrespondentBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receiversCorrespondentCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	regulatoryReporting varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	relatedParty varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	senderToReceiverInfo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	serviceLevel varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementAccountAlternativeAccNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementAccountPartyAccount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementAccountPartyAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementAccountPartyBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementAccountPartyCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementAccountUsed varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementNarrative varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementPartyAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementPartyAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementPartyBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementPartyCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementSundryReferenceCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementTypeVia varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementUserCode1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlementUserCode2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	spskCategoryCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	spskMnemonic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sundryReferenceCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	swifTmessageType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	swiftChargesFor varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	team varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tenorEnd varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tenorStart varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	thirdReimbursingBankAccountNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	thirdReimbursingBankAddress varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	thirdReimbursingBankBIC varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	thirdReimbursingBankCustID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	timeCode1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	timeCode2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	timeCode3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	timeDetails1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	timeDetails2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	timeDetails3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transactionCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transactionID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transactionSeqNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transactionType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	userCode1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	userCode2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	valueDate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__FtiTrans__3213E83F67C35C94 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.LogInterfaceProcess definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.LogInterfaceProcess;

CREATE TABLE IntegratorUAT.dbo.LogInterfaceProcess (
	id bigint IDENTITY(1,1) NOT NULL,
	Activity varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ActivityDescription varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ActivityStatus varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	IdLogParent bigint NULL,
	IntegrationMessage text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	LogDate datetime2(6) NULL,
	CONSTRAINT PK__LogInter__3213E83FD9BB6270 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.LogQueueData definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.LogQueueData;

CREATE TABLE IntegratorUAT.dbo.LogQueueData (
	id bigint IDENTITY(1,1) NOT NULL,
	correlationID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime2(6) NULL,
	delivery_date datetime2(6) NULL,
	destination varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	messageUID varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	origin varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	relatedTransRef varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reqMessage nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	resMessage nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_info varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updated_date datetime2(6) NULL,
	CONSTRAINT PK__LogQueue__3213E83F66C88AE4 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsAccountType definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsAccountType;

CREATE TABLE IntegratorUAT.dbo.MsAccountType (
	id bigint IDENTITY(1,1) NOT NULL,
	AccountType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsAccoun__3213E83FBFA8A27D PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsBank definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsBank;

CREATE TABLE IntegratorUAT.dbo.MsBank (
	idBank int IDENTITY(1,1) NOT NULL,
	bankCode varchar(7) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankName varchar(35) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankIdCode varchar(15) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bankType varchar(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	businessType varchar(20) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cityCode varchar(4) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	willKlr varchar(4) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	rtNo varchar(7) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status bit NULL,
	createdBy varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	createdDate datetime NULL,
	lastModifiedBy varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	lastModifiedDate datetime NULL,
	CONSTRAINT PK_MsBank PRIMARY KEY (idBank)
);


-- IntegratorUAT.dbo.MsBranch definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsBranch;

CREATE TABLE IntegratorUAT.dbo.MsBranch (
	id int IDENTITY(1,1) NOT NULL,
	branchCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branchName varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	region varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	userId varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	terminalCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	approvalStatus varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status bit NULL,
	createdBy varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	createdDate datetime NULL,
	lastModifiedBy varchar(10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	lastModifiedDate datetime NULL,
	branchRegion varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	spvUserId varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK_MsBranch PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsCompanyData definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsCompanyData;

CREATE TABLE IntegratorUAT.dbo.MsCompanyData (
	id bigint IDENTITY(1,1) NOT NULL,
	accInfoData text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cifno varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime2(6) NULL,
	custInfoData text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	gcifno varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tagBank varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tagCustomer varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updated_date datetime2(6) NULL,
	CONSTRAINT PK__MsCompan__3213E83FAEDB3FA1 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsCompanyLimit definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsCompanyLimit;

CREATE TABLE IntegratorUAT.dbo.MsCompanyLimit (
	id bigint IDENTITY(1,1) NOT NULL,
	cbranch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cifno varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ibranch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsCompan__3213E83F45DDFF90 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsCurrency definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsCurrency;

CREATE TABLE IntegratorUAT.dbo.MsCurrency (
	id bigint IDENTITY(1,1) NOT NULL,
	CountryName varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	DecimalPoint bigint NULL,
	InternalCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	IsoCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsCurren__3213E83FE169DD46 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsMapClsProductType definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsMapClsProductType;

CREATE TABLE IntegratorUAT.dbo.MsMapClsProductType (
	id bigint IDENTITY(1,1) NOT NULL,
	EventCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	IslamicFlag int NULL,
	LiabilityCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	LineOfBusiness varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ProductName varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ProductType001 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ProductType999 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SpecialFlag int NULL,
	CONSTRAINT PK__MsMapCls__3213E83F7BE3B7B3 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsParameter definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsParameter;

CREATE TABLE IntegratorUAT.dbo.MsParameter (
	id bigint IDENTITY(1,1) NOT NULL,
	CreatedBy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CreatedDate datetime2(6) NULL,
	UpdateDate datetime2(6) NULL,
	UpdatedBy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prmDesc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prmKey varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prmValue text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsParame__3213E83F91D37D05 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsQueueConfig definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsQueueConfig;

CREATE TABLE IntegratorUAT.dbo.MsQueueConfig (
	id bigint IDENTITY(1,1) NOT NULL,
	EnableStatus int NOT NULL,
	ListenerName varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Request_Queue_Address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Request_Queue_Channel varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Request_Queue_Manager varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Request_Queue_Name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Request_Queue_Password varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Request_Queue_Port varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Request_Queue_Username varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Response_Queue_Address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Response_Queue_Channel varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Response_Queue_Manager varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Response_Queue_Name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Response_Queue_Password varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Response_Queue_Port varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Response_Queue_Username varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	service_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsQueueC__3213E83F2D5C16E0 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsReferenceConfig definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsReferenceConfig;

CREATE TABLE IntegratorUAT.dbo.MsReferenceConfig (
	id bigint IDENTITY(1,1) NOT NULL,
	RefConfig varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	RefProduct varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	RefPurpose varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	RefType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsRefere__3213E83FAE7B3265 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsServiceMessageFields definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsServiceMessageFields;

CREATE TABLE IntegratorUAT.dbo.MsServiceMessageFields (
	ID bigint IDENTITY(0,1) NOT NULL,
	STRUCTURE_ID bigint NULL,
	FIELD_NAME varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	FIELD_TYPE varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	IS_REQUIRED varchar(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	DEFAULT_VALUE varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT MsServiceMessageFields_PK PRIMARY KEY (ID)
);


-- IntegratorUAT.dbo.MsServiceMessageStructure definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsServiceMessageStructure;

CREATE TABLE IntegratorUAT.dbo.MsServiceMessageStructure (
	ID bigint IDENTITY(0,1) NOT NULL,
	FIELD_NAME varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	PARENT_ID bigint NULL,
	IS_ARRAY varchar(1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT MsServiceMessageStructure_PK PRIMARY KEY (ID)
);


-- IntegratorUAT.dbo.SwiftConversionRule definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.SwiftConversionRule;

CREATE TABLE IntegratorUAT.dbo.SwiftConversionRule (
	id bigint IDENTITY(1,1) NOT NULL,
	ConstantValue text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CreatedDate datetime2(6) NULL,
	Description text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	InputTag varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	IsActive varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MappingJson text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ModifiedDate datetime2(6) NULL,
	MxFieldPath varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SourceMtType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SourceType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SourceValue varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SwiftStandardReleaseNumber varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TargetMxType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__SwiftCon__3213E83FB0BE84A5 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.SwiftMtMessage definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.SwiftMtMessage;

CREATE TABLE IntegratorUAT.dbo.SwiftMtMessage (
	id bigint IDENTITY(1,1) NOT NULL,
	CreatedDate datetime2(6) NULL,
	Direction varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MessageStatus varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MessageType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ModifiedDate datetime2(6) NULL,
	RawMessage text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ReceiverBic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SenderBic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SwiftStandardReleaseVersion varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TransactionReference varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__SwiftMtM__3213E83F07D289B1 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.SwiftMtTag definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.SwiftMtTag;

CREATE TABLE IntegratorUAT.dbo.SwiftMtTag (
	id bigint IDENTITY(1,1) NOT NULL,
	CreatedDate datetime2(6) NULL,
	SequenceOrder int NULL,
	SwiftMtMessageId bigint NULL,
	TagId varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TagValue varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__SwiftMtT__3213E83FBDE3FD3B PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.SwiftMxMessage definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.SwiftMxMessage;

CREATE TABLE IntegratorUAT.dbo.SwiftMxMessage (
	id bigint IDENTITY(1,1) NOT NULL,
	CreatedDate datetime2(6) NULL,
	DestinationBic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	GeneratedXml text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MessageStatus varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MessageStatusDetails varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MessageType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	MxNamespace varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SentDate datetime2(6) NULL,
	SwiftMtMessageId bigint NULL,
	SwiftStandardReleaseVersion varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__SwiftMxM__3213E83F89C7BA8C PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.SwiftXmlTemplate definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.SwiftXmlTemplate;

CREATE TABLE IntegratorUAT.dbo.SwiftXmlTemplate (
	id bigint IDENTITY(1,1) NOT NULL,
	CreatedDate datetime2(6) NULL,
	IsActive varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ModifiedDate datetime2(6) NULL,
	MxType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SchemaDefinition text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	SwiftStandardReleaseNumber varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TemplateContent text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__SwiftXml__3213E83FCD17EB83 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.TBRDesc_Excel definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.TBRDesc_Excel;

CREATE TABLE IntegratorUAT.dbo.TBRDesc_Excel (
	TBRCode varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TBRDesc varchar(64) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TBRMethod varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);


-- IntegratorUAT.dbo.TBRMapping_Excel definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.TBRMapping_Excel;

CREATE TABLE IntegratorUAT.dbo.TBRMapping_Excel (
	[TBR No] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Field Name] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Type] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[LENGTH] bigint NULL,
	[FIELD OCCURANCE] varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	DELIMITER varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	TBRCode varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);


-- IntegratorUAT.dbo.fti_account_type definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.fti_account_type;

CREATE TABLE IntegratorUAT.dbo.fti_account_type (
	id bigint IDENTITY(1,1) NOT NULL,
	account_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	account_type_id bigint NULL,
	account_description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__fti_acco__3213E83F41487B08 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.fti_transaction definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.fti_transaction;

CREATE TABLE IntegratorUAT.dbo.fti_transaction (
	id bigint IDENTITY(1,1) NOT NULL,
	created_date datetime2(6) NULL,
	draw_number varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_event varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	last_step varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	master_ref_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	reservation_id varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	update_date datetime2(6) NULL,
	CONSTRAINT PK__fti_tran__3213E83F433BB682 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.fti_transaction_detail definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.fti_transaction_detail;

CREATE TABLE IntegratorUAT.dbo.fti_transaction_detail (
	id bigint IDENTITY(1,1) NOT NULL,
	core_sys_message varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	core_sys_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	core_sys_status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime2(6) NULL,
	fti_event varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	header_id bigint NULL,
	trans_message_log_id bigint NULL,
	trans_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additional_info1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additional_info2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additional_info3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additional_info4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	additional_info5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	req_message text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	res_message text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__fti_tran__3213E83F8ABE482C PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.fti_transaction_detail_posting definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.fti_transaction_detail_posting;

CREATE TABLE IntegratorUAT.dbo.fti_transaction_detail_posting (
	id bigint IDENTITY(1,1) NOT NULL,
	account varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	account_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	account_type_alias varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	amount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ccy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ccy_alias varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ccy_number varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	debit_credit varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_seq_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	value_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	id_group bigint NULL,
	CONSTRAINT PK__fti_tran__3213E83F3BDE048F PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.fti_transaction_detail_posting_group definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.fti_transaction_detail_posting_group;

CREATE TABLE IntegratorUAT.dbo.fti_transaction_detail_posting_group (
	id bigint IDENTITY(1,1) NOT NULL,
	flag_cross_valas varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	flag_mdmc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	group_id varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	mapping_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tbr_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	detail_id bigint NULL,
	CONSTRAINT PK__fti_tran__3213E83F0C30929B PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.fti_transaction_posting definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.fti_transaction_posting;

CREATE TABLE IntegratorUAT.dbo.fti_transaction_posting (
	id bigint IDENTITY(1,1) NOT NULL,
	account_identifier varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	account_number varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	account_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	add_mnt_del_flag varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	against_ccy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	analysis_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	application varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	back_office_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bank_code1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bank_code2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bank_code3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bank_code4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	bank_code5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	beneficiary_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_amt1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_amt2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_amt3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_amt4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_amt5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_amt6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_categorisation_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_ccy1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_ccy2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_ccy3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_ccy4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_ccy5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	charge_ccy6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cheque_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	clearing_number varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterparty_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterparty_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartybic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterparty_bank_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterparty_bank_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterparty_bankbic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterparty_bank_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterparty_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	counterpartyiban varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_sender_to_receiver_info varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_time_code1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_time_code2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_time_code3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_time_details1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_time_details2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_time_details3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cover_transfer_method varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	customer_mnemonic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	customer_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	debit_credit_flag varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	event_key varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	event_reference varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	exchange_rate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	external_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	iban varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	id_header bigint NULL,
	input_branch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructed_amount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instructed_ccy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_code1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_code2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_code3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_code4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_code5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_code6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_text1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_text2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_text3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_text4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_text5 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	instruction_text6 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediary_bank_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediary_bank_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediary_bankbic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	intermediary_bank_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	internal_recn_ref varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	issue_or_contract_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	main_transfer_method varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	master_key varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	master_reference varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	nostro_mnemonic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ordering_customer_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ordering_customer_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ordering_customerbic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ordering_customer_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	original_amount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	original_ccy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	other_account_number varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	other_party_ref varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	parent_country varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	pay_receive_flag varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	paying_bank_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	paying_bank_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	paying_bankbic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	paying_bank_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	paying_bank_transliterate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payment_details varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	payment_system varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_amount varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_branch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_ccy varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_narrative1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_narrative2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_narrative3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_narrative4 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	posting_seq_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	product_reference varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receivers_correspondent_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receivers_correspondent_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receivers_correspondentbic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	receivers_correspondent_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	regulatory_reporting varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	related_party varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sender_to_receiver_info varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	service_level varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_account_alternative_acc_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_account_party_account varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_account_party_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_account_partybic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_account_party_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_account_used varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_narrative varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_party_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_party_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_partybic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_party_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_sundry_reference_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_type_via varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_user_code1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	settlement_user_code2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	spsk_category_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	spsk_mnemonic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	sundry_reference_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	swif_tmessage_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	swift_charges_for varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	team varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tenor_end varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tenor_start varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	third_reimbursing_bank_account_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	third_reimbursing_bank_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	third_reimbursing_bankbic varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	third_reimbursing_bank_custid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	time_code1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	time_code2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	time_code3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	time_details1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	time_details2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	time_details3 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transaction_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transactionid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transaction_seq_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	transaction_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_code1 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_code2 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	value_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__fti_tran__3213E83F3AA8FE9D PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.log_interface_process definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.log_interface_process;

CREATE TABLE IntegratorUAT.dbo.log_interface_process (
	id bigint IDENTITY(1,1) NOT NULL,
	activity varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	activity_description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	activity_status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	id_log_parent bigint NULL,
	integration_message text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	log_date datetime2(6) NULL,
	CONSTRAINT PK__log_inte__3213E83FB5D021FC PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.log_queue_data definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.log_queue_data;

CREATE TABLE IntegratorUAT.dbo.log_queue_data (
	id bigint IDENTITY(1,1) NOT NULL,
	correlationid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime2(6) NULL,
	delivery_date datetime2(6) NULL,
	destination varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	messageuid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	origin varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	req_message nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	res_message nvarchar(MAX) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_info varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updated_date datetime2(6) NULL,
	related_trans_ref varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__log_queu__3213E83FB2B1A231 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.log_queue_data_backup definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.log_queue_data_backup;

CREATE TABLE IntegratorUAT.dbo.log_queue_data_backup (
	id bigint IDENTITY(1,1) NOT NULL,
	correlationid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime2(6) NULL,
	delivery_date datetime2(6) NULL,
	destination varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	messageuid varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	origin varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	req_message text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	res_message text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status_info varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updated_date datetime2(6) NULL
);


-- IntegratorUAT.dbo.ms_account_type definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_account_type;

CREATE TABLE IntegratorUAT.dbo.ms_account_type (
	id bigint IDENTITY(1,1) NOT NULL,
	account_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_accou__3213E83F7CBC0D88 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_branch definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_branch;

CREATE TABLE IntegratorUAT.dbo.ms_branch (
	id bigint IDENTITY(1,1) NOT NULL,
	branch_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branch_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branch_region varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	terminal_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	user_id varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	spv_user_id varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_branc__3213E83F78FD48A9 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_company_data definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_company_data;

CREATE TABLE IntegratorUAT.dbo.ms_company_data (
	id bigint IDENTITY(1,1) NOT NULL,
	acc_info_data text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cifno varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime2(6) NULL,
	cust_info_data text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	gcifno varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updated_date datetime2(6) NULL,
	tag_bank varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tag_customer varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_compa__3213E83F950E2A01 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_company_limit definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_company_limit;

CREATE TABLE IntegratorUAT.dbo.ms_company_limit (
	id bigint IDENTITY(1,1) NOT NULL,
	cifno varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cbranch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ibranch varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_compa__3213E83F9DB4059E PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_currency definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_currency;

CREATE TABLE IntegratorUAT.dbo.ms_currency (
	id bigint IDENTITY(1,1) NOT NULL,
	country_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	internal_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	iso_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	decimal_point bigint NULL,
	CONSTRAINT PK__ms_curre__3213E83FCF85CBBF PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_facility_backup_20260615 definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_facility_backup_20260615;

CREATE TABLE IntegratorUAT.dbo.ms_facility_backup_20260615 (
	id bigint IDENTITY(1,1) NOT NULL,
	commitment_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitment_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	company_limit_id bigint NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_digit_note varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_loan_acc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	loan_currency_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maturity_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branch_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cif_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);


-- IntegratorUAT.dbo.ms_facility_recon definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_facility_recon;

CREATE TABLE IntegratorUAT.dbo.ms_facility_recon (
	id bigint IDENTITY(1,1) NOT NULL,
	commitment_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitment_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	company_limit_id bigint NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_digit_note varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_loan_acc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	loan_currency_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maturity_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branch_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cif_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	rn bigint NULL,
	record_type varchar(9) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	duplicate_count int NULL
);


-- IntegratorUAT.dbo.ms_facility_utilize_backup_20260615 definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_facility_utilize_backup_20260615;

CREATE TABLE IntegratorUAT.dbo.ms_facility_utilize_backup_20260615 (
	id bigint IDENTITY(1,1) NOT NULL,
	commitment_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitment_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	company_limit_id bigint NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	facility_id bigint NULL,
	key_digit_note varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_loan_acc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	loan_currency_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maturity_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branch_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cif_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);


-- IntegratorUAT.dbo.ms_map_cls_product_type definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_map_cls_product_type;

CREATE TABLE IntegratorUAT.dbo.ms_map_cls_product_type (
	id bigint IDENTITY(1,1) NOT NULL,
	islamic_flag int NULL,
	line_of_business varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	product_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	product_type001 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	product_type999 varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	special_flag int NULL,
	event_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	liability_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_map_c__3213E83F1A775391 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_parameter definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_parameter;

CREATE TABLE IntegratorUAT.dbo.ms_parameter (
	id bigint IDENTITY(1,1) NOT NULL,
	created_by varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime2(6) NULL,
	update_date datetime2(6) NULL,
	updated_by varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prm_desc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prm_key varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	prm_value text COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_param__3213E83F1AE2CEBC PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_queue_config definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_queue_config;

CREATE TABLE IntegratorUAT.dbo.ms_queue_config (
	id bigint IDENTITY(1,1) NOT NULL,
	enable_status int NOT NULL,
	request_queue_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	request_queue_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	request_queue_password varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	request_queue_username varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_queue_address varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_queue_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_queue_password varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_queue_username varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	service_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	listener_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	request_queue_channel varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	request_queue_manager varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	request_queue_port varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_queue_channel varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_queue_manager varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_queue_port varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	process_name varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_queue__3213E83FAEDE4213 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_reference_config definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_reference_config;

CREATE TABLE IntegratorUAT.dbo.ms_reference_config (
	id bigint IDENTITY(1,1) NOT NULL,
	ref_config varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ref_product varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ref_purpose varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ref_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	ref_event varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_refer__3213E83F3B0F788D PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_user definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_user;

CREATE TABLE IntegratorUAT.dbo.ms_user (
	id bigint IDENTITY(1,1) NOT NULL,
	created_date datetime2(6) NULL,
	email varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	fullname varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	password varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	updated_date datetime2(6) NULL,
	username varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_user__3213E83F242C60EC PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.ms_utilize_running_number_backup_20260615 definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_utilize_running_number_backup_20260615;

CREATE TABLE IntegratorUAT.dbo.ms_utilize_running_number_backup_20260615 (
	id bigint IDENTITY(1,1) NOT NULL,
	company_limit_id bigint NULL,
	facility_id bigint NULL,
	running_number int NOT NULL
);


-- IntegratorUAT.dbo.mstbr definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.mstbr;

CREATE TABLE IntegratorUAT.dbo.mstbr (
	id bigint IDENTITY(1,1) NOT NULL,
	tbrcode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tbrmethod varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tbrname varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__mstbr__3213E83FA011DFF1 PRIMARY KEY (id)
);


-- IntegratorUAT.dbo.MsFacility definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsFacility;

CREATE TABLE IntegratorUAT.dbo.MsFacility (
	id bigint IDENTITY(1,1) NOT NULL,
	branchCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cifNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitmentBalance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitmentBalanceSign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	companyLimitId bigint NULL,
	currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	keyDigitNote varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	keyLoanAcc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	loanCurrencyCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maturityDate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	noteDate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	noteType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principalBalance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principalBalanceSign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsFacili__3213E83FF0CC712E PRIMARY KEY (id),
	CONSTRAINT FKjjeqdg42kb9vryi006qspefll FOREIGN KEY (companyLimitId) REFERENCES IntegratorUAT.dbo.MsCompanyLimit(id)
);


-- IntegratorUAT.dbo.MsFacilityUtilize definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsFacilityUtilize;

CREATE TABLE IntegratorUAT.dbo.MsFacilityUtilize (
	id bigint IDENTITY(1,1) NOT NULL,
	branchCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cifNo varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitmentBalance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitmentBalanceSign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	companyLimitId bigint NULL,
	currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	facilityId bigint NULL,
	keyDigitNote varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	keyLoanAcc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	loanCurrencyCode varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maturityDate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	noteDate varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	noteType varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principalBalance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principalBalanceSign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__MsFacili__3213E83F4A332C28 PRIMARY KEY (id),
	CONSTRAINT FKekli3loenk9qivv2vbj0qph8 FOREIGN KEY (companyLimitId) REFERENCES IntegratorUAT.dbo.MsCompanyLimit(id)
);


-- IntegratorUAT.dbo.MsUtilizeRunningNumber definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.MsUtilizeRunningNumber;

CREATE TABLE IntegratorUAT.dbo.MsUtilizeRunningNumber (
	id bigint IDENTITY(1,1) NOT NULL,
	companyLimitId bigint NULL,
	facilityId bigint NULL,
	runningNumber int NOT NULL,
	CONSTRAINT PK__MsUtiliz__3213E83FFDE4E5D5 PRIMARY KEY (id),
	CONSTRAINT FK8fbirg5kwik7eakhsgh7l47jm FOREIGN KEY (facilityId) REFERENCES IntegratorUAT.dbo.MsFacility(id)
);


-- IntegratorUAT.dbo.ms_facility definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_facility;

CREATE TABLE IntegratorUAT.dbo.ms_facility (
	id bigint IDENTITY(1,1) NOT NULL,
	commitment_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitment_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	company_limit_id bigint NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_digit_note varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_loan_acc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	loan_currency_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maturity_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branch_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cif_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_facil__3213E83F3FD958DB PRIMARY KEY (id),
	CONSTRAINT FK8g8rax2yo9e5yqq5wo7at08tf FOREIGN KEY (company_limit_id) REFERENCES IntegratorUAT.dbo.ms_company_limit(id)
);


-- IntegratorUAT.dbo.ms_facility_utilize definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_facility_utilize;

CREATE TABLE IntegratorUAT.dbo.ms_facility_utilize (
	id bigint IDENTITY(1,1) NOT NULL,
	commitment_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	commitment_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	company_limit_id bigint NULL,
	description varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	facility_id bigint NULL,
	key_digit_note varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	key_loan_acc varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	loan_currency_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	maturity_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_date varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	note_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	principal_balance_sign varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	status varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	branch_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	cif_no varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__ms_facil__3213E83FF9725F8E PRIMARY KEY (id),
	CONSTRAINT FK58ax6kfwxd6p3qhf70713dpx8 FOREIGN KEY (company_limit_id) REFERENCES IntegratorUAT.dbo.ms_company_limit(id)
);


-- IntegratorUAT.dbo.ms_utilize_running_number definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.ms_utilize_running_number;

CREATE TABLE IntegratorUAT.dbo.ms_utilize_running_number (
	id bigint IDENTITY(1,1) NOT NULL,
	company_limit_id bigint NULL,
	facility_id bigint NULL,
	running_number int NOT NULL,
	CONSTRAINT PK__ms_utili__3213E83FF642C5C5 PRIMARY KEY (id),
	CONSTRAINT FKa5et5uwurdp09001nxjibjqfm FOREIGN KEY (facility_id) REFERENCES IntegratorUAT.dbo.ms_facility(id),
	CONSTRAINT FKieydyehn9yp8magc79dhsovim FOREIGN KEY (company_limit_id) REFERENCES IntegratorUAT.dbo.ms_company_limit(id)
);


-- IntegratorUAT.dbo.mstbrfield definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.mstbrfield;

CREATE TABLE IntegratorUAT.dbo.mstbrfield (
	id bigint IDENTITY(1,1) NOT NULL,
	destination_field varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	field_length int NOT NULL,
	padding_char varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	padding_position varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	source_field varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tbr_id bigint NULL,
	default_value varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	destination_field_data_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	mapping_account_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	mapping_currency varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	mapping_debit_credit varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	mapping_position varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__mstbrfie__3213E83FF88BB149 PRIMARY KEY (id),
	CONSTRAINT FK14vgac3v2m9v9s8g57gjut41p FOREIGN KEY (tbr_id) REFERENCES IntegratorUAT.dbo.mstbr(id),
	CONSTRAINT FK972r7xdwna950khuehi9yb91m FOREIGN KEY (tbr_id) REFERENCES IntegratorUAT.dbo.mstbr(id)
);


-- IntegratorUAT.dbo.mstbrmapping definition

-- Drop table

-- DROP TABLE IntegratorUAT.dbo.mstbrmapping;

CREATE TABLE IntegratorUAT.dbo.mstbrmapping (
	id bigint IDENTITY(1,1) NOT NULL,
	account_type_id bigint NULL,
	currency_code varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	debit_credit varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	tbr_id bigint NULL,
	mapping_type varchar(255) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	CONSTRAINT PK__mstbrmap__3213E83F50550FE2 PRIMARY KEY (id),
	CONSTRAINT FK8u3rtlaxe6h1k2bx1opw72km5 FOREIGN KEY (tbr_id) REFERENCES IntegratorUAT.dbo.mstbr(id),
	CONSTRAINT FKppy7ucfa05f5o53n6j07y5g97 FOREIGN KEY (tbr_id) REFERENCES IntegratorUAT.dbo.mstbr(id),
	CONSTRAINT FKs47uibrsoo37mb7jemhssyeel FOREIGN KEY (account_type_id) REFERENCES IntegratorUAT.dbo.ms_account_type(id)
);


-- dbo.vw_tbr_mapping source

-- dbo.vw_tbr_mapping source

-- dbo.vw_tbr_mapping source

-- dbo.vw_tbr_mapping source

-- dbo.vw_tbr_mapping source

-- dbo.vw_tbr_mapping source

CREATE VIEW [dbo].[vw_tbr_mapping] AS
select DISTINCT  m.id,mat.account_type,m.currency_code,m.debit_credit,tbr.tbrcode,m.mapping_type FROM 
Mstbr tbr
join mstbrmapping m on m.tbr_id = tbr.id 
join ms_account_type mat on mat.id = m.account_type_id;


