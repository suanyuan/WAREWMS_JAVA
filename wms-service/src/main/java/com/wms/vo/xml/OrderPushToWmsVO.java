package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 出库订单资料，出库时调用wms接口soToWms
 * 
 * @author Owen
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "exportRequest")
@XmlType(propOrder = { "customerId", "orderList" })
public class OrderPushToWmsVO {
	@XmlElement
	private String customerId;
	@XmlElementWrapper(name = "orders")
	@XmlElement(name = "order")
	private List<OrderXmlVo> orderList;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<OrderXmlVo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderXmlVo> orderList) {
		this.orderList = orderList;
	}

	@Override
	public String toString() {
		return "ExportRequestXmlVo [customerId=" + customerId + ", orderList=" + orderList + "]";
	}

}
