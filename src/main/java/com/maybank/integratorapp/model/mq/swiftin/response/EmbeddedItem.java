package com.maybank.integratorapp.model.mq.swiftin.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class EmbeddedItem {
    @JacksonXmlProperty(localName = "ID", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String Id;
    @JacksonXmlProperty(localName = "AttachmentType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String AttachType;

    @JacksonXmlProperty(localName = "DocType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String DocType;
    @JacksonXmlProperty(localName = "Description", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String Description;
    @JacksonXmlProperty(localName = "FileName", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String FileName;
    @JacksonXmlProperty(localName = "DataStream", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String DataStream;
    @JacksonXmlProperty(localName = "MimeType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String MimeType;
    @JacksonXmlProperty(localName = "BatchID", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String BatchID;
    @JacksonXmlProperty(localName = "DocFaceRef", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String DocFaceRef;
    @JacksonXmlProperty(localName = "FirstMail", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String FirstMail;
    @JacksonXmlProperty(localName = "SecondMail", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String SecondMail;

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public String getBatchID() {
        return BatchID;
    }

    public void setBatchID(String batchID) {
        BatchID = batchID;
    }

    public String getDocFaceRef() {
        return DocFaceRef;
    }

    public void setDocFaceRef(String docFaceRef) {
        DocFaceRef = docFaceRef;
    }

    public String getFirstMail() {
        return FirstMail;
    }

    public void setFirstMail(String firstMail) {
        FirstMail = firstMail;
    }

    public String getSecondMail() {
        return SecondMail;
    }

    public void setSecondMail(String secondMail) {
        SecondMail = secondMail;
    }

    public String getMailingTotal() {
        return MailingTotal;
    }

    public void setMailingTotal(String mailingTotal) {
        MailingTotal = mailingTotal;
    }

    @JacksonXmlProperty(localName = "MailingTotal", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String MailingTotal;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAttachType() {
        return AttachType;
    }

    public void setAttachType(String attachType) {
        AttachType = attachType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getMimeType() {
        return MimeType;
    }

    public void setMimeType(String mimeType) {
        MimeType = mimeType;
    }

    public String getDataStream() {
        return DataStream;
    }

    public void setDataStream(String dataStream) {
        DataStream = dataStream;
    }
}
