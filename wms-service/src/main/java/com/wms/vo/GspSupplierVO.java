package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspSupplierVO {

	private java.lang.String supplierId;
	private java.lang.String enterpriseId;
	private java.lang.String isCheck;
	private java.lang.String operateType;
	private java.lang.String createId;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String createDate;
	private java.lang.String editId;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String editDate;
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



	public java.lang.String getEditId() {
		return editId;
	}

	public void setEditId(java.lang.String editId) {
		this.editId = editId;
	}



	public java.lang.String getIsUse() {
		return isUse;
	}

	public void setIsUse(java.lang.String isUse) {
		this.isUse = isUse;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
}