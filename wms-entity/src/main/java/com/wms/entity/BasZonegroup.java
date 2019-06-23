package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BAS_ZONEGROUP database table.
 * 
 */
@Entity
@Table(name="BAS_ZONEGROUP")
@NamedQuery(name="BasZonegroup.findAll", query="SELECT b FROM BasZonegroup b")
public class BasZonegroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String zonegroup;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	private String descr;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	private String warehouseid;

	public BasZonegroup() {
	}

	public String getZonegroup() {
		return this.zonegroup;
	}

	public void setZonegroup(String zonegroup) {
		this.zonegroup = zonegroup;
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

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}