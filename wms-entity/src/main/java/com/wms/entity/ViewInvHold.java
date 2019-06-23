package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VIEW_INV_HOLD database table.
 * 
 */
@Entity
@Table(name="VIEW_INV_HOLD")
@NamedQuery(name="ViewInvHold.findAll", query="SELECT v FROM ViewInvHold v")
public class ViewInvHold implements Serializable {
	private static final long serialVersionUID = 1L;

	private String customerid;

	private String customername;

	private String fromtime;

	private String holdby;

	@Column(name="HOLDBY_NAME")
	private String holdbyName;

	private String holdcode;

	@Column(name="HOLDCODE_NAME")
	private String holdcodeName;

	private String holdflag;

	private String inventoryholdid;

	private String locationid;

	private String lotatt01;

	private String lotatt02;

	private String lotatt03;

	private String lotatt04;

	private String lotatt05;

	private String lotatt06;

	private String lotatt07;

	private String lotatt08;

	private String lotatt09;

	private String lotatt10;

	private String lotatt11;

	private String lotatt12;

	private String lotnum;

	private String offfromtime;

	private String reason;
	@Id
	private String sku;

	private String skudescrc;

	private String traceid;

	private String warehouseid;

	private String whooff;

	private String whoon;

	public ViewInvHold() {
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return this.customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getFromtime() {
		return this.fromtime;
	}

	public void setFromtime(String fromtime) {
		this.fromtime = fromtime;
	}

	public String getHoldby() {
		return this.holdby;
	}

	public void setHoldby(String holdby) {
		this.holdby = holdby;
	}

	public String getHoldbyName() {
		return this.holdbyName;
	}

	public void setHoldbyName(String holdbyName) {
		this.holdbyName = holdbyName;
	}

	public String getHoldcode() {
		return this.holdcode;
	}

	public void setHoldcode(String holdcode) {
		this.holdcode = holdcode;
	}

	public String getHoldcodeName() {
		return this.holdcodeName;
	}

	public void setHoldcodeName(String holdcodeName) {
		this.holdcodeName = holdcodeName;
	}

	public String getHoldflag() {
		return this.holdflag;
	}

	public void setHoldflag(String holdflag) {
		this.holdflag = holdflag;
	}

	public String getInventoryholdid() {
		return this.inventoryholdid;
	}

	public void setInventoryholdid(String inventoryholdid) {
		this.inventoryholdid = inventoryholdid;
	}

	public String getLocationid() {
		return this.locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getLotatt01() {
		return this.lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return this.lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt03() {
		return this.lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt04() {
		return this.lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return this.lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt06() {
		return this.lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt07() {
		return this.lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt08() {
		return this.lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt09() {
		return this.lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt10() {
		return this.lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt11() {
		return this.lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt12() {
		return this.lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotnum() {
		return this.lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getOfffromtime() {
		return this.offfromtime;
	}

	public void setOfffromtime(String offfromtime) {
		this.offfromtime = offfromtime;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkudescrc() {
		return this.skudescrc;
	}

	public void setSkudescrc(String skudescrc) {
		this.skudescrc = skudescrc;
	}

	public String getTraceid() {
		return this.traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWhooff() {
		return this.whooff;
	}

	public void setWhooff(String whooff) {
		this.whooff = whooff;
	}

	public String getWhoon() {
		return this.whoon;
	}

	public void setWhoon(String whoon) {
		this.whoon = whoon;
	}

}