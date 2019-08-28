package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspFirstRecordVO {

	private String recordId;
	private String enterpriseName;
	private String recordNo;
	private String enterpriseId;
	private String residence;
	private String headName;
	private java.sql.Timestamp approveDate;
	private String registrationAuthority;
	private String bussinessScope;
	private String recordUrl;
	private String createId;
	private java.sql.Timestamp createDate;
	private String editId;
	private java.sql.Timestamp editDate;
	private String isUse;
	private String reservedfield1;
	private String reservedfield2;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public java.sql.Timestamp getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(java.sql.Timestamp approveDate) {
		this.approveDate = approveDate;
	}

	public String getRegistrationAuthority() {
		return registrationAuthority;
	}

	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}

	public String getBussinessScope() {
		return bussinessScope;
	}

	public void setBussinessScope(String bussinessScope) {
		this.bussinessScope = bussinessScope;
	}

	public String getRecordUrl() {
		return recordUrl;
	}

	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public java.sql.Timestamp getEditDate() {
		return editDate;
	}

	public void setEditDate(java.sql.Timestamp editDate) {
		this.editDate = editDate;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getReservedfield1() {
		return reservedfield1;
	}

	public void setReservedfield1(String reservedfield1) {
		this.reservedfield1 = reservedfield1;
	}

	public String getReservedfield2() {
		return reservedfield2;
	}

	public void setReservedfield2(String reservedfield2) {
		this.reservedfield2 = reservedfield2;
	}

}