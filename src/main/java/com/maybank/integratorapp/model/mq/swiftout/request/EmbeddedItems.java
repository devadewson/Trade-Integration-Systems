package com.maybank.integratorapp.model.mq.swiftout.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class EmbeddedItems {
    @JacksonXmlProperty(localName = "ID", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String Id;
    @JacksonXmlProperty(localName = "AttachType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String AttachType;
    @JacksonXmlProperty(localName = "Description", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String Description;
    @JacksonXmlProperty(localName = "FileName", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String FileName;
    @JacksonXmlProperty(localName = "MimeType", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String MimeType;
    @JacksonXmlProperty(localName = "DataStream", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String DataStream;

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
