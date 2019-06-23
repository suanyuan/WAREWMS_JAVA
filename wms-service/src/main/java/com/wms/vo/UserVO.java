package com.wms.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.entity.Country;
import com.wms.entity.Role;
import com.wms.utils.serialzer.JsonDateSerializer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class UserVO implements Comparable<UserVO> {
	private String id;
	private String userName;
	private String gender;
	private Byte enable;
	private Date birthday;
	private Country country;
	private String email;
	private Date createTime;
	private Date lastLoginTime;
	private Map<String, String> parent;
	private java.lang.String state;
	private List<UserVO> children;// 子節點
	private int userType;
	private Set<Role> roleSet;
	
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
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

	public List<UserVO> getChildren() {
		return children;
	}

	public void setChildren(List<UserVO> children) {
		this.children = children;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

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

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", userName=" + userName + ", gender=" + gender + ", enable=" + enable + ", birthday=" + birthday + ", country=" + country + ", email=" + email + ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime + ", roleSet=" + roleSet + ", parent=" + parent + ", state=" + state + ", children=" + children + "]";
	}

	@Override
	public int compareTo(UserVO o) {
		return this.id.compareToIgnoreCase(o.getId());
	}

}
