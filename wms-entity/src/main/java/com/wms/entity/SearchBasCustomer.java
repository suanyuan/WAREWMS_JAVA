package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class SearchBasCustomer implements Serializable {


	private String token;

	private String enterpriseName;  //委托方企业名称
	private String licenseNumber;  //营业执照编号
	private String socialCreditCode;  //统一社会信用代码
	private String residence;  //住所
	private String juridicalPerson;  //法定代表人
	private String headName;  //企业负责人
	private String businessResidence;  //经营场所
	private String warehouseAddress;  //库房地址
	private String businessScope;  //经营范围
	private String licenseNo;  //经营许可证号
	private String licenseExpiryDate;  //许可证效期
	private String registrationAuthorityL;  //发证机关
	private String recordNo;  //备案凭证号
	private String registrationAuthorityR;  //发证机关
	private String clientStartDate;  //开始委托时间
	private String clientEndDate;  //停止委托时间
	private String clientTerm;  //委托期限
	private String isChineseLabel;  //是否委托加贴中文标签
	private String clientContent;  //委托业务范围
	private String activeFlag;  //是否合作

	private String productCode;	//产品代码



}