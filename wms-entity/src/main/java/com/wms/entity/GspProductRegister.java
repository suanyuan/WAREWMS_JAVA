package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class GspProductRegister  implements Serializable {

	/**
	 * 根据批准日期排序
	 * gspProductRegisterMybatisDao.queryByNoAndOrderBy
	 */
	public final static String ORDERBY_APPROVE_DATE_ASC = "approve_date asc";

	/**
	 * 根据产品失效期排序
	 * gspProductRegisterMybatisDao.queryByNoAndOrderBy
	 */
	public final static String ORDERBY_EXPIRY_DATE_DESC = "product_register_expiry_date desc";

	/**
	 * 根据批准日期排序
	 * gspProductRegisterMybatisDao.queryBysku
	 */
	public final static String ORDERBY_APPROVE_DATE_ASC_SKU = "t1.approve_date asc";

	/**
	 * 根据产品失效期排序
	 * gspProductRegisterMybatisDao.queryBysku
	 */
	public final static String ORDERBY_EXPIRY_DATE_DESC_SKU = "t1.product_register_expiry_date desc";


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
	private String approveDateDc;

	private java.util.Date productRegisterExpiryDate;
	private String productRegisterExpiryDateDc;

	private String productRegisterVersion;
	private String productRegisterVersionName;

	private String version;

	private String checkerId;

	private java.util.Date checkDate;
	private String checkDateDc;
	private String createId;

	private java.util.Date createDate;
	private String createDateDc;
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
	private String licenseOrRecordNol;//生产许可证号/备案号

}
