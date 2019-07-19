package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class QcMeteringDeviceVO {

	private String calId;
	private String calName;
	private String calNumber;
	private String calTerm;
	private String calCardUrl;
	private String createId;
	private java.util.Date createDate;
	private String editId;
	private java.util.Date editDate;
	private String activeFlag;

	public String getCalId() {
		return calId;
	}

	public void setCalId(String calId) {
		this.calId = calId;
	}

	public String getCalName() {
		return calName;
	}

	public void setCalName(String calName) {
		this.calName = calName;
	}

	public String getCalNumber() {
		return calNumber;
	}

	public void setCalNumber(String calNumber) {
		this.calNumber = calNumber;
	}

	public String getCalTerm() {
		return calTerm;
	}

	public void setCalTerm(String calTerm) {
		this.calTerm = calTerm;
	}

	public String getCalCardUrl() {
		return calCardUrl;
	}

	public void setCalCardUrl(String calCardUrl) {
		this.calCardUrl = calCardUrl;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEditDate() {
		return editDate;
	}

	public void setEditDate(java.util.Date editDate) {
		this.editDate = editDate;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}