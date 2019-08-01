package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspProductRegisterForm {

	private String productRegisterId;
	private String productRegisterNo;
	private String productNameMain;
	private String productRegisterName;
	private String productRegisterAddress;
	private String productProductionAddress;
	private String productionAddress;

	private String agentName;
	private String applyScope;
	private String mainPart;
	private String expectUse;
	private String storageConditions;
	private String productExpiryDate;
	private String otherContent;
	private String remark;
	private String approveDate;
	private String productRegisterExpiryDate;
	private String productRegisterVersion;
	private String version;
	private String checkerId;
	private String checkDate;
	private String createId;
	private String createDate;
	private String editId;
	private String editDate;
	private String isUse;
	private String attachmentUrl;
	private String classifyId;
	private String classifyCatalog;
	private String enterpriseId;
	private String choseScope;
	private String transportConditionMain;//运输条件
	private String approvalDepartment;//审核部门
	private String productRegsiterUrl;//附件路径
	private String structureAndComposition;//结构及组成
	private String agentAddress;//代理人住所

	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	public String getStructureAndComposition() {
		return structureAndComposition;
	}

	public void setStructureAndComposition(String structureAndComposition) {
		this.structureAndComposition = structureAndComposition;
	}

	public String getProductionAddress() {
		return productionAddress;
	}

	public void setProductionAddress(String productionAddress) {
		this.productionAddress = productionAddress;
	}

	public String getApprovalDepartment() {
		return approvalDepartment;
	}

	public void setApprovalDepartment(String approvalDepartment) {
		this.approvalDepartment = approvalDepartment;
	}

	public String getProductRegsiterUrl() {
		return productRegsiterUrl;
	}

	public void setProductRegsiterUrl(String productRegsiterUrl) {
		this.productRegsiterUrl = productRegsiterUrl;
	}

	public String getTransportConditionMain() {
		return transportConditionMain;
	}

	public void setTransportConditionMain(String transportConditionMain) {
		this.transportConditionMain = transportConditionMain;
	}

	public String getProductRegisterId() {
		return productRegisterId;
	}

	public void setProductRegisterId(String productRegisterId) {
		this.productRegisterId = productRegisterId;
	}

	public String getProductRegisterNo() {
		return productRegisterNo;
	}

	public void setProductRegisterNo(String productRegisterNo) {
		this.productRegisterNo = productRegisterNo;
	}

	public String getProductNameMain() {
		return productNameMain;
	}

	public void setProductNameMain(String productNameMain) {
		this.productNameMain = productNameMain;
	}

	public String getProductRegisterName() {
		return productRegisterName;
	}

	public void setProductRegisterName(String productRegisterName) {
		this.productRegisterName = productRegisterName;
	}

	public String getProductRegisterAddress() {
		return productRegisterAddress;
	}

	public void setProductRegisterAddress(String productRegisterAddress) {
		this.productRegisterAddress = productRegisterAddress;
	}

	public String getProductProductionAddress() {
		return productProductionAddress;
	}

	public void setProductProductionAddress(String productProductionAddress) {
		this.productProductionAddress = productProductionAddress;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getApplyScope() {
		return applyScope;
	}

	public void setApplyScope(String applyScope) {
		this.applyScope = applyScope;
	}

	public String getMainPart() {
		return mainPart;
	}

	public void setMainPart(String mainPart) {
		this.mainPart = mainPart;
	}

	public String getExpectUse() {
		return expectUse;
	}

	public void setExpectUse(String expectUse) {
		this.expectUse = expectUse;
	}

	public String getStorageConditions() {
		return storageConditions;
	}

	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}

	public String getProductExpiryDate() {
		return productExpiryDate;
	}

	public void setProductExpiryDate(String productExpiryDate) {
		this.productExpiryDate = productExpiryDate;
	}

	public String getOtherContent() {
		return otherContent;
	}

	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getProductRegisterExpiryDate() {
		return productRegisterExpiryDate;
	}

	public void setProductRegisterExpiryDate(String productRegisterExpiryDate) {
		this.productRegisterExpiryDate = productRegisterExpiryDate;
	}

	public String getProductRegisterVersion() {
		return productRegisterVersion;
	}

	public void setProductRegisterVersion(String productRegisterVersion) {
		this.productRegisterVersion = productRegisterVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
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

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getClassifyCatalog() {
		return classifyCatalog;
	}

	public void setClassifyCatalog(String classifyCatalog) {
		this.classifyCatalog = classifyCatalog;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getChoseScope() {
		return choseScope;
	}

	public void setChoseScope(String choseScope) {
		this.choseScope = choseScope;
	}
}