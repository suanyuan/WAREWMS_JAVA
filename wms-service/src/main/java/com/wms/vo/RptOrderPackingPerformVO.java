package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptOrderPackingPerformVO {

	private java.lang.String customerid;
	private java.math.BigDecimal qtyordered;
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

	public java.math.BigDecimal getQtyordered() {
		return qtyordered;
	}

	public void setQtyordered(java.math.BigDecimal qtyordered) {
		this.qtyordered = qtyordered;
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