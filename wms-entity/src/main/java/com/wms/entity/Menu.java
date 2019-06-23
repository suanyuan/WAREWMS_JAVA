package com.wms.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author OwenHuang
 * @Date 2013/5/30
 */
@Entity
@Table(name="sfc_menu")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu implements Serializable{
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "menu_id", length=36)
	private java.lang.String id;

	@Column(name = "menu_name", length=25)
	private java.lang.String menuName;
	
	@Column(name = "menu_type", length=25)
	private java.lang.String menuType;
	
	@Column(name = "url", length=100)
	private java.lang.String url;
	
	@Column(name = "parent_id", length=36)
	private java.lang.String parentId;
	
	@Column(name = "display_seq", length=25)
	private java.lang.Integer displaySeq;

	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name = "sfc_role_menu", joinColumns = { @JoinColumn(name = "menu_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roleSet;

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

	@JsonIgnore
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.wms.entity.Menu)) return false;
		else {
			com.wms.entity.Menu menu = (com.wms.entity.Menu) obj;
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