package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocMtDetailsForm {

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
	private String checkFlag;
	private String conclusion;
	private java.util.Date conversedate;
	private String conversewho;
	private String remark;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private String warehouseid;
	private String userid;

	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

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

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
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

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}