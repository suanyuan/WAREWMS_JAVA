package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class DocMtDetails {

  private String mtno;
  private long mtlineno;
  private String linestatus;
  private String customerid;
  private String sku;
  private String inventoryage;     //库龄
  private String locationid;       //库位
  private String lotnum;           //批号
  private double mtqtyExpected;    //预期养护件数
  private double mtqtyEachExpected;//预期养护数量
  private double mtqtyCompleted;
  private double mtqtyEachCompleted;
  private String uom;                  //单位
  private String checkFlag;              //检查内容（外观、包装等）
  private String conclusion;          //养护结论
  private Date conversedate; //养护日期
  private String conversewho;         //养护人
  private String remark;              //备注
  private Date addtime;
  private String addwho;
  private Date edittime;
  private String editwho;
//养护
  private InvLotAtt invLotAtt;
  private Date conversedatetest; //预期养护日期

//inv_lot_att by lotnum
  private String lotatt12;  //产品名称
  private String lotatt07;  //灭菌批号
  private String lotatt15;  //生产厂家
  private String lotatt01;  //生产日期
  private String lotatt06;  //产品注册证
  private String lotatt11;  //存储条件
}
