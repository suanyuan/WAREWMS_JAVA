package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptOrderPackingshipVO {

	private java.lang.String addtime;
	private java.lang.String addtimetext;
	private java.math.BigDecimal cube;
	private java.lang.String customerid;
	private java.lang.String descrC;
	private java.math.BigDecimal grossweight;
	private java.lang.String lastshipmenttime;
	private java.lang.String lastshipmenttimetext;
	private java.lang.String orderno;
	private java.math.BigDecimal qty;
	private java.lang.String sku;
	private java.lang.String soreference1;
	private java.lang.String soreference3;
	private java.lang.String traceid;
	private java.lang.String warehouseid;

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

	public java.math.BigDecimal getCube() {
		return cube;
	}

	public void setCube(java.math.BigDecimal cube) {
		this.cube = cube;
	}

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.lang.String getDescrC() {
		return descrC;
	}

	public void setDescrC(java.lang.String descrC) {
		this.descrC = descrC;
	}

	public java.math.BigDecimal getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(java.math.BigDecimal grossweight) {
		this.grossweight = grossweight;
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

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
	}

	public java.math.BigDecimal getQty() {
		return qty;
	}

	public void setQty(java.math.BigDecimal qty) {
		this.qty = qty;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getSoreference1() {
		return soreference1;
	}

	public void setSoreference1(java.lang.String soreference1) {
		this.soreference1 = soreference1;
	}

	public java.lang.String getSoreference3() {
		return soreference3;
	}

	public void setSoreference3(java.lang.String soreference3) {
		this.soreference3 = soreference3;
	}

	public java.lang.String getTraceid() {
		return traceid;
	}

	public void setTraceid(java.lang.String traceid) {
		this.traceid = traceid;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}

}