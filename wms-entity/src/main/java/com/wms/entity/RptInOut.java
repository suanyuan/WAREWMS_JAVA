package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RPT_IN_OUT database table.
 * 
 */
@Entity
@Table(name="RPT_IN_OUT")
@NamedQuery(name="RptInOut.findAll", query="SELECT r FROM RptInOut r")
public class RptInOut implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal adqty;

	private String commodity;

	private String customerid;

	@Column(name="DESCR_C")
	private String descrC;

	@Column(name="DESCR_E")
	private String descrE;

	private BigDecimal fvqty;

	private BigDecimal ibqty;

	private BigDecimal ivqty;

	private BigDecimal obqty;

	private String packid;

	private String packuom1;

	private String sku;

	@Column(name="SKU_DESCR_C")
	private String skuDescrC;

	@Column(name="SKU_DESCR_E")
	private String skuDescrE;

	private String skutext;

	private String startdate;

	private String startdatetext;

	private BigDecimal trqty;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	@Id
	private String userid;

	private String warehouseid;

	public RptInOut() {
	}

	public BigDecimal getAdqty() {
		return this.adqty;
	}

	public void setAdqty(BigDecimal adqty) {
		this.adqty = adqty;
	}

	public String getCommodity() {
		return this.commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
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

	public String getDescrE() {
		return this.descrE;
	}

	public void setDescrE(String descrE) {
		this.descrE = descrE;
	}

	public BigDecimal getFvqty() {
		return this.fvqty;
	}

	public void setFvqty(BigDecimal fvqty) {
		this.fvqty = fvqty;
	}

	public BigDecimal getIbqty() {
		return this.ibqty;
	}

	public void setIbqty(BigDecimal ibqty) {
		this.ibqty = ibqty;
	}

	public BigDecimal getIvqty() {
		return this.ivqty;
	}

	public void setIvqty(BigDecimal ivqty) {
		this.ivqty = ivqty;
	}

	public BigDecimal getObqty() {
		return this.obqty;
	}

	public void setObqty(BigDecimal obqty) {
		this.obqty = obqty;
	}

	public String getPackid() {
		return this.packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getPackuom1() {
		return this.packuom1;
	}

	public void setPackuom1(String packuom1) {
		this.packuom1 = packuom1;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuDescrC() {
		return this.skuDescrC;
	}

	public void setSkuDescrC(String skuDescrC) {
		this.skuDescrC = skuDescrC;
	}

	public String getSkuDescrE() {
		return this.skuDescrE;
	}

	public void setSkuDescrE(String skuDescrE) {
		this.skuDescrE = skuDescrE;
	}

	public String getSkutext() {
		return this.skutext;
	}

	public void setSkutext(String skutext) {
		this.skutext = skutext;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getStartdatetext() {
		return this.startdatetext;
	}

	public void setStartdatetext(String startdatetext) {
		this.startdatetext = startdatetext;
	}

	public BigDecimal getTrqty() {
		return this.trqty;
	}

	public void setTrqty(BigDecimal trqty) {
		this.trqty = trqty;
	}

	public String getUserdefine1() {
		return this.userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return this.userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return this.userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}