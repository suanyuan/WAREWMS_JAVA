package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class OrderShip2bExportForm {

	private java.lang.String customerid;
	private java.lang.String orderno;
	private java.lang.String addtime;
	private java.lang.String addtimetext;
	private java.lang.String lastshipmenttime;
	private java.lang.String lastshipmenttimetext;
	private String token;
	
	
	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}
	
	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
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
	
	public java.lang.String getLastshipmenttime() {
		return lastshipmenttime;
	}

	public void setLastshipmenttime(java.lang.String lastshipmenttime) {
		this.lastshipmenttime = lastshipmenttime;
	}

	public java.lang.String getLastshipmenttimetext() {
		return lastshipmenttimetext;
	}

	public void setLastshipmenttimetext(java.lang.String lastshipmenttimetext) {
		this.lastshipmenttimetext = lastshipmenttimetext;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}