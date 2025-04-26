package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MsUtilizeRunningNumber", schema = "dbo")
@NoArgsConstructor
public class MsUtilizeRunningNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long facilityId;
    private Long companyLimitId;
    @ManyToOne
    @JoinColumn(name = "companyLimitId", insertable = false, updatable = false)
    private MsCompanyLimit msCompanyLimit;
    private int runningNumber;

    public MsCompanyLimit getMsCompanyLimit() {
        return msCompanyLimit;
    }

    public void setMsCompanyLimit(MsCompanyLimit msCompanyLimit) {
        this.msCompanyLimit = msCompanyLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }

    public Long getCompanyLimitId() {
        return companyLimitId;
    }

    public void setCompanyLimitId(Long companyLimitId) {
        this.companyLimitId = companyLimitId;
    }

    public int getRunningNumber() {
        return runningNumber;
    }
    public void setRunningNumber(int runningNumber) {
        this.runningNumber = runningNumber;
    }
}
