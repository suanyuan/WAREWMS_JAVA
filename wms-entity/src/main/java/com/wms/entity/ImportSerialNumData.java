package com.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
public class ImportSerialNumData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	private String asnno;
	private String customerid;
	private int cartonNo;
	private String soreference;
	private String orderNo;
	private String batchNum;
	private String serialNum;
	private String sku;
	private String userdefine1;


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

	public int getCartonNo() {
		return cartonNo;
	}

	public void setCartonNo(int cartonNo) {
		this.cartonNo = cartonNo;
	}

	public String getSoreference() {
		return soreference;
	}

	public void setSoreference(String soreference) {
		this.soreference = soreference;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}
}