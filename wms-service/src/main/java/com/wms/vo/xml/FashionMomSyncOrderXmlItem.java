package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
@XmlType(propOrder = { "spuCode", "itemName", "itemCount", "itemValue", "item_price" ,"itemUnitcost"})
public class FashionMomSyncOrderXmlItem {
	@XmlElement
	private String spuCode;
	@XmlElement
	private String itemName;
	@XmlElement
	private String itemCount;
	@XmlElement
	private String itemValue;
	@XmlElement
	private String item_price;
	@XmlElement
	private String itemUnitcost;

	public String getItemUnitcost() {
		return itemUnitcost;
	}

	public void setItemUnitcost(String itemUnitcost) {
		this.itemUnitcost = itemUnitcost;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItem_price() {
		return item_price;
	}

	public void setItem_price(String item_price) {
		this.item_price = item_price;
	}

	@Override
	public String toString() {
		return "FashionMomSyncOrderXmlItem [spuCode=" + spuCode + ", itemName=" + itemName + ", itemCount=" + itemCount
				+ ", itemValue=" + itemValue + ", item_price=" + item_price + ", itemUnitcost=" + itemUnitcost + "]";
	}
	
}
