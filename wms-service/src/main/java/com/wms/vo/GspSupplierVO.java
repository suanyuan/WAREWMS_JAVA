package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspSupplierVO {

	private java.lang.String supplierId;
	private java.lang.String enterpriseId;
	private java.lang.String isCheck;
	private java.lang.String operateType;
	private java.lang.String createId;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String createDate;
	private java.lang.String editId;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String editDate;
	private java.lang.String isUse;
	private java.lang.String firstState;
	private java.lang.String enterpriseNo;
	private java.lang.String enterpriseName;
	private java.lang.String shorthandName;
	private java.lang.String enterpriseType;


	private java.lang.String contractNo;
	private java.lang.String contractUrl;
	private java.lang.String clientContent;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String clientStartDate;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String clientEndDate;
	private java.lang.String clientTerm;


	private java.lang.String costomerid;
	private java.lang.String empowerUnit;
	private java.lang.String empowerPhoto;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String empowerStartdate;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String empowerEnddate;
	private java.lang.String empowerContent;
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCostomerid() {
		return costomerid;
	}

	public void setCostomerid(String costomerid) {
		this.costomerid = costomerid;
	}

	public String getEmpowerUnit() {
		return empowerUnit;
	}

	public void setEmpowerUnit(String empowerUnit) {
		this.empowerUnit = empowerUnit;
	}

	public String getEmpowerPhoto() {
		return empowerPhoto;
	}

	public void setEmpowerPhoto(String empowerPhoto) {
		this.empowerPhoto = empowerPhoto;
	}

	public String getEmpowerStartdate() {
		return empowerStartdate;
	}

	public void setEmpowerStartdate(String empowerStartdate) {
		this.empowerStartdate = empowerStartdate;
	}

	public String getEmpowerEnddate() {
		return empowerEnddate;
	}

	public void setEmpowerEnddate(String empowerEnddate) {
		this.empowerEnddate = empowerEnddate;
	}

	public String getEmpowerContent() {
		return empowerContent;
	}

	public void setEmpowerContent(String empowerContent) {
		this.empowerContent = empowerContent;
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

	public String getClientStartDate() {
		return clientStartDate;
	}

	public void setClientStartDate(String clientStartDate) {
		this.clientStartDate = clientStartDate;
	}

	public String getClientEndDate() {
		return clientEndDate;
	}

	public void setClientEndDate(String clientEndDate) {
		this.clientEndDate = clientEndDate;
	}

	public String getClientTerm() {
		return clientTerm;
	}

	public void setClientTerm(String clientTerm) {
		this.clientTerm = clientTerm;
	}

	public java.lang.String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(java.lang.String supplierId) {
		this.supplierId = supplierId;
	}

	public java.lang.String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(java.lang.String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public java.lang.String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(java.lang.String isCheck) {
		this.isCheck = isCheck;
	}

	public java.lang.String getOperateType() {
		return operateType;
	}

	public void setOperateType(java.lang.String operateType) {
		this.operateType = operateType;
	}

	public java.lang.String getCreateId() {
		return createId;
	}

	public void setCreateId(java.lang.String createId) {
		this.createId = createId;
	}



	public java.lang.String getEditId() {
		return editId;
	}

	public void setEditId(java.lang.String editId) {
		this.editId = editId;
	}



	public java.lang.String getIsUse() {
		return isUse;
	}

	public void setIsUse(java.lang.String isUse) {
		this.isUse = isUse;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}

	public String getFirstState() {
		return firstState;
	}

	public void setFirstState(String firstState) {
		this.firstState = firstState;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
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

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
}