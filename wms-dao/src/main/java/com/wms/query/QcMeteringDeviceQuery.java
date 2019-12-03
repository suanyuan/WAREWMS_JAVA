package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class QcMeteringDeviceQuery implements IQuery {

	private String calId;
	private String calName;
	private String calNumber;
	private String calTerm;
	private String calCardUrl;
	private String createId;
	private String createDate;
	private String createDateStart;
	private String createDateEnd;
	private String editId;
	private String editDate;
	private String editDateStart;
	private String editDateEnd;
	private String activeFlag;

	private String idList;

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getEditDateStart() {
		return editDateStart;
	}

	public void setEditDateStart(String editDateStart) {
		this.editDateStart = editDateStart;
	}

	public String getEditDateEnd() {
		return editDateEnd;
	}

	public void setEditDateEnd(String editDateEnd) {
		this.editDateEnd = editDateEnd;
	}

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}