package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class RptOrderPackingcartonByOrderNo implements Serializable {

    private String token;
    private String orderno;

    private int packlineno;

    private String traceid;

    private Integer cartonno;

    private String customerid;

    private String sku;

    private int qty;
    private double qtyEach;

    private String allocationdetailsid;

    private String lotnum;

    private String skudescrc;//规格型号

    private String description;//复核说明

    private String conclusion;//复核结论

    private String editwho;

    private String edittime;

    private String addwho;

    private String addtime;

    private String lotatt01;
    private String lotatt02;
    private String lotatt03;
    private String lotatt04;
    private String lotatt05;
    private String lotatt06;
    private String lotatt07;
    private String lotatt08;
    private String lotatt08Name;   //供应商名称
    private String lotatt09;
    private String lotatt10;
    private String lotatt11;
    private String lotatt12;
    private String lotatt13;
    private String lotatt14;
    private String lotatt15;
    private String lotatt16;
    private String lotatt17;
    private String lotatt18;

    //查询条件
    private String edittimeStart; //复核日期
    private String edittimeEnd;
    private String  packingflag;  //是否装箱

    //bas表
    private String shippershortname;//货主简称
    private String reservedfield06;//生产许可证号/备案号

    private double qty1; //换算率
    private String uom;


}