package com.wms.vo;

import java.util.Map;
import java.util.Set;

import com.wms.mybatis.entity.SfcRole;

public class SfcMenuVO implements Comparable<SfcMenuVO> {
	private java.lang.String id;
	private java.lang.String menuName;
	private java.lang.String menuType;
	private java.lang.String url;
	private Map<String, String> parent;
	private java.lang.Integer displaySeq;
	private java.lang.String state;
	private Set<SfcMenuVO> children;// 子節點
	private Set<SfcRole> roleSet;
	
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

	public Map<String, String> getParent() {
		return parent;
	}

	public void setParent(Map<String, String> parent) {
		this.parent = parent;
	}

	public java.lang.Integer getDisplaySeq() {
		return displaySeq;
	}

	public void setDisplaySeq(java.lang.Integer displaySeq) {
		this.displaySeq = displaySeq;
	}

	public java.lang.String getState() {
		return state;
	}

	public void setState(java.lang.String state) {
		this.state = state;
	}

	public Set<SfcMenuVO> getChildren() {
		return children;
	}

	public void setChildren(Set<SfcMenuVO> children) {
		this.children = children;
	}

	public Set<SfcRole> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<SfcRole> roleSet) {
		this.roleSet = roleSet;
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
		SfcMenuVO other = (SfcMenuVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(SfcMenuVO other) {
		return displaySeq - other.getDisplaySeq(); 
	}

}
