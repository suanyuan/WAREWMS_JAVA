package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;


@Data
@Entity
public class DocQcSearchExportForm implements Serializable {


	private String token;
	private String pano;     //上架单号
	private String qcno;

	private String qclineno;

	private String linestatus;

	private Double palineno;

	private String customerid;

	private String sku;

	private String lotnum;

	private Double paqty_Expected;

	private Double qcqty_Expected;
	private Double qcqty_ExpectedEach;

	private Double qcqty_Completed;
	private Double qcqty_CompletedEach;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String qcdescr;

	private String qcresult;

	private String filecontent;

	private String notes;

	private String addtime;

	private String addwho;

	private String edittime;

	private String editwho;

	private String packid;

	private String transactionid;

//inv_lot_att by lotnum

	private String lotatt01;  //生产日期
	private String lotatt02;  //效期
	private String lotatt03;  //入库日期
	private String lotatt04;  //生产批号
	private String lotatt05;  //序列号
	private String lotatt06;  //产品注册证
	private String lotatt07;  //灭菌批号
	private String lotatt08;  //供应商
	private String lotatt10;  //质量状态
	private String lotatt11;  //存储条件
	private String lotatt12;  //产品名称
	private String lotatt14;  //入库单号
	private String lotatt15;  //生产企业
//bas_sku
	private String descrc;  //规格型号
	private String reservedfield06;  //生产许可证号/备案号
//bas_package
	private Double qty1;  //转换率
//bas_customer
	private String shippershortname;  //货主简称
//导出查询时间
	private String lotatt03Start; //入库日期
	private String lotatt03End;     //入库日期




}
