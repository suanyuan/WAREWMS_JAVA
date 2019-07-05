package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspSupplierVO {

	private java.lang.String supplierId;
	private java.lang.String enterpriseId;
	private java.lang.String isCheck;
	private java.lang.String operateType;
	private java.lang.String createId;
	private java.util.Date createDate;
	private java.lang.String editId;
	private java.util.Date editDate;
	private java.lang.String isUse;

	public java.lang.String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.String supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(java.lang.String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public java.lang.String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(java.lang.String isCheck) {
		this.isCheck = isCheck;
	}

	public java.lang.String getOperateType() {
		return operateType;
	}

	public void setOperateType(java.lang.String operateType) {
		this.operateType = operateType;
	}

	public java.lang.String getCreateId() {
		return createId;
	}

	public void setCreateId(java.lang.String createId) {
		this.createId = createId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getEditId() {
		return editId;
	}

	public void setEditId(java.lang.String editId) {
		this.editId = editId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEditDate() {
		return editDate;
	}

	public void setEditDate(java.util.Date editDate) {
		this.editDate = editDate;
	}

	public java.lang.String getIsUse() {
		return isUse;
	}

	public void setIsUse(java.lang.String isUse) {
		this.isUse = isUse;
	}

}