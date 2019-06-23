package com.wms.utils.generator;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.entity.UserLogin;
import com.wms.utils.serialzer.JsonDateSerializer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class WmsDeliveredVO {

	private java.lang.String id;
	private java.util.Date arrivedTime;
	private Date createTime;
	private Date updateTime;
	private java.lang.String externalCode;
	private String merchantName;
	private String createUsername;
	private java.lang.String orderCode;
	private Integer orderType;
	private Integer quantity;
	private Integer status;
	private Integer location;

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.util.Date getArrivedTime() {
		return arrivedTime;
	}

	public void setArrivedTime(java.util.Date arrivedTime) {
		this.arrivedTime = arrivedTime;
	}

	public java.lang.String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(java.lang.String externalCode) {
		this.externalCode = externalCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getCreateUsername() {
		return createUsername;
	}

	public void setCreateUsername(String createUsername) {
		this.createUsername = createUsername;
	}

	public java.lang.String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(java.lang.String orderCode) {
		this.orderCode = orderCode;
	}


	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}