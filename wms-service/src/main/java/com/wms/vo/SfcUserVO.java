package com.wms.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCountry;
import com.wms.mybatis.entity.SfcCustomer;
import com.wms.mybatis.entity.SfcRole;
import com.wms.mybatis.entity.SfcWarehouse;
import com.wms.utils.serialzer.JsonDateSerializer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class SfcUserVO implements Comparable<SfcUserVO> {
	private String id;
	private String userName;
	private String gender;
	private Byte enable;
	private Date birthday;
	private SfcCountry country;
	private String email;
	private Date createTime;
	private Date lastLoginTime;
	private Map<String, String> parent;
	private java.lang.String state;
	private List<SfcUserVO> children;// 子節點
	private int userType;
	private Set<SfcRole> roleSet;
	private Set<SfcWarehouse> warehouseSet;
	private SfcWarehouse defaultWarehouse;
	private Set<SfcCustomer> customerSet;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Map<String, String> getParent() {
		return parent;
	}

	public void setParent(Map<String, String> parent) {
		this.parent = parent;
	}

	public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	public List<SfcUserVO> getChildren() {
		return children;
	}

	public void setChildren(List<SfcUserVO> children) {
		this.children = children;
	}

	public SfcCountry getCountry() {
		return country;
	}

	public void setCountry(SfcCountry country) {
		this.country = country;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Byte getEnable() {
		return enable;
	}

	public void setEnable(Byte enable) {
		this.enable = enable;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
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

	public SfcWarehouse getDefaultWarehouse() {
		return defaultWarehouse;
	}

	public void setDefaultWarehouse(SfcWarehouse defaultWarehouse) {
		this.defaultWarehouse = defaultWarehouse;
	}

	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}

	@Override
	public String toString() {
		return "SfcUserVO [id=" + id + ", userName=" + userName + ", gender="
				+ gender + ", enable=" + enable + ", birthday=" + birthday
				+ ", country=" + country + ", email=" + email + ", createTime="
				+ createTime + ", lastLoginTime=" + lastLoginTime + ", parent="
				+ parent + ", state=" + state + ", children=" + children
				+ ", userType=" + userType + ", roleSet=" + roleSet
				+ ", warehouseSet=" + warehouseSet + ", defaultWarehouse="
				+ defaultWarehouse + ", customerSet=" + customerSet + "]";
	}

	@Override
	public int compareTo(SfcUserVO o) {
		return this.id.compareToIgnoreCase(o.getId());
	}

}
