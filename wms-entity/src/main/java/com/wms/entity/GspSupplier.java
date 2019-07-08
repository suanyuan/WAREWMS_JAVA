package com.wms.entity;


import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
public class GspSupplier implements Serializable {

  private static final long serialVersionUID = 1L;


  private String supplierId;
  private String enterpriseId;
  private String isCheck;
  private String operateType;
  private String createId;
  @Temporal(TemporalType.TIMESTAMP)
  private Date createDate;
  private String editId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date editDate;
  private String isUse;
  private String firstState;
  private String enterpriseNo;
  private String enterpriseName;
  private String shorthandName;
  private String enterpriseType;
  public GspSupplier() {
  }

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }


  public String getEnterpriseId() {
    return enterpriseId;
  }

  public void setEnterpriseId(String enterpriseId) {
    this.enterpriseId = enterpriseId;
  }


  public String getIsCheck() {
    return isCheck;
  }

  public void setIsCheck(String isCheck) {
    this.isCheck = isCheck;
  }


  public String getOperateType() {
    return operateType;
  }

  public void setOperateType(String operateType) {
    this.operateType = operateType;
  }


  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }


  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }


  public String getEditId() {
    return editId;
  }

  public void setEditId(String editId) {
    this.editId = editId;
  }


  public Date getEditDate() {
    return editDate;
  }

  public void setEditDate(Date editDate) {
    this.editDate = editDate;
  }


  public String getIsUse() {
    return isUse;
  }

  public void setIsUse(String isUse) {
    this.isUse = isUse;
  }

  public String getFirstState() {
    return firstState;
  }

  public void setFirstState(String firstState) {
    this.firstState = firstState;
  }

  public String getEnterpriseNo() {
    return enterpriseNo;
  }

  public void setEnterpriseNo(String enterpriseNo) {
    this.enterpriseNo = enterpriseNo;
  }

  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }

  public String getShorthandName() {
    return shorthandName;
  }

  public void setShorthandName(String shorthandName) {
    this.shorthandName = shorthandName;
  }

  public String getEnterpriseType() {
    return enterpriseType;
  }

  public void setEnterpriseType(String enterpriseType) {
    this.enterpriseType = enterpriseType;
  }
}
