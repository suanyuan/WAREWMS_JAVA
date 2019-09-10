package com.wms.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.wms.entity.BasSku;
import lombok.Data;

import java.util.Date;

@Data
public class CouRequestDetailsVO {

	private String cycleCountno;
	private int cycleCountlineno;
	private String customerid;
	private String sku;
	private String locationid;
	private double qtyInv;
	private double qtyInvEach;
	private double qtyAct;
	private String lotatt04;
	private String lotatt05;
	private Date addtime;
	private String addwho;
	@JSONField(format="yyyy-MM-dd")
	private Date edittime;
	private String editwho;
	private String userdefined1;
	private String userdefined2;
	private String userdefined3;
//	双击查看明细
    private String reservedfield01;
	private String descre;
	private String descrc;
	private String productLineName;
	private double qty1; //换算率

	//PDA 盘点
    private String goodsName;
}