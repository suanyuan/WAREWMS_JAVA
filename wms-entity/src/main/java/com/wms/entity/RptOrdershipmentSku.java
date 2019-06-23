package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RPT_ORDERSHIPMENT_SKU database table.
 * 
 */
@Entity
@Table(name="RPT_ORDERSHIPMENT_SKU")
@NamedQuery(name="RptOrdershipmentSku.findAll", query="SELECT r FROM RptOrdershipmentSku r")
public class RptOrdershipmentSku implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal cnt;

	@Column(name="CODENAME_C")
	private String codenameC;

	@Column(name="\"CUBE\"")
	private BigDecimal cube;

	private String customerid;

	private String deliveryno;

	private String issuepartyid;

	private String lastshipmenttime;

	private String lastshipmenttimetext;

	@Id
	private String orderno;

	private String ordertype;

	@Column(name="QTY_EACH")
	private BigDecimal qtyEach;

	private String soreference1;

	private String soreference2;

	private String sostatus;

	private String traceid;

	private String warehouseid;

	public RptOrdershipmentSku() {
	}

	public BigDecimal getCnt() {
		return this.cnt;
	}

	public void setCnt(BigDecimal cnt) {
		this.cnt = cnt;
	}

	public String getCodenameC() {
		return this.codenameC;
	}

	public void setCodenameC(String codenameC) {
		this.codenameC = codenameC;
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

	public String getDeliveryno() {
		return this.deliveryno;
	}

	public void setDeliveryno(String deliveryno) {
		this.deliveryno = deliveryno;
	}

	public String getIssuepartyid() {
		return this.issuepartyid;
	}

	public void setIssuepartyid(String issuepartyid) {
		this.issuepartyid = issuepartyid;
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

	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public BigDecimal getQtyEach() {
		return this.qtyEach;
	}

	public void setQtyEach(BigDecimal qtyEach) {
		this.qtyEach = qtyEach;
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

	public String getSostatus() {
		return this.sostatus;
	}

	public void setSostatus(String sostatus) {
		this.sostatus = sostatus;
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