package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the DOC_ORDER_HEADER database table.
 * 
 */
@Entity
public class ImportOrderData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	
	private String orderno;

	private String customerid;

	private String soreference1;

	private String soreference2;

	private String consigneeid;

	private String cAddress1;

	private String cTel1;

	private long orderlineno;

	private String sku;

	private String qtyordered;

	private String qtyshipped;

	private String price;

	public ImportOrderData() {
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSoreference1() {
		return soreference1;
	}

	public void setSoreference1(String soreference1) {
		this.soreference1 = soreference1;
	}

	public String getSoreference2() {
		return soreference2;
	}

	public void setSoreference2(String soreference2) {
		this.soreference2 = soreference2;
	}

	public String getConsigneeid() {
		return consigneeid;
	}

	public void setConsigneeid(String consigneeid) {
		this.consigneeid = consigneeid;
	}

	public String getcAddress1() {
		return cAddress1;
	}

	public void setcAddress1(String cAddress1) {
		this.cAddress1 = cAddress1;
	}

	public String getcTel1() {
		return cTel1;
	}

	public void setcTel1(String cTel1) {
		this.cTel1 = cTel1;
	}

	public long getOrderlineno() {
		return orderlineno;
	}

	public void setOrderlineno(long orderlineno) {
		this.orderlineno = orderlineno;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getQtyordered() {
		return qtyordered;
	}

	public void setQtyordered(String qtyordered) {
		this.qtyordered = qtyordered;
	}

	public String getQtyshipped() {
		return qtyshipped;
	}

	public void setQtyshipped(String qtyshipped) {
		this.qtyshipped = qtyshipped;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}