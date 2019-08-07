package com.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
public class ImportGPRData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	private String productRegisterNo;     //注册证编号
	private String productNameMain;       //产品名称
	private String classifyId;            //管理分类
	private String productRegisterVersion; //注册证版本
	private String classifyCatalog;       //分类目录
	private String productionAddress;     //产地
	private String storageConditions;     //储存条件
	private String transportConditionMain;//运输条件
	private String enterpriseId;          //注册人名称,生产企业
	private String enterpriseName;        //注册人名称,生产企业
	private String productRegisterAddress;//注册人住所
	private String productProductionAddress;//生产地址
	private String agentName;              //代理人名称
	private String agentAddress;           //代理人住所
	private String approvalDepartment;     //审批部门
	private String approveDate;            //批准日期
	private String productRegisterExpiryDate; //有效期至
	private String structureAndComposition;  //结构及组成
	private String applyScope;               //适用范围
	private String expectUse;                //预期用途
	private String mainPart;                 //主要组成部门
	private String productRegsiterUrl;       //附件
	private String otherContent;             //其他内容
    private String remark;                   //备注



	public ImportGPRData() {
	}

	public String getClassifyCatalog() {
		return classifyCatalog;
	}

	public void setClassifyCatalog(String classifyCatalog) {
		this.classifyCatalog = classifyCatalog;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
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

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getProductRegisterVersion() {
		return productRegisterVersion;
	}

	public void setProductRegisterVersion(String productRegisterVersion) {
		this.productRegisterVersion = productRegisterVersion;
	}

	public String getProductionAddress() {
		return productionAddress;
	}

	public void setProductionAddress(String productionAddress) {
		this.productionAddress = productionAddress;
	}

	public String getStorageConditions() {
		return storageConditions;
	}

	public void setStorageConditions(String storageConditions) {
		this.storageConditions = storageConditions;
	}

	public String getTransportConditionMain() {
		return transportConditionMain;
	}

	public void setTransportConditionMain(String transportConditionMain) {
		this.transportConditionMain = transportConditionMain;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
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

	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	public String getApprovalDepartment() {
		return approvalDepartment;
	}

	public void setApprovalDepartment(String approvalDepartment) {
		this.approvalDepartment = approvalDepartment;
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

	public String getStructureAndComposition() {
		return structureAndComposition;
	}

	public void setStructureAndComposition(String structureAndComposition) {
		this.structureAndComposition = structureAndComposition;
	}

	public String getApplyScope() {
		return applyScope;
	}

	public void setApplyScope(String applyScope) {
		this.applyScope = applyScope;
	}

	public String getExpectUse() {
		return expectUse;
	}

	public void setExpectUse(String expectUse) {
		this.expectUse = expectUse;
	}

	public String getMainPart() {
		return mainPart;
	}

	public void setMainPart(String mainPart) {
		this.mainPart = mainPart;
	}

	public String getProductRegsiterUrl() {
		return productRegsiterUrl;
	}

	public void setProductRegsiterUrl(String productRegsiterUrl) {
		this.productRegsiterUrl = productRegsiterUrl;
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
}