package com.wms.vo.form;

/**
 * 登入介面Form
 * @author OwenHuang
 * @Date 2013/5/29
 */
public class SfcUserLoginForm {
	
	private String id;
	
	private String userName;
	
	private String pwd;
	
	private String warehouseId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	
}
