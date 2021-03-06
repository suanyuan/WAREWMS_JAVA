package com.wms.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GspFirstRecord {

  private String recordId;
  private String enterpriseName;
  private String recordNo;
  private String enterpriseId;
  private String residence;
  private String headName;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private java.util.Date  approveDate;
  private String registrationAuthority;
  private String bussinessScope;
  private String recordUrl;
  private String createId;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private java.util.Date  createDate;
  private String editId;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private java.util.Date  editDate;
  private String isUse;


  private String reservedfield1;   //生产场所
  private String reservedfield2;   //


  public Date getApproveDate() {
    return approveDate;
  }

  public void setApproveDate(Date approveDate) {
    this.approveDate = approveDate;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getEditDate() {
    return editDate;
  }

  public void setEditDate(Date editDate) {
    this.editDate = editDate;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }


  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }


  public String getRecordNo() {
    return recordNo;
  }

  public void setRecordNo(String recordNo) {
    this.recordNo = recordNo;
  }


  public String getEnterpriseId() {
    return enterpriseId;
  }

  public void setEnterpriseId(String enterpriseId) {
    this.enterpriseId = enterpriseId;
  }


  public String getResidence() {
    return residence;
  }

  public void setResidence(String residence) {
    this.residence = residence;
  }


  public String getHeadName() {
    return headName;
  }

  public void setHeadName(String headName) {
    this.headName = headName;
  }




  public String getRegistrationAuthority() {
    return registrationAuthority;
  }

  public void setRegistrationAuthority(String registrationAuthority) {
    this.registrationAuthority = registrationAuthority;
  }


  public String getBussinessScope() {
    return bussinessScope;
  }

  public void setBussinessScope(String bussinessScope) {
    this.bussinessScope = bussinessScope;
  }


  public String getRecordUrl() {
    return recordUrl;
  }

  public void setRecordUrl(String recordUrl) {
    this.recordUrl = recordUrl;
  }


  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }





  public String getEditId() {
    return editId;
  }

  public void setEditId(String editId) {
    this.editId = editId;
  }





  public String getIsUse() {
    return isUse;
  }

  public void setIsUse(String isUse) {
    this.isUse = isUse;
  }


  public String getReservedfield1() {
    return reservedfield1;
  }

  public void setReservedfield1(String reservedfield1) {
    this.reservedfield1 = reservedfield1;
  }


  public String getReservedfield2() {
    return reservedfield2;
  }

  public void setReservedfield2(String reservedfield2) {
    this.reservedfield2 = reservedfield2;
  }

}
