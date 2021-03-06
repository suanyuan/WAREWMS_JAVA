package com.wms.entity;


import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


@Entity
public class GspProductRegisterSpecs implements Serializable {
  private static final long serialVersionUID = 1L;


  @Transient
  private int hashCode = Integer.MIN_VALUE;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public int getHashCode() {
    return hashCode;
  }

  public void setHashCode(int hashCode) {
    this.hashCode = hashCode;
  }

  public String getLlong() {
    return llong;
  }

  public void setLlong(String llong) {
    this.llong = llong;
  }

  private String specsId;
  private String productRegisterId;




  private String productRegisterNo;
  private String productNameMain;
  private String enterpriseId;//附加
  private String enterpriseName;//附加

  private String specsName;
  private String productCode;
  private String productName;
  private String productRemark;
  private String productModel;
  private String productionAddress;
  private String barCode;
  private String unit;
  private String packingUnit;
  private String categories;
  private String conversionRate;
  private String llong;
  private String wide;
  private String hight;
  private String productLine;
  private String manageCategories;
  private String packingRequire;
  private String storageCondition;
  private String transportCondition;
  private String createId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createDate;
  private String createDateDC;


  private String editId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date editDate;
  private String editDateDC;

  private String isUse;
  private String isCertificate;
  private String isDoublec;


  private String attacheCardCategory;//附卡类别
  private String coldHainMark;      //冷链标志
  private String sterilizationMarkers;//灭菌标志
  private String medicalDeviceMark;//医疗器械标志
  private String maintenanceCycle;//养护周期
  private String wight;//重量
  private String packagingUnit;//包装单位
  private String licenseOrRecordNo;//产品许可证 备案号
  private String productEnterpriseName;

  //产品许可证 备案号
  private String licenseNo;
  private String recordNo;






  private String alternatName1;
  private String alternatName2;
  private String alternatName3;
  private String alternatName4;
  private String alternatName5;

  private String type;


  public String getCreateDateDC() {
    return createDateDC;
  }

  public void setCreateDateDC(String createDateDC) {
    this.createDateDC = createDateDC;
  }

  public String getEditDateDC() {
    return editDateDC;
  }

  public void setEditDateDC(String editDateDC) {
    this.editDateDC = editDateDC;
  }

  public String getLicenseOrRecordNo() {
    return licenseOrRecordNo;
  }

  public void setLicenseOrRecordNo(String licenseOrRecordNo) {
    this.licenseOrRecordNo = licenseOrRecordNo;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLicenseNo() {
    return licenseNo;
  }

  public void setLicenseNo(String licenseNo) {
    this.licenseNo = licenseNo;
  }

  public String getRecordNo() {
    return recordNo;
  }

  public void setRecordNo(String recordNo) {
    this.recordNo = recordNo;
  }

  public GspProductRegisterSpecs() {
  }

  public String getPackagingUnit() {
    return packagingUnit;
  }

  public void setPackagingUnit(String packagingUnit) {
    this.packagingUnit = packagingUnit;
  }

  public String getColdHainMark() {
    return coldHainMark;
  }

  public void setColdHainMark(String coldHainMark) {
    this.coldHainMark = coldHainMark;
  }

  public String getSterilizationMarkers() {
    return sterilizationMarkers;
  }

  public void setSterilizationMarkers(String sterilizationMarkers) {
    this.sterilizationMarkers = sterilizationMarkers;
  }

  public String getMedicalDeviceMark() {
    return medicalDeviceMark;
  }

  public void setMedicalDeviceMark(String medicalDeviceMark) {
    this.medicalDeviceMark = medicalDeviceMark;
  }

  public String getMaintenanceCycle() {
    return maintenanceCycle;
  }

  public void setMaintenanceCycle(String maintenanceCycle) {
    this.maintenanceCycle = maintenanceCycle;
  }

  public String getWight() {
    return wight;
  }

  public void setWight(String wight) {
    this.wight = wight;
  }

  public String getSpecsId() {
    return specsId;
  }

  public void setSpecsId(String specsId) {
    this.specsId = specsId;
  }


  public String getProductRegisterId() {
    return productRegisterId;
  }

  public void setProductRegisterId(String productRegisterId) {
    this.productRegisterId = productRegisterId;
  }


  public String getSpecsName() {
    return specsName;
  }

  public void setSpecsName(String specsName) {
    this.specsName = specsName;
  }


  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public String getProductRemark() {
    return productRemark;
  }

  public void setProductRemark(String productRemark) {
    this.productRemark = productRemark;
  }


  public String getProductModel() {
    return productModel;
  }

  public void setProductModel(String productModel) {
    this.productModel = productModel;
  }


  public String getProductionAddress() {
    return productionAddress;
  }

  public void setProductionAddress(String productionAddress) {
    this.productionAddress = productionAddress;
  }


  public String getBarCode() {
    return barCode;
  }

  public void setBarCode(String barCode) {
    this.barCode = barCode;
  }


  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }


