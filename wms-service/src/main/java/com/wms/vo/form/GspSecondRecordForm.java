package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspSecondRecordForm {

	private Long recordId;
	private String recordNo;
	private String enterpriseId;
	private String headName;
	private String operateMode;
	private String operatePlace;
	private String bussinessScope;
	private String residence;
	private String recordUrl;
	private java.util.Date approveDate;
	private String registrationAuthority;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
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

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getOperateMode() {
		return operateMode;
	}

	public void setOperateMode(String operateMode) {
		this.operateMode = operateMode;
	}

	public String getOperatePlace() {
		return operatePlace;
	}

	public void setOperatePlace(String operatePlace) {
		this.operatePlace = operatePlace;
	}

	public String getBussinessScope() {
		return bussinessScope;
	}

	public void setBussinessScope(String bussinessScope) {
		this.bussinessScope = bussinessScope;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getRecordUrl() {
		return recordUrl;
	}

	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(java.util.Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getRegistrationAuthority() {
		return registrationAuthority;
	}

	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}

}