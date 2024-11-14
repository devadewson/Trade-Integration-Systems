package com.maybank.integratorapp.model.soap.limit.XL41;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CMS_XL41Request {
    @JacksonXmlProperty(localName = "ctl2")
    private String ctl2;
    @JacksonXmlProperty(localName = "ctl3")
    private String ctl3;
    @JacksonXmlProperty(localName = "cust")
    private String cust;
    @JacksonXmlProperty(localName = "draw")
    private String draw;
    @JacksonXmlProperty(localName = "flag")
    private String flag;
    @JacksonXmlProperty(localName = "note")
    private String note;
    @JacksonXmlProperty(localName = "part")
    private String part;

    public String getCtl2() {
        return ctl2;
    }

    public void setCtl2(String ctl2) {
        this.ctl2 = ctl2;
    }

    public String getCtl3() {
        return ctl3;
    }

    public void setCtl3(String ctl3) {
        this.ctl3 = ctl3;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
