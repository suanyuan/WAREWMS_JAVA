package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasCarrierLicenseQuery implements IQuery {

	private String carrierLicenseId;
	private String roadNumber;
	private String roadNumberTerm;
	private String roadAuthorityPermit;
	private String roadBusinessScope;
	private String carrierNo;
	private String carrierDate;
	private String carrierEndDate;
	private String clientTerm;
	private String carrierAuthorityPermit;
	private String carrierBusinessScope;
	private String createId;
	private String createDate;
	private String editId;
	private String editDate;
	private String activeFlag;

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

	public String getCarrierDate() {
		return carrierDate;
	}

	public void setCarrierDate(String carrierDate) {
		this.carrierDate = carrierDate;
	}

	public String getCarrierEndDate() {
		return carrierEndDate;
	}

	public void setCarrierEndDate(String carrierEndDate) {
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

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}