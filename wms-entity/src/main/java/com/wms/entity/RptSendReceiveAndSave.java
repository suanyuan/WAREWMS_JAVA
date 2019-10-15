package com.wms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class RptSendReceiveAndSave implements Serializable {


    private String token;

    private String warehouse;            //仓库名称
    private String sku;                  //存货编码
    private String lotatt12;             //存货名称
    private String descrc;               //规格型号
    private String uom;                  //主计量单位
    private String skuCategoryName;      //存货分类名称
    private Double startResultQty;       //期初结存件数
    private Double asnqtySum;            //总计_入库件数
    private Double soqtySum;             //总计_出库件数
    private Double endResultQty;         //期末结存件数
    private Double qty1;                 //换算率

}