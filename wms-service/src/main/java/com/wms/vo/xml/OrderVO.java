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
@XmlType(propOrder = { "orderCode","orderType","name","mobile","address","quantity","requiredTime","custName","barCodes","seq","skuList" })
public class OrderVO {
	@XmlElement
	private String orderCode;
	@XmlElement
	private String orderType;
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
	private Integer seq;
	@XmlElement
	private List<BarcodeVo> barCodes;
	@XmlElementWrapper(name = "skus")
	@XmlElement(name = "sku")
	private List<SkuXmlVo> skuList;
	
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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

	public List<BarcodeVo> getBarCodes() {
		return barCodes;
	}

	public void setBarCodes(List<BarcodeVo> barCodes) {
		this.barCodes = barCodes;
	}

}
