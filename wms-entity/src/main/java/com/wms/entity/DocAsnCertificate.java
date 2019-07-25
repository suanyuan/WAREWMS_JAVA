package com.wms.entity;


import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
@Entity
public class DocAsnCertificate implements Serializable {
  private static final long serialVersionUID = 1L;


  private String customerid;
  private String sku;
  private String lotatt04;
  @Temporal(TemporalType.TIMESTAMP)
  private Date addtime;
  private String addwho;
  @Temporal(TemporalType.TIMESTAMP)
  private Date edittime;
  private String editwho;
  private String certificateContext;
  private String isUse;

  public String getIsUse() {
    return isUse;
  }

  public void setIsUse(String isUse) {
    this.isUse = isUse;
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


  public String getLotatt04() {
    return lotatt04;
  }

  public void setLotatt04(String lotatt04) {
    this.lotatt04 = lotatt04;
  }



  public String getAddwho() {
    return addwho;
  }

  public void setAddwho(String addwho) {
    this.addwho = addwho;
  }


  public String getEditwho() {
    return editwho;
  }

  public void setEditwho(String editwho) {
    this.editwho = editwho;
  }


  public String getCertificateContext() {
    return certificateContext;
  }

  public void setCertificateContext(String certificateContext) {
    this.certificateContext = certificateContext;
  }

}
