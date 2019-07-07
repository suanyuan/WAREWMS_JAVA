package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspInstrumentCatalogQuery implements IQuery {

	private String instrumentCatalogId;
	private String instrumentCatalogNo;
	private String instrumentCatalogName;
	private String instrumentCatalogRemark;
	private String classifyId;
	private String version;
	private String createId;
	private String cretaeDate;
	private String editId;
	private String editDate;
	private String isUse;
	private String enterpriseId;

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

	public String getCretaeDate() {
		return cretaeDate;
	}

	public void setCretaeDate(String cretaeDate) {
		this.cretaeDate = cretaeDate;
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

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
}