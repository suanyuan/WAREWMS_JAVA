package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
@XmlType(propOrder = { "orderCode", "gmtDate", "isSuccess" })
public class FashionMomMarkItemVO {
	@XmlElement(name = "order_code")
	private String orderCode;
	@XmlElement(name = "gmt_date")
	private String gmtDate;
	@XmlElement(name = "is_success")
	private String isSuccess;
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getGmtDate() {
		return gmtDate;
	}
	public void setGmtDate(String gmtDate) {
		this.gmtDate = gmtDate;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	@Override
	public String toString() {
		return "FashionMomMarkItemXmlVo [orderCode=" + orderCode + ", gmtDate=" + gmtDate + ", isSuccess=" + isSuccess
				+ "]";
	}
}
