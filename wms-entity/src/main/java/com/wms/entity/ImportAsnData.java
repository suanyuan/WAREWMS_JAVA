package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
public class ImportAsnData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	private String asnno;
	private String customerid;
	private String asnreference1;
	private String asnreference2;
	private String expectedarrivetime1;
	private long asnlineno;
	private String sku;
	private String expectedqty;
	private String receivinglocation;
	private String totalgrossweight;
	private String totalcubic;
	private String totalprice;
	private String lotatt01;
	private String lotatt02;
	private String lotatt03;
	private String lotatt04;
	private String lotatt05;
	private String lotatt06;
	private String notes;

	public ImportAsnData() {
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getAsnno() {
		return asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getAsnreference1() {
		return asnreference1;
	}

	public void setAsnreference1(String asnreference1) {
		this.asnreference1 = asnreference1;
	}

	public String getAsnreference2() {
		return asnreference2;
	}

	public void setAsnreference2(String asnreference2) {
		this.asnreference2 = asnreference2;
	}

	public String getExpectedarrivetime1() {
		return expectedarrivetime1;
	}

	public void setExpectedarrivetime1(String expectedarrivetime1) {
		this.expectedarrivetime1 = expectedarrivetime1;
	}

	public long getAsnlineno() {
		return asnlineno;
	}

	public void setAsnlineno(long asnlineno) {
		this.asnlineno = asnlineno;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getExpectedqty() {
		return expectedqty;
	}

	public void setExpectedqty(String expectedqty) {
		this.expectedqty = expectedqty;
	}

	public String getReceivinglocation() {
		return receivinglocation;
	}

	public void setReceivinglocation(String receivinglocation) {
		this.receivinglocation = receivinglocation;
	}

	public String getTotalgrossweight() {
		return totalgrossweight;
	}

	public void setTotalgrossweight(String totalgrossweight) {
		this.totalgrossweight = totalgrossweight;
	}

	public String getTotalcubic() {
		return totalcubic;
	}

	public void setTotalcubic(String totalcubic) {
		this.totalcubic = totalcubic;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}

	public String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}