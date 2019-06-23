package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BAS_LOCATION database table.
 * 
 */
@Entity

//@Table(name="BAS_LOCATION")
//@NamedQuery(name="BasLocation.findAll", query="SELECT b FROM BasLocation b")
public class BasLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;
	
	@Id
	private String locationid;

	@Column(updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	private BigDecimal countcapacity;

	private BigDecimal cscount;

	private BigDecimal cubiccapacity;

	private String demand;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	private String environment;

	@Column(updatable=false,name="FACILITY_ID")
	private String facilityId;

	private BigDecimal height;

	@Column(name="\"LENGTH\"")
	private BigDecimal length;

	private String locationattribute;

	private String locationcategory;

	private String locationhandling;

	private String locationusage;

	private String locgroup1;

	private String locgroup2;

	private String loclevel;

	private String logicalsequence;

	@Column(name="LOSEID_FLAG")
	private String loseidFlag;

	@Column(name="MIX_FLAG")
	private String mixFlag;

	@Column(name="MIX_LOTFLAG")
	private String mixLotflag;

	private String picklogicalsequence;

	private String pickzone;

	private BigDecimal plcount;

	private String putawayzone;

	@Column(updatable=false)
	private String status;

	private BigDecimal weightcapacity;

	private BigDecimal width;

	private BigDecimal xcoord;

	private BigDecimal xdistance;

	private BigDecimal ycoord;

	private BigDecimal ydistance;

	private BigDecimal zcoord;
	
	private String locationusageName;
	
	private String locationhandlingName;
	
	private String locationcategoryName;
	
	private String locationattributeName;
	
	private String demandName;
	
	private String pickzoneName;
	
	private String putawayzoneName;
	
	public BasLocation() {
	}

	public String getLocationid() {
		return this.locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return this.addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public BigDecimal getCountcapacity() {
		return this.countcapacity;
	}

	public void setCountcapacity(BigDecimal countcapacity) {
		this.countcapacity = countcapacity;
	}

	public BigDecimal getCscount() {
		return this.cscount;
	}

	public void setCscount(BigDecimal cscount) {
		this.cscount = cscount;
	}

	public BigDecimal getCubiccapacity() {
		return this.cubiccapacity;
	}

	public void setCubiccapacity(BigDecimal cubiccapacity) {
		this.cubiccapacity = cubiccapacity;
	}

	public String getDemand() {
		return this.demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public Date getEdittime() {
		return this.edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return this.editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getEnvironment() {
		return this.environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public BigDecimal getHeight() {
		return this.height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getLength() {
		return this.length;
	}

	public void setLength(BigDecimal length) {
		this.length = length;
	}

	public String getLocationattribute() {
		return this.locationattribute;
	}

	public void setLocationattribute(String locationattribute) {
		this.locationattribute = locationattribute;
	}

	public String getLocationcategory() {
		return this.locationcategory;
	}

	public void setLocationcategory(String locationcategory) {
		this.locationcategory = locationcategory;
	}

	public String getLocationhandling() {
		return this.locationhandling;
	}

	public void setLocationhandling(String locationhandling) {
		this.locationhandling = locationhandling;
	}

	public String getLocationusage() {
		return this.locationusage;
	}

	public void setLocationusage(String locationusage) {
		this.locationusage = locationusage;
	}

	public String getLocgroup1() {
		return this.locgroup1;
	}

	public void setLocgroup1(String locgroup1) {
		this.locgroup1 = locgroup1;
	}

	public String getLocgroup2() {
		return this.locgroup2;
	}

	public void setLocgroup2(String locgroup2) {
		this.locgroup2 = locgroup2;
	}

	public String getLoclevel() {
		return this.loclevel;
	}

	public void setLoclevel(String loclevel) {
		this.loclevel = loclevel;
	}

	public String getLogicalsequence() {
		return this.logicalsequence;
	}

	public void setLogicalsequence(String logicalsequence) {
		this.logicalsequence = logicalsequence;
	}

	public String getLoseidFlag() {
		return this.loseidFlag;
	}

	public void setLoseidFlag(String loseidFlag) {
		this.loseidFlag = loseidFlag;
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

	public String getPicklogicalsequence() {
		return this.picklogicalsequence;
	}

	public void setPicklogicalsequence(String picklogicalsequence) {
		this.picklogicalsequence = picklogicalsequence;
	}

	public String getPickzone() {
		return this.pickzone;
	}

	public void setPickzone(String pickzone) {
		this.pickzone = pickzone;
	}

	public BigDecimal getPlcount() {
		return this.plcount;
	}

	public void setPlcount(BigDecimal plcount) {
		this.plcount = plcount;
	}

	public String getPutawayzone() {
		return this.putawayzone;
	}

	public void setPutawayzone(String putawayzone) {
		this.putawayzone = putawayzone;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getWeightcapacity() {
		return this.weightcapacity;
	}

	public void setWeightcapacity(BigDecimal weightcapacity) {
		this.weightcapacity = weightcapacity;
	}

	public BigDecimal getWidth() {
		return this.width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getXcoord() {
		return this.xcoord;
	}

	public void setXcoord(BigDecimal xcoord) {
		this.xcoord = xcoord;
	}

	public BigDecimal getXdistance() {
		return this.xdistance;
	}

	public void setXdistance(BigDecimal xdistance) {
		this.xdistance = xdistance;
	}

	public BigDecimal getYcoord() {
		return this.ycoord;
	}

	public void setYcoord(BigDecimal ycoord) {
		this.ycoord = ycoord;
	}

	public BigDecimal getYdistance() {
		return this.ydistance;
	}

	public void setYdistance(BigDecimal ydistance) {
		this.ydistance = ydistance;
	}

	public BigDecimal getZcoord() {
		return this.zcoord;
	}

	public void setZcoord(BigDecimal zcoord) {
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