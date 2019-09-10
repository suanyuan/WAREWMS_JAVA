package com.wms.query;

import lombok.Data;

@Data
public class DocPaDetailsQuery implements IQuery {

	private String pano;
	private String palineno;
	private String linestatus;
	private String asnno;
	private String asnlineno;
	private String customerid;
	private String sku;
	private String lotnum;
	private String asnqtyExpected;
	private String putwayqtyExpected;
	private String putwayqtyCompleted;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String notes;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String packid;
	private String transactionid;

	private int sumflag;//DocPaDetailMybatisDao.querySimilarDetail 区分 获取库位推荐 && 获取同批汇总件数

}