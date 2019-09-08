package com.wms.query;

import lombok.Data;

@Data
public class CouRequestDetailsQuery{

	private String cycleCountno;
	private String cycleCountlineno;
	private String customerid;
	private String sku;
	private String locationid;
	private String qtyInv;
	private String qtyAct;
	private String lotatt04;
	private String lotatt05;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String userdefined1;
	private String userdefined2;
	private String userdefined3;

//盘点任务查询
	private String productLineName;//产品线
	private String reservedfield01;//产品名称

    //pda 扫码查询
    private String GTIN;
    private String otherCode;
}