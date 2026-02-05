package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsReferenceConfig",schema = "dbo")
@NoArgsConstructor
public class MsReferenceConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String RefType;
    private String RefPurpose;
    private String RefProduct;
    private String RefConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefType() {
        return RefType;
    }

    public void setRefType(String refType) {
        RefType = refType;
    }

    public String getRefPurpose() {
        return RefPurpose;
    }

    public void setRefPurpose(String refPurpose) {
        RefPurpose = refPurpose;
    }

    public String getRefProduct() {
        return RefProduct;
    }

    public void setRefProduct(String refProduct) {
        RefProduct = refProduct;
    }

    public String getRefConfig() {
        return RefConfig;
    }

    public void setRefConfig(String refConfig) {
        RefConfig = refConfig;
    }
}
