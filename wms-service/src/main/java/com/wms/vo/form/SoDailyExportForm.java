package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class SoDailyExportForm {

	private java.lang.String customerid;
	private java.lang.String sku;
	private java.lang.String skutext;
	private java.lang.String shipmenttime;
	private java.lang.String shipmenttimetext;
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

	public java.lang.String getShipmenttime() {
		return shipmenttime;
	}

	public void setShipmenttime(java.lang.String shipmenttime) {
		this.shipmenttime = shipmenttime;
	}

	public java.lang.String getShipmenttimetext() {
		return shipmenttimetext;
	}

	public void setShipmenttimetext(java.lang.String shipmenttimetext) {
		this.shipmenttimetext = shipmenttimetext;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}