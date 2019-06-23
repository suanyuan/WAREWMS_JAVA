package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RPT_ORDERDETAIL_HIS database table.
 * 
 */
@Entity
@Table(name="RPT_ORDERDETAIL_HIS")
@NamedQuery(name="RptOrderdetailHis.findAll", query="SELECT r FROM RptOrderdetailHis r")
public class RptOrderdetailHis implements Serializable {
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

	private String carrierid;

	private String consigneeid;

	private String customerid;

	@Column(name="DESCR_C")
	private String descrC;

	@Column(name="H_EDI_02")
	private String hEdi02;

	@Column(name="H_EDI_04")
	private String hEdi04;

	private String issuepartyid;

	private String lastshipmenttime;
	
	private String lastshipmenttimetext;

	@Id
	private String orderno;

	private BigDecimal price;

	private BigDecimal qtyordered;

	private BigDecimal qtyshipped;

	private String sku;

	private String soreference1;

	private String soreference2;

	private String sostatus;

	private String userdefine5;

	private String warehouseid;

	private String waveno;

	public RptOrderdetailHis() {
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

	public String getHEdi02() {
		return this.hEdi02;
	}

	public void setHEdi02(String hEdi02) {
		this.hEdi02 = hEdi02;
	}

	public String getHEdi04() {
		return this.hEdi04;
	}

	public void setHEdi04(String hEdi04) {
		this.hEdi04 = hEdi04;
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

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQtyordered() {
		return this.qtyordered;
	}

	public void setQtyordered(BigDecimal qtyordered) {
		this.qtyordered = qtyordered;
	}

	public BigDecimal getQtyshipped() {
		return this.qtyshipped;
	}

	public void setQtyshipped(BigDecimal qtyshipped) {
		this.qtyshipped = qtyshipped;
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

	public String getSostatus() {
		return this.sostatus;
	}

	public void setSostatus(String sostatus) {
		this.sostatus = sostatus;
	}

	public String getUserdefine5() {
		return this.userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWaveno() {
		return this.waveno;
	}

	public void setWaveno(String waveno) {
		this.waveno = waveno;
	}

}