package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RPT_ORDER_PACKINGBOX database table.
 * 
 */
@Entity
@Table(name="RPT_ORDER_PACKINGBOX")
@NamedQuery(name="RptOrderPackingbox.findAll", query="SELECT r FROM RptOrderPackingbox r")
public class RptOrderPackingbox implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String addtime;

	@Column(name="ALTERNATE_SKU1")
	private String alternateSku1;

	private String carrierid;

	private String consigneeid;

	private String customerid;

	@Column(name="DESCR_C")
	private String descrC;

	private String orderno;

	private BigDecimal qty;

	private String sku;

	private String soreference1;

	private String soreference2;

	private String tel;

	@Id
	private String traceid;

	private String warehouseid;

	public RptOrderPackingbox() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAlternateSku1() {
		return this.alternateSku1;
	}

	public void setAlternateSku1(String alternateSku1) {
		this.alternateSku1 = alternateSku1;
	}

	public String getCarrierid() {
		return this.carrierid;
	}

	public void setCarrierid(String carrierid) {
		this.carrierid = carrierid;
	}

	public String getConsigneeid() {
		return this.consigneeid;
	}

	public void setConsigneeid(String consigneeid) {
		this.consigneeid = consigneeid;
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

	public String getSoreference2() {
		return this.soreference2;
	}

	public void setSoreference2(String soreference2) {
		this.soreference2 = soreference2;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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