package com.dictionary.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String BILL_ID;
    private String BILL_NO;
    private String BILL_NAME;
    private String COMMITTEE;
    private String PROPOSE_DT;
    private String PROC_RESULT;
    private String AGE;
    private String DETAIL_LINK;
    private String PROPOSER;
    private String MEMBER_LIST;
    private String RST_PROPOSER;
    private String PUBL_PROPOSER;
    private String COMMITTEE_ID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBILL_ID() {
        return BILL_ID;
    }

    public void setBILL_ID(String BILL_ID) {
        this.BILL_ID = BILL_ID;
    }

    public String getBILL_NO() {
        return BILL_NO;
    }

    public void setBILL_NO(String BILL_NO) {
        this.BILL_NO = BILL_NO;
    }

    public String getBILL_NAME() {
        return BILL_NAME;
    }

    public void setBILL_NAME(String BILL_NAME) {
        this.BILL_NAME = BILL_NAME;
    }

    public String getCOMMITTEE() {
        return COMMITTEE;
    }

    public void setCOMMITTEE(String COMMITTEE) {
        this.COMMITTEE = COMMITTEE;
    }

    public String getPROPOSE_DT() {
        return PROPOSE_DT;
    }

    public void setPROPOSE_DT(String PROPOSE_DT) {
        this.PROPOSE_DT = PROPOSE_DT;
    }

    public String getPROC_RESULT() {
        return PROC_RESULT;
    }

    public void setPROC_RESULT(String PROC_RESULT) {
        this.PROC_RESULT = PROC_RESULT;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public String getDETAIL_LINK() {
        return DETAIL_LINK;
    }

    public void setDETAIL_LINK(String DETAIL_LINK) {
        this.DETAIL_LINK = DETAIL_LINK;
    }

    public String getPROPOSER() {
        return PROPOSER;
    }

    public void setPROPOSER(String PROPOSER) {
        this.PROPOSER = PROPOSER;
    }

    public String getMEMBER_LIST() {
        return MEMBER_LIST;
    }

    public void setMEMBER_LIST(String MEMBER_LIST) {
        this.MEMBER_LIST = MEMBER_LIST;
    }

    public String getRST_PROPOSER() {
        return RST_PROPOSER;
    }

    public void setRST_PROPOSER(String RST_PROPOSER) {
        this.RST_PROPOSER = RST_PROPOSER;
    }

    public String getPUBL_PROPOSER() {
        return PUBL_PROPOSER;
    }

    public void setPUBL_PROPOSER(String PUBL_PROPOSER) {
        this.PUBL_PROPOSER = PUBL_PROPOSER;
    }

    public String getCOMMITTEE_ID() {
        return COMMITTEE_ID;
    }

    public void setCOMMITTEE_ID(String COMMITTEE_ID) {
        this.COMMITTEE_ID = COMMITTEE_ID;
    }
}
