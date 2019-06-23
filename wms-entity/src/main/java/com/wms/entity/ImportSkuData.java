package com.wms.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for the BAS_SKU database table.
 * 
 */
@Entity
public class ImportSkuData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	
	private String customerid;

	private String sku;

	private String descrC;

	private String descrE;

	private String alternateSku1;

	private String packid;

	private String reservedfield01;

	private String cube;

	private String grossweight;

	private String price;

	public ImportSkuData() {
	}
	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescrC() {
		return descrC;
	}

	public void setDescrC(String descrC) {
		this.descrC = descrC;
	}

	public String getDescrE() {
		return descrE;
	}

	public void setDescrE(String descrE) {
		this.descrE = descrE;
	}

	public String getAlternateSku1() {
		return alternateSku1;
	}

	public void setAlternateSku1(String alternateSku1) {
		this.alternateSku1 = alternateSku1;
	}

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getReservedfield01() {
		return reservedfield01;
	}

	public void setReservedfield01(String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public String getCube() {
		return cube;
	}

	public void setCube(String cube) {
		this.cube = cube;
	}

	public String getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(String grossweight) {
		this.grossweight = grossweight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}