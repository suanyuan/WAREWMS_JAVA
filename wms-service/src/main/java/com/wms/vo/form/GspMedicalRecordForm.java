package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class GspMedicalRecordForm {

	private String medicalId;
	private String medicalRegisterNo;
	private String medicalName;
	private String medicalAddress;
	private String juridicalPerson;
	private String registrationAuthority;
	private String headName;
	private java.util.Date approveDate;
	private String recordUrl;
	private java.util.Date licenseExpiryDateBegin;
	private java.util.Date licenseExpiryDateEnd;
	private String createId;
	private java.util.Date createDate;
	private String editId;
	private java.util.Date editDate;
	private String isUse;
	private String reservedfield1;
	private String reservedfield2;
	private String enterpriseId;
	private String operateType;
	private String opType;
	private String isLong;
	private String scopArr;

	public String getScopArr() {
		return scopArr;
	}

	public void setScopArr(String scopArr) {
		this.scopArr = scopArr;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Date getLicenseExpiryDateBegin() {
		return licenseExpiryDateBegin;
	}

	public void setLicenseExpiryDateBegin(Date licenseExpiryDateBegin) {
		this.licenseExpiryDateBegin = licenseExpiryDateBegin;
	}

	public Date getLicenseExpiryDateEnd() {
		return licenseExpiryDateEnd;
	}

	public void setLicenseExpiryDateEnd(Date licenseExpiryDateEnd) {
		this.licenseExpiryDateEnd = licenseExpiryDateEnd;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getIsLong() {
		return isLong;
	}

	public void setIsLong(String isLong) {
		this.isLong = isLong;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getMedicalId() {
		return medicalId;
	}

	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId;
	}

	public String getMedicalRegisterNo() {
		return medicalRegisterNo;
	}

	public void setMedicalRegisterNo(String medicalRegisterNo) {
		this.medicalRegisterNo = medicalRegisterNo;
	}

	public String getMedicalName() {
		return medicalName;
	}

	public void setMedicalName(String medicalName) {
		this.medicalName = medicalName;
	}

	public String getMedicalAddress() {
		return medicalAddress;
	}

	public void setMedicalAddress(String medicalAddress) {
		this.medicalAddress = medicalAddress;
	}

	public String getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(String juridicalPerson) {
		this.juridicalPerson = juridicalPerson;
	}

	public String getRegistrationAuthority() {
		return registrationAuthority;
	}

	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
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





	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
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

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

}