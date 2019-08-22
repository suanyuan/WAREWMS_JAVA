package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocMtDetailsVO {

	private String mtno;
	private long mtlineno;
	private String linestatus;
	private String customerid;
	private String sku;
	private String inventoryage;
	private String locationid;
	private String lotnum;
	private double mtqtyExpected;
	private double mtqtyEachExpected;
	private double mtqtyCompleted;
	private double mtqtyEachCompleted;
	private String uom;
	private long checkFlag;
	private String conclusion;
	private java.util.Date conversedate;
	private String conversewho;
	private long storageFlag;
	private long flowFlag;
	private long signFlag;
	private long fenceFlag;
	private long sanitationFlag;
	private String remark;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;

	public String getMtno() {
		return mtno;
	}

	public void setMtno(String mtno) {
		this.mtno = mtno;
	}

	public long getMtlineno() {
		return mtlineno;
	}

	public void setMtlineno(long mtlineno) {
		this.mtlineno = mtlineno;
	}

	public String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
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

	public String getInventoryage() {
		return inventoryage;
	}

	public void setInventoryage(String inventoryage) {
		this.inventoryage = inventoryage;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public double getMtqtyExpected() {
		return mtqtyExpected;
	}

	public void setMtqtyExpected(double mtqtyExpected) {
		this.mtqtyExpected = mtqtyExpected;
	}

	public double getMtqtyEachExpected() {
		return mtqtyEachExpected;
	}

	public void setMtqtyEachExpected(double mtqtyEachExpected) {
		this.mtqtyEachExpected = mtqtyEachExpected;
	}

	public double getMtqtyCompleted() {
		return mtqtyCompleted;
	}

	public void setMtqtyCompleted(double mtqtyCompleted) {
		this.mtqtyCompleted = mtqtyCompleted;
	}

	public double getMtqtyEachCompleted() {
		return mtqtyEachCompleted;
	}

	public void setMtqtyEachCompleted(double mtqtyEachCompleted) {
		this.mtqtyEachCompleted = mtqtyEachCompleted;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public long getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(long checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getConversedate() {
		return conversedate;
	}

	public void setConversedate(java.util.Date conversedate) {
		this.conversedate = conversedate;
	}

	public String getConversewho() {
		return conversewho;
	}

	public void setConversewho(String conversewho) {
		this.conversewho = conversewho;
	}

	public long getStorageFlag() {
		return storageFlag;
	}

	public void setStorageFlag(long storageFlag) {
		this.storageFlag = storageFlag;
	}

	public long getFlowFlag() {
		return flowFlag;
	}

	public void setFlowFlag(long flowFlag) {
		this.flowFlag = flowFlag;
	}

	public long getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(long signFlag) {
		this.signFlag = signFlag;
	}

	public long getFenceFlag() {
		return fenceFlag;
	}

	public void setFenceFlag(long fenceFlag) {
		this.fenceFlag = fenceFlag;
	}

	public long getSanitationFlag() {
		return sanitationFlag;
	}

	public void setSanitationFlag(long sanitationFlag) {
		this.sanitationFlag = sanitationFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

}