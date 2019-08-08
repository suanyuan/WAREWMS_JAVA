package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class GspSupplierForm {

	private java.lang.String supplierId;
	private java.lang.String enterpriseId;
	private java.lang.String isCheck;
	private java.lang.String operateType;
	private java.lang.String createId;
	private java.util.Date createDate;
	private java.lang.String editId;
	private java.util.Date editDate;
	private java.lang.String isUse;
	private java.lang.String firstState;
	private java.lang.String enterpriseName;
	private java.lang.String shorthandName;
	private java.lang.String enterpriseType;
	private java.lang.String enterpriseNo;
	private java.lang.String productionAddress;

	private java.lang.String contractNo;
	private java.lang.String contractUrl;
	private java.lang.String clientContent;
	private java.util.Date clientStartDate;
	private java.util.Date clientEndDate;
	private java.lang.String clientTerm;


	private java.lang.String costomerid;
	private java.lang.String empowerUnit;
	private java.lang.String empowerPhoto;
	private java.util.Date empowerStartdate;
	private java.util.Date empowerEnddate;
	private java.lang.String empowerContent;

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

	public Date getEmpowerStartdate() {
		return empowerStartdate;
	}

	public void setEmpowerStartdate(Date empowerStartdate) {
		this.empowerStartdate = empowerStartdate;
	}

	public Date getEmpowerEnddate() {
		return empowerEnddate;
	}

	public void setEmpowerEnddate(Date empowerEnddate) {
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

	public String getEnterpriseNo() {
		return enterpriseNo;
	}

	public void setEnterpriseNo(String enterpriseNo) {
		this.enterpriseNo = enterpriseNo;
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

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.String getEditId() {
		return editId;
	}

	public void setEditId(java.lang.String editId) {
		this.editId = editId;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEditDate() {
		return editDate;
	}

	public void setEditDate(java.util.Date editDate) {
		this.editDate = editDate;
	}

	public java.lang.String getIsUse() {
		return isUse;
	}

	public void setIsUse(java.lang.String isUse) {
		this.isUse = isUse;
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

	public String getProductionAddress() {
		return productionAddress;
	}

	public void setProductionAddress(String productionAddress) {
		this.productionAddress = productionAddress;
	}
}