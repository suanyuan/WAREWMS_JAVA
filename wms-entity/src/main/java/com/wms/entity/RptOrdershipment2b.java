package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RPT_ORDERSHIPMENT_2B database table.
 * 
 */
@Entity
@Table(name="RPT_ORDERSHIPMENT_2B")
@NamedQuery(name="RptOrdershipment2b.findAll", query="SELECT r FROM RptOrdershipment2b r")
public class RptOrdershipment2b implements Serializable {
	private static final long serialVersionUID = 1L;

	private String addtime;

	private String addtimetext;

	@Column(name="C_ADDRESS1")
	private String cAddress1;

	@Column(name="C_ADDRESS2")
	private String cAddress2;

	@Column(name="C_CITY")
	private String cCity;

	@Column(name="C_PROVINCE")
	private String cProvince;

	@Column(name="C_TEL1")
	private String cTel1;

	@Column(name="C_TEL2")
	private String cTel2;

	private BigDecimal cnt;

	@Column(name="CODENAME_C")
	private String codenameC;

	private String consigneeid;

	private String consigneename;

	private String customerid;

	private BigDecimal edi09;

	private BigDecimal edi10;

	private String lastshipmenttime;

	private String lastshipmenttimetext;

	private String notes;

	@Id
	private String orderno;

	private String soreference1;

	private String soreference2;

	private String sostatus;

	private String warehouseid;

	public RptOrdershipment2b() {
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

	public String getCAddress1() {
		return this.cAddress1;
	}

	public void setCAddress1(String cAddress1) {
		this.cAddress1 = cAddress1;
	}

	public String getCAddress2() {
		return this.cAddress2;
	}

	public void setCAddress2(String cAddress2) {
		this.cAddress2 = cAddress2;
	}

	public String getCCity() {
		return this.cCity;
	}

	public void setCCity(String cCity) {
		this.cCity = cCity;
	}

	public String getCProvince() {
		return this.cProvince;
	}

	public void setCProvince(String cProvince) {
		this.cProvince = cProvince;
	}

	public String getCTel1() {
		return this.cTel1;
	}

	public void setCTel1(String cTel1) {
		this.cTel1 = cTel1;
	}

	public String getCTel2() {
		return this.cTel2;
	}

	public void setCTel2(String cTel2) {
		this.cTel2 = cTel2;
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

	public String getConsigneeid() {
		return this.consigneeid;
	}

	public void setConsigneeid(String consigneeid) {
		this.consigneeid = consigneeid;
	}

	public String getConsigneename() {
		return this.consigneename;
	}

	public void setConsigneename(String consigneename) {
		this.consigneename = consigneename;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public BigDecimal getEdi09() {
		return this.edi09;
	}

	public void setEdi09(BigDecimal edi09) {
		this.edi09 = edi09;
	}

	public BigDecimal getEdi10() {
		return this.edi10;
	}

	public void setEdi10(BigDecimal edi10) {
		this.edi10 = edi10;
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

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
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

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}