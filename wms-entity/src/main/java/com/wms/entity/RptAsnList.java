package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class RptAsnList implements Serializable {


    private String token;

    private String documentNo ;          //单据号
    @JSONField(format = "yyyy-MM-dd")
    private String asndate;            //日期
    private String lotatt03StartDate;            //开始日期
    private String lotatt03EndDate;            //结束日期
    private String lotatt05;            //序列号
    private String documentType;         //单据类型
    private String warehouse;            //仓库
    private String customerid;           //供货单位
    private String sku;                  //存货编码
    private String lotatt12;             //存货名称
    private String descrc;              //规格型号
    private Double asnqtyeach;          //入库数量
    private Double asnqty;               //入库件数
    private Double soqtyeach;           //出库数量
    private Double soqty;               //出库件数
    private String uom;               //主计量单位
    private String lotatt04;               //批号
    private String purchaseOrderNumber;   //采购订单号
    private String notes;             //备注
    private Double planPrice;          //计划价
    private Double qty1;             //换算率

}