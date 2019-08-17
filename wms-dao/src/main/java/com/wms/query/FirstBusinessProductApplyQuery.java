package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class FirstBusinessProductApplyQuery implements IQuery {

	private String productApplyId;
	private String applyId;
	private String specsId;
	private String isCheck;
	private String isOperate;
	private String isCold;
	private String createId;
	private String createDate;
	private String editId;
	private String editDate;
	private String isUse;
	private String supplierName;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getProductApplyId() {
		return productApplyId;
	}

	public void setProductApplyId(String productApplyId) {
		this.productApplyId = productApplyId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getSpecsId() {
		return specsId;
	}

	public void setSpecsId(String specsId) {
		this.specsId = specsId;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsOperate() {
		return isOperate;
	}

	public void setIsOperate(String isOperate) {
		this.isOperate = isOperate;
	}

	public String getIsCold() {
		return isCold;
	}

	public void setIsCold(String isCold) {
		this.isCold = isCold;
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

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

}