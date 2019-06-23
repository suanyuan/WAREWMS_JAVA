package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptPackPerformanceVO {

	private java.lang.String customerid;
	private java.math.BigDecimal fee;
	private java.lang.String issuepartyname;
	private java.math.BigDecimal mulirate;
	private java.lang.String orderType;
	private java.math.BigDecimal qty;
	private java.lang.String sdate;
	private java.lang.String sdatetext;
	private java.lang.String ttim;
	private java.lang.String userId;
	private java.lang.String userName;

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.math.BigDecimal getFee() {
		return fee;
	}

	public void setFee(java.math.BigDecimal fee) {
		this.fee = fee;
	}

	public java.lang.String getIssuepartyname() {
		return issuepartyname;
	}

	public void setIssuepartyname(java.lang.String issuepartyname) {
		this.issuepartyname = issuepartyname;
	}

	public java.math.BigDecimal getMulirate() {
		return mulirate;
	}

	public void setMulirate(java.math.BigDecimal mulirate) {
		this.mulirate = mulirate;
	}

	public java.lang.String getOrderType() {
		return orderType;
	}

	public void setOrderType(java.lang.String orderType) {
		this.orderType = orderType;
	}

	public java.math.BigDecimal getQty() {
		return qty;
	}

	public void setQty(java.math.BigDecimal qty) {
		this.qty = qty;
	}

	public java.lang.String getSdate() {
		return sdate;
	}

	public void setSdate(java.lang.String sdate) {
		this.sdate = sdate;
	}

	public java.lang.String getSdatetext() {
		return sdatetext;
	}

	public void setSdatetext(java.lang.String sdatetext) {
		this.sdatetext = sdatetext;
	}

	public java.lang.String getTtim() {
		return ttim;
	}

	public void setTtim(java.lang.String ttim) {
		this.ttim = ttim;
	}

	public java.lang.String getUserId() {
		return userId;
	}

	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

}