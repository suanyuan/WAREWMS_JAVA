package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestOrderInfo")
@XmlType(propOrder = { "customerId", "orderCode", "orderType", "shipping", "codItemValue", "itemsValue", "remark","ordermarks","total_fee","payments","receiver", "items" })
public class FashionMomOrderVO {
	@XmlElement
	private String customerId;
	@XmlElement
	private String orderCode;
	@XmlElement
	private String orderType;
	@XmlElement
	private String shipping;
	@XmlElement
	private String codItemValue;
	@XmlElement
	private String itemsValue;
	@XmlElement
	private String remark;
	@XmlElement
	private String ordermarks;
	@XmlElement
	private String total_fee;
	@XmlElement
	private FashionMomSyncPaymentXml payments;
	@XmlElement
	private FashionMomSyncOrderXmlReceiver receiver;
	@XmlElementWrapper(name = "items")
	@XmlElement(name = "item")
	private List<FashionMomSyncOrderXmlItem> items;

	public FashionMomSyncPaymentXml getPayments() {
		return payments;
	}

	public void setPayments(FashionMomSyncPaymentXml payments) {
		this.payments = payments;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getOrdermarks() {
		return ordermarks;
	}

	public void setOrdermarks(String ordermarks) {
		this.ordermarks = ordermarks;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getCodItemValue() {
		return codItemValue;
	}

	public void setCodItemValue(String codItemValue) {
		this.codItemValue = codItemValue;
	}

	public String getItemsValue() {
		return itemsValue;
	}

	public void setItemsValue(String itemsValue) {
		this.itemsValue = itemsValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public FashionMomSyncOrderXmlReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(FashionMomSyncOrderXmlReceiver receiver) {
		this.receiver = receiver;
	}

	public List<FashionMomSyncOrderXmlItem> getItems() {
		return items;
	}

	public void setItems(List<FashionMomSyncOrderXmlItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "FashionMomSyncOrderXmlRequestOrderInfo [customerId=" + customerId + ", orderCode=" + orderCode
				+ ", orderType=" + orderType + ", shipping=" + shipping + ", codItemValue=" + codItemValue
				+ ", itemsValue=" + itemsValue + ", remark=" + remark + ", ordermarks=" + ordermarks + ", total_fee="
				+ total_fee + ", payments=" + payments + ", receiver=" + receiver + ", items=" + items + "]";
	}
	
}
