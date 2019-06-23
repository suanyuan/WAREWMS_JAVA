package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class ImportLocationDataVO {
	private Integer seq;
	private java.lang.String locationid;
	private java.lang.String picklogicalsequence;
	private java.lang.String locationusage;
	private java.lang.String locationcategory;
	private java.lang.String logicalsequence;
	private java.lang.String locationattribute;
	private java.lang.String locationhandling;
	private java.lang.String demand;
	private java.lang.String putawayzone;
	private java.lang.String pickzone;
	private java.math.BigDecimal cubiccapacity;
	private java.math.BigDecimal weightcapacity;
	private java.math.BigDecimal cscount;
	private java.math.BigDecimal countcapacity;
	private java.math.BigDecimal plcount;
	private java.lang.String mixFlag;
	private java.lang.String mixLotflag;
	private java.lang.String loseidFlag;
	private java.math.BigDecimal length;
	private java.math.BigDecimal width;
	private java.math.BigDecimal height;
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public java.lang.String getLocationid() {
		return locationid;
	}

	public void setLocationid(java.lang.String locationid) {
		this.locationid = locationid;
	}

	public java.lang.String getPicklogicalsequence() {
		return picklogicalsequence;
	}

	public void setPicklogicalsequence(java.lang.String picklogicalsequence) {
		this.picklogicalsequence = picklogicalsequence;
	}
	
	public java.lang.String getLocationusage() {
		return locationusage;
	}

	public void setLocationusage(java.lang.String locationusage) {
		this.locationusage = locationusage;
	}
	
	public java.lang.String getLocationcategory() {
		return locationcategory;
	}

	public void setLocationcategory(java.lang.String locationcategory) {
		this.locationcategory = locationcategory;
	}
	
	public java.lang.String getLogicalsequence() {
		return logicalsequence;
	}

	public void setLogicalsequence(java.lang.String logicalsequence) {
		this.logicalsequence = logicalsequence;
	}

	public java.lang.String getLocationattribute() {
		return locationattribute;
	}

	public void setLocationattribute(java.lang.String locationattribute) {
		this.locationattribute = locationattribute;
	}
	
	public java.lang.String getLocationhandling() {
		return locationhandling;
	}

	public void setLocationhandling(java.lang.String locationhandling) {
		this.locationhandling = locationhandling;
	}

	public java.lang.String getDemand() {
		return demand;
	}

	public void setDemand(java.lang.String demand) {
		this.demand = demand;
	}
	
	public java.lang.String getPutawayzone() {
		return putawayzone;
	}

	public void setPutawayzone(java.lang.String putawayzone) {
		this.putawayzone = putawayzone;
	}
	
	public java.lang.String getPickzone() {
		return pickzone;
	}

	public void setPickzone(java.lang.String pickzone) {
		this.pickzone = pickzone;
	}
	
	public java.math.BigDecimal getCubiccapacity() {
		return cubiccapacity;
	}

	public void setCubiccapacity(java.math.BigDecimal cubiccapacity) {
		this.cubiccapacity = cubiccapacity;
	}
	
	public java.math.BigDecimal getWeightcapacity() {
		return weightcapacity;
	}

	public void setWeightcapacity(java.math.BigDecimal weightcapacity) {
		this.weightcapacity = weightcapacity;
	}
	
	public java.math.BigDecimal getCscount() {
		return cscount;
	}

	public void setCscount(java.math.BigDecimal cscount) {
		this.cscount = cscount;
	}

	public java.math.BigDecimal getCountcapacity() {
		return countcapacity;
	}

	public void setCountcapacity(java.math.BigDecimal countcapacity) {
		this.countcapacity = countcapacity;
	}
	
	public java.math.BigDecimal getPlcount() {
		return plcount;
	}

	public void setPlcount(java.math.BigDecimal plcount) {
		this.plcount = plcount;
	}
	
	public java.lang.String getMixFlag() {
		return mixFlag;
	}

	public void setMixFlag(java.lang.String mixFlag) {
		this.mixFlag = mixFlag;
	}

	public java.lang.String getMixLotflag() {
		return mixLotflag;
	}

	public void setMixLotflag(java.lang.String mixLotflag) {
		this.mixLotflag = mixLotflag;
	}
	
	public java.lang.String getLoseidFlag() {
		return loseidFlag;
	}

	public void setLoseidFlag(java.lang.String loseidFlag) {
		this.loseidFlag = loseidFlag;
	}
	
	public java.math.BigDecimal getLength() {
		return length;
	}

	public void setLength(java.math.BigDecimal length) {
		this.length = length;
	}
	
	public java.math.BigDecimal getWidth() {
		return width;
	}

	public void setWidth(java.math.BigDecimal width) {
		this.width = width;
	}
	
	public java.math.BigDecimal getHeight() {
		return height;
	}

	public void setHeight(java.math.BigDecimal height) {
		this.height = height;
	}
}