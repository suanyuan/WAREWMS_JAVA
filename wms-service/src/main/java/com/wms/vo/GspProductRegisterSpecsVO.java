package com.wms.vo;


import com.wms.entity.DocAsnDetail;
import com.wms.utils.serialzer.JsonDatetimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;


public class GspProductRegisterSpecsVO {
	private Integer seq;
	private java.lang.String specsId;
	private java.lang.String productRegisterId;

	private java.lang.String productRegisterNo;
	private java.lang.String productNameMain;

	private java.lang.String specsName;
	private java.lang.String productCode;
	private java.lang.String productName;
	private java.lang.String productRemark;
	private java.lang.String productModel;
	private java.lang.String productionAddress;
	private java.lang.String barCode;
	private java.lang.String unit;
	private java.lang.String packingUnit;
	private java.lang.String categories;
	private java.lang.String conversionRate;
	private java.lang.String llong;
	private java.lang.String wide;
	private java.lang.String hight;
	//private java.lang.String productLine;
	private java.lang.String manageCategories;
	private java.lang.String packingRequire;
	private java.lang.String storageCondition;
	private java.lang.String transportCondition;
	private java.lang.String createId;

    //@Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String createDate;
	private java.lang.String editId;

    //@Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDatetimeSerializer.class)
	private java.lang.String editDate;
	private java.lang.String isUse;
	private java.lang.String isCertificate;
	private java.lang.String isDoublec;
	private java.lang.String attacheCardCategory;

	private java.lang.String alternatName1;
	private java.lang.String alternatName2;
	private java.lang.String alternatName3;
	private java.lang.String alternatName4;
	private java.lang.String alternatName5;

	//private java.util.Set<DocAsnDetail> docAsnDetailSet;
	private List<GspProductRegisterSpecsVO> gspProductRegisterSpecsVOList;


	public java.lang.String getSpecsId() {
		return specsId;
	}

	public void setSpecsId(java.lang.String specsId) {
		this.specsId = specsId;
	}

	public java.lang.String getProductRegisterId() {
		return productRegisterId;
	}

	public void setProductRegisterId(java.lang.String productRegisterId) {
		this.productRegisterId = productRegisterId;
	}

	public java.lang.String getSpecsName() {
		return specsName;
	}

	public void setSpecsName(java.lang.String specsName) {
		this.specsName = specsName;
	}

	public java.lang.String getProductCode() {
		return productCode;
	}

	public void setProductCode(java.lang.String productCode) {
		this.productCode = productCode;
	}

	public java.lang.String getProductName() {
		return productName;
	}

	public void setProductName(java.lang.String productName) {
		this.productName = productName;
	}

	public java.lang.String getProductRemark() {
		return productRemark;
	}

	public void setProductRemark(java.lang.String productRemark) {
		this.productRemark = productRemark;
	}

	public java.lang.String getProductModel() {
		return productModel;
	}

	public void setProductModel(java.lang.String productModel) {
		this.productModel = productModel;
	}

	public java.lang.String getProductionAddress() {
		return productionAddress;
	}

	public void setProductionAddress(java.lang.String productionAddress) {
		this.productionAddress = productionAddress;
	}

	public java.lang.String getBarCode() {
		return barCode;
	}

	public void setBarCode(java.lang.String barCode) {
		this.barCode = barCode;
	}

	public java.lang.String getUnit() {
		return unit;
	}

	public void setUnit(java.lang.String unit) {
		this.unit = unit;
	}

	public java.lang.String getPackingUnit() {
		return packingUnit;
	}

	public void setPackingUnit(java.lang.String packingUnit) {
		this.packingUnit = packingUnit;
	}

	public java.lang.String getCategories() {
		return categories;
	}

	public void setCategories(java.lang.String categories) {
		this.categories = categories;
	}

	public java.lang.String getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(java.lang.String conversionRate) {
		this.conversionRate = conversionRate;
	}

	public java.lang.String getLlong() {
		return llong;
	}

	public void setLlong(java.lang.String llong) {
		this.llong = llong;
	}

	public java.lang.String getWide() {
		return wide;
	}

	public void setWide(java.lang.String wide) {
		this.wide = wide;
	}

	public java.lang.String getHight() {
		return hight;
	}

	public void setHight(java.lang.String hight) {
		this.hight = hight;
	}

//	public java.lang.String getProductLine() {
//		return productLine;
//	}
//
//	public void setProductLine(java.lang.String productLine) {
//		this.productLine = productLine;
//	}

	public java.lang.String getManageCategories() {
		return manageCategories;
	}

	public void setManageCategories(java.lang.String manageCategories) {
		this.manageCategories = manageCategories;
	}



	public java.lang.String getStorageCondition() {
		return storageCondition;
	}

	public void setStorageCondition(java.lang.String storageCondition) {
		this.storageCondition = storageCondition;
	}

	public java.lang.String getTransportCondition() {
		return transportCondition;
	}

	public void setTransportCondition(java.lang.String transportCondition) {
		this.transportCondition = transportCondition;
	}

	public java.lang.String getCreateId() {
		return createId;
	}

	public void setCreateId(java.lang.String createId) {
		this.createId = createId;
	}



	public java.lang.String getEditId() {
		return editId;
	}

	public void setEditId(java.lang.String editId) {
		this.editId = editId;
	}



	public java.lang.String getIsUse() {
		return isUse;
	}

	public void setIsUse(java.lang.String isUse) {
		this.isUse = isUse;
	}

	public java.lang.String getAlternatName1() {
		return alternatName1;
	}

	public void setAlternatName1(java.lang.String alternatName1) {
		this.alternatName1 = alternatName1;
	}

	public java.lang.String getAlternatName2() {
		return alternatName2;
	}

	public void setAlternatName2(java.lang.String alternatName2) {
		this.alternatName2 = alternatName2;
	}

	public java.lang.String getAlternatName3() {
		return alternatName3;
	}

	public void setAlternatName3(java.lang.String alternatName3) {
		this.alternatName3 = alternatName3;
	}

	public java.lang.String getAlternatName4() {
		return alternatName4;
	}

	public void setAlternatName4(java.lang.String alternatName4) {
		this.alternatName4 = alternatName4;
	}

	public java.lang.String getAlternatName5() {
		return alternatName5;
	}

	public void setAlternatName5(java.lang.String alternatName5) {
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


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEditDate() {
        return editDate;
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate;
    }

	public List<GspProductRegisterSpecsVO> getGspProductRegisterSpecsVOList() {
		return gspProductRegisterSpecsVOList;
	}

	public void setGspProductRegisterSpecsVOList(List<GspProductRegisterSpecsVO> gspProductRegisterSpecsVOList) {
		this.gspProductRegisterSpecsVOList = gspProductRegisterSpecsVOList;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public String getPackingRequire() {
		return packingRequire;
	}

	public void setPackingRequire(String packingRequire) {
		this.packingRequire = packingRequire;
	}


	public String getAttacheCardCategory() {
		return attacheCardCategory;
	}

	public void setAttacheCardCategory(String attacheCardCategory) {
		this.attacheCardCategory = attacheCardCategory;
	}
}