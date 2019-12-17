package com.wms.vo.form;

public class RoleForm {
	private String roleId;
	private String roleName;
	private String btns;
	private String roleIDMenu;

	public String getRoleIDMenu() {
		return roleIDMenu;
	}

	public void setRoleIDMenu(String roleIDMenu) {
		this.roleIDMenu = roleIDMenu;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getBtns() {
		return btns;
	}

	public void setBtns(String btns) {
		this.btns = btns;
	}
}
