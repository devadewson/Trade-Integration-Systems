package com.maybank.integratorapp.model.rest.CustomerSearchh.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelHeader {
    @JsonProperty("messageID")
    private String messageID;
    @JsonProperty("branchCode")
        private String branchCode;
    @JsonProperty("channelID")
        private String channelID;
    @JsonProperty("reference")
        private String reference;
    @JsonProperty("sequenceNo")
        private String sequenceNo;
    @JsonProperty("transactionDate")
        private String transactionDate;
    @JsonProperty("transactionTime")
        private String transactionTime;


        // Getters and Setters
        public String getMessageID() {
            return messageID;
        }

        public void setMessageID(String messageID) {
            this.messageID = messageID;
        }

        public String getBranchCode() {
            return branchCode;
        }

        public void setBranchCode(String branchCode) {
            this.branchCode = branchCode;
        }

        public String getChannelID() {
            return channelID;
        }

        public void setChannelID(String channelID) {
            this.channelID = channelID;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getSequenceNo() {
            return sequenceNo;
        }

        public void setSequenceNo(String sequenceNo) {
            this.sequenceNo = sequenceNo;
        }

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public String getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(String transactionTime) {
            this.transactionTime = transactionTime;
        }
    }


