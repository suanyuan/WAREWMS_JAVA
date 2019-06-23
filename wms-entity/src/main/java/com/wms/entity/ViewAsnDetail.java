package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the VIEW_ASN_DETAIL database table.
 * 
 */
@Entity
@Table(name="VIEW_ASN_DETAIL")
@NamedQuery(name="ViewAsnDetail.findAll", query="SELECT v FROM ViewAsnDetail v")
public class ViewAsnDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private String addtime;

	@Id
	private String asnno;

	private String asnreference1;

	@Column(name="CODENAME_C")
	private String codenameC;

	@Column(name="CODENAME_C1")
	private String codenameC1;

	@Column(name="\"CUBE\"")
	private BigDecimal cube;

	private String customerid;

	@Column(name="DESCR_C")
	private String descrC;

	private String edittime;

	private BigDecimal expectedqty;

	private String lastreceivingtime;

	private String notes;

	private BigDecimal receivedqty;

	private String receivedtime;

	private String reservedfield01;

	private String sku;

	private String userdefine2;
	
	private String warehouseid;

	public ViewAsnDetail() {
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAsnno() {
		return this.asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public String getAsnreference1() {
		return this.asnreference1;
	}

	public void setAsnreference1(String asnreference1) {
		this.asnreference1 = asnreference1;
	}

	public String getCodenameC() {
		return this.codenameC;
	}

	public void setCodenameC(String codenameC) {
		this.codenameC = codenameC;
	}

	public String getCodenameC1() {
		return this.codenameC1;
	}

	public void setCodenameC1(String codenameC1) {
		this.codenameC1 = codenameC1;
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

	public String getEdittime() {
		return this.edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public BigDecimal getExpectedqty() {
		return this.expectedqty;
	}

	public void setExpectedqty(BigDecimal expectedqty) {
		this.expectedqty = expectedqty;
	}

	public String getLastreceivingtime() {
		return this.lastreceivingtime;
	}

	public void setLastreceivingtime(String lastreceivingtime) {
		this.lastreceivingtime = lastreceivingtime;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BigDecimal getReceivedqty() {
		return this.receivedqty;
	}

	public void setReceivedqty(BigDecimal receivedqty) {
		this.receivedqty = receivedqty;
	}

	public String getReceivedtime() {
		return this.receivedtime;
	}

	public void setReceivedtime(String receivedtime) {
		this.receivedtime = receivedtime;
	}

	public String getReservedfield01() {
		return this.reservedfield01;
	}

	public void setReservedfield01(String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getUserdefine2() {
		return this.userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}
	
	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}


}