package com.wms.vo.pda;

import java.util.Date;

public class PdaDocAsnHeaderVO {

    /**
     * 预期到货通知编号 ≈ 收货任务单号
     */
    private String asnno;

    /**
     * ASN创建时间
     */
    private Date asncreationtime;

    /**
     * 预期到货通知状态
     */
    private String asnstatus;

    /**
     * 预期到货通知类型
     */
    private String asntype;

    /**
     * 客户单号
     */
    private String asnreference1;

    public String getAsnno() {
        return asnno;
    }

    public void setAsnno(String asnno) {
        this.asnno = asnno;
    }

    public Date getAsncreationtime() {
        return asncreationtime;
    }

    public void setAsncreationtime(Date asncreationtime) {
        this.asncreationtime = asncreationtime;
    }

    public String getAsnstatus() {
        return asnstatus;
    }

    public void setAsnstatus(String asnstatus) {
        this.asnstatus = asnstatus;
    }

    public String getAsntype() {
        return asntype;
    }

    public void setAsntype(String asntype) {
        this.asntype = asntype;
    }

    public String getAsnreference1() {
        return asnreference1;
    }

    public void setAsnreference1(String asnreference1) {
        this.asnreference1 = asnreference1;
    }
}
