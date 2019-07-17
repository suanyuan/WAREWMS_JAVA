package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class BasSkuHistory  implements Serializable {

	private String customerid;

	private String sku;

	private String hazardFlag;

	private String descrE;

	private String descrC;

	private String packid;

	private Double tare;

	private String activeFlag;

	private String lotid;

	private String cartongroup;

	private String putawayzone;

	private Double cube;

	private String putawaylocation;

	private Double grossweight;

	private Double netweight;

	private String cycleclass;

	private java.sql.Date lastcyclecount;

	private Double reorderqty;

	private Double price;

	private String shelflifeflag;

	private Long inboundlifedays;

	private String softallocationrule;

	private String allocationrule;

	private String shelflifetype;

	private Long outboundlifedays;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private String alternateSku4;

	private String alternateSku5;

	private String notes;

	private String putawayrule;

	private String alternateSku1;

	private String alternateSku2;

	private String alternateSku3;

	private String reservedfield05;

	private String skuGroup1;

	private String skuGroup2;

	private String skuGroup3;

	private String skuGroup4;

	private String skuGroup5;

	private String reservedfield01;

	private String reservedfield02;

	private String reservedfield03;

	private String reservedfield04;

	private String defaultreceivinguom;

	private String defaultshipmentuom;

	private String defaulthold;

	private String rotationid;

	private String copypackidtolotatt12;

	private Double qtymin;

	private Double qtymax;

	private Long shelflife;

	private String hscode;

	private String reservedfield06;

	private String reservedfield07;

	private String reservedfield08;

	private String reservedfield09;

	private String reservedfield10;

	private String reservedfield11;

	private String reservedfield12;

	private Double overrcvpercentage;

	private String replenishrule;

	private String specialmaintenance;

	private java.sql.Date lastmaintenancedate;

	private String firstop;

	private String medicaltype;

	private String approvalno;

	private String snAsnQty;

	private String snSoQty;

	private String invchgwithshipment;

	private String freightclass;

	private String tariffid;

	private String kitflag;

	private String reservecode;

	private String reportuom;

	private Double skulength;

	private Double skuwidth;

	private Double skuhigh;

	private Double qctime;

	private String qcrule;

	private java.sql.Date firstinbounddate;

	private String reservedfield13;

	private String reservedfield14;

	private String reservedfield15;

	private String skuGroup6;

	private String skuGroup7;

	private String skuGroup8;

	private String skuGroup9;

	private String medicinespecicalcontrol;

	private String serialnocatch;

	private String scanwhencasepicking;

	private String scanwhenpiecepicking;

	private String scanwhencheck;

	private String scanwhenreceive;

	private String scanwhenputaway;

	private Long shelflifealertdays;

	private String reservedfield17;

	private String reservedfield18;

	private String reservedfield16;

	private String chkScnUom;

	private String alternativePutawayzone1;

	private String alternativePutawayzone2;

	private String scanwhenpack;

	private String scanwhenmove;

	private String imageaddress;

	private String onestepallocation;

	private String orderbysql;

	private String secondserialnocatch;

	private String allowreceiving;

	private String allowshipment;

	private String inboundserialnoqtycontrol;

	private String outboundserialnoqtycontrol;

	private String defaultcartontype;

	private String breakcs;

	private String breakea;

	private String breakip;

	private String printmedicineqcreport;

	private String scanwhenqc;

	private String overreceiving;

	private String defaultsupplierid;

}
