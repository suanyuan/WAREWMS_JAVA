package com.wms.query;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCustomer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptOrderPackingboxQuery implements IQuery {

	private java.lang.String address;
	private java.lang.String addtime;
	private java.lang.String alternateSku1;
	private java.lang.String carrierid;
	private java.lang.String consigneeid;
	private java.lang.String customerid;
	private java.lang.String descrC;
	private java.lang.String orderno;
	private java.math.BigDecimal qty;
	private java.lang.String sku;
	private java.lang.String soreference1;
	private java.lang.String soreference2;
	private java.lang.String tel;
	private java.lang.String traceid;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getAddtime() {
		return addtime;
	}

	public void setAddtime(java.lang.String addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAlternateSku1() {
		return alternateSku1;
	}

	public void setAlternateSku1(java.lang.String alternateSku1) {
		this.alternateSku1 = alternateSku1;
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

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
	}

	public java.math.BigDecimal getQty() {
		return qty;
	}

	public void setQty(java.math.BigDecimal qty) {
		this.qty = qty;
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

	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	public java.lang.String getTraceid() {
		return traceid;
	}

	public void setTraceid(java.lang.String traceid) {
		this.traceid = traceid;
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