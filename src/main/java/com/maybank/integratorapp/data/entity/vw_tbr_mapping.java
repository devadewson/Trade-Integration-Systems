package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Table(name = "`vw_tbr_mapping`")
@NoArgsConstructor
public class vw_tbr_mapping {
    @Id
    private Long id;
    private String account_type;
    private String currency_code;
    private String debit_credit;
    private String tbrcode;
    private String mapping_type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getDebit_credit() {
        return debit_credit;
    }

    public void setDebit_credit(String debit_credit) {
        this.debit_credit = debit_credit;
    }

    public String getTbrcode() {
        return tbrcode;
    }

    public void setTbrcode(String tbrcode) {
        this.tbrcode = tbrcode;
    }

    public String getMapping_type() {
        return mapping_type;
    }

    public void setMapping_type(String mapping_type) {
        this.mapping_type = mapping_type;
    }
}
