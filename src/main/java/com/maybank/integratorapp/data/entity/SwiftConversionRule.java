package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "SwiftConversionRule",schema = "dbo")
@NoArgsConstructor
public class SwiftConversionRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String SourceMtType;
    private String TargetMxType;
    private String SwiftStandardReleaseNumber;
    private String MxFieldPath;
    private String SourceType;
    private String SourceValue;
    private String InputTag;
    @Column(columnDefinition = "TEXT")
    private String ConstantValue;
    @Column(columnDefinition = "TEXT")
    private String MappingJson;
    private String IsActive;
    @Column(columnDefinition = "TEXT")
    private String Description;
    private Date CreatedDate;
    private Date ModifiedDate;

    public String getInputTag() {
        return InputTag;
    }

    public void setInputTag(String inputTag) {
        InputTag = inputTag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceMtType() {
        return SourceMtType;
    }

    public void setSourceMtType(String sourceMtType) {
        SourceMtType = sourceMtType;
    }

    public String getTargetMxType() {
        return TargetMxType;
    }

    public void setTargetMxType(String targetMxType) {
        TargetMxType = targetMxType;
    }

    public String getSwiftStandardReleaseNumber() {
        return SwiftStandardReleaseNumber;
    }

    public void setSwiftStandardReleaseNumber(String swiftStandardReleaseNumber) {
        SwiftStandardReleaseNumber = swiftStandardReleaseNumber;
    }

    public String getMxFieldPath() {
        return MxFieldPath;
    }

    public void setMxFieldPath(String mxFieldPath) {
        MxFieldPath = mxFieldPath;
    }

    public String getSourceType() {
        return SourceType;
    }

    public void setSourceType(String sourceType) {
        SourceType = sourceType;
    }

    public String getSourceValue() {
        return SourceValue;
    }

    public void setSourceValue(String sourceValue) {
        SourceValue = sourceValue;
    }

    public String getConstantValue() {
        return ConstantValue;
    }

    public void setConstantValue(String constantValue) {
        ConstantValue = constantValue;
    }

    public String getMappingJson() {
        return MappingJson;
    }

    public void setMappingJson(String mappingJson) {
        MappingJson = mappingJson;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public Date getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        ModifiedDate = modifiedDate;
    }
}
