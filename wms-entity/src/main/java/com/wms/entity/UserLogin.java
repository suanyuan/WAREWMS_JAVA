package com.wms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sfc_user")
public class UserLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id", length = 36)
	private java.lang.String id;

	@Column(name = "user_name", length = 36)
	private java.lang.String userName;

	@Column(name = "user_type")
	private int userType;

	@Column(name = "pwd", length = 50)
	private java.lang.String pwd;

	@Column(name = "gender", length = 1)
	private java.lang.String gender;

	@Column(name = "enable", length = 3)
	private java.lang.Byte enable;

	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 10)
	private java.util.Date birthday;

	@Column(name = "email", length = 50)
	private java.lang.String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private java.util.Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	private java.util.Date lastLoginTime;

	@Column(name = "session_id", length = 36)
	private java.lang.String sessionId;

	@Column(name = "node_id", length = 36)
	private String nodeId;

	@Column(name = "parent_node_id", length = 36)
	private java.lang.String parentNodeId;

	@Column(name = "merchant_id", length = 36)
	private String merchantId;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

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
}
