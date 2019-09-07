package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocQcDetails  implements Serializable {

	private String pano;     //上架单号
	private String qcno;

	private String qclineno;

	private String linestatus;

	private Double palineno;

	private String customerid;

	private String sku;

	private String lotnum;

	private Double paqtyExpected;

	private Double qcqtyExpected;
	private Double qcqtyExpectedEach;

	private Double qcqtyCompleted;
	private Double qcqtyCompletedEach;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String qcdescr;

	private String qcresult;

	private String filecontent;

	private String notes;

	private java.util.Date addtime;

	private String addwho;

	private java.util.Date edittime;

	private String editwho;

	private String packid;

	private String transactionid;

//inv_lot_att by lotnum

	private String lotatt01;  //生产日期
	private String lotatt02;  //效期
	private String lotatt04;  //生产批号
	private String lotatt05;  //序列号
	private String lotatt06;  //产品注册证
	private String lotatt07;  //灭菌批号
	private String lotatt08;  //供应商
	private String lotatt10;  //质量状态
	private String lotatt11;  //存储条件
	private String lotatt12;  //产品名称
	private String lotatt15;  //生产企业
//bas_sku
	private String descrc;  //规格型号
	private String reservedfield06;  //生产许可证号/备案号
//bas_package
	private Double qty1;  //转换率
//bas_customer
	private String shippershortname;  //货主简称

}
