package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspBusinessLicenseForm {

	private Long businessId;
	private Long enterpriseId;
	private String licenseNumber;
	private String socialCreditCode;
	private String licenseName;
	private String licenseType;
	private String residence;
	private String juridicalPerson;
	private String registeredCapital;
	private java.util.Date establishmentDate;
	private java.util.Date businessStartDate;
	private java.util.Date businessEndDate;
	private Long isLong;
	private String businessScope;
	private java.util.Date issueDate;
	private String registrationAuthority;
	private String attachmentUrl;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getSocialCreditCode() {
		return socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getJuridicalPerson() {
		return juridicalPerson;
	}

	public void setJuridicalPerson(String juridicalPerson) {
		this.juridicalPerson = juridicalPerson;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(java.util.Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(java.util.Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getBusinessEndDate() {
		return businessEndDate;
	}

	public void setBusinessEndDate(java.util.Date businessEndDate) {
		this.businessEndDate = businessEndDate;
	}

	public Long getIsLong() {
		return isLong;
	}

	public void setIsLong(Long isLong) {
		this.isLong = isLong;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(java.util.Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getRegistrationAuthority() {
		return registrationAuthority;
	}

	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

}