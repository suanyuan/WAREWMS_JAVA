package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class AsnDailyExportForm {

	private java.lang.String customerid;
	private java.lang.String sku;
	private java.lang.String skutext;
	private java.lang.String inbounddate;
	private java.lang.String inbounddatetext;
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

	public java.lang.String getSkutext() {
		return skutext;
	}

	public void setSkutext(java.lang.String skutext) {
		this.skutext = skutext;
	}

	public java.lang.String getInbounddate() {
		return inbounddate;
	}

	public void setInbounddate(java.lang.String inbounddate) {
		this.inbounddate = inbounddate;
	}

	public java.lang.String getInbounddatetext() {
		return inbounddatetext;
	}

	public void setInbounddatetext(java.lang.String inbounddatetext) {
		this.inbounddatetext = inbounddatetext;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}