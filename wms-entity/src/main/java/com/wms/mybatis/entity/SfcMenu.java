package com.wms.mybatis.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Set;

/**
 * @author OwenHuang
 * @Date 2013/5/30
 */
@Entity
public class SfcMenu implements Serializable{
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	private java.lang.String id;

	private java.lang.String menuName;
	
	private java.lang.String menuType;
	
	private java.lang.String url;
	
	private java.lang.String parentId;
	
	private java.lang.Integer displaySeq;
	
	private Set<SfcRole> roleSet;
	private Set<SfcBtn> btnSetMenu;
	private java.lang.String roleIDMenu;

	public String getRoleIDMenu() {
		return roleIDMenu;
	}

	public void setRoleIDMenu(String roleIDMenu) {
		this.roleIDMenu = roleIDMenu;
	}

	public Set<SfcBtn> getBtnSetMenu() {
		return btnSetMenu;
	}

	public void setBtnSetMenu(Set<SfcBtn> btnSetMenu) {
		this.btnSetMenu = btnSetMenu;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getMenuName() {
		return menuName;
	}

	public void setMenuName(java.lang.String menuName) {
		this.menuName = menuName;
	}

	public java.lang.String getMenuType() {
		return menuType;
	}

	public void setMenuType(java.lang.String menuType) {
		this.menuType = menuType;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	public java.lang.Integer getDisplaySeq() {
		return displaySeq;
	}

	public void setDisplaySeq(java.lang.Integer displaySeq) {
		this.displaySeq = displaySeq;
	}

	public Set<SfcRole> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<SfcRole> roleSet) {
		this.roleSet = roleSet;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.wms.mybatis.entity.SfcMenu)) return false;
		else {
			com.wms.mybatis.entity.SfcMenu menu = (com.wms.mybatis.entity.SfcMenu) obj;
			if (null == this.getId() || null == menu.getId()) return false;
			else return (this.getId().equals(menu.getId()));
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
		return "Menu [hashCode=" + hashCode + ", id=" + id + ", menuName="
				+ menuName + ", menuType=" + menuType + ", url=" + url
				+ ", parentId=" + parentId + ", displaySeq=" + displaySeq
				+ "]";
	}
}