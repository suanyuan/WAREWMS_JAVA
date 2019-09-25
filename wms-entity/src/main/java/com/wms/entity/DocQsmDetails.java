package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class DocQsmDetails {

  private String qcudocno;
  private String qcustatus;
  private String lotatt14;
  private String customerid;
  private Date lotatt03;
  private String lotatt08;
  private String businesstype;
  private String sku;
  private String lotatt12;
  private String lotatt06;
  private String descrc;
  private String lotatt04;
  private String lotatt05;
  private String lotatt07;
  private Date lotatt01;
  private Date lotatt02;
  private Long locqty;    //原库存件数
  private Long qty;       //目标不合格件数
  private Double qtyeach;   //目标不合格数量
  private String lotatt15;
  private String reservedfield06;
  private String lotatt10;
  private String locationid;
  private Date finddate;
  private String failedDescription;
  private String customeridTreatment;
  private Date treatmentDate;
  private String remarks;
  private Date recordingDate;
  private String recordingPeople;
  private String lotnum;
  private Double qty1;//换算率


}
