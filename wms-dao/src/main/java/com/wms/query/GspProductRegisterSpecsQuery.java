package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class GspProductRegisterSpecsQuery implements IQuery {

	private String token;
	private String specsId;
	private String productRegisterId;

	private String productRegisterNo;
	private String productNameMain;

	private String createDateStart;
	private String createDateEnd;
	private String editDateStart;
	private String editDateEnd;
	private String enterpriseName;


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
	private String createDate;
	private String editId;
	private String editDate;
	private String isUse;
	private String isCertificate;
	private String isDoublec;
	private String alternatName1;
	private String alternatName2;
	private String alternatName3;
	private String alternatName4;
	private String alternatName5;

	private String type;
	private String supplierId;


	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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



	public GspProductRegisterSpecsQuery() {
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

	public String getLlong() {
		return llong;
	}

	public void setLlong(String llong) {
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

	public String getPacking_Require() {
		return packingRequire;
	}

	public void setPacking_Require(String packing_Require) {
		this.packingRequire = packing_Require;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
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

	public String getProductRegisterNo() {
		return productRegisterNo;
	}

	public void setProductRegisterNo(String productRegisterNo) {
		this.productRegisterNo = productRegisterNo;
	}

	public String getProductNameMain() {
		return productNameMain;
	}

	public void setProductNameMain(String productNameMain) {
		this.productNameMain = productNameMain;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getEditDateStart() {
		return editDateStart;
	}

	public void setEditDateStart(String editDateStart) {
		this.editDateStart = editDateStart;
	}

	public String getEditDateEnd() {
		return editDateEnd;
	}

	public void setEditDateEnd(String editDateEnd) {
		this.editDateEnd = editDateEnd;
	}
}