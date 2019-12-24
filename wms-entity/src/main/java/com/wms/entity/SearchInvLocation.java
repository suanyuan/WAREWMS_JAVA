package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class SearchInvLocation implements Serializable {


    private String token;

    private String enterpriseName;    //委托方企业名称
    private String supplierName;      //供应商
    @JSONField(format = "yyyy-MM-dd")
    private String lotatt03;          //入库日期
    private String lotatt14;          //入库单号
    private String lotatt12;          //产品名称
    private String descrc;           //规格/型号
    private String lotatt15;          //生产企业
    private String reservedfield06;          //生产企业许可证号/备案凭证号
    private String lotatt06;          //产品注册证号/备案凭证号
    private String lotatt04;          //生产批号
    private String lotatt05;         //序列号
    private String lotatt01;         //生产日期
    private String lotatt02;         //有效期/失效期
    private String lotatt01Andlotatt02;         //生产日期和有效期(或者失效期)
    private Double qty;              //件数
    private Double qtyeach;          //数量
    private String uom;               //单位
    private String locationid;        //库位
    private String lotatt11;           //储存条件
    private String lotatt10;           //质量状态
    private String notes;             //备注
    private Double qty1;             //换算率
    private String reservedfield09;//是否是医疗器械


    //库存信息统计查询
    private String lotatt01Start;         //生产日期
    private String lotatt01End;         //生产日期
    private String lotatt02Start;         //效期
    private String lotatt02End;         //效期
    private Integer inventoryage;         //库龄
    private String productLineName;         //产品线
    private String sku;         //产品代码


}