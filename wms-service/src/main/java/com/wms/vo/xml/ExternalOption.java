package com.wms.vo.xml;

public class ExternalOption {
	private String customerId;//潮妈帮仓库Id
	private String buyerCode;// 潮妈帮会员
	private String orderRemark;// 潮妈帮订单备注

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}

	@Override
	public String toString() {
		return "ExternalOrderOption [customerId=" + customerId + ", buyerCode=" + buyerCode + ", orderRemark="
				+ orderRemark + "]";
	}

}
