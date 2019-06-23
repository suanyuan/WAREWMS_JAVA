package com.wms.query;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCustomer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptAsnDetailQuery implements IQuery {

	private java.lang.String addtime;
	private java.lang.String addtimetext;
	private java.lang.String asnno;
	private java.lang.String asnreference1;
	private java.lang.String codenameC;
	private java.lang.String codenameC1;
	private java.math.BigDecimal cube;
	private java.lang.String customerid;
	private java.lang.String descrC;
	private java.lang.String edittime;
	private java.lang.String edittimetext;
	private java.math.BigDecimal expectedqty;
	private java.lang.String lastreceivingtime;
	private java.lang.String notes;
	private java.math.BigDecimal receivedqty;
	private java.lang.String receivedtime;
	private java.lang.String reservedfield01;
	private java.lang.String sku;
	private java.lang.String userdefine2;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;

	public java.lang.String getAddtime() {
		return addtime;
	}

	public void setAddtime(java.lang.String addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddtimetext() {
		return addtimetext;
	}

	public void setAddtimetext(java.lang.String addtimetext) {
		this.addtimetext = addtimetext;
	}

	public java.lang.String getAsnno() {
		return asnno;
	}

	public void setAsnno(java.lang.String asnno) {
		this.asnno = asnno;
	}

	public java.lang.String getAsnreference1() {
		return asnreference1;
	}

	public void setAsnreference1(java.lang.String asnreference1) {
		this.asnreference1 = asnreference1;
	}

	public java.lang.String getCodenameC() {
		return codenameC;
	}

	public void setCodenameC(java.lang.String codenameC) {
		this.codenameC = codenameC;
	}

	public java.lang.String getCodenameC1() {
		return codenameC1;
	}

	public void setCodenameC1(java.lang.String codenameC1) {
		this.codenameC1 = codenameC1;
	}

	public java.math.BigDecimal getCube() {
		return cube;
	}

	public void setCube(java.math.BigDecimal cube) {
		this.cube = cube;
	}

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.lang.String getDescrC() {
		return descrC;
	}

	public void setDescrC(java.lang.String descrC) {
		this.descrC = descrC;
	}

	public java.lang.String getEdittime() {
		return edittime;
	}

	public void setEdittime(java.lang.String edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEdittimetext() {
		return edittimetext;
	}

	public void setEdittimetext(java.lang.String edittimetext) {
		this.edittimetext = edittimetext;
	}

	public java.math.BigDecimal getExpectedqty() {
		return expectedqty;
	}

	public void setExpectedqty(java.math.BigDecimal expectedqty) {
		this.expectedqty = expectedqty;
	}

	public java.lang.String getLastreceivingtime() {
		return lastreceivingtime;
	}

	public void setLastreceivingtime(java.lang.String lastreceivingtime) {
		this.lastreceivingtime = lastreceivingtime;
	}

	public java.lang.String getNotes() {
		return notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}

	public java.math.BigDecimal getReceivedqty() {
		return receivedqty;
	}

	public void setReceivedqty(java.math.BigDecimal receivedqty) {
		this.receivedqty = receivedqty;
	}

	public java.lang.String getReceivedtime() {
		return receivedtime;
	}

	public void setReceivedtime(java.lang.String receivedtime) {
		this.receivedtime = receivedtime;
	}

	public java.lang.String getReservedfield01() {
		return reservedfield01;
	}

	public void setReservedfield01(java.lang.String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(java.lang.String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}
	
	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}


}