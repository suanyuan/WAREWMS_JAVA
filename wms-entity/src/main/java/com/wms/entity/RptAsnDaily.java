package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the RPT_ASN_DAILY database table.
 * 
 */
@Entity
@Table(name="RPT_ASN_DAILY")
@NamedQuery(name="RptAsnDaily.findAll", query="SELECT r FROM RptAsnDaily r")
public class RptAsnDaily implements Serializable {
	private static final long serialVersionUID = 1L;

	private String asncreatetime;

	private BigDecimal asnlineno;

	@Id
	private String asnno;

	private String asnreference1inreport;

	private String asnreference2inreport;

	private String asnreference3inreport;

	private String asnreference4inreport;

	private String asnreference5inreport;

	private String asntype;

	private String carrierid;

	private String carriername;

	private String commodity;

	private BigDecimal cubic;

	private String customerid;

	private BigDecimal grossweight;

	private String inbounddate;

	private String inbounddatetext;

	private String lotatt01;

	private String lotatt02;

	private String lotatt03;

	private String lotatt04;

	private String lotatt05;

	private String lotatt06;

	private String notes;

	private String packid;

	private String packuom1;

	private BigDecimal qty;

	private String sku;

	@Column(name="SKU_DESCR_C")
	private String skuDescrC;

	@Column(name="SKU_DESCR_E")
	private String skuDescrE;

	private String skugroup;

	private String skutext;

	private String supplierid;

	private String suppliername;

	private String udf1;

	private String udf2;

	private String udf3;

	private String udf4;

	private String udf5;

	private String warehouseid;

	public RptAsnDaily() {
	}

	public String getAsncreatetime() {
		return this.asncreatetime;
	}

	public void setAsncreatetime(String asncreatetime) {
		this.asncreatetime = asncreatetime;
	}

	public BigDecimal getAsnlineno() {
		return this.asnlineno;
	}

	public void setAsnlineno(BigDecimal asnlineno) {
		this.asnlineno = asnlineno;
	}

	public String getAsnno() {
		return this.asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
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

	public String getAsnreference4inreport() {
		return this.asnreference4inreport;
	}

	public void setAsnreference4inreport(String asnreference4inreport) {
		this.asnreference4inreport = asnreference4inreport;
	}

	public String getAsnreference5inreport() {
		return this.asnreference5inreport;
	}

	public void setAsnreference5inreport(String asnreference5inreport) {
		this.asnreference5inreport = asnreference5inreport;
	}

	public String getAsntype() {
		return this.asntype;
	}

	public void setAsntype(String asntype) {
		this.asntype = asntype;
	}

	public String getCarrierid() {
		return this.carrierid;
	}

	public void setCarrierid(String carrierid) {
		this.carrierid = carrierid;
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

	public BigDecimal getGrossweight() {
		return this.grossweight;
	}

	public void setGrossweight(BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public String getInbounddate() {
		return this.inbounddate;
	}

	public void setInbounddate(String inbounddate) {
		this.inbounddate = inbounddate;
	}

	public String getInbounddatetext() {
		return this.inbounddatetext;
	}

	public void setInbounddatetext(String inbounddatetext) {
		this.inbounddatetext = inbounddatetext;
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

	public BigDecimal getQty() {
		return this.qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
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

	public String getSupplierid() {
		return this.supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getSuppliername() {
		return this.suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
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

	public String getUdf4() {
		return this.udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return this.udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

}