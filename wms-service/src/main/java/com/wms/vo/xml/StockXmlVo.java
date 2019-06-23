package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 库存表xml vo
 * @author Owen
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stock")
@XmlType(propOrder = {"merchantId","skuCode","skuName","skuWeight","skuVolume","quantity","unit","unitPrice","isNew"})
public class StockXmlVo {
	@XmlElement
	private Integer merchantId;
	@XmlElement
	private Integer quantity;
	@XmlElement
	private String skuCode;
	@XmlElement
	private String skuName;
	@XmlElement
	private Double skuVolume;
	@XmlElement
	private Double skuWeight;
	@XmlElement
	private String unit;
	@XmlElement
	private Double unitPrice;
	@XmlElement
	private boolean isNew;
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public Double getSkuVolume() {
		return skuVolume;
	}
	public void setSkuVolume(Double skuVolume) {
		this.skuVolume = skuVolume;
	}
	public Double getSkuWeight() {
		return skuWeight;
	}
	public void setSkuWeight(Double skuWeight) {
		this.skuWeight = skuWeight;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	@Override
	public String toString() {
		return "StockXmlVo [merchantId=" + merchantId + ", quantity=" + quantity + ", skuCode=" + skuCode + ", skuName="
				+ skuName + ", skuVolume=" + skuVolume + ", skuWeight=" + skuWeight + ", unit=" + unit + ", isNew="
				+ isNew + "]";
	}
	
}
