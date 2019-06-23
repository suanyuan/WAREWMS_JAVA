package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sku")
@XmlType(propOrder = {"skuCode","itemCount","itemValue","itemUnitCost"})
public class SkuXmlVo {
	@XmlElement
	private String skuCode;
	@XmlElement
	private Integer itemCount;
	@XmlElement
	private Double itemValue;
	@XmlElement
	private Double itemUnitCost;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Double getItemValue() {
		return itemValue;
	}

	public void setItemValue(Double itemValue) {
		this.itemValue = itemValue;
	}

	public Double getItemUnitCost() {
		return itemUnitCost;
	}

	public void setItemUnitCost(Double itemUnitCost) {
		this.itemUnitCost = itemUnitCost;
	}

	@Override
	public String toString() {
		return "SkuXmlVo [skuCode=" + skuCode + ", itemCount=" + itemCount + "]";
	}

}
