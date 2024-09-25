/**
 * ChannelHeaderType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.maybank.integratorapp.model.soap;

public class ChannelHeaderType  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8645078085390580980L;

	private java.lang.String messageID;

    private java.lang.String additionalHeader;

    private java.lang.String branchCode;

    private java.lang.String channelID;

    private java.lang.String clientSupervisorID;

    private java.lang.String clientUserID;

    private java.lang.String reference;

    private java.lang.String reversalsequenceno;

    private java.lang.String sequenceno;

    private java.lang.String transactiondate;

    private java.lang.String transactiontime;

    public ChannelHeaderType() {
    }

    public ChannelHeaderType(
           java.lang.String messageID,
           java.lang.String additionalHeader,
           java.lang.String branchCode,
           java.lang.String channelID,
           java.lang.String clientSupervisorID,
           java.lang.String clientUserID,
           java.lang.String reference,
           java.lang.String reversalsequenceno,
           java.lang.String sequenceno,
           java.lang.String transactiondate,
           java.lang.String transactiontime) {
           this.messageID = messageID;
           this.additionalHeader = additionalHeader;
           this.branchCode = branchCode;
           this.channelID = channelID;
           this.clientSupervisorID = clientSupervisorID;
           this.clientUserID = clientUserID;
           this.reference = reference;
           this.reversalsequenceno = reversalsequenceno;
           this.sequenceno = sequenceno;
           this.transactiondate = transactiondate;
           this.transactiontime = transactiontime;
    }

	public java.lang.String getMessageID() {
		return messageID;
	}

	public void setMessageID(java.lang.String messageID) {
		this.messageID = messageID;
	}

	public java.lang.String getAdditionalHeader() {
		return additionalHeader;
	}

	public void setAdditionalHeader(java.lang.String additionalHeader) {
		this.additionalHeader = additionalHeader;
	}

	public java.lang.String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(java.lang.String branchCode) {
		this.branchCode = branchCode;
	}

	public java.lang.String getChannelID() {
		return channelID;
	}

	public void setChannelID(java.lang.String channelID) {
		this.channelID = channelID;
	}

	public java.lang.String getClientSupervisorID() {
		return clientSupervisorID;
	}

	public void setClientSupervisorID(java.lang.String clientSupervisorID) {
		this.clientSupervisorID = clientSupervisorID;
	}

	public java.lang.String getClientUserID() {
		return clientUserID;
	}

	public void setClientUserID(java.lang.String clientUserID) {
		this.clientUserID = clientUserID;
	}

	public java.lang.String getReference() {
		return reference;
	}

	public void setReference(java.lang.String reference) {
		this.reference = reference;
	}

	public java.lang.String getReversalsequenceno() {
		return reversalsequenceno;
	}

	public void setReversalsequenceno(java.lang.String reversalsequenceno) {
		this.reversalsequenceno = reversalsequenceno;
	}

	public java.lang.String getSequenceno() {
		return sequenceno;
	}

	public void setSequenceno(java.lang.String sequenceno) {
		this.sequenceno = sequenceno;
	}

	public java.lang.String getTransactiondate() {
		return transactiondate;
	}

	public void setTransactiondate(java.lang.String transactiondate) {
		this.transactiondate = transactiondate;
	}

	public java.lang.String getTransactiontime() {
		return transactiontime;
	}

	public void setTransactiontime(java.lang.String transactiontime) {
		this.transactiontime = transactiontime;
	}

    
    
}