  public String getPackingUnit() {
    return packingUnit;
  }

  public void setPackingUnit(String packingUnit) {
    this.packingUnit = packingUnit;
  }


  public String getCategories() {
    return categories;
  }

  public void setCategories(String categories) {
    this.categories = categories;
  }


  public String getConversionRate() {
    return conversionRate;
  }

  public void setConversionRate(String conversionRate) {
    this.conversionRate = conversionRate;
  }


  public String getLong() {
    return llong;
  }

  public void setLong(String llong) {
    this.llong = llong;
  }


  public String getWide() {
    return wide;
  }

  public void setWide(String wide) {
    this.wide = wide;
  }


  public String getHight() {
    return hight;
  }

  public void setHight(String hight) {
    this.hight = hight;
  }


  public String getProductLine() {
    return productLine;
  }

  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }


  public String getManageCategories() {
    return manageCategories;
  }

  public void setManageCategories(String manageCategories) {
    this.manageCategories = manageCategories;
  }





  public String getStorageCondition() {
    return storageCondition;
  }

  public void setStorageCondition(String storageCondition) {
    this.storageCondition = storageCondition;
  }


  public String getTransportCondition() {
    return transportCondition;
  }

  public void setTransportCondition(String transportCondition) {
    this.transportCondition = transportCondition;
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


  public String getAlternatName1() {
    return alternatName1;
  }

  public void setAlternatName1(String alternatName1) {
    this.alternatName1 = alternatName1;
  }


  public String getAlternatName2() {
    return alternatName2;
  }

  public void setAlternatName2(String alternatName2) {
    this.alternatName2 = alternatName2;
  }


  public String getAlternatName3() {
    return alternatName3;
  }

  public void setAlternatName3(String alternatName3) {
    this.alternatName3 = alternatName3;
  }


  public String getAlternatName4() {
    return alternatName4;
  }

  public void setAlternatName4(String alternatName4) {
    this.alternatName4 = alternatName4;
  }


  public String getAlternatName5() {
    return alternatName5;
  }

  public void setAlternatName5(String alternatName5) {
    this.alternatName5 = alternatName5;
  }

  public String getProductNameMain() {
    return productNameMain;
  }

  public void setProductNameMain(String productNameMain) {
    this.productNameMain = productNameMain;
  }

  public String getProductRegisterNo() {
    return productRegisterNo;
  }

  public void setProductRegisterNo(String productRegisterNo) {
    this.productRegisterNo = productRegisterNo;
  }

  public String getPackingRequire() {
    return packingRequire;
  }

  public void setPackingRequire(String packingRequire) {
    this.packingRequire = packingRequire;
  }

    public String getIsCertificate() {
        return isCertificate;
    }

    public void setIsCertificate(String isCertificate) {
        this.isCertificate = isCertificate;
    }

    public String getIsDoublec() {
        return isDoublec;
    }

    public void setIsDoublec(String isDoublec) {
        this.isDoublec = isDoublec;
    }


  public String getAttacheCardCategory() {
    return attacheCardCategory;
  }

  public void setAttacheCardCategory(String attacheCardCategory) {
    this.attacheCardCategory = attacheCardCategory;
  }

  public String getEnterpriseId() {
    return enterpriseId;
  }

  public void setEnterpriseId(String enterpriseId) {
    this.enterpriseId = enterpriseId;
  }

  public String getEnterpriseName() {
    return enterpriseName;
  }

  public void setEnterpriseName(String enterpriseName) {
    this.enterpriseName = enterpriseName;
  }

  public String getProductEnterpriseName() {
    return productEnterpriseName;
  }

  public void setProductEnterpriseName(String productEnterpriseName) {
    this.productEnterpriseName = productEnterpriseName;
  }
}
