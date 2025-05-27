package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FtiTransactionDetailPosting",schema = "dbo")
@NoArgsConstructor
public class FtiTransactionDetailPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idGroup;
    private String CcyAlias;
    private String Ccy;
    private String CcyNumber;
    private String AccountTypeAlias;
    private String AccountType;
    private String DebitCredit;
    private String Account;
    private String Amount;
    private String ValueDate;
    private String PostingSeqNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Long idGroup) {
        this.idGroup = idGroup;
    }

    public String getCcyAlias() {
        return CcyAlias;
    }

    public void setCcyAlias(String ccyAlias) {
        CcyAlias = ccyAlias;
    }

    public String getCcy() {
        return Ccy;
    }

    public void setCcy(String ccy) {
        Ccy = ccy;
    }

    public String getCcyNumber() {
        return CcyNumber;
    }

    public void setCcyNumber(String ccyNumber) {
        CcyNumber = ccyNumber;
    }

    public String getAccountTypeAlias() {
        return AccountTypeAlias;
    }

    public void setAccountTypeAlias(String accountTypeAlias) {
        AccountTypeAlias = accountTypeAlias;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getDebitCredit() {
        return DebitCredit;
    }

    public void setDebitCredit(String debitCredit) {
        DebitCredit = debitCredit;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getValueDate() {
        return ValueDate;
    }

    public void setValueDate(String valueDate) {
        ValueDate = valueDate;
    }

    public String getPostingSeqNo() {
        return PostingSeqNo;
    }

    public void setPostingSeqNo(String postingSeqNo) {
        PostingSeqNo = postingSeqNo;
    }
}
