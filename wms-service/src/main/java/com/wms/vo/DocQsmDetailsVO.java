package com.wms.vo;

import com.alibaba.fastjson.annotation.JSONField;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocQsmDetailsVO {

	private String qcudocno;
	private String qcustatus;
	private String lotatt14;
	private String customerid;
	@JSONField(format = "yyyy-MM-dd")
	private java.util.Date lotatt03;
	private String lotatt08;
	private String businesstype;
	private String sku;
	private String lotatt12;
	private String lotatt06;
	private String descrc;
	private String lotatt04;
	private String lotatt05;
	private String lotatt07;
	@JSONField(format = "yyyy-MM-dd")
	private java.util.Date lotatt01;
	@JSONField(format = "yyyy-MM-dd")
	private java.util.Date lotatt02;
	private long locqty;
	private long qty;
	private double qtyeach;
	private String lotatt15;
	private String reservedfield06;
	private String lotatt10;
	private String locationid;
	private java.util.Date finddate;
	private String failedDescription;
	private String customeridTreatment;
	private java.util.Date treatmentDate;
	private String remarks;
	private java.util.Date recordingDate;
	private String recordingPeople;
	private String lotnum;


	public String getQcudocno() {
		return qcudocno;
	}

	public void setQcudocno(String qcudocno) {
		this.qcudocno = qcudocno;
	}

	public String getQcustatus() {
		return qcustatus;
	}

	public void setQcustatus(String qcustatus) {
		this.qcustatus = qcustatus;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(java.util.Date lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getBusinesstype() {
		return businesstype;
	}

	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getDescrc() {
		return descrc;
	}

	public void setDescrc(String descrc) {
		this.descrc = descrc;
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

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(java.util.Date lotatt01) {
		this.lotatt01 = lotatt01;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(java.util.Date lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public long getQty() {
		return qty;
	}

	public void setQty(long qty) {
		this.qty = qty;
	}

	public double getQtyeach() {
		return qtyeach;
	}

	public void setQtyeach(double qtyeach) {
		this.qtyeach = qtyeach;
	}

	public String getLotatt15() {
		return lotatt15;
	}

	public void setLotatt15(String lotatt15) {
		this.lotatt15 = lotatt15;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getFinddate() {
		return finddate;
	}

	public void setFinddate(java.util.Date finddate) {
		this.finddate = finddate;
	}

	public String getFailedDescription() {
		return failedDescription;
	}

	public void setFailedDescription(String failedDescription) {
		this.failedDescription = failedDescription;
	}

	public String getCustomeridTreatment() {
		return customeridTreatment;
	}

	public void setCustomeridTreatment(String customeridTreatment) {
		this.customeridTreatment = customeridTreatment;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(java.util.Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getRecordingDate() {
		return recordingDate;
	}

	public void setRecordingDate(java.util.Date recordingDate) {
		this.recordingDate = recordingDate;
	}

	public String getRecordingPeople() {
		return recordingPeople;
	}

	public void setRecordingPeople(String recordingPeople) {
		this.recordingPeople = recordingPeople;
	}

	public String getReservedfield06() {
		return reservedfield06;
	}

	public void setReservedfield06(String reservedfield06) {
		this.reservedfield06 = reservedfield06;
	}

	public long getLocqty() {
		return locqty;
	}

	public void setLocqty(long locqty) {
		this.locqty = locqty;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}
}