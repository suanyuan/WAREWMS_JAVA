package com.wms.vo.form;

import java.util.Date;

public class ViewInvLocationExportForm {

	private java.lang.String fmcustomerid;
	private java.lang.String fmlocation;
	private java.lang.String fmsku;
	private java.lang.String warehouseid;
	private java.lang.String name;
	private java.lang.String lotatt04;//批号
	private  String lotatt05;      //序列号
	private  String lotatt12;      //产品名称
	private  String lotatt14;      //入库单号
	private Date  lotatt02Start;      //失效期查询开始
	private  Date lotatt02End;      //失效期查询结束
	private Date  lotatt03Start;      //入库日期查询开始
	private  Date lotatt03End;      //入库日期查询结束
	private String token;

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public Date getLotatt03Start() {
		return lotatt03Start;
	}

	public void setLotatt03Start(Date lotatt03Start) {
		this.lotatt03Start = lotatt03Start;
	}

	public Date getLotatt03End() {
		return lotatt03End;
	}

	public void setLotatt03End(Date lotatt03End) {
		this.lotatt03End = lotatt03End;
	}

	public Date getLotatt02Start() {
		return lotatt02Start;
	}

	public void setLotatt02Start(Date lotatt02Start) {
		this.lotatt02Start = lotatt02Start;
	}

	public Date getLotatt02End() {
		return lotatt02End;
	}

	public void setLotatt02End(Date lotatt02End) {
		this.lotatt02End = lotatt02End;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public java.lang.String getFmcustomerid() {
		return fmcustomerid;
	}
	public void setFmcustomerid(java.lang.String fmcustomerid) {
		this.fmcustomerid = fmcustomerid;
	}
	public java.lang.String getFmlocation() {
		return fmlocation;
	}
	public void setFmlocation(java.lang.String fmlocation) {
		this.fmlocation = fmlocation;
	}
	public java.lang.String getFmsku() {
		return fmsku;
	}
	public void setFmsku(java.lang.String fmsku) {
		this.fmsku = fmsku;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}
	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}