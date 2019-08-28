package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspMedicalRecordQuery implements IQuery {

	private String medicalId;
	private String medicalRegisterNo;
	private String medicalName;
	private String medicalAddress;
	private String juridicalPerson;
	private String registrationAuthority;
	private String headName;
	private String approveDate;
	private String recordUrl;
	private String licenseExpiryDateBegin;
	private String licenseExpiryDateEnd;
	private String createId;
	private String createDate;
	private String editId;
	private String editDate;
	private String isUse;
	private String reservedfield1;
	private String reservedfield2;
	private String enterpriseId;
	private String isLong;
	private String scopArr;

	public String getIsLong() {
		return isLong;
	}

	public void setIsLong(String isLong) {
		this.isLong = isLong;
	}

	public String getScopArr() {
		return scopArr;
	}

	public void setScopArr(String scopArr) {
		this.scopArr = scopArr;
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

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getRecordUrl() {
		return recordUrl;
	}

	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}

	public String getLicenseExpiryDateBegin() {
		return licenseExpiryDateBegin;
	}

	public void setLicenseExpiryDateBegin(String licenseExpiryDateBegin) {
		this.licenseExpiryDateBegin = licenseExpiryDateBegin;
	}

	public String getLicenseExpiryDateEnd() {
		return licenseExpiryDateEnd;
	}

	public void setLicenseExpiryDateEnd(String licenseExpiryDateEnd) {
		this.licenseExpiryDateEnd = licenseExpiryDateEnd;
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