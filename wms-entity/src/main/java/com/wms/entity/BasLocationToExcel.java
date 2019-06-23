package com.wms.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for the BAS_LOCATION database table.
 * 
 */
@Entity

public class BasLocationToExcel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String seq;
	
	private String locationid;
	
	private String picklogicalsequence;
	
	private String locationusage;
	
	private String locationcategory;
	
	private String logicalsequence;
	
	private String locationattribute;
	
	private String locationhandling;
	
	private String demand;
	
	private String putawayzone;
	
	private String pickzone;
	
	private String cubiccapacity;
	
	private String weightcapacity;
	
	private String cscount;
	
	private String countcapacity;
	
	private String plcount;
	
	private String mixFlag;
	
	private String mixLotflag;
	
	private String loseidFlag;
	
	private String length;
	
	private String width;
	
	private String height;
	
	
	public BasLocationToExcel() {
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getLocationid() {
		return this.locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getPicklogicalsequence() {
		return this.picklogicalsequence;
	}

	public void setPicklogicalsequence(String picklogicalsequence) {
		this.picklogicalsequence = picklogicalsequence;
	}
	
	public String getLocationusage() {
		return this.locationusage;
	}

	public void setLocationusage(String locationusage) {
		this.locationusage = locationusage;
	}
	
	public String getLocationcategory() {
		return this.locationcategory;
	}

	public void setLocationcategory(String locationcategory) {
		this.locationcategory = locationcategory;
	}
	
	public String getLogicalsequence() {
		return this.logicalsequence;
	}

	public void setLogicalsequence(String logicalsequence) {
		this.logicalsequence = logicalsequence;
	}
	
	public String getLocationattribute() {
		return this.locationattribute;
	}

	public void setLocationattribute(String locationattribute) {
		this.locationattribute = locationattribute;
	}
	
	public String getLocationhandling() {
		return this.locationhandling;
	}

	public void setLocationhandling(String locationhandling) {
		this.locationhandling = locationhandling;
	}
	
	public String getDemand() {
		return this.demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}
	
	public String getPutawayzone() {
		return this.putawayzone;
	}

	public void setPutawayzone(String putawayzone) {
		this.putawayzone = putawayzone;
	}
	
	public String getPickzone() {
		return this.pickzone;
	}

	public void setPickzone(String pickzone) {
		this.pickzone = pickzone;
	}

	public String getCubiccapacity() {
		return this.cubiccapacity;
	}

	public void setCubiccapacity(String cubiccapacity) {
		this.cubiccapacity = cubiccapacity;
	}
	
	public String getWeightcapacity() {
		return this.weightcapacity;
	}

	public void setWeightcapacity(String weightcapacity) {
		this.weightcapacity = weightcapacity;
	}
	
	public String getCscount() {
		return this.cscount;
	}

	public void setCscount(String cscount) {
		this.cscount = cscount;
	}
	
	public String getCountcapacity() {
		return this.countcapacity;
	}

	public void setCountcapacity(String countcapacity) {
		this.countcapacity = countcapacity;
	}
	
	public String getPlcount() {
		return this.plcount;
	}

	public void setPlcount(String plcount) {
		this.plcount = plcount;
	}
	
	public String getMixFlag() {
		return this.mixFlag;
	}

	public void setMixFlag(String mixFlag) {
		this.mixFlag = mixFlag;
	}

	public String getMixLotflag() {
		return this.mixLotflag;
	}

	public void setMixLotflag(String mixLotflag) {
		this.mixLotflag = mixLotflag;
	}
	
	public String getLoseidFlag() {
		return this.loseidFlag;
	}

	public void setLoseidFlag(String loseidFlag) {
		this.loseidFlag = loseidFlag;
	}
	
	public String getLength() {
		return this.length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}	
}