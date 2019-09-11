package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspEnterpriseInfoVO {

	private String enterpriseId;
	private String enterpriseNo;
	private String shorthandName;
	private String enterpriseName;
	private String enterpriseType;
	private String contacts;
	private String contactsPhone;
	private String remark;
	private String createId;
	private String createDate;
	private String editId;
	private String editDate;
	private String isUse;
	private String state;
	private String userDefine1;
	private String userDefine2;
	private String userDefine3;
	private String userDefine4;
//产品许可证 备案号
	private String licenseNo;
	private String recordNo;
	private String plicenseNo;
	private String grecordNo;//生产第一类备案


	public String getPlicenseNo() {
		return plicenseNo;
	}

	public void setPlicenseNo(String plicenseNo) {
		this.plicenseNo = plicenseNo;
	}

	public String getGrecordNo() {
		return grecordNo;
	}

	public void setGrecordNo(String grecordNo) {
		this.grecordNo = grecordNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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

	public String getEnterpriseNo() {
		return enterpriseNo;
	}

	public void setEnterpriseNo(String enterpriseNo) {
		this.enterpriseNo = enterpriseNo;
	}

	public String getShorthandName() {
		return shorthandName;
	}

	public void setShorthandName(String shorthandName) {
		this.shorthandName = shorthandName;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
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

	@JsonSerialize(using = JsonDatetimeSerializer.class)
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUserDefine1() {
		return userDefine1;
	}

	public void setUserDefine1(String userDefine1) {
		this.userDefine1 = userDefine1;
	}

	public String getUserDefine2() {
		return userDefine2;
	}

	public void setUserDefine2(String userDefine2) {
		this.userDefine2 = userDefine2;
	}

	public String getUserDefine3() {
		return userDefine3;
	}

	public void setUserDefine3(String userDefine3) {
		this.userDefine3 = userDefine3;
	}

	public String getUserDefine4() {
		return userDefine4;
	}

	public void setUserDefine4(String userDefine4) {
		this.userDefine4 = userDefine4;
	}
}