package com.wms.mybatis.entity.pda;

import com.wms.entity.BaseLotatt;
import lombok.Data;

/**
 * lotatt01,02,06,10,11 需要提交
 */

public class PdaDocQcDetailForm extends BaseLotatt {

    //同生产批号批量验收合格
    private int allqcflag;

    private String qcno;

    private String qclineno;

    private String qcqty;

    private String qcdescr;
    private String lotnum;

    private String warehouseid;

    //--------------------上面是pda传参、lotatt01、02、04、06、11

//    private String lotatt01;//生产日期
//    private String lotatt02;//效期
//    private String lotatt04;//生产批号
//    private String lotatt06;//产品注册证
//    private String lotatt10;//合格 不合格
//    private String lotatt11;//存储条件
//    private String lotatt15;//生产厂商名称
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


    public String getLotnum() {
        return lotnum;
    }

    public void setLotnum(String lotnum) {
        this.lotnum = lotnum;
    }
}
