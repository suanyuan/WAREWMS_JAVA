package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class CouRequestDetailsForm {

	private String cycleCountno;
	private String cycleCountlineno;
	private String customerid;
	private String sku;
	private String locationid;
	private double qtyInv;
	private double qtyAct;
	private String lotatt04;
	private String lotatt05;
	private Date addtime;
	private String addwho;
	private Date edittime;
	private String editwho;
	private String userdefined1;
	private String userdefined2;
	private String userdefined3;

	public String getCycleCountno() {
		return cycleCountno;
	}

	public void setCycleCountno(String cycleCountno) {
		this.cycleCountno = cycleCountno;
	}

	public String getCycleCountlineno() {
		return cycleCountlineno;
	}

	public void setCycleCountlineno(String cycleCountlineno) {
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

}