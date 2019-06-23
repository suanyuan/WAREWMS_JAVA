package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;


public class BasLocationVO {

	private java.lang.String locationid;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.math.BigDecimal countcapacity;
	private java.math.BigDecimal cscount;
	private java.math.BigDecimal cubiccapacity;
	private java.lang.String demand;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.lang.String environment;
	private java.lang.String facilityId;
	private java.math.BigDecimal height;
	private java.math.BigDecimal length;
	private java.lang.String locationattribute;
	private java.lang.String locationcategory;
	private java.lang.String locationhandling;
	private java.lang.String locationusage;
	private java.lang.String locgroup1;
	private java.lang.String locgroup2;
	private java.lang.String loclevel;
	private java.lang.String logicalsequence;
	private java.lang.String loseidFlag;
	private java.lang.String mixFlag;
	private java.lang.String mixLotflag;
	private java.lang.String picklogicalsequence;
	private java.lang.String pickzone;
	private java.math.BigDecimal plcount;
	private java.lang.String putawayzone;
	private java.lang.String status;
	private java.math.BigDecimal weightcapacity;
	private java.math.BigDecimal width;
	private java.math.BigDecimal xcoord;
	private java.math.BigDecimal xdistance;
	private java.math.BigDecimal ycoord;
	private java.math.BigDecimal ydistance;
	private java.math.BigDecimal zcoord;
	
    private String locationusageName;
	
	private String locationhandlingName;
	
	private String locationcategoryName;
	
	private String locationattributeName;
	
	private String demandName;
	
	private String pickzoneName;
	
	private String putawayzoneName;

	public java.lang.String getLocationid() {
		return locationid;
	}

