package com.wms.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author OwenHuang
 * @Date 2013/5/30
 */
@Entity
@Table(name="sfc_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User implements Serializable {
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Column(name = "node_id", length=36)
	private String nodeId;

	@Column(name = "parent_node_id", length=36)
	private java.lang.String parentNodeId;
	
	@Id
	@Column(name = "user_id", length=36)
	private String id;

	@Column(name = "user_name", length=36)
	private java.lang.String userName;
	
	@Column(name = "pwd", length=50)
	private java.lang.String pwd;
	
	@Column(name = "gender", length=1)
	private java.lang.String gender;
	
	@Column(name = "enable", length=3)
	private java.lang.Byte enable;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length=10)
	private java.util.Date birthday;
	
	@Column(name = "email", length=50)
	private java.lang.String email;
	
	@Column(name = "user_type")
	private int userType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private java.util.Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	private java.util.Date lastLoginTime;
	
	@Column(name = "session_id", length=36)
	private java.lang.String sessionId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="country_id")
	private com.wms.entity.Country country;

	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name = "sfc_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roleSet;
		
	
	@Column(name = "merchant_id", length = 36)
	private String merchantId;
	

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public com.wms.entity.Country getCountry() {
		return country;
	}

	public void setCountry(com.wms.entity.Country country) {
		this.country = country;
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

	@JsonIgnore
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	
	
	/**
	* @Title: getMerchantId
	* @Description: (  )
	*/
	public String getMerchantId() {
		return merchantId;
	}

	/**
	* @Title: setMerchantId
	* @Description: (  )
	*/
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	
	    @Override
	public String toString() {
		return "User [hashCode=" + hashCode + ", nodeId=" + nodeId + ", parentNodeId=" + parentNodeId + ", id=" + id
				+ ", userName=" + userName + ", pwd=" + pwd + ", gender=" + gender + ", enable=" + enable
				+ ", birthday=" + birthday + ", email=" + email + ", userType=" + userType + ", createTime="
				+ createTime + ", lastLoginTime=" + lastLoginTime + ", sessionId=" + sessionId + ", country=" + country
				+ ", roleSet=" + roleSet + ", merchantId=" + merchantId + "]";
	}
}