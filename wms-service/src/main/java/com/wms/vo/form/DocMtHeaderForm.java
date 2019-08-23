package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocMtHeaderForm {

	private String mtno;
	private String mtstatus;
	private String mttype;
	private java.util.Date fromdate;
	private java.util.Date todate;
	private long storageFlag;           //贮存条件 1||0
	private long flowFlag;              //作业流程 1||0
	private long signFlag;              //标志清晰 1||0
	private long fenceFlag;             //防护措施 1||0
	private long sanitationFlag;        //卫生环境 1||0
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String remark;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private String warehouseid;

	public String getMtno() {
		return mtno;
	}

	public void setMtno(String mtno) {
		this.mtno = mtno;
	}

	public String getMtstatus() {
		return mtstatus;
	}

	public void setMtstatus(String mtstatus) {
		this.mtstatus = mtstatus;
	}

	public String getMttype() {
		return mttype;
	}

	public void setMttype(String mttype) {
		this.mttype = mttype;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getFromdate() {
		return fromdate;
	}

	public void setFromdate(java.util.Date fromdate) {
		this.fromdate = fromdate;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getTodate() {
		return todate;
	}

	public void setTodate(java.util.Date todate) {
		this.todate = todate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public long getStorageFlag() {
		return storageFlag;
	}

	public void setStorageFlag(long storageFlag) {
		this.storageFlag = storageFlag;
	}

	public long getFlowFlag() {
		return flowFlag;
	}

	public void setFlowFlag(long flowFlag) {
		this.flowFlag = flowFlag;
	}

	public long getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(long signFlag) {
		this.signFlag = signFlag;
	}

	public long getFenceFlag() {
		return fenceFlag;
	}

	public void setFenceFlag(long fenceFlag) {
		this.fenceFlag = fenceFlag;
	}

	public long getSanitationFlag() {
		return sanitationFlag;
	}

	public void setSanitationFlag(long sanitationFlag) {
		this.sanitationFlag = sanitationFlag;
	}
}