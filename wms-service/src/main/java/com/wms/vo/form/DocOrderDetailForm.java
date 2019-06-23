package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocOrderDetailForm {

	private java.lang.String customerid;
	private java.lang.String orderno;
	private long orderlineno;
	private java.lang.String linestatus;
	private java.lang.String linestatusName;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.lang.String alternativesku;
	private java.math.BigDecimal cubic;
	private java.math.BigDecimal grossweight;
	private java.math.BigDecimal price;
	private java.lang.String pickzone;
	private java.lang.String location;
	private java.lang.String lotnum;
	private java.lang.String notes;
	private java.lang.String packid;
	private java.math.BigDecimal qtyallocated;
	private java.math.BigDecimal qtyallocatedEach;
	private java.math.BigDecimal qtyordered;
	private java.math.BigDecimal qtyorderedEach;
	private java.math.BigDecimal qtypicked;
	private java.math.BigDecimal qtypickedEach;
	private java.math.BigDecimal qtyreleased;
	private java.math.BigDecimal qtyshipped;
	private java.math.BigDecimal qtyshippedEach;
	private java.math.BigDecimal qtysoftallocated;
	private java.math.BigDecimal qtysoftallocatedEach;
	private java.lang.String sku;
	private java.lang.String skuName;
	private java.lang.String skuNameE;

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
	}

	public long getOrderlineno() {
		return orderlineno;
	}

	public void setOrderlineno(long orderlineno) {
		this.orderlineno = orderlineno;
	}

	public java.lang.String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(java.lang.String linestatus) {
		this.linestatus = linestatus;
	}

	public java.lang.String getLinestatusName() {
		return linestatusName;
	}

	public void setLinestatusName(java.lang.String linestatusName) {
		this.linestatusName = linestatusName;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddwho() {
		return addwho;
	}

	public void setAddwho(java.lang.String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	public java.lang.String getAlternativesku() {
		return alternativesku;
	}

	public void setAlternativesku(java.lang.String alternativesku) {
		this.alternativesku = alternativesku;
	}

	public java.math.BigDecimal getCubic() {
		return cubic;
	}

	public void setCubic(java.math.BigDecimal cubic) {
		this.cubic = cubic;
	}

	public java.math.BigDecimal getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(java.math.BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.lang.String getPickzone() {
		return pickzone;
	}

	public void setPickzone(java.lang.String pickzone) {
		this.pickzone = pickzone;
	}

	public java.lang.String getLocation() {
		return location;
	}

	public void setLocation(java.lang.String location) {
		this.location = location;
	}

	public java.lang.String getLotnum() {
		return lotnum;
	}

	public void setLotnum(java.lang.String lotnum) {
		this.lotnum = lotnum;
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

	public java.math.BigDecimal getQtyallocated() {
		return qtyallocated;
	}

	public void setQtyallocated(java.math.BigDecimal qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public java.math.BigDecimal getQtyallocatedEach() {
		return qtyallocatedEach;
	}

	public void setQtyallocatedEach(java.math.BigDecimal qtyallocatedEach) {
		this.qtyallocatedEach = qtyallocatedEach;
	}

	public java.math.BigDecimal getQtyordered() {
		return qtyordered;
	}

	public void setQtyordered(java.math.BigDecimal qtyordered) {
		this.qtyordered = qtyordered;
	}

	public java.math.BigDecimal getQtyorderedEach() {
		return qtyorderedEach;
	}

	public void setQtyorderedEach(java.math.BigDecimal qtyorderedEach) {
		this.qtyorderedEach = qtyorderedEach;
	}

	public java.math.BigDecimal getQtypicked() {
		return qtypicked;
	}

	public void setQtypicked(java.math.BigDecimal qtypicked) {
		this.qtypicked = qtypicked;
	}

	public java.math.BigDecimal getQtypickedEach() {
		return qtypickedEach;
	}

	public void setQtypickedEach(java.math.BigDecimal qtypickedEach) {
		this.qtypickedEach = qtypickedEach;
	}

	public java.math.BigDecimal getQtyreleased() {
		return qtyreleased;
	}

	public void setQtyreleased(java.math.BigDecimal qtyreleased) {
		this.qtyreleased = qtyreleased;
	}

	public java.math.BigDecimal getQtyshipped() {
		return qtyshipped;
	}

	public void setQtyshipped(java.math.BigDecimal qtyshipped) {
		this.qtyshipped = qtyshipped;
	}

	public java.math.BigDecimal getQtyshippedEach() {
		return qtyshippedEach;
	}

	public void setQtyshippedEach(java.math.BigDecimal qtyshippedEach) {
		this.qtyshippedEach = qtyshippedEach;
	}

	public java.math.BigDecimal getQtysoftallocated() {
		return qtysoftallocated;
	}

	public void setQtysoftallocated(java.math.BigDecimal qtysoftallocated) {
		this.qtysoftallocated = qtysoftallocated;
	}

	public java.math.BigDecimal getQtysoftallocatedEach() {
		return qtysoftallocatedEach;
	}

	public void setQtysoftallocatedEach(java.math.BigDecimal qtysoftallocatedEach) {
		this.qtysoftallocatedEach = qtysoftallocatedEach;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getSkuName() {
		return skuName;
	}

	public void setSkuName(java.lang.String skuName) {
		this.skuName = skuName;
	}

	public java.lang.String getSkuNameE() {
		return skuNameE;
	}

	public void setSkuNameE(java.lang.String skuNameE) {
		this.skuNameE = skuNameE;
	}

}