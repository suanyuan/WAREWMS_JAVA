package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class CustomerProduct implements Serializable {


	private String token;

	private String enterpriseName;  //委托方企业名称
//	private String licenseNumber;  //营业执照编号
//	private String residence;  //住所
//	private String juridicalPerson;  //法定代表人
//	private String headName;  //企业负责人
//	private String businessResidence;  //经营场所
//	private String warehouseAddress;  //库房地址
//	private String businessScope;  //经营范围
//	private String licenseNo;  //经营许可证号
//	@JSONField(format="yyyy-MM-dd")
//	private String licenseExpiryDate;  //许可证效期
//	private String registrationAuthorityL;  //发证机关
//	private String recordNo;  //备案凭证号
//	private String registrationAuthorityR;  //发证机关
//	private String clientStartDate;  //开始委托时间
//	private String clientEndDate;  //停止委托时间
//	private String clientTerm;  //委托期限
//	private String isChineseLabel;  //是否委托加贴中文标签
//	private String clientContent;  //委托业务范围
//	private String activeFlag;  //是否激活
	private String productName; //产品名称
	private String specsName; //规格
	private String productModel; //型号

	private String productRegisterNo; //产品注册证
	@JSONField(format="yyyy-MM-dd")
	private String productRegisterExpiryDate; //有效期
	@JSONField(format="yyyy-MM-dd")
	private java.lang.String approveDate;//批准日期
	private String enterpriseId; //生产企业

	private String enterpriseSc;
	private String licenseOrRecordNo;//生产许可证号/备案号
	private String unit; 			  //单位
	private String storageCondition; //储存条件


    private String activeFlag; //是否激活




}