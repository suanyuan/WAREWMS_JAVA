package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BAS_ZONE database table.
 * 
 */
@Entity

//@Table(name="BAS_ZONE")
//@NamedQuery(name="BasZone.findAll", query="SELECT b FROM BasZone b")
public class BasZone implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	@Column(name="\"ZONE\"")
	private String zone;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	@Column(name="ALLOW_CS_RPL")
	private String allowCsRpl;

	@Column(name="ALLOW_EA_RPL")
	private String allowEaRpl;

	private String baselocation;

	private BigDecimal cubage;

	private String descr;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	@Column(name="FACILITY_ID")
	private String facilityId;

	private String picktoloc;

	private String putawaytoloc;

	private String route;

	private String zonegroup;
	
	private String zonegroupName;

	public BasZone() {
	}

	public String getZone() {
		return this.zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
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

	public String getAllowCsRpl() {
		return this.allowCsRpl;
	}

	public void setAllowCsRpl(String allowCsRpl) {
		this.allowCsRpl = allowCsRpl;
	}

	public String getAllowEaRpl() {
		return this.allowEaRpl;
	}

	public void setAllowEaRpl(String allowEaRpl) {
		this.allowEaRpl = allowEaRpl;
	}

	public String getBaselocation() {
		return this.baselocation;
	}

	public void setBaselocation(String baselocation) {
		this.baselocation = baselocation;
	}

	public BigDecimal getCubage() {
		return this.cubage;
	}

	public void setCubage(BigDecimal cubage) {
		this.cubage = cubage;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
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

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getPicktoloc() {
		return this.picktoloc;
	}

	public void setPicktoloc(String picktoloc) {
		this.picktoloc = picktoloc;
	}

	public String getPutawaytoloc() {
		return this.putawaytoloc;
	}

	public void setPutawaytoloc(String putawaytoloc) {
		this.putawaytoloc = putawaytoloc;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getZonegroup() {
		return this.zonegroup;
	}

	public void setZonegroup(String zonegroup) {
		this.zonegroup = zonegroup;
	}

	public String getZonegroupName() {
		return this.zonegroupName;
	}

	public void setZonegroupName(String zonegroupName) {
		this.zonegroupName = zonegroupName;
	}
}