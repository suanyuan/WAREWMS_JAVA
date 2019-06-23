package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class AsnDetailExportForm {

	private java.lang.String customerid;
	private java.lang.String asnno;
	private java.lang.String sku;
	private java.lang.String addtime;
	private java.lang.String addtimetext;
	private java.lang.String edittime;
	private java.lang.String edittimetext;
	private String token;
	
	
	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}
	
	public java.lang.String getAsnno() {
		return asnno;
	}

	public void setAsnno(java.lang.String asnno) {
		this.asnno = asnno;
	}
	
	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getAddtime() {
		return addtime;
	}

	public void setAddtime(java.lang.String addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddtimetext() {
		return addtimetext;
	}

	public void setAddtimetext(java.lang.String addtimetext) {
		this.addtimetext = addtimetext;
	}
	
	public java.lang.String getEdittime() {
		return edittime;
	}

	public void setEdittime(java.lang.String edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEdittimetext() {
		return edittimetext;
	}

	public void setEdittimetext(java.lang.String edittimetext) {
		this.edittimetext = edittimetext;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}