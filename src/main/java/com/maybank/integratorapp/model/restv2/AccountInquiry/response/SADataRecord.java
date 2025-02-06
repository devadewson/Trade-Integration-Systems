package com.maybank.integratorapp.model.restv2.AccountInquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SADataRecord {
    @JsonProperty("TransId")
    private String transId;

    @JsonProperty("TransCd")
    private String transCd;

    @JsonProperty("AcctCcy")
    private String acctCcy;

    @JsonProperty("AcctBranch")
    private String acctBranch;

    @JsonProperty("AcctCtl4")
    private String acctCtl4;

    @JsonProperty("AcctNumber")
    private String acctNumber;

    @JsonProperty("Filler")
    private String filler;

    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("BeginningBalance")
    private String beginningBalance;

    @JsonProperty("TodayClearing")
    private String todayClearing;

    @JsonProperty("YesterdayClearing")
    private String yesterdayClearing;

    @JsonProperty("AvailableBalance")
    private String availableBalance;

    @JsonProperty("HoldAmount")
    private String holdAmount;

    @JsonProperty("PsbkBalance")
    private String psbkBalance;

    @JsonProperty("AccruedInterest")
    private String accruedInterest;

    @JsonProperty("OpenBalance")
    private String openBalance;

    @JsonProperty("CloseBalance")
    private String closeBalance;

    @JsonProperty("OpenDate")
    private String openDate;

    @JsonProperty("MntPeriod")
    private String mntPeriod;

    @JsonProperty("MntIncr")
    private String mntIncr;

    @JsonProperty("MatDateNextMat")
    private String matDateNextMat;

    @JsonProperty("LastRenewDate")
    private String lastRenewDate;

    @JsonProperty("IpCurAnnlRate2")
    private String ipCurAnnlRate2;

    @JsonProperty("IntDistCode")
    private String intDistCode;

    @JsonProperty("IntDistDesc")
    private String intDistDesc;

    @JsonProperty("IntDistAct01")
    private String intDistAct01;

    @JsonProperty("IntDistAct02")
    private String intDistAct02;

    @JsonProperty("MatPrincipalDist")
    private String matPrincipalDist;

    @JsonProperty("MatPrincipalDesc")
    private String matPrincipalDesc;

    @JsonProperty("MatPrincipalAct")
    private String matPrincipalAct;

    @JsonProperty("IntDistDateNextPay")
    private String intDistDateNextPay;

    @JsonProperty("CustName")
    private String custName;

    @JsonProperty("MailAddrLine1")
    private String mailAddrLine1;

    @JsonProperty("MailAddrLine2")
    private String mailAddrLine2;

    @JsonProperty("MailAddrLine3")
    private String mailAddrLine3;

    @JsonProperty("MailAddrLine4")
    private String mailAddrLine4;

    @JsonProperty("CityState")
    private String cityState;

    @JsonProperty("ZipCode")
    private String zipCode;

    @JsonProperty("ProdCode")
    private String prodCode;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("StatusDescription")
    private String statusDescription;

    @JsonProperty("LastMainDate")
    private String lastMainDate;

    @JsonProperty("IntPayable")
    private String intPayable;

    @JsonProperty("ResPhNo")
    private String resPhNo;

    @JsonProperty("BusinessPhone")
    private String businessPhone;

    @JsonProperty("ATMFlag")
    private String atmFlag;

    @JsonProperty("ResidentFlag")
    private String residentFlag;

    @JsonProperty("YesterdayClrStatus")
    private String yesterdayClrStatus;

    @JsonProperty("CIFNo")
    private String cifNo;

    @JsonProperty("ProductName")
    private String productName;

    @JsonProperty("ShortAccountName")
    private String shortAccountName;

    @JsonProperty("TaxId")
    private String taxId;

    @JsonProperty("AccountType")
    private String accountType;

    @JsonProperty("IslamicFlag")
    private String islamicFlag;

    @JsonProperty("PreferredStmtLanguage")
    private String preferredStmtLanguage;

    @JsonProperty("CIFName")
    private String cifName;

    @JsonProperty("UtilisedAmount")
    private String utilisedAmount;

    @JsonProperty("UnutilisedAmount")
    private String unutilisedAmount;

    @JsonProperty("LedgerAmount")
    private String ledgerAmount;

    @JsonProperty("BusinessReg")
    private String businessReg;

    @JsonProperty("MinimumBalance")
    private String minimumBalance;

    @JsonProperty("ForexAcumAmt")
    private String forexAcumAmt;

    @JsonProperty("Cabang")
    private String cabang;

    @JsonProperty("NPKCrossSeller")
    private String npkCrossSeller;

    @JsonProperty("Unused")
    private String unused;

    @JsonProperty("CardNumber")
    private String cardNumber;
    @JsonProperty("TranInAmount")
    private String tranInAmount;

    @JsonProperty("TranOutAmount")
    private String tranOutAmount;

    @JsonProperty("TranInFrequency")
    private String tranInFrequency;

    @JsonProperty("TranOutFrequency")
    private String tranOutFrequency;

    @JsonProperty("SourceOfFund")
    private String sourceOfFund;

    @JsonProperty("PurposeOfFund")
    private String purposeOfFund;

    @JsonProperty("RelDesc")
    private String relDesc;

    @JsonProperty("RelAct")
    private String relAct;

    @JsonProperty("BranchNm")
    private String branchNm;

    @JsonProperty("TimesRenew")
    private String timesRenew;

    @JsonProperty("CurrCd")
    private String currCd;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getTransCd() {
        return transCd;
    }

    public void setTransCd(String transCd) {
        this.transCd = transCd;
    }

    public String getAcctCcy() {
        return acctCcy;
    }

    public void setAcctCcy(String acctCcy) {
        this.acctCcy = acctCcy;
    }

    public String getAcctBranch() {
        return acctBranch;
    }

    public void setAcctBranch(String acctBranch) {
        this.acctBranch = acctBranch;
    }

    public String getAcctCtl4() {
        return acctCtl4;
    }

    public void setAcctCtl4(String acctCtl4) {
        this.acctCtl4 = acctCtl4;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getBeginningBalance() {
        return beginningBalance;
    }

    public void setBeginningBalance(String beginningBalance) {
        this.beginningBalance = beginningBalance;
    }

    public String getTodayClearing() {
        return todayClearing;
    }

    public void setTodayClearing(String todayClearing) {
        this.todayClearing = todayClearing;
    }

    public String getYesterdayClearing() {
        return yesterdayClearing;
    }

    public void setYesterdayClearing(String yesterdayClearing) {
        this.yesterdayClearing = yesterdayClearing;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getHoldAmount() {
        return holdAmount;
    }

    public void setHoldAmount(String holdAmount) {
        this.holdAmount = holdAmount;
    }

    public String getPsbkBalance() {
        return psbkBalance;
    }

    public void setPsbkBalance(String psbkBalance) {
        this.psbkBalance = psbkBalance;
    }

    public String getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(String accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public String getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(String openBalance) {
        this.openBalance = openBalance;
    }

    public String getCloseBalance() {
        return closeBalance;
    }

    public void setCloseBalance(String closeBalance) {
        this.closeBalance = closeBalance;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getMntPeriod() {
        return mntPeriod;
    }

    public void setMntPeriod(String mntPeriod) {
        this.mntPeriod = mntPeriod;
    }

    public String getMntIncr() {
        return mntIncr;
    }

    public void setMntIncr(String mntIncr) {
        this.mntIncr = mntIncr;
    }

    public String getMatDateNextMat() {
        return matDateNextMat;
    }

    public void setMatDateNextMat(String matDateNextMat) {
        this.matDateNextMat = matDateNextMat;
    }

    public String getLastRenewDate() {
        return lastRenewDate;
    }

    public void setLastRenewDate(String lastRenewDate) {
        this.lastRenewDate = lastRenewDate;
    }

    public String getIpCurAnnlRate2() {
        return ipCurAnnlRate2;
    }

    public void setIpCurAnnlRate2(String ipCurAnnlRate2) {
        this.ipCurAnnlRate2 = ipCurAnnlRate2;
    }

    public String getIntDistCode() {
        return intDistCode;
    }

    public void setIntDistCode(String intDistCode) {
        this.intDistCode = intDistCode;
    }

    public String getIntDistDesc() {
        return intDistDesc;
    }

    public void setIntDistDesc(String intDistDesc) {
        this.intDistDesc = intDistDesc;
    }

    public String getIntDistAct01() {
        return intDistAct01;
    }

    public void setIntDistAct01(String intDistAct01) {
        this.intDistAct01 = intDistAct01;
    }

    public String getIntDistAct02() {
        return intDistAct02;
    }

    public void setIntDistAct02(String intDistAct02) {
        this.intDistAct02 = intDistAct02;
    }

    public String getMatPrincipalDist() {
        return matPrincipalDist;
    }

    public void setMatPrincipalDist(String matPrincipalDist) {
        this.matPrincipalDist = matPrincipalDist;
    }

    public String getMatPrincipalDesc() {
        return matPrincipalDesc;
    }

    public void setMatPrincipalDesc(String matPrincipalDesc) {
        this.matPrincipalDesc = matPrincipalDesc;
    }

    public String getMatPrincipalAct() {
        return matPrincipalAct;
    }

    public void setMatPrincipalAct(String matPrincipalAct) {
        this.matPrincipalAct = matPrincipalAct;
    }

    public String getIntDistDateNextPay() {
        return intDistDateNextPay;
    }

    public void setIntDistDateNextPay(String intDistDateNextPay) {
        this.intDistDateNextPay = intDistDateNextPay;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMailAddrLine1() {
        return mailAddrLine1;
    }

    public void setMailAddrLine1(String mailAddrLine1) {
        this.mailAddrLine1 = mailAddrLine1;
    }

    public String getMailAddrLine2() {
        return mailAddrLine2;
    }

    public void setMailAddrLine2(String mailAddrLine2) {
        this.mailAddrLine2 = mailAddrLine2;
    }

    public String getMailAddrLine3() {
        return mailAddrLine3;
    }

    public void setMailAddrLine3(String mailAddrLine3) {
        this.mailAddrLine3 = mailAddrLine3;
    }

    public String getMailAddrLine4() {
        return mailAddrLine4;
    }

    public void setMailAddrLine4(String mailAddrLine4) {
        this.mailAddrLine4 = mailAddrLine4;
    }

    public String getCityState() {
        return cityState;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getLastMainDate() {
        return lastMainDate;
    }

    public void setLastMainDate(String lastMainDate) {
        this.lastMainDate = lastMainDate;
    }

    public String getIntPayable() {
        return intPayable;
    }

    public void setIntPayable(String intPayable) {
        this.intPayable = intPayable;
    }

    public String getResPhNo() {
        return resPhNo;
    }

    public void setResPhNo(String resPhNo) {
        this.resPhNo = resPhNo;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getAtmFlag() {
        return atmFlag;
    }

    public void setAtmFlag(String atmFlag) {
        this.atmFlag = atmFlag;
    }

    public String getResidentFlag() {
        return residentFlag;
    }

    public void setResidentFlag(String residentFlag) {
        this.residentFlag = residentFlag;
    }

    public String getYesterdayClrStatus() {
        return yesterdayClrStatus;
    }

    public void setYesterdayClrStatus(String yesterdayClrStatus) {
        this.yesterdayClrStatus = yesterdayClrStatus;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortAccountName() {
        return shortAccountName;
    }

    public void setShortAccountName(String shortAccountName) {
        this.shortAccountName = shortAccountName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIslamicFlag() {
        return islamicFlag;
    }

    public void setIslamicFlag(String islamicFlag) {
        this.islamicFlag = islamicFlag;
    }

    public String getPreferredStmtLanguage() {
        return preferredStmtLanguage;
    }

    public void setPreferredStmtLanguage(String preferredStmtLanguage) {
        this.preferredStmtLanguage = preferredStmtLanguage;
    }

    public String getCifName() {
        return cifName;
    }

    public void setCifName(String cifName) {
        this.cifName = cifName;
    }

    public String getUtilisedAmount() {
        return utilisedAmount;
    }

    public void setUtilisedAmount(String utilisedAmount) {
        this.utilisedAmount = utilisedAmount;
    }

    public String getUnutilisedAmount() {
        return unutilisedAmount;
    }

    public void setUnutilisedAmount(String unutilisedAmount) {
        this.unutilisedAmount = unutilisedAmount;
    }

    public String getLedgerAmount() {
        return ledgerAmount;
    }

    public void setLedgerAmount(String ledgerAmount) {
        this.ledgerAmount = ledgerAmount;
    }

    public String getBusinessReg() {
        return businessReg;
    }

    public void setBusinessReg(String businessReg) {
        this.businessReg = businessReg;
    }

    public String getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(String minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public String getForexAcumAmt() {
        return forexAcumAmt;
    }

    public void setForexAcumAmt(String forexAcumAmt) {
        this.forexAcumAmt = forexAcumAmt;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getNpkCrossSeller() {
        return npkCrossSeller;
    }

    public void setNpkCrossSeller(String npkCrossSeller) {
        this.npkCrossSeller = npkCrossSeller;
    }

    public String getUnused() {
        return unused;
    }

    public void setUnused(String unused) {
        this.unused = unused;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTranInAmount() {
        return tranInAmount;
    }

    public void setTranInAmount(String tranInAmount) {
        this.tranInAmount = tranInAmount;
    }

    public String getTranOutAmount() {
        return tranOutAmount;
    }

    public void setTranOutAmount(String tranOutAmount) {
        this.tranOutAmount = tranOutAmount;
    }

    public String getTranInFrequency() {
        return tranInFrequency;
    }

    public void setTranInFrequency(String tranInFrequency) {
        this.tranInFrequency = tranInFrequency;
    }

    public String getTranOutFrequency() {
        return tranOutFrequency;
    }

    public void setTranOutFrequency(String tranOutFrequency) {
        this.tranOutFrequency = tranOutFrequency;
    }

    public String getSourceOfFund() {
        return sourceOfFund;
    }

    public void setSourceOfFund(String sourceOfFund) {
        this.sourceOfFund = sourceOfFund;
    }

    public String getPurposeOfFund() {
        return purposeOfFund;
    }

    public void setPurposeOfFund(String purposeOfFund) {
        this.purposeOfFund = purposeOfFund;
    }

    public String getRelDesc() {
        return relDesc;
    }

    public void setRelDesc(String relDesc) {
        this.relDesc = relDesc;
    }

    public String getRelAct() {
        return relAct;
    }

    public void setRelAct(String relAct) {
        this.relAct = relAct;
    }

    public String getBranchNm() {
        return branchNm;
    }

    public void setBranchNm(String branchNm) {
        this.branchNm = branchNm;
    }

    public String getTimesRenew() {
        return timesRenew;
    }

    public void setTimesRenew(String timesRenew) {
        this.timesRenew = timesRenew;
    }

    public String getCurrCd() {
        return currCd;
    }

    public void setCurrCd(String currCd) {
        this.currCd = currCd;
    }
}
