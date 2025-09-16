package com.maybank.integratorapp.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "SwiftMtMessage",schema = "dbo")
@NoArgsConstructor
public class SwiftMtMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String MessageType;
    private String SenderBic;
    private String ReceiverBic;
    private String TransactionReference;
    private String Direction;
    @Column(columnDefinition = "TEXT")
    private String RawMessage;
    private String SwiftStandardReleaseVersion;
    private String MessageStatus;
    private Date CreatedDate;
    private Date ModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getSenderBic() {
        return SenderBic;
    }

    public void setSenderBic(String senderBic) {
        SenderBic = senderBic;
    }

    public String getReceiverBic() {
        return ReceiverBic;
    }

    public void setReceiverBic(String receiverBic) {
        ReceiverBic = receiverBic;
    }

    public String getTransactionReference() {
        return TransactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        TransactionReference = transactionReference;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getRawMessage() {
        return RawMessage;
    }

    public void setRawMessage(String rawMessage) {
        RawMessage = rawMessage;
    }

    public String getSwiftStandardReleaseVersion() {
        return SwiftStandardReleaseVersion;
    }

    public void setSwiftStandardReleaseVersion(String swiftStandardReleaseVersion) {
        SwiftStandardReleaseVersion = swiftStandardReleaseVersion;
    }

    public String getMessageStatus() {
        return MessageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        MessageStatus = messageStatus;
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
