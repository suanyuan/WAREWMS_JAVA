package com.wms.mybatis.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author 
 * @Date 
 */
@Entity
public class SfcUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;
	
	@Id
	private String id;

	private java.lang.String userName;
	
	private java.lang.String pwd;

	private String nodeId;

	private java.lang.String parentNodeId;
	
	private java.lang.String gender;
	
	private java.lang.Byte enable;
	
	private java.util.Date birthday;
	
	private java.lang.String email;
	
	private int userType;
	
	private java.util.Date createTime;
	
	private java.util.Date lastLoginTime;
	
	private java.lang.String sessionId;

	private com.wms.mybatis.entity.SfcCountry country;
	
	private String merchantId;

	private Set<SfcRole> roleSet;

	private Set<SfcWarehouse> warehouseSet;

	private com.wms.mybatis.entity.SfcWarehouse defaultWarehouse;

	private Set<SfcCustomer> customerSet;

	private String userGrade;

	private String createWho;

	private Date editTime;

	private String editWho;

	public String getCreateWho() {
		return createWho;
	}

	public void setCreateWho(String createWho) {
		this.createWho = createWho;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getEditWho() {
		return editWho;
	}

	public void setEditWho(String editWho) {
		this.editWho = editWho;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getPwd() {
		return pwd;
	}

	public void setPwd(java.lang.String pwd) {
		this.pwd = pwd;
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

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
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

	public com.wms.mybatis.entity.SfcCountry getCountry() {
		return country;
	}

	public void setCountry(com.wms.mybatis.entity.SfcCountry country) {
		this.country = country;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Set<SfcRole> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<SfcRole> roleSet) {
		this.roleSet = roleSet;
	}

	public Set<SfcWarehouse> getWarehouseSet() {
		return warehouseSet;
	}

	public void setWarehouseSet(Set<SfcWarehouse> warehouseSet) {
		this.warehouseSet = warehouseSet;
	}

	public com.wms.mybatis.entity.SfcWarehouse getDefaultWarehouse() {
		return defaultWarehouse;
	}

	public void setDefaultWarehouse(
			com.wms.mybatis.entity.SfcWarehouse defaultWarehouse) {
		this.defaultWarehouse = defaultWarehouse;
	}

	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
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
		SfcUser other = (SfcUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SfcUser [hashCode=" + hashCode + ", id=" + id + ", userName="
				+ userName + ", pwd=" + pwd + ", nodeId=" + nodeId
				+ ", parentNodeId=" + parentNodeId + ", gender=" + gender
				+ ", enable=" + enable + ", birthday=" + birthday + ", email="
				+ email + ", userType=" + userType + ", createTime="
				+ createTime + ", lastLoginTime=" + lastLoginTime
				+ ", sessionId=" + sessionId + ", country=" + country
				+ ", merchantId=" + merchantId + ", roleSet=" + roleSet
				+ ", warehouseSet=" + warehouseSet + ", defaultWarehouse="
				+ defaultWarehouse + ", customerSet=" + customerSet + "]";
	}
}