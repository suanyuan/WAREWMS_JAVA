package com.wms.entity.order;

import com.wms.entity.BasCodes;
import com.wms.entity.BasCustomer;
import com.wms.entity.BasSku;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class OrderDetailsForNormal implements Serializable {

    private String result;

    private String orderno;//出库单号

    private Double orderlineno;//出库明细行号

    private String customerid;

    private String sku;

    private String linestatus;

    private String lotnum;

    private String lotatt01 = "";

    private String lotatt02 = "";

    private String lotatt03 = "";

    private String lotatt04 = "";

    private String lotatt05 = "";

    private String lotatt06 = "";

    private String lotatt07 = "";

    private String lotatt08 = "";

    private String lotatt09 = "";

    private String lotatt10 = "";

    private String lotatt11 = "";

    private String lotatt12 = "";

    private String lotatt13 = "";

    private String lotatt14 = "";

    private String lotatt15 = "";

    private String lotatt16 = "";

    private String lotatt17 = "";

    private String lotatt18 = "";

    private String pickzone;//拣货区

    private String location;//拣货库位

    private String traceid;//跟踪id

    private Double qtyordered;//订货数量

    private Double qtysoftallocated;//预分配数量 Deprecated

    private Double qtyallocated;//分配数量

    private Double qtypicked;//拣货数量

    private Double qtyshipped;//发运数量

    private String uom;//单位

    private String packid;//包装单位

    private String softallocationrule;//软分配规则

    private String allocationrule;//分配规则

    private String userdefine1;

    private String userdefine2;

    private String userdefine3;

    private String userdefine4;

    private String userdefine5;

    private String notes;

    private Double qtyorderedEach;//订货件数

    private Double qtysoftallocatedEach;//软分配件数 Deprecated

    private Double qtyallocatedEach;//分配件数

    private Double qtypickedEach;//拣货件数

    private Double qtyshippedEach;//发运件数

    private java.util.Date addtime;

    private String addwho;

    private java.util.Date edittime;

    private String editwho;

    private String rotationid;

    private Double netweight;

    private Double grossweight;

    private Double cubic;

    private Double price;

    private String alternativesku;

    private Double kitreferenceno;

    private String dEdi01;

    private String dEdi02;

    private String dEdi03;

    private String dEdi04;

    private String dEdi05;

    private String dEdi06;

    private String dEdi07;

    private String dEdi08;

    private Double dEdi09;

    private Double dEdi10;

    private String orderlinereferenceno;

    private String dEdi11;

    private String dEdi12;

    private String dEdi13;

    private String dEdi14;

    private String dEdi15;

    private String dEdi16;

    private String dEdi17;

    private String dEdi18;

    private String dEdi19;

    private String dEdi20;

    private String kitsku;

    private String erpcancelflag;

    private String userdefine6;

    private String zonegroup;

    private String locgroup1;

    private String locgroup2;

    private String comminglesku;

    private String onestepallocation;

    private String orderlotcontrol;

    private String fullcaselotcontrol;

    private String piecelotcontrol;

    private Double referencelineno;

    private String salesorderno;

    private String salesorderlineno;

    private Double qtyreleased;

    private String freegift;

    private BasCodes basCodes;

    private BasCustomer basCustomer;

    private BasSku basSku;

    private String skuName;
    private Double qty1;


//    导出带出字段
    private String rededi04;//样品/投诉单号
    private String descrc;   //规格/型号
    private String doublec;  //双证
    private String card;     //附卡类别
    private String report;   //质量合格证
    private double qtyorderedSum;   //订货件数总和
    private double qtyorderedEachSum;   //订货数量总和
    private double qty; //件数
    private double qtyeach;  //数量
    private String alocation;
    private Integer index;  //序号
    private String codename;//单位
    private String reservedfield02;//商品描述
    private String reservedfield01;//商品名称
    private String reservedfield06;//生产许可证/备案号
    private String descr;//换算率
    private String skugroup5;//运输条件
    private String skugroup4;//存储条件
    private String nums;//明细数
    private String name;//产品线名

	public OrderDetailsForNormal() {
		
	}
}