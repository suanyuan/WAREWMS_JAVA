package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class SearchEnterInvLocation implements Serializable {


    private String token;

    private String enterpriseName;    //委托方企业名称
    @JSONField(format = "yyyy-MM-dd")
    private String lotatt03;          //入库日期
    private String type;              //入库类型
    private String lotatt12;          //产品名称
    private String descrc;           //规格/型号
    private String lotatt14;          //入库单号
    private String lotatt15;          //生产企业
    private String lotatt06;          //产品注册证号/备案凭证号
    private String lotatt04;          //生产批号
    private String lotatt05;         //序列号
    private String lotatt01Andlotatt02;         //生产日期和有效期(或者失效期)
    private Double qty;              //件数
    private Double qtyeach;          //数量
    private String uom;               //单位
    private String lotatt11;           //储存条件
    private String locationid;        //库位
    private String lotatt10;           //质量状态
    private String notes;             //备注
    private Double qty1;             //换算率
    private String addtime;             //制单时间
    private String addwho;             //制单人


    //查询条件
    private String lotatt03StartDate;           //入库日期开始
    private String lotatt03EndDate;           //入库日期结束
    private String activeFlag;  //是否合作
    private String reservedfield09;//是否是医疗器械

}