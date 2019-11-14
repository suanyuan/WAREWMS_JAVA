package com.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
public class Remind implements Serializable {

  private static final long serialVersionUID = 1L;

  private String remindModel;
  private String remindContent;
  private String remindNum;


  @Transient
  private int hashCode = Integer.MIN_VALUE;

  public String getRemindModel() {
    return remindModel;
  }

  public void setRemindModel(String remindModel) {
    this.remindModel = remindModel;
  }

  public String getRemindContent() {
    return remindContent;
  }

  public void setRemindContent(String remindContent) {
    this.remindContent = remindContent;
  }

  public String getRemindNum() {
    return remindNum;
  }

  public void setRemindNum(String remindNum) {
    this.remindNum = remindNum;
  }
}
