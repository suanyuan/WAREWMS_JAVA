package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasZoneVO {

	private java.lang.String zone;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.lang.String allowCsRpl;
	private java.lang.String allowEaRpl;
	private java.lang.String baselocation;
	private java.math.BigDecimal cubage;
	private java.lang.String descr;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.lang.String facilityId;
	private java.lang.String picktoloc;
	private java.lang.String putawaytoloc;
	private java.lang.String route;
	private java.lang.String zonegroup;
	private java.lang.String zonegroupName;

	public java.lang.String getZone() {
		return zone;
	}

	public void setZone(java.lang.String zone) {
		this.zone = zone;
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

	public java.lang.String getAllowCsRpl() {
		return allowCsRpl;
	}

	public void setAllowCsRpl(java.lang.String allowCsRpl) {
		this.allowCsRpl = allowCsRpl;
	}

	public java.lang.String getAllowEaRpl() {
		return allowEaRpl;
	}

	public void setAllowEaRpl(java.lang.String allowEaRpl) {
		this.allowEaRpl = allowEaRpl;
	}

	public java.lang.String getBaselocation() {
		return baselocation;
	}

	public void setBaselocation(java.lang.String baselocation) {
		this.baselocation = baselocation;
	}

	public java.math.BigDecimal getCubage() {
		return cubage;
	}

	public void setCubage(java.math.BigDecimal cubage) {
		this.cubage = cubage;
	}

	public java.lang.String getDescr() {
		return descr;
	}

	public void setDescr(java.lang.String descr) {
		this.descr = descr;
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

	public java.lang.String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(java.lang.String facilityId) {
		this.facilityId = facilityId;
	}

	public java.lang.String getPicktoloc() {
		return picktoloc;
	}

	public void setPicktoloc(java.lang.String picktoloc) {
		this.picktoloc = picktoloc;
	}

	public java.lang.String getPutawaytoloc() {
		return putawaytoloc;
	}

	public void setPutawaytoloc(java.lang.String putawaytoloc) {
		this.putawaytoloc = putawaytoloc;
	}

	public java.lang.String getRoute() {
		return route;
	}

	public void setRoute(java.lang.String route) {
		this.route = route;
	}

	public java.lang.String getZonegroup() {
		return zonegroup;
	}

	public void setZonegroup(java.lang.String zonegroup) {
		this.zonegroup = zonegroup;
	}
	
	public String getZonegroupName() {
		return this.zonegroupName;
	}

	public void setZonegroupName(String zonegroupName) {
		this.zonegroupName = zonegroupName;
	}

}