package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasZonegroupVO {

	private java.lang.String zonegroup;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String addtime;
	private java.lang.String addwho;
	private java.lang.String descr;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String edittime;
	private java.lang.String editwho;
	private java.lang.String warehouseid;

	public java.lang.String getZonegroup() {
		return zonegroup;
	}

	public void setZonegroup(java.lang.String zonegroup) {
		this.zonegroup = zonegroup;
	}



	public java.lang.String getAddwho() {
		return addwho;
	}

	public void setAddwho(java.lang.String addwho) {
		this.addwho = addwho;
	}

	public java.lang.String getDescr() {
		return descr;
	}

	public void setDescr(java.lang.String descr) {
		this.descr = descr;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}

}