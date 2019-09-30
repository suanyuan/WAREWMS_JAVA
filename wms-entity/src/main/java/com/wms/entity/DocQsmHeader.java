package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
public class DocQsmHeader {

  private String qcudocno;
  private String status;
  private String warehouseid;
  private String customerid;
  private String notes;        //备注
  private String userdefine1;
  private String userdefine2;
  private String userdefine3;
  private String userdefine4;
  private String userdefine5;
  private Date addtime;
  private String addwho;
  private Date edittime;
  private String editwho;
  private String hedi01;        //质量变更单号
  private String hedi02;        //参考编号
  private String hedi03;
  private String hedi04;
  private String hedi05;
}
