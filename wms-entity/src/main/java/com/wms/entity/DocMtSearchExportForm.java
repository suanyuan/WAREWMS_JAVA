package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class DocMtSearchExportForm {


  private String token;
  private String mtno;
  private long mtlineno;
  private String linestatus;
  private String customerid;
  private String sku;
  private String inventoryage;     //库龄
  private String locationid;       //库位
  private String lotnum;           //批号
  private double mtqty_Expected;    //预期养护件数
  private double mtqty_Each_Expected;//预期养护数量
  private double mtqty_Completed;
  private double mtqty_Each_Completed;
  private String uom;                  //单位
  private String check_Flag;              //检查内容（外观、包装等）
  private String conclusion;          //养护结论
  private String conversedate; //养护日期
  private String conversewho;         //养护人
  private String remark;              //备注
  private String addtime;
  private String addwho;
  private String edittime;
  private String editwho;
//养护
  private InvLotAtt invLotAtt;
  private Date conversedatetest; //预期养护日期

//inv_lot_att by lotnum
private String lotatt01;  //生产日期
  private String lotatt02;  //效期
  private String lotatt03;  //入库日期
  private String lotatt04;  //生产批号
  private String lotatt05;  //序列号
  private String lotatt06;  //产品注册证
  private String lotatt07;  //灭菌批号
  private String lotatt10;  //质量状态
  private String lotatt11;  //存储条件
  private String lotatt12;  //产品名称
  private String lotatt14;  //入库单号
  private String lotatt15;  //生产企业
// bas_sku
  private String descrc;//规格
  private String descre;//型号
  private String productLineName;//产品线
//bas_customer
  private String reservedfield06;//生产许可证号/备案号


}
