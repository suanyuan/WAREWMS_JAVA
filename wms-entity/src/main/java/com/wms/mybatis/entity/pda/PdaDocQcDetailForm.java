package com.wms.mybatis.entity.pda;

import com.wms.entity.BaseLotatt;
import lombok.Data;

/**
 * lotatt01,02,06,10,11 需要提交
 */

public class PdaDocQcDetailForm extends BaseLotatt {

    //同批次批量验收合格
    private int allqcflag;

    private String qcno;

    private String qclineno;

    private String qcqty;

    private String qcdescr;

    private String warehouseid;

    //--------------------

    private String userid;

    private String language;

    private String returncode;//procedure执行结果

    public int getAllqcflag() {
        return allqcflag;
    }

    public void setAllqcflag(int allqcflag) {
        this.allqcflag = allqcflag;
    }

    public String getQcno() {
        return qcno;
    }

    public void setQcno(String qcno) {
        this.qcno = qcno;
    }

    public String getQclineno() {
        return qclineno;
    }

    public void setQclineno(String qclineno) {
        this.qclineno = qclineno;
    }

    public String getQcqty() {
        return qcqty;
    }

    public void setQcqty(String qcqty) {
        this.qcqty = qcqty;
    }

    public String getQcdescr() {
        return qcdescr;
    }

    public void setQcdescr(String qcdescr) {
        this.qcdescr = qcdescr;
    }

    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }
}
