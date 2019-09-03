package com.wms.entity;


import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
public class CouRequestDetails implements Serializable {

  private String cycleCountno;
  private String cycleCountlineno;
  private String customerid;
  private String sku;
  private String locationid;
  private double qtyInv;
  private double qtyAct;
  private String lotatt04;
  private String lotatt05;
  private Date addtime;
  private String addwho;
  private Date edittime;
  private String editwho;
  private String userdefined1;
  private String userdefined2;
  private String userdefined3;



}
