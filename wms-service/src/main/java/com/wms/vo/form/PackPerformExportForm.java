package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class PackPerformExportForm {

	private java.lang.String customerid;
	private java.lang.String userId;
	private java.lang.String sdate;
	private java.lang.String sdatetext;
	private String token;
	
	
	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}
	
	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getSdate() {
		return sdate;
	}

	public void setSdate(java.lang.String sdate) {
		this.sdate = sdate;
	}

	public java.lang.String getSdatetext() {
		return sdatetext;
	}

	public void setSdatetext(java.lang.String sdatetext) {
		this.sdatetext = sdatetext;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}