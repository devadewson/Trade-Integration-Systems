package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Table(name = "MsFacility",schema = "dbo")
@NoArgsConstructor
public class MsFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyLimitId;
    private String keyDigitNote;
    private String commitmentBalance;
    private String commitmentBalanceSign;
    private String description;
    private String keyLoanAcc;
    private String loanCurrencyCode;
    private String maturityDate;
    private String noteDate;
    private String noteType;
    private String principalBalance;
    private String principalBalanceSign;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyLimitId() {
        return companyLimitId;
    }

    public void setCompanyLimitId(Long companyLimitId) {
        this.companyLimitId = companyLimitId;
    }

    public String getKeyDigitNote() {
        return keyDigitNote;
    }

    public void setKeyDigitNote(String keyDigitNote) {
        this.keyDigitNote = keyDigitNote;
    }

    public String getCommitmentBalance() {
        return commitmentBalance;
    }

    public void setCommitmentBalance(String commitmentBalance) {
        this.commitmentBalance = commitmentBalance;
    }

    public String getCommitmentBalanceSign() {
        return commitmentBalanceSign;
    }

    public void setCommitmentBalanceSign(String commitmentBalanceSign) {
        this.commitmentBalanceSign = commitmentBalanceSign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyLoanAcc() {
        return keyLoanAcc;
    }

    public void setKeyLoanAcc(String keyLoanAcc) {
        this.keyLoanAcc = keyLoanAcc;
    }

    public String getLoanCurrencyCode() {
        return loanCurrencyCode;
    }

    public void setLoanCurrencyCode(String loanCurrencyCode) {
        this.loanCurrencyCode = loanCurrencyCode;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(String principalBalance) {
        this.principalBalance = principalBalance;
    }

    public String getPrincipalBalanceSign() {
        return principalBalanceSign;
    }

    public void setPrincipalBalanceSign(String principalBalanceSign) {
        this.principalBalanceSign = principalBalanceSign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
