package com.wms.entity;


import com.wms.utils.serialzer.JsonDatetimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

public class UserStatisticsPerformance implements Serializable {

  private long id;
  private String userId;
  @JsonSerialize(using = JsonDatetimeSerializer.class)
  private String perfDate;  //效绩统计时间
  private long perfPick;   //拣货效绩
  private long perfRecheck;//复核绩效
  private long perfPa;    //上架效绩
  private long perfQc;    //验收效绩
  private long perfOrder; //订单效绩
  private String userdefine1;
  private String userdefine2;
  private String userdefine3;
  private String userdefine4;
  private String userdefine5;

  public String getPerfDate() {
    return perfDate;
  }

  public void setPerfDate(String perfDate) {
    this.perfDate = perfDate;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }




  public long getPerfPick() {
    return perfPick;
  }

  public void setPerfPick(long perfPick) {
    this.perfPick = perfPick;
  }


  public long getPerfRecheck() {
    return perfRecheck;
  }

  public void setPerfRecheck(long perfRecheck) {
    this.perfRecheck = perfRecheck;
  }


  public long getPerfPa() {
    return perfPa;
  }

  public void setPerfPa(long perfPa) {
    this.perfPa = perfPa;
  }


  public long getPerfQc() {
    return perfQc;
  }

  public void setPerfQc(long perfQc) {
    this.perfQc = perfQc;
  }


  public long getPerfOrder() {
    return perfOrder;
  }

  public void setPerfOrder(long perfOrder) {
    this.perfOrder = perfOrder;
  }


  public String getUserdefine1() {
    return userdefine1;
  }

  public void setUserdefine1(String userdefine1) {
    this.userdefine1 = userdefine1;
  }


  public String getUserdefine2() {
    return userdefine2;
  }

  public void setUserdefine2(String userdefine2) {
    this.userdefine2 = userdefine2;
  }


  public String getUserdefine3() {
    return userdefine3;
  }

  public void setUserdefine3(String userdefine3) {
    this.userdefine3 = userdefine3;
  }


  public String getUserdefine4() {
    return userdefine4;
  }

  public void setUserdefine4(String userdefine4) {
    this.userdefine4 = userdefine4;
  }


  public String getUserdefine5() {
    return userdefine5;
  }

  public void setUserdefine5(String userdefine5) {
    this.userdefine5 = userdefine5;
  }

}
