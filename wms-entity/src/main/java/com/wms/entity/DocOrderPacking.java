package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the DOC_ORDER_PACKING database table.
 * 
 */
@Entity
public class DocOrderPacking implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String traceid;

	private String orderno;

	private String sku;

	private String skuName;

	private BigDecimal allocationQty;

	private BigDecimal qty;

	private String allocationdetailsid;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;
	
	private String merchantName;

	private String consigneename;

	private String cAddress1;

	public DocOrderPacking() {
	}

	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public BigDecimal getAllocationQty() {
		return allocationQty;
	}

	public void setAllocationQty(BigDecimal allocationQty) {
		this.allocationQty = allocationQty;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getAllocationdetailsid() {
		return allocationdetailsid;
	}

	public void setAllocationdetailsid(String allocationdetailsid) {
		this.allocationdetailsid = allocationdetailsid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getConsigneename() {
		return consigneename;
	}

	public void setConsigneename(String consigneename) {
		this.consigneename = consigneename;
	}

	public String getcAddress1() {
		return cAddress1;
	}

	public void setcAddress1(String cAddress1) {
		this.cAddress1 = cAddress1;
	}
}