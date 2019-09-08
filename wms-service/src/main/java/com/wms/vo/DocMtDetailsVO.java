package com.wms.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.wms.entity.*;
import lombok.Data;

@Data
public class DocMtDetailsVO {

	private String mtno;
	private long mtlineno;
	private String linestatus;
	private String customerid;
	private String sku;
	private String inventoryage;
	private String locationid;
	private String lotnum;
	private double mtqtyExpected;
	private double mtqtyEachExpected;
	private double mtqtyCompleted;
	private double mtqtyEachCompleted;
	private String uom;
	private String checkFlag;
	private String conclusion;
	@JSONField(format = "yyyy-MM-dd")
	private java.util.Date conversedate;
	private String conversewho;
	private String remark;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;

	private BasCustomer basCustomer;

	private InvLotAtt invLotAtt;

	private BasSku basSku;

	private BasPackage basPackage;

	private ProductLine productLine;


//inv_lot_att by lotnum
	private String lotatt01;  //生产日期
	private String lotatt02;  //效期
	private String lotatt03;  //入库日期
	private String lotatt04;  //生产批号
	private String lotatt05;  //序列号
	private String lotatt06;  //产品注册证
	private String lotatt07;  //灭菌批号
	private String lotatt10;  //质量状态
	private String lotatt11;  //存储条件
	private String lotatt12;  //产品名称
	private String lotatt14;  //入库单号
	private String lotatt15;  //生产企业
// bas_sku
	private String descrc;//规格
	private String descre;//型号
	private String productLineName;//产品线
	private String reservedfield06;//生产许可证号/备案号
}