package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class BasSerialNum implements Serializable {
    private Integer id; //序号  主键
    private String serialNum; //lotatt05 序列号
    private String batchNum; //lotatt04 批号
    private String materialNum; //sku 产品代码
    private String expireDate; //lotatt02 效期
    private String productDate; //lotatt01 生产日期
    private String batchFlag; //不知道是啥
    private String outFlag; //是否出库0,1
    private String uom; //包装单位
    private String purchaseOrder; //？销售单号
    private String packageNum;
    private String deliveryNum;
    private String userdefine1;  //入库时间
    private String userdefine2;  //出库时间
    private String userdefine3;  //出库单号
    private String userdefine4;
    private String userdefine5;
    private String addwho;
    private String addtime;
    private String editwho;
    private String edittime;

}
