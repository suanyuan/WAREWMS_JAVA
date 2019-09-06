package com.wms.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class CouRequestDetailsVO {

	private String cycleCountno;
	private int cycleCountlineno;
	private String customerid;
	private String sku;
	private String locationid;
	private double qtyInv;
	private double qtyAct;
	private String lotatt04;
	private String lotatt05;
	private Date addtime;
	private String addwho;
	@JSONField(format="yyyy-MM-dd")
	private Date edittime;
	private String editwho;
	private String userdefined1;
	private String userdefined2;
	private String userdefined3;
//	双击查看明细
    private String reservedfield01;
	private String descre;
	private String descrc;
	private String productLineName;

	public String getCycleCountno() {
		return cycleCountno;
	}

	public void setCycleCountno(String cycleCountno) {
		this.cycleCountno = cycleCountno;
	}

	public int getCycleCountlineno() {
		return cycleCountlineno;
	}

	public void setCycleCountlineno(int cycleCountlineno) {
		this.cycleCountlineno = cycleCountlineno;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public double getQtyInv() {
		return qtyInv;
	}

	public void setQtyInv(double qtyInv) {
		this.qtyInv = qtyInv;
	}

	public double getQtyAct() {
		return qtyAct;
	}

	public void setQtyAct(double qtyAct) {
		this.qtyAct = qtyAct;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getUserdefined1() {
		return userdefined1;
	}

	public void setUserdefined1(String userdefined1) {
		this.userdefined1 = userdefined1;
	}

	public String getUserdefined2() {
		return userdefined2;
	}

	public void setUserdefined2(String userdefined2) {
		this.userdefined2 = userdefined2;
	}

	public String getUserdefined3() {
		return userdefined3;
	}

	public void setUserdefined3(String userdefined3) {
		this.userdefined3 = userdefined3;
	}

	public String getReservedfield01() {
		return reservedfield01;
	}

	public void setReservedfield01(String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public String getDescre() {
		return descre;
	}

	public void setDescre(String descre) {
		this.descre = descre;
	}

	public String getDescrc() {
		return descrc;
	}

	public void setDescrc(String descrc) {
		this.descrc = descrc;
	}

	public String getProductLineName() {
		return productLineName;
	}

	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}
}