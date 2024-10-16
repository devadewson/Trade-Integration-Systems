package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "MsParameter",schema = "dbo")
@NoArgsConstructor
public class MsParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String prmKey;
    private String prmValue;

    private String prmDesc;
    private Date CreatedDate;
    private String CreatedBy;
    private String UpdatedBy;
    private Date UpdateDate;

    public String getPrmKey() {
        return prmKey;
    }

    public void setPrmKey(String prmKey) {
        this.prmKey = prmKey;
    }

    public String getPrmValue() {
        return prmValue;
    }

    public void setPrmValue(String prmValue) {
        this.prmValue = prmValue;
    }

    public String getPrmDesc() {
        return prmDesc;
    }

    public void setPrmDesc(String prmDesc) {
        this.prmDesc = prmDesc;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public Date getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(Date updateDate) {
        UpdateDate = updateDate;
    }
}
