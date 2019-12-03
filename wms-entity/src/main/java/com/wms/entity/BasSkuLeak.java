package com.wms.entity;


import java.io.Serializable;
import java.util.Date;

public class BasSkuLeak implements Serializable {

  private long id;
  private String customerid;
  private String sku;
  private String standard;
  private String lotatt06;
  private String lotatt11;
  private double conversionRatio;
  private String unit;
  private String productline;
  private String supplier;
  private String userdefine1;
  private String userdefine2;
  private String userdefine3;
  private String userdefine4;
  private String userdefine5;
  private String addwho;
  private java.util.Date addtime;
  private String editwho;
  private java.util.Date edittime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCustomerid() {
    return customerid;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }


  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }


  public String getStandard() {
    return standard;
  }

  public void setStandard(String standard) {
    this.standard = standard;
  }


  public String getLotatt06() {
    return lotatt06;
  }

  public void setLotatt06(String lotatt06) {
    this.lotatt06 = lotatt06;
  }


  public String getLotatt11() {
    return lotatt11;
  }

  public void setLotatt11(String lotatt11) {
    this.lotatt11 = lotatt11;
  }


  public double getConversionRatio() {
    return conversionRatio;
  }

  public void setConversionRatio(double conversionRatio) {
    this.conversionRatio = conversionRatio;
  }


  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }


  public String getProductline() {
    return productline;
  }

  public void setProductline(String productline) {
    this.productline = productline;
  }


  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
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
