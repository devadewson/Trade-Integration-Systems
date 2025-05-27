package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FtiTransactionDetailPostingGroup",schema = "dbo")
@NoArgsConstructor
public class FtiTransactionDetailPostingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long DetailId;
    private String GroupId;
    private String TbrCode;
    private String MappingType;
    private String FlagCrossValas;
    private String FlagMdmc;

    public Long getDetailId() {
        return DetailId;
    }

    public void setDetailId(Long detailId) {
        DetailId = detailId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getTbrCode() {
        return TbrCode;
    }

    public void setTbrCode(String tbrCode) {
        TbrCode = tbrCode;
    }

    public String getMappingType() {
        return MappingType;
    }

    public void setMappingType(String mappingType) {
        MappingType = mappingType;
    }

    public String getFlagCrossValas() {
        return FlagCrossValas;
    }

    public void setFlagCrossValas(String flagCrossValas) {
        FlagCrossValas = flagCrossValas;
    }

    public String getFlagMdmc() {
        return FlagMdmc;
    }

    public void setFlagMdmc(String flagMdmc) {
        FlagMdmc = flagMdmc;
    }
}