	public void setLocationid(java.lang.String locationid) {
		this.locationid = locationid;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddwho() {
		return addwho;
	}

	public void setAddwho(java.lang.String addwho) {
		this.addwho = addwho;
	}

	public java.math.BigDecimal getCountcapacity() {
		return countcapacity;
	}

	public void setCountcapacity(java.math.BigDecimal countcapacity) {
		this.countcapacity = countcapacity;
	}

	public java.math.BigDecimal getCscount() {
		return cscount;
	}

	public void setCscount(java.math.BigDecimal cscount) {
		this.cscount = cscount;
	}

	public java.math.BigDecimal getCubiccapacity() {
		return cubiccapacity;
	}

	public void setCubiccapacity(java.math.BigDecimal cubiccapacity) {
		this.cubiccapacity = cubiccapacity;
	}

	public java.lang.String getDemand() {
		return demand;
	}

	public void setDemand(java.lang.String demand) {
		this.demand = demand;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	public java.lang.String getEnvironment() {
		return environment;
	}

	public void setEnvironment(java.lang.String environment) {
		this.environment = environment;
	}

	public java.lang.String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(java.lang.String facilityId) {
		this.facilityId = facilityId;
	}

	public java.math.BigDecimal getHeight() {
		return height;
	}

	public void setHeight(java.math.BigDecimal height) {
		this.height = height;
	}

	public java.math.BigDecimal getLength() {
		return length;
	}

	public void setLength(java.math.BigDecimal length) {
		this.length = length;
	}

	public java.lang.String getLocationattribute() {
		return locationattribute;
	}

	public void setLocationattribute(java.lang.String locationattribute) {
		this.locationattribute = locationattribute;
	}

	public java.lang.String getLocationcategory() {
		return locationcategory;
	}

	public void setLocationcategory(java.lang.String locationcategory) {
		this.locationcategory = locationcategory;
	}

	public java.lang.String getLocationhandling() {
		return locationhandling;
	}

	public void setLocationhandling(java.lang.String locationhandling) {
		this.locationhandling = locationhandling;
	}

	public java.lang.String getLocationusage() {
		return locationusage;
	}

	public void setLocationusage(java.lang.String locationusage) {
		this.locationusage = locationusage;
	}

	public java.lang.String getLocgroup1() {
		return locgroup1;
	}

	public void setLocgroup1(java.lang.String locgroup1) {
		this.locgroup1 = locgroup1;
	}

	public java.lang.String getLocgroup2() {
		return locgroup2;
	}

	public void setLocgroup2(java.lang.String locgroup2) {
		this.locgroup2 = locgroup2;
	}

	public java.lang.String getLoclevel() {
		return loclevel;
	}

	public void setLoclevel(java.lang.String loclevel) {
		this.loclevel = loclevel;
	}

	public java.lang.String getLogicalsequence() {
		return logicalsequence;
	}

	public void setLogicalsequence(java.lang.String logicalsequence) {
		this.logicalsequence = logicalsequence;
	}

	public java.lang.String getLoseidFlag() {
		return loseidFlag;
	}

	public void setLoseidFlag(java.lang.String loseidFlag) {
		this.loseidFlag = loseidFlag;
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

	public java.lang.String getPicklogicalsequence() {
		return picklogicalsequence;
	}

	public void setPicklogicalsequence(java.lang.String picklogicalsequence) {
		this.picklogicalsequence = picklogicalsequence;
	}

	public java.lang.String getPickzone() {
		return pickzone;
	}

	public void setPickzone(java.lang.String pickzone) {
		this.pickzone = pickzone;
	}

	public java.math.BigDecimal getPlcount() {
		return plcount;
	}

	public void setPlcount(java.math.BigDecimal plcount) {
		this.plcount = plcount;
	}

	public java.lang.String getPutawayzone() {
		return putawayzone;
	}

	public void setPutawayzone(java.lang.String putawayzone) {
		this.putawayzone = putawayzone;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.math.BigDecimal getWeightcapacity() {
		return weightcapacity;
	}

	public void setWeightcapacity(java.math.BigDecimal weightcapacity) {
		this.weightcapacity = weightcapacity;
	}

	public java.math.BigDecimal getWidth() {
		return width;
	}

	public void setWidth(java.math.BigDecimal width) {
		this.width = width;
	}

	public java.math.BigDecimal getXcoord() {
		return xcoord;
	}

	public void setXcoord(java.math.BigDecimal xcoord) {
		this.xcoord = xcoord;
	}

	public java.math.BigDecimal getXdistance() {
		return xdistance;
	}

	public void setXdistance(java.math.BigDecimal xdistance) {
		this.xdistance = xdistance;
	}

	public java.math.BigDecimal getYcoord() {
		return ycoord;
	}

	public void setYcoord(java.math.BigDecimal ycoord) {
		this.ycoord = ycoord;
	}

	public java.math.BigDecimal getYdistance() {
		return ydistance;
	}

	public void setYdistance(java.math.BigDecimal ydistance) {
		this.ydistance = ydistance;
	}

	public java.math.BigDecimal getZcoord() {
		return zcoord;
	}

	public void setZcoord(java.math.BigDecimal zcoord) {
		this.zcoord = zcoord;
	}

	public String getLocationusageName() {
		return this.locationusageName;
	}

	public void setLocationusageName(String locationusageName) {
		this.locationusageName = locationusageName;
	}

	public String getLocationhandlingName() {
		return this.locationhandlingName;
	}

	public void setLocationhandlingName(String locationhandlingName) {
		this.locationhandlingName = locationhandlingName;
	}
	
	public String getLocationcategoryName() {
		return this.locationcategoryName;
	}

	public void setLocationcategoryName(String locationcategoryName) {
		this.locationcategoryName = locationcategoryName;
	}
	
	public String getLocationattributeName() {
		return this.locationattributeName;
	}

	public void setLocationattributeName(String locationattributeName) {
		this.locationattributeName = locationattributeName;
	}
	
	public String getDemandName() {
		return this.demandName;
	}

	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}
	
	public String getPutawayzoneName() {
		return this.putawayzoneName;
	}

	public void setPutawayzoneName(String putawayzoneName) {
		this.putawayzoneName = putawayzoneName;
	}
	
	public String getPickzoneName() {
		return this.pickzoneName;
	}

	public void setPickzoneName(String pickzoneName) {
		this.pickzoneName = pickzoneName;
	}
}