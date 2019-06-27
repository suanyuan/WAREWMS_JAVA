package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspInstrumentCatalogForm {

	private String instrumentCatalogId;
	private String instrumentCatalogNo;
	private String instrumentCatalogName;
	private String instrumentCatalogRemark;
	private String classifyId;
	private String version;
	private String createId;
	private java.util.Date cretaeDate;
	private String editId;
	private java.util.Date editDate;
	private String isUse;

	public String getInstrumentCatalogId() {
		return instrumentCatalogId;
	}

	public void setInstrumentCatalogId(String instrumentCatalogId) {
		this.instrumentCatalogId = instrumentCatalogId;
	}

	public String getInstrumentCatalogNo() {
		return instrumentCatalogNo;
	}

	public void setInstrumentCatalogNo(String instrumentCatalogNo) {
		this.instrumentCatalogNo = instrumentCatalogNo;
	}

	public String getInstrumentCatalogName() {
		return instrumentCatalogName;
	}

	public void setInstrumentCatalogName(String instrumentCatalogName) {
		this.instrumentCatalogName = instrumentCatalogName;
	}

	public String getInstrumentCatalogRemark() {
		return instrumentCatalogRemark;
	}

	public void setInstrumentCatalogRemark(String instrumentCatalogRemark) {
		this.instrumentCatalogRemark = instrumentCatalogRemark;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCretaeDate() {
		return cretaeDate;
	}

	public void setCretaeDate(java.util.Date cretaeDate) {
		this.cretaeDate = cretaeDate;
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

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

}