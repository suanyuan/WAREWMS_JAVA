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
	private String agentName;
	private String applyScope;
	private String mainPart;
	private String expectUse;
	private String storageConditions;
	private String productExpiryDate;
	private String otherContent;
	private String remark;
	private java.util.Date approveDate;
	private java.util.Date productRegisterExpiryDate;
	private String productRegisterVersion;
	private String version;
	private String checkerId;
	private java.util.Date checkDate;
	private String createId;
	private java.util.Date createDate;
	private String editId;
	private java.util.Date editDate;
	private String isUse;
	private String attachmentUrl;
	private String classifyId;
	private String classifyCatalog;

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

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(java.util.Date approveDate) {
		this.approveDate = approveDate;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getProductRegisterExpiryDate() {
		return productRegisterExpiryDate;
	}

	public void setProductRegisterExpiryDate(java.util.Date productRegisterExpiryDate) {
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

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(java.util.Date checkDate) {
		this.checkDate = checkDate;
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

}