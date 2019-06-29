package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasCarrierLicenseForm {

	private String carrierLicenseId;
	private String enterpriseId;
	private String roadNumber;
	private String roadNumberTerm;
	private String roadAuthorityPermit;
	private String roadBusinessScope;
	private String carrierNo;
	private java.util.Date carrierDate;
	private java.util.Date carrierEndDate;
	private String clientTerm;
	private String carrierAuthorityPermit;
	private String carrierBusinessScope;
	private String createId;
	private java.util.Date createDate;
	private String editId;
	private java.util.Date editDate;
	private String activeFlag;

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getCarrierLicenseId() {
		return carrierLicenseId;
	}

	public void setCarrierLicenseId(String carrierLicenseId) {
		this.carrierLicenseId = carrierLicenseId;
	}

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}

	public String getRoadNumberTerm() {
		return roadNumberTerm;
	}

	public void setRoadNumberTerm(String roadNumberTerm) {
		this.roadNumberTerm = roadNumberTerm;
	}

	public String getRoadAuthorityPermit() {
		return roadAuthorityPermit;
	}

	public void setRoadAuthorityPermit(String roadAuthorityPermit) {
		this.roadAuthorityPermit = roadAuthorityPermit;
	}

	public String getRoadBusinessScope() {
		return roadBusinessScope;
	}

	public void setRoadBusinessScope(String roadBusinessScope) {
		this.roadBusinessScope = roadBusinessScope;
	}

	public String getCarrierNo() {
		return carrierNo;
	}

	public void setCarrierNo(String carrierNo) {
		this.carrierNo = carrierNo;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCarrierDate() {
		return carrierDate;
	}

	public void setCarrierDate(java.util.Date carrierDate) {
		this.carrierDate = carrierDate;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCarrierEndDate() {
		return carrierEndDate;
	}

	public void setCarrierEndDate(java.util.Date carrierEndDate) {
		this.carrierEndDate = carrierEndDate;
	}

	public String getClientTerm() {
		return clientTerm;
	}

	public void setClientTerm(String clientTerm) {
		this.clientTerm = clientTerm;
	}

	public String getCarrierAuthorityPermit() {
		return carrierAuthorityPermit;
	}

	public void setCarrierAuthorityPermit(String carrierAuthorityPermit) {
		this.carrierAuthorityPermit = carrierAuthorityPermit;
	}

	public String getCarrierBusinessScope() {
		return carrierBusinessScope;
	}

	public void setCarrierBusinessScope(String carrierBusinessScope) {
		this.carrierBusinessScope = carrierBusinessScope;
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

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}