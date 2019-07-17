package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class InvLotAttQuery implements IQuery {

	private String lotnum;
	private String customerid;
	private String sku;
	private String lotatt01;
	private String lotatt02;
	private String lotatt03;
	private String lotatt04;
	private String lotatt05;
	private String lotatt06;
	private String lotatt07;
	private String lotatt08;
	private String lotatt09;
	private String lotatt10;
	private String lotatt11;
	private String lotatt12;
    private String lotatt13;//双证
    private String lotatt14;//入库单号
    private String lotatt15;
    private String lotatt16;
    private String lotatt17;
    private String lotatt18;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String receivingtime;
	private String qcreportfilename;

    public String getLotatt13() {
        return lotatt13;
    }

    public void setLotatt13(String lotatt13) {
        this.lotatt13 = lotatt13;
    }

    public String getLotatt14() {
        return lotatt14;
    }

    public void setLotatt14(String lotatt14) {
        this.lotatt14 = lotatt14;
    }

    public String getLotatt15() {
        return lotatt15;
    }

    public void setLotatt15(String lotatt15) {
        this.lotatt15 = lotatt15;
    }

    public String getLotatt16() {
        return lotatt16;
    }

    public void setLotatt16(String lotatt16) {
        this.lotatt16 = lotatt16;
    }

    public String getLotatt17() {
        return lotatt17;
    }

    public void setLotatt17(String lotatt17) {
        this.lotatt17 = lotatt17;
    }

    public String getLotatt18() {
        return lotatt18;
    }

    public void setLotatt18(String lotatt18) {
        this.lotatt18 = lotatt18;
    }

    public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt09() {
		return lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getReceivingtime() {
		return receivingtime;
	}

	public void setReceivingtime(String receivingtime) {
		this.receivingtime = receivingtime;
	}

	public String getQcreportfilename() {
		return qcreportfilename;
	}

	public void setQcreportfilename(String qcreportfilename) {
		this.qcreportfilename = qcreportfilename;
	}

}