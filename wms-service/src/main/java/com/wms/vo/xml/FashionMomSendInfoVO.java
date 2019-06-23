package com.wms.vo.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequestOrderStatusInfo")
@XmlType(propOrder = { "customerId", "orderCode", "orderStatus", "logisticProviderId", "mailNo", "remark", "accessDt", "assignedTo", "invoiceNum"})
public class FashionMomSendInfoVO {
	@XmlElement
	private String customerId;
	@XmlElement
	private String orderCode;
	@XmlElement
	private String orderStatus;
	@XmlElement
	private String logisticProviderId;
	@XmlElement
	private String mailNo;
	@XmlElement
	private String remark;
	@XmlElement
	private String accessDt;
	@XmlElement
	private String assignedTo;
	@XmlElement
	private String invoiceNum;

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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getLogisticProviderId() {
		return logisticProviderId;
	}

	public void setLogisticProviderId(String logisticProviderId) {
		this.logisticProviderId = logisticProviderId;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAccessDt() {
		return accessDt;
	}

	public void setAccessDt(String accessDt) {
		this.accessDt = accessDt;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
}