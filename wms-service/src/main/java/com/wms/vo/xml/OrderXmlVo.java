package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 订单资料
 * 
 * @author Owen
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = { "orderCode","orderType","externalOrderCode","backReceipt","isCollect","warehouseCode","collectAmt","name","mobile","address","quantity","requiredTime","custName","memberName","notes","skuList" })
public class OrderXmlVo {
	@XmlElement
	private String orderCode;
	@XmlElement
	private String orderType;
	@XmlElement
	private String externalOrderCode;
	@XmlElement
	private String backReceipt;
	@XmlElement
	private String isCollect;
	@XmlElement
	private String warehouseCode;
	@XmlElement
	private String collectAmt;
	@XmlElement
	private String name;
	@XmlElement
	private String mobile;
	@XmlElement
	private String address;
	@XmlElement
	private Integer quantity;
	@XmlElement
	private String requiredTime;
	@XmlElement
	private String custName;
	@XmlElement
	private String memberName;
	@XmlElement
	private String notes;
	@XmlElementWrapper(name = "skus")
	@XmlElement(name = "sku")
	private List<SkuXmlVo> skuList;
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getCollectAmt() {
		return collectAmt;
	}

	public void setCollectAmt(String collectAmt) {
		this.collectAmt = collectAmt;
	}

	public String getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(String isCollect) {
		this.isCollect = isCollect;
	}

	public String getBackReceipt() {
		return backReceipt;
	}

	public void setBackReceipt(String backReceipt) {
		this.backReceipt = backReceipt;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRequiredTime() {
		return requiredTime;
	}

	public void setRequiredTime(String requiredTime) {
		this.requiredTime = requiredTime;
	}

	public List<SkuXmlVo> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<SkuXmlVo> skuList) {
		this.skuList = skuList;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getExternalOrderCode() {
		return externalOrderCode;
	}

	public void setExternalOrderCode(String externalOrderCode) {
		this.externalOrderCode = externalOrderCode;
	}

	@Override
	public String toString() {
		return "OrderXmlVo [orderCode=" + orderCode + ", externalOrderCode=" + externalOrderCode + ", name=" + name
				+ ", mobile=" + mobile + ", address=" + address + ", quantity=" + quantity + ", requiredTime="
				+ requiredTime + ", custName=" + custName + ", skuList=" + skuList + "]";
	}

}
