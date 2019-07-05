package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocQcHeaderQuery implements IQuery {

	private String qcno;
	private String asnno;
	private String customerid;
	private String qcreference1;
	private String qcreference2;
	private String qcreference3;
	private String qcreference4;
	private String qcreference5;
	private String qctype;
	private String qcstatus;
	private String qccreationtime;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String notes;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String qcPrintFlag;
	private String warehouseid;

	public String getQcno() {
		return qcno;
	}

	public void setQcno(String qcno) {
		this.qcno = qcno;
	}

	public String getAsnno() {
		return asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getQcreference1() {
		return qcreference1;
	}

	public void setQcreference1(String qcreference1) {
		this.qcreference1 = qcreference1;
	}

	public String getQcreference2() {
		return qcreference2;
	}

	public void setQcreference2(String qcreference2) {
		this.qcreference2 = qcreference2;
	}

	public String getQcreference3() {
		return qcreference3;
	}

	public void setQcreference3(String qcreference3) {
		this.qcreference3 = qcreference3;
	}

	public String getQcreference4() {
		return qcreference4;
	}

	public void setQcreference4(String qcreference4) {
		this.qcreference4 = qcreference4;
	}

	public String getQcreference5() {
		return qcreference5;
	}

	public void setQcreference5(String qcreference5) {
		this.qcreference5 = qcreference5;
	}

	public String getQctype() {
		return qctype;
	}

	public void setQctype(String qctype) {
		this.qctype = qctype;
	}

	public String getQcstatus() {
		return qcstatus;
	}

	public void setQcstatus(String qcstatus) {
		this.qcstatus = qcstatus;
	}

	public String getQccreationtime() {
		return qccreationtime;
	}

	public void setQccreationtime(String qccreationtime) {
		this.qccreationtime = qccreationtime;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getQcPrintFlag() {
		return qcPrintFlag;
	}

	public void setQcPrintFlag(String qcPrintFlag) {
		this.qcPrintFlag = qcPrintFlag;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}