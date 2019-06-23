package com.wms.query;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCustomer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptSoDailyQuery implements IQuery {

	private java.lang.String address;
	private java.lang.String asnreference1inreport;
	private java.lang.String asnreference2inreport;
	private java.lang.String asnreference3inreport;
	private java.lang.String carriername;
	private java.lang.String commodity;
	private java.lang.String consigneename;
	private java.math.BigDecimal cubic;
	private java.lang.String customerid;
	private java.math.BigDecimal doclineno;
	private java.lang.String expdeliverytime;
	private java.math.BigDecimal grossweight;
	private java.lang.String lotatt01;
	private java.lang.String lotatt02;
	private java.lang.String lotatt03;
	private java.lang.String lotatt04;
	private java.lang.String lotatt05;
	private java.lang.String lotatt06;
	private java.lang.String notes;
	private java.lang.String orderno;
	private java.lang.String ordertime;
	private java.lang.String ordertype;
	private java.lang.String packid;
	private java.lang.String packuom1;
	private java.math.BigDecimal qtyordered;
	private java.math.BigDecimal qtyshipped;
	private java.lang.String shipmenttime;
	private java.lang.String shipmenttimetext;
	private java.lang.String sku;
	private java.lang.String skuDescrC;
	private java.lang.String skuDescrE;
	private java.lang.String skugroup;
	private java.lang.String skutext;
	private java.lang.String udf1;
	private java.lang.String udf2;
	private java.lang.String udf3;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getAsnreference1inreport() {
		return asnreference1inreport;
	}

	public void setAsnreference1inreport(java.lang.String asnreference1inreport) {
		this.asnreference1inreport = asnreference1inreport;
	}

	public java.lang.String getAsnreference2inreport() {
		return asnreference2inreport;
	}

	public void setAsnreference2inreport(java.lang.String asnreference2inreport) {
		this.asnreference2inreport = asnreference2inreport;
	}

	public java.lang.String getAsnreference3inreport() {
		return asnreference3inreport;
	}

	public void setAsnreference3inreport(java.lang.String asnreference3inreport) {
		this.asnreference3inreport = asnreference3inreport;
	}

	public java.lang.String getCarriername() {
		return carriername;
	}

	public void setCarriername(java.lang.String carriername) {
		this.carriername = carriername;
	}

	public java.lang.String getCommodity() {
		return commodity;
	}

	public void setCommodity(java.lang.String commodity) {
		this.commodity = commodity;
	}

	public java.lang.String getConsigneename() {
		return consigneename;
	}

	public void setConsigneename(java.lang.String consigneename) {
		this.consigneename = consigneename;
	}

	public java.math.BigDecimal getCubic() {
		return cubic;
	}

	public void setCubic(java.math.BigDecimal cubic) {
		this.cubic = cubic;
	}

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.math.BigDecimal getDoclineno() {
		return doclineno;
	}

	public void setDoclineno(java.math.BigDecimal doclineno) {
		this.doclineno = doclineno;
	}

	public java.lang.String getExpdeliverytime() {
		return expdeliverytime;
	}

	public void setExpdeliverytime(java.lang.String expdeliverytime) {
		this.expdeliverytime = expdeliverytime;
	}

	public java.math.BigDecimal getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(java.math.BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public java.lang.String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(java.lang.String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public java.lang.String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(java.lang.String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public java.lang.String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(java.lang.String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public java.lang.String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(java.lang.String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public java.lang.String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(java.lang.String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public java.lang.String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(java.lang.String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public java.lang.String getNotes() {
		return notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
	}

	public java.lang.String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(java.lang.String ordertime) {
		this.ordertime = ordertime;
	}

	public java.lang.String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(java.lang.String ordertype) {
		this.ordertype = ordertype;
	}

	public java.lang.String getPackid() {
		return packid;
	}

	public void setPackid(java.lang.String packid) {
		this.packid = packid;
	}

	public java.lang.String getPackuom1() {
		return packuom1;
	}

	public void setPackuom1(java.lang.String packuom1) {
		this.packuom1 = packuom1;
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

	public java.lang.String getShipmenttime() {
		return shipmenttime;
	}

	public void setShipmenttime(java.lang.String shipmenttime) {
		this.shipmenttime = shipmenttime;
	}

	public java.lang.String getShipmenttimetext() {
		return shipmenttimetext;
	}

	public void setShipmenttimetext(java.lang.String shipmenttimetext) {
		this.shipmenttimetext = shipmenttimetext;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getSkuDescrC() {
		return skuDescrC;
	}

	public void setSkuDescrC(java.lang.String skuDescrC) {
		this.skuDescrC = skuDescrC;
	}

	public java.lang.String getSkuDescrE() {
		return skuDescrE;
	}

	public void setSkuDescrE(java.lang.String skuDescrE) {
		this.skuDescrE = skuDescrE;
	}

	public java.lang.String getSkugroup() {
		return skugroup;
	}

	public void setSkugroup(java.lang.String skugroup) {
		this.skugroup = skugroup;
	}

	public java.lang.String getSkutext() {
		return skutext;
	}

	public void setSkutext(java.lang.String skutext) {
		this.skutext = skutext;
	}

	public java.lang.String getUdf1() {
		return udf1;
	}

	public void setUdf1(java.lang.String udf1) {
		this.udf1 = udf1;
	}

	public java.lang.String getUdf2() {
		return udf2;
	}

	public void setUdf2(java.lang.String udf2) {
		this.udf2 = udf2;
	}

	public java.lang.String getUdf3() {
		return udf3;
	}

	public void setUdf3(java.lang.String udf3) {
		this.udf3 = udf3;
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