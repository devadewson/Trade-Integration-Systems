package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "SwiftMtTag",schema = "dbo")
@NoArgsConstructor
public class SwiftMtTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long SwiftMtMessageId;
    private String TagId;
    private String TagValue;
    private Integer SequenceOrder;
    private Date CreatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSwiftMtMessageId() {
        return SwiftMtMessageId;
    }

    public void setSwiftMtMessageId(Long swiftMtMessageId) {
        SwiftMtMessageId = swiftMtMessageId;
    }

    public String getTagId() {
        return TagId;
    }

    public void setTagId(String tagId) {
        TagId = tagId;
    }

    public String getTagValue() {
        return TagValue;
    }

    public void setTagValue(String tagValue) {
        TagValue = tagValue;
    }

    public Integer getSequenceOrder() {
        return SequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        SequenceOrder = sequenceOrder;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }
}
