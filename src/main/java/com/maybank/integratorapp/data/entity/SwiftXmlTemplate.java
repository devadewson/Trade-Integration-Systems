package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "SwiftXmlTemplate",schema = "dbo")
@NoArgsConstructor
public class SwiftXmlTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String MxType;
    private String SwiftStandardReleaseNumber;
    @Column(columnDefinition = "TEXT")
    private String TemplateContent;
    @Column(columnDefinition = "TEXT")
    private String SchemaDefinition;
    private String IsActive;
    private Date CreatedDate;
    private Date ModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMxType() {
        return MxType;
    }

    public void setMxType(String mxType) {
        MxType = mxType;
    }

    public String getSwiftStandardReleaseNumber() {
        return SwiftStandardReleaseNumber;
    }

    public void setSwiftStandardReleaseNumber(String swiftStandardReleaseNumber) {
        SwiftStandardReleaseNumber = swiftStandardReleaseNumber;
    }

    public String getTemplateContent() {
        return TemplateContent;
    }

    public void setTemplateContent(String templateContent) {
        TemplateContent = templateContent;
    }

    public String getSchemaDefinition() {
        return SchemaDefinition;
    }

    public void setSchemaDefinition(String schemaDefinition) {
        SchemaDefinition = schemaDefinition;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
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
