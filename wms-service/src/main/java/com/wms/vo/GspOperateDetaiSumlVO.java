package com.wms.vo;

import com.wms.utils.serialzer.JsonDatetimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class GspOperateDetaiSumlVO implements Comparable<GspOperateDetaiSumlVO> {

	private String licenseId;
	private String operateId;
	private String operateName;
	private String createId;
	private java.util.Date createDate;
	private String editId;
	private java.util.Date editDate;
	private String isUse;

	private int instrumentCatalogNo;




	public int getInstrumentCatalogNo() {
		return instrumentCatalogNo;
	}

	public void setInstrumentCatalogNo(int instrumentCatalogNo) {
		this.instrumentCatalogNo = instrumentCatalogNo;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
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

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	@Override
	public int compareTo(GspOperateDetaiSumlVO o) {
		return this.instrumentCatalogNo - o.getInstrumentCatalogNo();
	}

}