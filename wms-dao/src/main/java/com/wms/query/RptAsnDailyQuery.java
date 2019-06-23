package com.wms.query;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCustomer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class RptAsnDailyQuery implements IQuery {

	private java.lang.String asncreatetime;
	private java.math.BigDecimal asnlineno;
	private java.lang.String asnno;
	private java.lang.String asnreference1inreport;
	private java.lang.String asnreference2inreport;
	private java.lang.String asnreference3inreport;
	private java.lang.String asnreference4inreport;
	private java.lang.String asnreference5inreport;
	private java.lang.String asntype;
	private java.lang.String carrierid;
	private java.lang.String carriername;
	private java.lang.String commodity;
	private java.math.BigDecimal cubic;
	private java.lang.String customerid;
	private java.math.BigDecimal grossweight;
	private java.lang.String inbounddate;
	private java.lang.String inbounddatetext;
	private java.lang.String lotatt01;
	private java.lang.String lotatt02;
	private java.lang.String lotatt03;
	private java.lang.String lotatt04;
	private java.lang.String lotatt05;
	private java.lang.String lotatt06;
	private java.lang.String notes;
	private java.lang.String packid;
	private java.lang.String packuom1;
	private java.math.BigDecimal qty;
	private java.lang.String sku;
	private java.lang.String skuDescrC;
	private java.lang.String skuDescrE;
	private java.lang.String skugroup;
	private java.lang.String skutext;
	private java.lang.String supplierid;
	private java.lang.String suppliername;
	private java.lang.String udf1;
	private java.lang.String udf2;
	private java.lang.String udf3;
	private java.lang.String udf4;
	private java.lang.String udf5;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;

	public java.lang.String getAsncreatetime() {
		return asncreatetime;
	}

	public void setAsncreatetime(java.lang.String asncreatetime) {
		this.asncreatetime = asncreatetime;
	}

	public java.math.BigDecimal getAsnlineno() {
		return asnlineno;
	}

	public void setAsnlineno(java.math.BigDecimal asnlineno) {
		this.asnlineno = asnlineno;
	}

	public java.lang.String getAsnno() {
		return asnno;
	}

	public void setAsnno(java.lang.String asnno) {
		this.asnno = asnno;
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

	public java.lang.String getAsnreference4inreport() {
		return asnreference4inreport;
	}

	public void setAsnreference4inreport(java.lang.String asnreference4inreport) {
		this.asnreference4inreport = asnreference4inreport;
	}

	public java.lang.String getAsnreference5inreport() {
		return asnreference5inreport;
	}

	public void setAsnreference5inreport(java.lang.String asnreference5inreport) {
		this.asnreference5inreport = asnreference5inreport;
	}

	public java.lang.String getAsntype() {
		return asntype;
	}

	public void setAsntype(java.lang.String asntype) {
		this.asntype = asntype;
	}

	public java.lang.String getCarrierid() {
		return carrierid;
	}

	public void setCarrierid(java.lang.String carrierid) {
		this.carrierid = carrierid;
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

	public java.math.BigDecimal getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(java.math.BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public java.lang.String getInbounddate() {
		return inbounddate;
	}

	public void setInbounddate(java.lang.String inbounddate) {
		this.inbounddate = inbounddate;
	}

	public java.lang.String getInbounddatetext() {
		return inbounddatetext;
	}

	public void setInbounddatetext(java.lang.String inbounddatetext) {
		this.inbounddatetext = inbounddatetext;
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

	public java.lang.String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(java.lang.String supplierid) {
		this.supplierid = supplierid;
	}

	public java.lang.String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(java.lang.String suppliername) {
		this.suppliername = suppliername;
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

	public java.lang.String getUdf4() {
		return udf4;
	}

	public void setUdf4(java.lang.String udf4) {
		this.udf4 = udf4;
	}

	public java.lang.String getUdf5() {
		return udf5;
	}

	public void setUdf5(java.lang.String udf5) {
		this.udf5 = udf5;
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