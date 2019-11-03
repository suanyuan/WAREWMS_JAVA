package com.wms.query;

import lombok.Data;

@Data
public class DocMtDetailsQuery implements IQuery {

	private String mtno;
	private String mtlineno;
	private String linestatus;
	private String customerid;
	private String sku;
	private String inventoryage;
	private String locationid;
	private String lotnum;
	private String mtqtyExpected;
	private String mtqtyEachExpected;
	private String mtqtyCompleted;
	private String mtqtyEachCompleted;
	private String uom;
	private String checkFlag;
	private String conclusion;
	private String conversedate;
	private String conversewho;
	private String remark;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;

//	查询
    private String fromdate;
	private String todate;
	private String lotatt12;
	private String descrc;   //规格
	private String productLineName;   //产品线


// Pda 扫码获取库存养护明细 mtno + lotatt04 + lotatt05 + gtin
    private String lotatt04;//生产批号
    private String lotatt05;//序列号
    private String GTIN;//gtin
    private String otherCode;
    private String version;
}