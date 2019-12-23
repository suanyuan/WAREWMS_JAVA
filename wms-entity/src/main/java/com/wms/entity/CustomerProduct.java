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
	private String productName; //产品名称
	private String specsName; //规格
	private String productModel; //型号

	private String productRegisterNo; //产品注册证
	@JSONField(format="yyyy-MM-dd")
	private String productRegisterExpiryDate; //有效期
	@JSONField(format="yyyy-MM-dd")
	private String approveDate;//批准日期
	private String enterpriseId; //生产企业

	private String enterpriseSc;
	private String licenseOrRecordNo;//生产许可证号/备案号
	private String unit; 			  //单位
	private String storageCondition; //储存条件


    private String activeFlag; //是否激活
	private String reservedfield09;//是否是医疗器械

	private String productCode;


}