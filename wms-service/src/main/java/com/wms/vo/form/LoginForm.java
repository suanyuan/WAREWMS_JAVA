package com.wms.vo.form;

/**
 * 登入介面Form
 * @author OwenHuang
 * @Date 2013/5/29
 */
public class LoginForm {
	private String username;
	private String password;
	private String warehouseId;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
}
