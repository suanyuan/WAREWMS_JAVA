package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RPT_ORDER_PACKINGSHIP database table.
 * 
 */
@Entity
@Table(name="RPT_ORDER_PACKINGSHIP")
@NamedQuery(name="RptOrderPackingship.findAll", query="SELECT r FROM RptOrderPackingship r")
public class RptOrderPackingship implements Serializable {
	private static final long serialVersionUID = 1L;

	private String addtime;
	
	private String addtimetext;

	@Column(name="\"CUBE\"")
	private BigDecimal cube;

	private String customerid;

	@Column(name="DESCR_C")
	private String descrC;

	private BigDecimal grossweight;

	private String lastshipmenttime;
	
	private String lastshipmenttimetext;

	private String orderno;

	private BigDecimal qty;

	private String sku;

	private String soreference1;

	private String soreference3;

	@Id
	private String traceid;

	private String warehouseid;

	public RptOrderPackingship() {
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	public String getAddtimetext() {
		return this.addtimetext;
	}

	public void setAddtimetext(String addtimetext) {
		this.addtimetext = addtimetext;
	}

	public BigDecimal getCube() {
		return this.cube;
	}

	public void setCube(BigDecimal cube) {
		this.cube = cube;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getDescrC() {
		return this.descrC;
	}

	public void setDescrC(String descrC) {
		this.descrC = descrC;
	}

	public BigDecimal getGrossweight() {
		return this.grossweight;
	}

	public void setGrossweight(BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public String getLastshipmenttime() {
		return this.lastshipmenttime;
	}

	public void setLastshipmenttime(String lastshipmenttime) {
		this.lastshipmenttime = lastshipmenttime;
	}
	
	public String getLastshipmenttimetext() {
		return this.lastshipmenttimetext;
	}

	public void setLastshipmenttimetext(String lastshipmenttimetext) {
		this.lastshipmenttimetext = lastshipmenttimetext;
	}


	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSoreference1() {
		return this.soreference1;
	}

	public void setSoreference1(String soreference1) {
		this.soreference1 = soreference1;
	}

	public String getSoreference3() {
		return this.soreference3;
	}

	public void setSoreference3(String soreference3) {
		this.soreference3 = soreference3;
	}

	public String getTraceid() {
		return this.traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}