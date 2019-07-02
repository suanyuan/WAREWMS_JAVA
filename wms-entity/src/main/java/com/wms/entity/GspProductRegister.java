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

}
