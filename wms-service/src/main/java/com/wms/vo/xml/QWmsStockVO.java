package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sku")
@XmlType(propOrder = {"customerId","skuCode","skuName","category","barCode","unit"})
public class QWmsStockVO {
	@XmlElement
	private String customerId;
	@XmlElement
	private String skuCode;
	@XmlElement
	private String skuName;
	@XmlElement
	private String category;
	@XmlElement
	private String barCode;
	@XmlElement
	private String unit;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	@Override
	public String toString() {
		return "QWmsStockVO [customerId=" + customerId + ", skuCode=" + skuCode + ", skuName=" + skuName + ", category="
				+ category + ", barCode=" + barCode + ", unit=" + unit + "]";
	}
	
}