package com.wms.vo;

import java.util.List;
import java.util.Set;

import com.wms.entity.Btn;
import com.wms.entity.Menu;

public class RoleVO {

	private java.lang.String id;
	private java.lang.String roleName;
	private List<Menu> menuList;
	private Set<Btn> btnSet;
	
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

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Set<Btn> getBtnSet() {
		return btnSet;
	}

	public void setBtnSet(Set<Btn> btnSet) {
		this.btnSet = btnSet;
	}
}
