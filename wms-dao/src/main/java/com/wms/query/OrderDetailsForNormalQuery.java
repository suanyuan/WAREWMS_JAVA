package com.wms.query;

import lombok.Data;

@Data
public class OrderDetailsForNormalQuery implements IQuery {

	private String orderNo;
	private int orderLineNo;
	private String customerId;
	private String customerShortName;
	private String lineStatus;
	private String lineStatusName;
	private String sku;
	private String skuName;
	private String skuEnglishName;
	private java.math.BigDecimal qtyordered;
	private java.math.BigDecimal qtyorderedEach;
	private java.math.BigDecimal qtyallocated;
	private java.math.BigDecimal qtyallocatedEach;
	private java.math.BigDecimal qtyshipped;
	private java.math.BigDecimal qtyshippedEach;
	private java.math.BigDecimal totalCubic;
	private java.math.BigDecimal totalGrossWeight;
	private java.math.BigDecimal totalPrice;
	private String pickZone;
	private String locationId;
	private String lotnum;
	private String lotatt01;
	private String lotatt02;
	private String lotatt03;
	private String lotatt04;
    private String lotatt05;//序列号
    private String lotatt06;//产品注册证
    private String lotatt07;//灭菌批号
    private String lotatt08;//供应商
    private String lotatt09;//样品属性
    private String lotatt10;//质量状态
    private String lotatt11;//储存条件
    private String lotatt12;//产品名称
    private String lotatt13;//双证
    private String lotatt14;//入库单号
    private String lotatt15;//生产厂家
    private String lotatt16;
    private String lotatt17;
    private String lotatt18;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private String warehouseId;
	private String allocationDetailsId;
	private String userId;
	private String result;
}