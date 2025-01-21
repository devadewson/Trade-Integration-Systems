package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "MsCompanyData",schema = "dbo")
@NoArgsConstructor
public class MsCompanyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gcifno;

    private String cifno;

    @Column(columnDefinition = "TEXT")
    private String custInfoData;

    @Column(columnDefinition = "TEXT")
    private String accInfoData;

    private String tagCustomer;
    private String tagBank;
    private Date created_date;
    private Date updated_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getGcifno() {
        return gcifno;
    }

    public void setGcifno(String gcifno) {
        this.gcifno = gcifno;
    }

    public String getCustInfoData() {
        return custInfoData;
    }

    public void setCustInfoData(String custInfoData) {
        this.custInfoData = custInfoData;
    }

    public String getAccInfoData() {
        return accInfoData;
    }

    public void setAccInfoData(String accInfoData) {
        this.accInfoData = accInfoData;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public String getTagCustomer() {
        return tagCustomer;
    }

    public void setTagCustomer(String tagCustomer) {
        this.tagCustomer = tagCustomer;
    }

    public String getTagBank() {
        return tagBank;
    }

    public void setTagBank(String tagBank) {
        this.tagBank = tagBank;
    }
}
