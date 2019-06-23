package com.wms.query;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCustomer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptOrderdetailHisQuery implements IQuery {

	private java.lang.String addtime;
	private java.lang.String addtimetext;
	private java.lang.String cAddress1;
	private java.lang.String cAddress2;
	private java.lang.String cCity;
	private java.lang.String cProvince;
	private java.lang.String carrierid;
	private java.lang.String consigneeid;
	private java.lang.String customerid;
	private java.lang.String descrC;
	private java.lang.String hEdi02;
	private java.lang.String hEdi04;
	private java.lang.String issuepartyid;
	private java.lang.String lastshipmenttime;
	private java.lang.String lastshipmenttimetext;
	private java.lang.String orderno;
	private java.math.BigDecimal price;
	private java.math.BigDecimal qtyordered;
	private java.math.BigDecimal qtyshipped;
	private java.lang.String sku;
	private java.lang.String soreference1;
	private java.lang.String soreference2;
	private java.lang.String sostatus;
	private java.lang.String userdefine5;
	private java.lang.String warehouseid;
	private java.lang.String waveno;
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

	public java.lang.String getCAddress1() {
		return cAddress1;
	}

	public void setCAddress1(java.lang.String cAddress1) {
		this.cAddress1 = cAddress1;
	}

	public java.lang.String getCAddress2() {
		return cAddress2;
	}

	public void setCAddress2(java.lang.String cAddress2) {
		this.cAddress2 = cAddress2;
	}

	public java.lang.String getCCity() {
		return cCity;
	}

	public void setCCity(java.lang.String cCity) {
		this.cCity = cCity;
	}

	public java.lang.String getCProvince() {
		return cProvince;
	}

	public void setCProvince(java.lang.String cProvince) {
		this.cProvince = cProvince;
	}

	public java.lang.String getCarrierid() {
		return carrierid;
	}

	public void setCarrierid(java.lang.String carrierid) {
		this.carrierid = carrierid;
	}

	public java.lang.String getConsigneeid() {
		return consigneeid;
	}

	public void setConsigneeid(java.lang.String consigneeid) {
		this.consigneeid = consigneeid;
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

	public java.lang.String getHEdi02() {
		return hEdi02;
	}

	public void setHEdi02(java.lang.String hEdi02) {
		this.hEdi02 = hEdi02;
	}

	public java.lang.String getHEdi04() {
		return hEdi04;
	}

	public void setHEdi04(java.lang.String hEdi04) {
		this.hEdi04 = hEdi04;
	}

	public java.lang.String getIssuepartyid() {
		return issuepartyid;
	}

	public void setIssuepartyid(java.lang.String issuepartyid) {
		this.issuepartyid = issuepartyid;
	}

	public java.lang.String getLastshipmenttime() {
		return lastshipmenttime;
	}

	public void setLastshipmenttime(java.lang.String lastshipmenttime) {
		this.lastshipmenttime = lastshipmenttime;
	}

	public java.lang.String getLastshipmenttimetext() {
		return lastshipmenttimetext;
	}

	public void setLastshipmenttimetext(java.lang.String lastshipmenttimetext) {
		this.lastshipmenttimetext = lastshipmenttimetext;
	}

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.math.BigDecimal getQtyordered() {
		return qtyordered;
	}

	public void setQtyordered(java.math.BigDecimal qtyordered) {
		this.qtyordered = qtyordered;
	}

	public java.math.BigDecimal getQtyshipped() {
		return qtyshipped;
	}

	public void setQtyshipped(java.math.BigDecimal qtyshipped) {
		this.qtyshipped = qtyshipped;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getSoreference1() {
		return soreference1;
	}

	public void setSoreference1(java.lang.String soreference1) {
		this.soreference1 = soreference1;
	}

	public java.lang.String getSoreference2() {
		return soreference2;
	}

	public void setSoreference2(java.lang.String soreference2) {
		this.soreference2 = soreference2;
	}

	public java.lang.String getSostatus() {
		return sostatus;
	}

	public void setSostatus(java.lang.String sostatus) {
		this.sostatus = sostatus;
	}

	public java.lang.String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(java.lang.String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public java.lang.String getWaveno() {
		return waveno;
	}

	public void setWaveno(java.lang.String waveno) {
		this.waveno = waveno;
	}

	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}
}