package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class WaybillStatisticsVO {

	private String id;
	private String enterpriseId;
	private String carrierName;
	private String year;
	private String month;
	private String day;
	private String orderNum;
	private String complaintNum;
	private String missingNum;
	private String damageNum;
	private String lagNum;
	private String otherNum;
	private String remark;
	private java.sql.Timestamp createDate;
	private String createId;
	private java.sql.Timestamp editDate;
	private String editId;
	private String userdefind1;
	private String userdefind2;
	private String userdefind3;
	private String userdefind4;
	private String userdefind5;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getComplaintNum() {
		return complaintNum;
	}

	public void setComplaintNum(String complaintNum) {
		this.complaintNum = complaintNum;
	}

	public String getMissingNum() {
		return missingNum;
	}

	public void setMissingNum(String missingNum) {
		this.missingNum = missingNum;
	}

	public String getDamageNum() {
		return damageNum;
	}

	public void setDamageNum(String damageNum) {
		this.damageNum = damageNum;
	}

	public String getLagNum() {
		return lagNum;
	}

	public void setLagNum(String lagNum) {
		this.lagNum = lagNum;
	}

	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public java.sql.Timestamp getEditDate() {
		return editDate;
	}

	public void setEditDate(java.sql.Timestamp editDate) {
		this.editDate = editDate;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public String getUserdefind1() {
		return userdefind1;
	}

	public void setUserdefind1(String userdefind1) {
		this.userdefind1 = userdefind1;
	}

	public String getUserdefind2() {
		return userdefind2;
	}

	public void setUserdefind2(String userdefind2) {
		this.userdefind2 = userdefind2;
	}

	public String getUserdefind3() {
		return userdefind3;
	}

	public void setUserdefind3(String userdefind3) {
		this.userdefind3 = userdefind3;
	}

	public String getUserdefind4() {
		return userdefind4;
	}

	public void setUserdefind4(String userdefind4) {
		this.userdefind4 = userdefind4;
	}

	public String getUserdefind5() {
		return userdefind5;
	}

	public void setUserdefind5(String userdefind5) {
		this.userdefind5 = userdefind5;
	}

}