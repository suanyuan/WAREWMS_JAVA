package com.wms.entity;


import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class CouRequestHeader implements Serializable {

  private String cycleCountno;
  private String status;
  private String fuzzyc;
  private Date addtime;
  private String addwho;
  private Date starttime;
  private Date endtime;
  private String notes;
  private String userdefine1;
  private String userdefine2;
  private String userdefine3;
  private String userdefine4;
  private String userdefine5;




}
