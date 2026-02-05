package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "SwiftMxMessage",schema = "dbo")
@NoArgsConstructor
public class SwiftMxMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long SwiftMtMessageId;
    private String MessageType;
    private String MxNamespace;
    @Column(columnDefinition = "TEXT")
    private String GeneratedXml;
    private String DestinationBic;
    private String MessageStatus;
    private String MessageStatusDetails;
    private Date CreatedDate;
    private Date SentDate;
    private String SwiftStandardReleaseVersion;

    public String getSwiftStandardReleaseVersion() {
        return SwiftStandardReleaseVersion;
    }

    public void setSwiftStandardReleaseVersion(String swiftStandardReleaseVersion) {
        SwiftStandardReleaseVersion = swiftStandardReleaseVersion;
    }

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

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getMxNamespace() {
        return MxNamespace;
    }

    public void setMxNamespace(String mxNamespace) {
        MxNamespace = mxNamespace;
    }

    public String getGeneratedXml() {
        return GeneratedXml;
    }

    public void setGeneratedXml(String generatedXml) {
        GeneratedXml = generatedXml;
    }

    public String getDestinationBic() {
        return DestinationBic;
    }

    public void setDestinationBic(String destinationBic) {
        DestinationBic = destinationBic;
    }

    public String getMessageStatus() {
        return MessageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        MessageStatus = messageStatus;
    }

    public String getMessageStatusDetails() {
        return MessageStatusDetails;
    }

    public void setMessageStatusDetails(String messageStatusDetails) {
        MessageStatusDetails = messageStatusDetails;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
    }

    public Date getSentDate() {
        return SentDate;
    }

    public void setSentDate(Date sentDate) {
        SentDate = sentDate;
    }
}
