package com.wms.vo.pda;

import java.util.Date;

public class PdaDocAsnHeaderVO {

    /**
     * 预期到货通知编号
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
}
