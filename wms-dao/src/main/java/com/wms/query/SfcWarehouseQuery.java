package com.wms.query;

public class SfcWarehouseQuery implements IQuery {

	private String id;
	
	private java.lang.String warehouseName;
	
	private java.lang.String defaultFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public java.lang.String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(java.lang.String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public java.lang.String getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(java.lang.String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

}