package com.maybank.integratorapp.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsMapClsProductType",schema = "dbo")
@NoArgsConstructor
public class MsMapClsProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ProductName;
    private String ProductType999;
    private String ProductType001;
    @Nullable
    private int IslamicFlag;
    private String LineOfBusiness;
    private String EventCode;
    private String LiabilityCode;
    @Nullable
    private int SpecialFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductType999() {
        return ProductType999;
    }

    public void setProductType999(String productType999) {
        ProductType999 = productType999;
    }

    public String getProductType001() {
        return ProductType001;
    }

    public void setProductType001(String productType001) {
        ProductType001 = productType001;
    }

    public int getIslamicFlag() {
        return IslamicFlag;
    }

    public void setIslamicFlag(int islamicFlag) {
        IslamicFlag = islamicFlag;
    }

    public String getLineOfBusiness() {
        return LineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        LineOfBusiness = lineOfBusiness;
    }

    public int getSpecialFlag() {
        return SpecialFlag;
    }

    public void setSpecialFlag(int specialFlag) {
        SpecialFlag = specialFlag;
    }

    public String getEventCode() {
        return EventCode;
    }

    public void setEventCode(String eventCode) {
        EventCode = eventCode;
    }

    public String getLiabilityCode() {
        return LiabilityCode;
    }

    public void setLiabilityCode(String liabilityCode) {
        LiabilityCode = liabilityCode;
    }
}
