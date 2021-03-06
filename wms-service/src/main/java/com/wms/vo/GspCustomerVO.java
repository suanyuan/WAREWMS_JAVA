package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class GspCustomerVO {

	private String clientId;
	private String clientNo;
	private String clientName;
	private String enterpriseId;
	private String enterpriseName;
	private String remark;
	private String firstState;
	private String isCheck;
	private String isCooperation;
	private String operateType;
	private String contractNo;
	private String contractUrl;
	private String clientContent;
	private Date clientStartDate;
	private Date clientEndDate;
	private String clientTerm;
	private String isChineseLabel;
	private String createId;
	private java.util.Date createDate;
	private String editId;
	private java.util.Date editDate;
	private String isUse;



	private String costomerid;
	private String contacts;
	private String idCardNumber;
	private String telephone;
	private String idCardFront;
	private String idCardBack;
	private java.util.Date empowerStartDate;
	private java.util.Date empowerEndDate;
	private String empowerPhoto;



	public String getCostomerid() {
		return costomerid;
	}

	public void setCostomerid(String costomerid) {
		this.costomerid = costomerid;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

	public Date getEmpowerStartDate() {
		return empowerStartDate;
	}

	public void setEmpowerStartDate(Date empowerStartDate) {
		this.empowerStartDate = empowerStartDate;
	}

	public Date getEmpowerEndDate() {
		return empowerEndDate;
	}

	public void setEmpowerEndDate(Date empowerEndDate) {
		this.empowerEndDate = empowerEndDate;
	}

	public String getEmpowerPhoto() {
		return empowerPhoto;
	}

	public void setEmpowerPhoto(String empowerPhoto) {
		this.empowerPhoto = empowerPhoto;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFirstState() {
		return firstState;
	}

	public void setFirstState(String firstState) {
		this.firstState = firstState;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getIsCooperation() {
		return isCooperation;
	}

	public void setIsCooperation(String isCooperation) {
		this.isCooperation = isCooperation;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	public String getClientContent() {
		return clientContent;
	}

	public void setClientContent(String clientContent) {
		this.clientContent = clientContent;
	}

	public Date getClientStartDate() {
		return clientStartDate;
	}

	public void setClientStartDate(Date clientStartDate) {
		this.clientStartDate = clientStartDate;
	}

	public Date getClientEndDate() {
		return clientEndDate;
	}

	public void setClientEndDate(Date clientEndDate) {
		this.clientEndDate = clientEndDate;
	}

	public String getClientTerm() {
		return clientTerm;
	}

	public void setClientTerm(String clientTerm) {
		this.clientTerm = clientTerm;
	}

	public String getIsChineseLabel() {
		return isChineseLabel;
	}

	public void setIsChineseLabel(String isChineseLabel) {
		this.isChineseLabel = isChineseLabel;
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

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
}