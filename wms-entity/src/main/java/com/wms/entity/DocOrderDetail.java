package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOC_ORDER_DETAILS database table.
 * 
 */
@Entity
public class DocOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private String customerid;
	@Id
	private String orderno;

	private long orderlineno;

	private String linestatus;

	private String linestatusName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	private String alternativesku;

	private BigDecimal cubic;

	private BigDecimal grossweight;

	private BigDecimal price;

	private String pickzone;

	private String location;

	private String lotnum;

	private String notes;

	private String packid;

	private String uom;

	private String lotatt01;

	private String lotatt02;

	private String lotatt03;

	private BigDecimal qtyallocated;

	private BigDecimal qtyallocatedEach;

	private BigDecimal qtyordered;

	private BigDecimal qtyorderedEach;

	private BigDecimal qtypicked;

	private BigDecimal qtypickedEach;

	private BigDecimal qtyreleased;

	private BigDecimal qtyshipped;

	private BigDecimal qtyshippedEach;

	private BigDecimal qtysoftallocated;

	private BigDecimal qtysoftallocatedEach;

	private String sku;

	private String skuName;

	private String skuNameE;

	public DocOrderDetail() {
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public long getOrderlineno() {
		return orderlineno;
	}

	public void setOrderlineno(long orderlineno) {
		this.orderlineno = orderlineno;
	}

	public String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
	}

	public String getLinestatusName() {
		return linestatusName;
	}

	public void setLinestatusName(String linestatusName) {
		this.linestatusName = linestatusName;
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

	public String getAlternativesku() {
		return alternativesku;
	}

	public void setAlternativesku(String alternativesku) {
		this.alternativesku = alternativesku;
	}

	public BigDecimal getCubic() {
		return cubic;
	}

	public void setCubic(BigDecimal cubic) {
		this.cubic = cubic;
	}

	public BigDecimal getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPickzone() {
		return pickzone;
	}

	public void setPickzone(String pickzone) {
		this.pickzone = pickzone;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public BigDecimal getQtyallocated() {
		return qtyallocated;
	}

	public void setQtyallocated(BigDecimal qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public BigDecimal getQtyallocatedEach() {
		return qtyallocatedEach;
	}

	public void setQtyallocatedEach(BigDecimal qtyallocatedEach) {
		this.qtyallocatedEach = qtyallocatedEach;
	}

	public BigDecimal getQtyordered() {
		return qtyordered;
	}

	public void setQtyordered(BigDecimal qtyordered) {
		this.qtyordered = qtyordered;
	}

	public BigDecimal getQtyorderedEach() {
		return qtyorderedEach;
	}

	public void setQtyorderedEach(BigDecimal qtyorderedEach) {
		this.qtyorderedEach = qtyorderedEach;
	}

	public BigDecimal getQtypicked() {
		return qtypicked;
	}

	public void setQtypicked(BigDecimal qtypicked) {
		this.qtypicked = qtypicked;
	}

	public BigDecimal getQtypickedEach() {
		return qtypickedEach;
	}

	public void setQtypickedEach(BigDecimal qtypickedEach) {
		this.qtypickedEach = qtypickedEach;
	}

	public BigDecimal getQtyreleased() {
		return qtyreleased;
	}

	public void setQtyreleased(BigDecimal qtyreleased) {
		this.qtyreleased = qtyreleased;
	}

	public BigDecimal getQtyshipped() {
		return qtyshipped;
	}

	public void setQtyshipped(BigDecimal qtyshipped) {
		this.qtyshipped = qtyshipped;
	}

	public BigDecimal getQtyshippedEach() {
		return qtyshippedEach;
	}

	public void setQtyshippedEach(BigDecimal qtyshippedEach) {
		this.qtyshippedEach = qtyshippedEach;
	}

	public BigDecimal getQtysoftallocated() {
		return qtysoftallocated;
	}

	public void setQtysoftallocated(BigDecimal qtysoftallocated) {
		this.qtysoftallocated = qtysoftallocated;
	}

	public BigDecimal getQtysoftallocatedEach() {
		return qtysoftallocatedEach;
	}

	public void setQtysoftallocatedEach(BigDecimal qtysoftallocatedEach) {
		this.qtysoftallocatedEach = qtysoftallocatedEach;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuNameE() {
		return skuNameE;
	}

	public void setSkuNameE(String skuNameE) {
		this.skuNameE = skuNameE;
	}

}