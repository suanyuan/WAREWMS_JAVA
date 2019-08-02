package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspProductRegister  implements Serializable {

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

	private String enterpriseId;
	private String enterpriseName;//附加

	private String productionAddress;
	private String transportConditionMain;//运输条件
	private String approvalDepartment;//审核部门
	private String productRegsiterUrl;//附件路径
	private String structureAndComposition;//结构及组成
	private String agentAddress;//代理人住所
	private String licenseNo;//生产许可证号
	private String recordNo;//备案号


}
