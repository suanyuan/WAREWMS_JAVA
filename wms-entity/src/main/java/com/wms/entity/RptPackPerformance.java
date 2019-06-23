package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RPT_PACK_PERFORMANCE database table.
 * 
 */
@Entity
@Table(name="RPT_PACK_PERFORMANCE")
@NamedQuery(name="RptPackPerformance.findAll", query="SELECT r FROM RptPackPerformance r")
public class RptPackPerformance implements Serializable {
	private static final long serialVersionUID = 1L;

	private String customerid;

	private BigDecimal fee;

	private String issuepartyname;

	private BigDecimal mulirate;

	@Column(name="ORDER_TYPE")
	private String orderType;

	private BigDecimal qty;

	private String sdate;

	private String sdatetext;

	private String ttim;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

	public RptPackPerformance() {
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public BigDecimal getFee() {
		return this.fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getIssuepartyname() {
		return this.issuepartyname;
	}

	public void setIssuepartyname(String issuepartyname) {
		this.issuepartyname = issuepartyname;
	}

	public BigDecimal getMulirate() {
		return this.mulirate;
	}

	public void setMulirate(BigDecimal mulirate) {
		this.mulirate = mulirate;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getSdate() {
		return this.sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getSdatetext() {
		return this.sdatetext;
	}

	public void setSdatetext(String sdatetext) {
		this.sdatetext = sdatetext;
	}

	public String getTtim() {
		return this.ttim;
	}

	public void setTtim(String ttim) {
		this.ttim = ttim;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}