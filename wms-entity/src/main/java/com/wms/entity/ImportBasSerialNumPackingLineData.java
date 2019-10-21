package com.wms.entity;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
public class ImportBasSerialNumPackingLineData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String seq;      //序号
	private String serialNum; // 序列号
	private String batchNum; // 批号
	private String materialNum; // 产品代码
	private String expireDate; //lotatt02 效期
	private String productDate; //lotatt01 生产日期
	private String batchFlag; //不知道是啥
	private String uom; //包装单位
	private String purchaseOrder; //？销售单号
	private String packageNum;
	private String deliveryNum;  //发货凭证号



	public ImportBasSerialNumPackingLineData() {
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getMaterialNum() {
		return materialNum;
	}

	public void setMaterialNum(String materialNum) {
		this.materialNum = materialNum;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getBatchFlag() {
		return batchFlag;
	}

	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getPackageNum() {
		return packageNum;
	}

	public void setPackageNum(String packageNum) {
		this.packageNum = packageNum;
	}

	public String getDeliveryNum() {
		return deliveryNum;
	}

	public void setDeliveryNum(String deliveryNum) {
		this.deliveryNum = deliveryNum;
	}
}