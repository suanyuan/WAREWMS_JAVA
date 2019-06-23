package com.wms.mybatis.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author 
 * @Date 
 */
@Entity
public class SfcRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;
	
	@Id
	private java.lang.String id;

	private java.lang.String roleName;

	private Set<SfcMenu> menuSet;
	
	private Set<SfcBtn> btnSet;
	
	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getRoleName() {
		return roleName;
	}

	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}

	public Set<SfcMenu> getMenuSet() {
		return menuSet;
	}

	public void setMenuSet(Set<SfcMenu> menuSet) {
		this.menuSet = menuSet;
	}

	public Set<SfcBtn> getBtnSet() {
		return btnSet;
	}

	public void setBtnSet(Set<SfcBtn> btnSet) {
		this.btnSet = btnSet;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.wms.mybatis.entity.SfcRole)) return false;
		else {
			com.wms.mybatis.entity.SfcRole role = (com.wms.mybatis.entity.SfcRole) obj;
			if (null == this.getId() || null == role.getId()) return false;
			else return (this.getId().equals(role.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + "]";
	}
}