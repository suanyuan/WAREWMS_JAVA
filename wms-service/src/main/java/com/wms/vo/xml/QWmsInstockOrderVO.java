package com.wms.vo.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestPurchaseInfo")
public class QWmsInstockOrderVO {
	@XmlElement
	private Integer customerId;
	@XmlElement
	private String orderCode;
	@XmlElement
	private String type;
	@XmlElement
	private String expectedArriveTime;
	@XmlElement
	private String notes;
	@XmlElementWrapper(name = "products")
	@XmlElement(name = "productInfo")
	private List<QWmsInstockOrderDetailVO> detail;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpectedArriveTime() {
		return expectedArriveTime;
	}

	public void setExpectedArriveTime(String expectedArriveTime) {
		this.expectedArriveTime = expectedArriveTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<QWmsInstockOrderDetailVO> getDetail() {
		return detail;
	}

	public void setDetail(List<QWmsInstockOrderDetailVO> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "QWmsInstockOrderVO [customerId=" + customerId + ", orderCode=" + orderCode + ", type=" + type
				+ ", expectedArriveTime=" + expectedArriveTime + ", notes=" + notes + "]";
	}
}
