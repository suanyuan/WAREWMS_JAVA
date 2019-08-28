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

	private Double qty;

	private Double qtyallocated;

	private Double qtyrpin;

	private Double qtyrpout;

	private Double qtymvin;

	private Double qtymvout;

	private Double qtyonhold;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private Double netweight;

	private Double grossweight;

	private Double cubic;

	private Double price;

	private Long onholdlocker;

	private String lpn;

	private Double qtypa;
	@JSONField(format = "yyyy-MM-dd")
	private Date lotatt03; //入库时间
	@JSONField(format = "yyyy-MM-dd")
	private Date lotatt03test; //预期养护时间
	private String reservedfield10; //养护周期
	private String packuom1; //单位
	private Double qty1; //转换率
	private Date fromDate;//回传到页面的时间
	private Date toDate;




}
