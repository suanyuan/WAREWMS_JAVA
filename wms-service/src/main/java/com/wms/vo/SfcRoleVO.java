package com.wms.vo;

import java.util.List;
import java.util.Set;

import com.wms.mybatis.entity.SfcBtn;
import com.wms.mybatis.entity.SfcMenu;

public class SfcRoleVO {

	private java.lang.String id;
	private java.lang.String roleName;
	private List<SfcMenu> menuList;
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

	public List<SfcMenu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<SfcMenu> menuList) {
		this.menuList = menuList;
	}

	public Set<SfcBtn> getBtnSet() {
		return btnSet;
	}

	public void setBtnSet(Set<SfcBtn> btnSet) {
		this.btnSet = btnSet;
	}
}
