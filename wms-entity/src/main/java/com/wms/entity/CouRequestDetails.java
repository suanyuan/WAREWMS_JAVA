package com.wms.entity;


import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
public class CouRequestDetails implements Serializable {

  private String cycleCountno;
  private int cycleCountlineno;
  private String customerid;
  private String sku;
  private String locationid;
  private double qtyInv;
  private double qtyInvEach;
  private double qtyAct;
  private String lotatt04;
  private String lotatt05;
  private Date addtime;
  private String addwho;
  private Date edittime;       //盘点时间
  private String editwho;      //盘点人
  private String userdefined1; //差异
  private String userdefined2; //备注
  private String userdefined3; //复核人
  //双击查看明细
  private String reservedfield01;
  private String descre;
  private String descrc;
  private String productLineName;
  private double qty1; //换算率





}
