package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class RptSalesSo implements Serializable {

    private String token;

    private String lotatt03StartDate;            //开始日期
    private String lotatt03EndDate;            //结束日期
    private String consigneeid;            //收货单位
    private Integer serialNumber ;          //序号
    private String choose ;                //选择
    private String warehouseid;              //仓库编码
    private String sourceOrder;            //来源订单号
    private String soOrderNum;             //发货单号码
    private String soOrderNo;              //出库单号
    private String soDate;                //出库日期
    private String soType;                //出库类别
    private String warehouse;              //仓库
    private String customerid;              //客户名称
    private String sku;                  //存货编码
    private String lotatt12;             //存货名称
    private String descrc;              //规格型号
    private Double qtyeach;             //数量
    private Double qty;                 //件数
    private String uom;                 //主计量单位
    private String lotatt04;            //批号
    private String lotatt02;            //失效日期
    private String addwho;              //制单人
    private String reviewWho;          //审核人
    private String trackingNumber;     //快递单号码

    private Double qty1;             //换算率

}