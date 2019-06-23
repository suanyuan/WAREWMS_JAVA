package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RPT_SORT_PERFORMANCE database table.
 * 
 */
@Entity
@Table(name="RPT_SORT_PERFORMANCE")
@NamedQuery(name="RptSortPerformance.findAll", query="SELECT r FROM RptSortPerformance r")
public class RptSortPerformance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String customerid;

	private BigDecimal qtyordered;

	private String sdate;

	private String sdatetext;

	private String ttim;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="USER_NAME")
	private String userName;

	public RptSortPerformance() {
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public BigDecimal getQtyordered() {
		return this.qtyordered;
	}

	public void setQtyordered(BigDecimal qtyordered) {
		this.qtyordered = qtyordered;
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