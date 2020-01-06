package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class InvLotLocId  implements Serializable {

	private String lotnum;

	private String locationid;

	private String traceid;

	private String customerid;

	private String sku;

	private Double qty;   //库存件数

	private Double qtyallocated;//分配件数

	private Double qtyrpin;

	private Double qtyrpout;

	private Double qtymvin;

	private Double qtymvout;

	private Double qtyonhold;
	private Double qtyavailed;//可用件数

	private Date addtime;

	private String addwho;

	private Date edittime;

	private String editwho;

	private Double netweight;

	private Double grossweight;

	private Double cubic;

	private Double price;

	private int onholdlocker;

	private String lpn;

	private Double qtypa;


	//养护计划
	@JSONField(format = "yyyy-MM-dd")
	private Date lotatt03; //入库时间
	@JSONField(format = "yyyy-MM-dd")
	private Date lotatt03test; //预期养护时间
	private String reservedfield10; //养护周期
	private String packuom1; //单位
	private Double qty1; //转换率
	private Date fromDate;//回传到页面的时间
	private Date toDate;
	//距离天数
	private String remainDay;


	private InvLotAtt invLotAtt;
	private BasSku basSku;

	//盘点任务
	private String lotatt05;
	private String lotatt04;
	private String productLineName;//产品线


    //双证是否匹配
    private boolean doublecflag;

   //质量状态管理
    private String lotatt14;  //入库单号
    private String lotatt15;
    private String lotatt08;
    private String lotatt12;
    private String lotatt06;
    private String lotatt07;
    private String lotatt01;
    private String lotatt02;
    private String lotatt10;
    private String descrc;
    private String reservedfield06;

}
