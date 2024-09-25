package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsTBRMapping",schema = "dbo")
@NoArgsConstructor
public class MsTBRMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long TBR_Id;
    private Long AccountType_Id;
    private String Debit_Credit;
    private String CurrencyCode;
    private String MappingType;

    public String getMappingType() {
        return MappingType;
    }

    public void setMappingType(String mappingType) {
        MappingType = mappingType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTBR_Id() {
        return TBR_Id;
    }

    public void setTBR_Id(Long TBR_Id) {
        this.TBR_Id = TBR_Id;
    }

    public Long getAccountType_Id() {
        return AccountType_Id;
    }

    public void setAccountType_Id(Long accountType_Id) {
        AccountType_Id = accountType_Id;
    }

    public String getDebit_Credit() {
        return Debit_Credit;
    }

    public void setDebit_Credit(String debit_Credit) {
        Debit_Credit = debit_Credit;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        CurrencyCode = currencyCode;
    }
}
