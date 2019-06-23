package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class OrderPackExportForm {

	private java.lang.String customerid;
	private java.lang.String orderno;
	private java.lang.String sku;
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
	
	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}