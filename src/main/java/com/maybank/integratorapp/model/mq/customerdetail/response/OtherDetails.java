package com.maybank.integratorapp.model.mq.customerdetail.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OtherDetails {
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "AllowMT103C")
    private String allowMT103C;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "CutoffAmount")
    private CutoffAmount cutoffAmount;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "SWIFTAckRequired")
    private String swiftACKRequired;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "TransliterateSWIFT")
    private String transliterateSWIFT;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "Team")
    private String team;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "CorporateAccessMappings")
    private CorporateAccessMappings corporateAccessMappings;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "CorporateAccess")
    private String corporateAccess;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "PrincipalFxRateCode")
    private String principalFxRateCode;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "ChargeFxRateCode")
    private String chargeFxRateCode;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "ChargeGroup")
    private String chargeGroup;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "AllowTaxExemptions")
    private String allowTaxExemptions;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "Suspended")
    private String suspended;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "CutoffFxRateCode")
    private String cutoffFxRateCode;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "AllowInterestConsolidation")
    private String allowInterestConsolidation;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "SWIFTScoreEnabled")
    private String swiftScoreEnabled;
    @JacksonXmlProperty(namespace = "urn:common.service.ti.apps.tiplus2.misys.com", localName = "DocumentPreparationService")
    private String documentPreparationService;

    public String getAllowMT103C() { return allowMT103C; }
    public void setAllowMT103C(String value) { this.allowMT103C = value; }

    public CutoffAmount getCutoffAmount() { return cutoffAmount; }
    public void setCutoffAmount(CutoffAmount value) { this.cutoffAmount = value; }

    public String getSwiftACKRequired() { return swiftACKRequired; }
    public void setSwiftACKRequired(String value) { this.swiftACKRequired = value; }

    public String getTransliterateSWIFT() { return transliterateSWIFT; }
    public void setTransliterateSWIFT(String value) { this.transliterateSWIFT = value; }

    public String getTeam() { return team; }
    public void setTeam(String value) { this.team = value; }

    public CorporateAccessMappings getCorporateAccessMappings() { return corporateAccessMappings; }
    public void setCorporateAccessMappings(CorporateAccessMappings value) { this.corporateAccessMappings = value; }

    public String getCorporateAccess() { return corporateAccess; }
    public void setCorporateAccess(String value) { this.corporateAccess = value; }

    public String getPrincipalFxRateCode() { return principalFxRateCode; }
    public void setPrincipalFxRateCode(String value) { this.principalFxRateCode = value; }

    public String getChargeFxRateCode() { return chargeFxRateCode; }
    public void setChargeFxRateCode(String value) { this.chargeFxRateCode = value; }

    public String getChargeGroup() { return chargeGroup; }
    public void setChargeGroup(String value) { this.chargeGroup = value; }

    public String getAllowTaxExemptions() { return allowTaxExemptions; }
    public void setAllowTaxExemptions(String value) { this.allowTaxExemptions = value; }

    public String getSuspended() { return suspended; }
    public void setSuspended(String value) { this.suspended = value; }

    public String getCutoffFxRateCode() { return cutoffFxRateCode; }
    public void setCutoffFxRateCode(String value) { this.cutoffFxRateCode = value; }

    public String getAllowInterestConsolidation() { return allowInterestConsolidation; }
    public void setAllowInterestConsolidation(String value) { this.allowInterestConsolidation = value; }

    public String getSwiftScoreEnabled() { return swiftScoreEnabled; }
    public void setSwiftScoreEnabled(String value) { this.swiftScoreEnabled = value; }

    public String getDocumentPreparationService() { return documentPreparationService; }
    public void setDocumentPreparationService(String value) { this.documentPreparationService = value; }

}

