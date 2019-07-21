package com.wms.entity;


import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
public class DocAsnDoublec implements Serializable {
  private static final long serialVersionUID = 1L;






  private String doublecno;
  private String customerid;
  private String context1;
  private String context2;
  private String context3;
  private String context4;
  private String context5;
  private String context6;
  private String context7;
  private String context8;
  private String matchFlag;
  @Temporal(TemporalType.TIMESTAMP)
  private Date addtime;
  private String addwho;
  @Temporal(TemporalType.TIMESTAMP)
  private Date edittime;
  private String editwho;


  public String getDoublecno() {
    return doublecno;
  }

  public void setDoublecno(String doublecno) {
    this.doublecno = doublecno;
  }


  public String getCustomerid() {
    return customerid;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }


  public String getContext1() {
    return context1;
  }

  public void setContext1(String context1) {
    this.context1 = context1;
  }


  public String getContext2() {
    return context2;
  }

  public void setContext2(String context2) {
    this.context2 = context2;
  }


  public String getContext3() {
    return context3;
  }

  public void setContext3(String context3) {
    this.context3 = context3;
  }


  public String getContext4() {
    return context4;
  }

  public void setContext4(String context4) {
    this.context4 = context4;
  }


  public String getContext5() {
    return context5;
  }

  public void setContext5(String context5) {
    this.context5 = context5;
  }


  public String getContext6() {
    return context6;
  }

  public void setContext6(String context6) {
    this.context6 = context6;
  }


  public String getContext7() {
    return context7;
  }

  public void setContext7(String context7) {
    this.context7 = context7;
  }


  public String getContext8() {
    return context8;
  }

  public void setContext8(String context8) {
    this.context8 = context8;
  }


  public String getMatchFlag() {
    return matchFlag;
  }

  public void setMatchFlag(String matchFlag) {
    this.matchFlag = matchFlag;
  }



  public String getAddwho() {
    return addwho;
  }

  public void setAddwho(String addwho) {
    this.addwho = addwho;
  }

  public Date getAddtime() {
    return addtime;
  }

  public void setAddtime(Date addtime) {
    this.addtime = addtime;
  }

  public Date getEdittime() {
    return edittime;
  }

  public void setEdittime(Date edittime) {
    this.edittime = edittime;
  }

  public String getEditwho() {
    return editwho;
  }

  public void setEditwho(String editwho) {
    this.editwho = editwho;
  }

}