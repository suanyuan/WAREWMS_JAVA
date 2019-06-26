package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocMovementHeaderForm {

	private String mdocno;
	private String mdoctype;
	private String customerid;
	private java.sql.Date mdoccreationtime;
	private java.sql.Date movementtime;
	private String reasoncode;
	private String reason;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private java.sql.Date addtime;
	private String addwho;
	private java.sql.Date edittime;
	private String editwho;
	private String status;
	private String zonegroup;
	private String fmwarehouseid;
	private String towarehouseid;
	private String userdefinea;
	private String userdefineb;
	private String source;
	private String sourceno;
	private String fromloc;

	public String getMdocno() {
		return mdocno;
	}

	public void setMdocno(String mdocno) {
		this.mdocno = mdocno;
	}

	public String getMdoctype() {
		return mdoctype;
	}

	public void setMdoctype(String mdoctype) {
		this.mdoctype = mdoctype;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getMdoccreationtime() {
		return mdoccreationtime;
	}

	public void setMdoccreationtime(java.sql.Date mdoccreationtime) {
		this.mdoccreationtime = mdoccreationtime;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getMovementtime() {
		return movementtime;
	}

	public void setMovementtime(java.sql.Date movementtime) {
		this.movementtime = movementtime;
	}

	public String getReasoncode() {
		return reasoncode;
	}

	public void setReasoncode(String reasoncode) {
		this.reasoncode = reasoncode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.sql.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.sql.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZonegroup() {
		return zonegroup;
	}

	public void setZonegroup(String zonegroup) {
		this.zonegroup = zonegroup;
	}

	public String getFmwarehouseid() {
		return fmwarehouseid;
	}

	public void setFmwarehouseid(String fmwarehouseid) {
		this.fmwarehouseid = fmwarehouseid;
	}

	public String getTowarehouseid() {
		return towarehouseid;
	}

	public void setTowarehouseid(String towarehouseid) {
		this.towarehouseid = towarehouseid;
	}

	public String getUserdefinea() {
		return userdefinea;
	}

	public void setUserdefinea(String userdefinea) {
		this.userdefinea = userdefinea;
	}

	public String getUserdefineb() {
		return userdefineb;
	}

	public void setUserdefineb(String userdefineb) {
		this.userdefineb = userdefineb;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceno() {
		return sourceno;
	}

	public void setSourceno(String sourceno) {
		this.sourceno = sourceno;
	}

	public String getFromloc() {
		return fromloc;
	}

	public void setFromloc(String fromloc) {
		this.fromloc = fromloc;
	}

}