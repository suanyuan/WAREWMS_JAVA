package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RPT_SO_DAILY database table.
 * 
 */
@Entity
@Table(name="RPT_SO_DAILY")
@NamedQuery(name="RptSoDaily.findAll", query="SELECT r FROM RptSoDaily r")
public class RptSoDaily implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String asnreference1inreport;

	private String asnreference2inreport;

	private String asnreference3inreport;

	private String carriername;

	private String commodity;

	private String consigneename;

	private BigDecimal cubic;

	private String customerid;

	private BigDecimal doclineno;

	private String expdeliverytime;

	private BigDecimal grossweight;

	private String lotatt01;

	private String lotatt02;

	private String lotatt03;

	private String lotatt04;

	private String lotatt05;

	private String lotatt06;

	private String notes;

	@Id
	private String orderno;

	private String ordertime;

	private String ordertype;

	private String packid;

	private String packuom1;

	private BigDecimal qtyordered;

	private BigDecimal qtyshipped;

	private String shipmenttime;

	private String shipmenttimetext;

	private String sku;

	@Column(name="SKU_DESCR_C")
	private String skuDescrC;

	@Column(name="SKU_DESCR_E")
	private String skuDescrE;

	private String skugroup;

	private String skutext;

	private String udf1;

	private String udf2;

	private String udf3;

	private String warehouseid;

	public RptSoDaily() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAsnreference1inreport() {
		return this.asnreference1inreport;
	}

	public void setAsnreference1inreport(String asnreference1inreport) {
		this.asnreference1inreport = asnreference1inreport;
	}

	public String getAsnreference2inreport() {
		return this.asnreference2inreport;
	}

	public void setAsnreference2inreport(String asnreference2inreport) {
		this.asnreference2inreport = asnreference2inreport;
	}

	public String getAsnreference3inreport() {
		return this.asnreference3inreport;
	}

	public void setAsnreference3inreport(String asnreference3inreport) {
		this.asnreference3inreport = asnreference3inreport;
	}

	public String getCarriername() {
		return this.carriername;
	}

	public void setCarriername(String carriername) {
		this.carriername = carriername;
	}

	public String getCommodity() {
		return this.commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getConsigneename() {
		return this.consigneename;
	}

	public void setConsigneename(String consigneename) {
		this.consigneename = consigneename;
	}

	public BigDecimal getCubic() {
		return this.cubic;
	}

	public void setCubic(BigDecimal cubic) {
		this.cubic = cubic;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public BigDecimal getDoclineno() {
		return this.doclineno;
	}

	public void setDoclineno(BigDecimal doclineno) {
		this.doclineno = doclineno;
	}

	public String getExpdeliverytime() {
		return this.expdeliverytime;
	}

	public void setExpdeliverytime(String expdeliverytime) {
		this.expdeliverytime = expdeliverytime;
	}

	public BigDecimal getGrossweight() {
		return this.grossweight;
	}

	public void setGrossweight(BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public String getLotatt01() {
		return this.lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return this.lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt03() {
		return this.lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt04() {
		return this.lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return this.lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt06() {
		return this.lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getOrdertime() {
		return this.ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getPackid() {
		return this.packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getPackuom1() {
		return this.packuom1;
	}

	public void setPackuom1(String packuom1) {
		this.packuom1 = packuom1;
	}

	public BigDecimal getQtyordered() {
		return this.qtyordered;
	}

	public void setQtyordered(BigDecimal qtyordered) {
		this.qtyordered = qtyordered;
	}

	public BigDecimal getQtyshipped() {
		return this.qtyshipped;
	}

	public void setQtyshipped(BigDecimal qtyshipped) {
		this.qtyshipped = qtyshipped;
	}

	public String getShipmenttime() {
		return this.shipmenttime;
	}

	public void setShipmenttime(String shipmenttime) {
		this.shipmenttime = shipmenttime;
	}

	public String getShipmenttimetext() {
		return this.shipmenttimetext;
	}

	public void setShipmenttimetext(String shipmenttimetext) {
		this.shipmenttimetext = shipmenttimetext;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuDescrC() {
		return this.skuDescrC;
	}

	public void setSkuDescrC(String skuDescrC) {
		this.skuDescrC = skuDescrC;
	}

	public String getSkuDescrE() {
		return this.skuDescrE;
	}

	public void setSkuDescrE(String skuDescrE) {
		this.skuDescrE = skuDescrE;
	}

	public String getSkugroup() {
		return this.skugroup;
	}

	public void setSkugroup(String skugroup) {
		this.skugroup = skugroup;
	}

	public String getSkutext() {
		return this.skutext;
	}

	public void setSkutext(String skutext) {
		this.skutext = skutext;
	}

	public String getUdf1() {
		return this.udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return this.udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return this.udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}