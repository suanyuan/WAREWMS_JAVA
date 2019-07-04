package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocPaDetailsQuery implements IQuery {

	private String pano;
	private String palineno;
	private String linestatus;
	private String asnno;
	private String asnlineno;
	private String customerid;
	private String sku;
	private String lotnum;
	private String asnqtyExpected;
	private String putwayqtyExpected;
	private String putwayqtyCompleted;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String notes;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String packid;
	private String transactionid;

    public String getPano() {
        return pano;
    }

    public void setPano(String pano) {
        this.pano = pano;
    }

    public String getPalineno() {
        return palineno;
    }

    public void setPalineno(String palineno) {
        this.palineno = palineno;
    }

    public String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
	}

	public String getAsnno() {
		return asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public String getAsnlineno() {
		return asnlineno;
	}

	public void setAsnlineno(String asnlineno) {
		this.asnlineno = asnlineno;
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

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getAsnqtyExpected() {
		return asnqtyExpected;
	}

	public void setAsnqtyExpected(String asnqtyExpected) {
		this.asnqtyExpected = asnqtyExpected;
	}

	public String getPutwayqtyExpected() {
		return putwayqtyExpected;
	}

	public void setPutwayqtyExpected(String putwayqtyExpected) {
		this.putwayqtyExpected = putwayqtyExpected;
	}

	public String getPutwayqtyCompleted() {
		return putwayqtyCompleted;
	}

	public void setPutwayqtyCompleted(String putwayqtyCompleted) {
		this.putwayqtyCompleted = putwayqtyCompleted;
	}

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

}