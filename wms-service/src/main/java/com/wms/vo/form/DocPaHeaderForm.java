package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocPaHeaderForm {

	private String pano;
	private String asnno;
	private String customerid;
	private String pareference1;
	private String pareference2;
	private String pareference3;
	private String pareference4;
	private String pareference5;
	private String patype;
	private String pastatus;
	private java.util.Date pacreationtime;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String notes;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private String paPrintFlag;
	private String warehouseid;

	public String getPano() {
		return pano;
	}

	public void setPano(String pano) {
		this.pano = pano;
	}

	public String getAsnno() {
		return asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getPareference1() {
		return pareference1;
	}

	public void setPareference1(String pareference1) {
		this.pareference1 = pareference1;
	}

	public String getPareference2() {
		return pareference2;
	}

	public void setPareference2(String pareference2) {
		this.pareference2 = pareference2;
	}

	public String getPareference3() {
		return pareference3;
	}

	public void setPareference3(String pareference3) {
		this.pareference3 = pareference3;
	}

	public String getPareference4() {
		return pareference4;
	}

	public void setPareference4(String pareference4) {
		this.pareference4 = pareference4;
	}

	public String getPareference5() {
		return pareference5;
	}

	public void setPareference5(String pareference5) {
		this.pareference5 = pareference5;
	}

	public String getPatype() {
		return patype;
	}

	public void setPatype(String patype) {
		this.patype = patype;
	}

	public String getPastatus() {
		return pastatus;
	}

	public void setPastatus(String pastatus) {
		this.pastatus = pastatus;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getPacreationtime() {
		return pacreationtime;
	}

	public void setPacreationtime(java.util.Date pacreationtime) {
		this.pacreationtime = pacreationtime;
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

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getPaPrintFlag() {
		return paPrintFlag;
	}

	public void setPaPrintFlag(String paPrintFlag) {
		this.paPrintFlag = paPrintFlag;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}