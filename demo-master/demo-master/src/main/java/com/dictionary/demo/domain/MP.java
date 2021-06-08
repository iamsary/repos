package com.dictionary.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String HG_NM;
    private String HJ_NM;
    private String ENG_NM;
    private String BTH_GBN_NM;
    private String BTH_DATE;
    private String JOB_RES_NM;
    private String POLY_NM;
    private String ORIG_NM;
    private String ELECT_GBN_NM;
    private String CMIT_NM;
    private String CMITS;
    private String REELE_GBN_NM;
    private String UNITS;
    private String SEX_GBN_NM;
    private String TEL_NO;
    private String E_MAIL;
    private String HOMEPAGE;
    private String STAFF;
    private String SECRETARY;
    private String SECRETARY2;
    private String mona;
    private String MEM_TITLE;
    private String ASSEM_ADDR;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHG_NM() {
        return HG_NM;
    }

    public void setHG_NM(String HG_NM) {
        this.HG_NM = HG_NM;
    }

    public String getHJ_NM() {
        return HJ_NM;
    }

    public void setHJ_NM(String HJ_NM) {
        this.HJ_NM = HJ_NM;
    }

    public String getENG_NM() {
        return ENG_NM;
    }

    public void setENG_NM(String ENG_NM) {
        this.ENG_NM = ENG_NM;
    }

    public String getBTH_GBN_NM() {
        return BTH_GBN_NM;
    }

    public void setBTH_GBN_NM(String BTH_GBN_NM) {
        this.BTH_GBN_NM = BTH_GBN_NM;
    }

    public String getBTH_DATE() {
        return BTH_DATE;
    }

    public void setBTH_DATE(String BTH_DATE) {
        this.BTH_DATE = BTH_DATE;
    }

    public String getJOB_RES_NM() {
        return JOB_RES_NM;
    }

    public void setJOB_RES_NM(String JOB_RES_NM) {
        this.JOB_RES_NM = JOB_RES_NM;
    }

    public String getPOLY_NM() {
        return POLY_NM;
    }

    public void setPOLY_NM(String POLY_NM) {
        this.POLY_NM = POLY_NM;
    }

    public String getORIG_NM() {
        return ORIG_NM;
    }

    public void setORIG_NM(String ORIG_NM) {
        this.ORIG_NM = ORIG_NM;
    }

    public String getELECT_GBN_NM() {
        return ELECT_GBN_NM;
    }

    public void setELECT_GBN_NM(String ELECT_GBN_NM) {
        this.ELECT_GBN_NM = ELECT_GBN_NM;
    }

    public String getCMIT_NM() {
        return CMIT_NM;
    }

    public void setCMIT_NM(String CMIT_NM) {
        this.CMIT_NM = CMIT_NM;
    }

    public String getCMITS() {
        return CMITS;
    }

    public void setCMITS(String CMITS) {
        this.CMITS = CMITS;
    }

    public String getREELE_GBN_NM() {
        return REELE_GBN_NM;
    }

    public void setREELE_GBN_NM(String REELE_GBN_NM) {
        this.REELE_GBN_NM = REELE_GBN_NM;
    }

    public String getUNITS() {
        return UNITS;
    }

    public void setUNITS(String UNITS) {
        this.UNITS = UNITS;
    }

    public String getSEX_GBN_NM() {
        return SEX_GBN_NM;
    }

    public void setSEX_GBN_NM(String SEX_GBN_NM) {
        this.SEX_GBN_NM = SEX_GBN_NM;
    }

    public String getTEL_NO() {
        return TEL_NO;
    }

    public void setTEL_NO(String TEL_NO) {
        this.TEL_NO = TEL_NO;
    }

    public String getE_MAIL() {
        return E_MAIL;
    }

    public void setE_MAIL(String e_MAIL) {
        E_MAIL = e_MAIL;
    }

    public String getHOMEPAGE() {
        return HOMEPAGE;
    }

    public void setHOMEPAGE(String HOMEPAGE) {
        this.HOMEPAGE = HOMEPAGE;
    }

    public String getSTAFF() {
        return STAFF;
    }

    public void setSTAFF(String STAFF) {
        this.STAFF = STAFF;
    }

    public String getSECRETARY() {
        return SECRETARY;
    }

    public void setSECRETARY(String SECRETARY) {
        this.SECRETARY = SECRETARY;
    }

    public String getSECRETARY2() {
        return SECRETARY2;
    }

    public void setSECRETARY2(String SECRETARY2) {
        this.SECRETARY2 = SECRETARY2;
    }

    public String getMONA_CD() {
        return mona;
    }

    public void setMONA_CD(String MONA_CD) {
        this.mona = MONA_CD;
    }

    public String getMEM_TITLE() {
        return MEM_TITLE;
    }

    public void setMEM_TITLE(String MEM_TITLE) {
        this.MEM_TITLE = MEM_TITLE;
    }

    public String getASSEM_ADDR() {
        return ASSEM_ADDR;
    }

    public void setASSEM_ADDR(String ASSEM_ADDR) {
        this.ASSEM_ADDR = ASSEM_ADDR;
    }
}
