package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class OrderSkuExportForm {

	private java.lang.String customerid;
	private java.lang.String sku;
	private java.lang.String orderno;
	private java.lang.String lastshipmenttime;
	private java.lang.String lastshipmenttimetext;
	private String token;
	
	
	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}
	
	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
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