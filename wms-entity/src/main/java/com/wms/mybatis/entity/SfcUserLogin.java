package com.wms.mybatis.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class SfcUserLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	private java.lang.String id;

	private java.lang.String userName;

	private int userType;

	private java.lang.String pwd;

	private java.lang.String gender;

	private java.lang.Byte enable;

	private java.util.Date birthday;

	private java.lang.String email;

	private java.util.Date createTime;

	private java.util.Date lastLoginTime;

	private java.lang.String sessionId;

	private String nodeId;

	private java.lang.String parentNodeId;

	private String merchantId;

	private com.wms.mybatis.entity.SfcCountry country;

	private com.wms.mybatis.entity.SfcWarehouse warehouse;

	private Set<SfcCustomer> customerSet;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public java.lang.String getPwd() {
		return pwd;
	}

	public void setPwd(java.lang.String pwd) {
		this.pwd = pwd;
	}

	public java.lang.String getGender() {
		return gender;
	}

	public void setGender(java.lang.String gender) {
		this.gender = gender;
	}

	public java.lang.Byte getEnable() {
		return enable;
	}

	public void setEnable(java.lang.Byte enable) {
		this.enable = enable;
	}

	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public java.lang.String getSessionId() {
		return sessionId;
	}

	public void setSessionId(java.lang.String sessionId) {
		this.sessionId = sessionId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public java.lang.String getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(java.lang.String parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public com.wms.mybatis.entity.SfcCountry getCountry() {
		return country;
	}

	public void setCountry(com.wms.mybatis.entity.SfcCountry country) {
		this.country = country;
	}

	public com.wms.mybatis.entity.SfcWarehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(com.wms.mybatis.entity.SfcWarehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SfcUserLogin other = (SfcUserLogin) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SfcUserLogin [hashCode=" + hashCode + ", id=" + id
				+ ", userName=" + userName + ", userType=" + userType
				+ ", pwd=" + pwd + ", gender=" + gender + ", enable=" + enable
				+ ", birthday=" + birthday + ", email=" + email
				+ ", createTime=" + createTime + ", lastLoginTime="
				+ lastLoginTime + ", sessionId=" + sessionId + ", nodeId="
				+ nodeId + ", parentNodeId=" + parentNodeId + ", merchantId="
				+ merchantId + ", country=" + country + ", warehouse="
				+ warehouse + ", customerSet=" + customerSet + "]";
	}
}
