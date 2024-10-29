package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FtiAccountType",schema = "dbo")
@NoArgsConstructor
public class FtiAccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String AccountType;
    private String AccountDescription;
    private Long AccountTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public Long getAccountTypeId() {
        return AccountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        AccountTypeId = accountTypeId;
    }

    public String getAccountDescription() {
        return AccountDescription;
    }

    public void setAccountDescription(String accountDescription) {
        AccountDescription = accountDescription;
    }
}
