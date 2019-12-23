package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class SearchOutInvLocation implements Serializable {


    private String token;

    private String orderno;    //出库单号
    private String enterpriseName;    //委托方企业名称
    @JSONField(format = "yyyy-MM-dd")
    private String lotatt03;          //出库日期
    private String type;              //出库类型
    private String lotatt12;          //产品名称
    private String descrc;           //规格/型号
    private String lotatt15;          //生产企业
    private String reservedfield06;          //生产企业许可证号/备案凭证号
    private String lotatt06;          //产品注册证号/备案凭证号
    private String lotatt04;          //生产批号
    private String lotatt05;         //序列号
    private String lotatt01Andlotatt02;  //生产日期和有效期(或者失效期)
    private String lotatt01;  //生产日期
    private String lotatt02;  //有效期(或者失效期)
    private String lotatt10;           //质量状态
    private String lotatt11;           //储存条件
    private String uom;               //单位
    private Double qty;              //件数
    private Double qtyeach;          //数量
    private String consigneeID;       //收货客户名称
    private String caddress1;         //收货地址
    private String contact;           //联系人 待定
    private String ctel1;             //联系电话
    private String notes;             //备注
    private Double qty1;             //换算率
    private String addtime;             //制单时间
    private String addwho;             //制单人

    //查询条件
    private String outStartDate;           //出库日期开始
    private String outEndDate;           //出库日期结束
    private String activeFlag;  //是否合作
    private String reservedfield09;//是否是医疗器械
    private String sku;//产品代码
    private String locationid;//库位


}