package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocOrderPackingForm {

	private java.lang.String traceid;
	private java.lang.String orderno;
	private java.lang.String sku;
	private java.math.BigDecimal qty;
	private java.lang.String allocationdetailsid;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.util.Date edittime;
	private java.lang.String editwho;

	public java.lang.String getTraceid() {
		return traceid;
	}

	public void setTraceid(java.lang.String traceid) {
		this.traceid = traceid;
	}

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.math.BigDecimal getQty() {
		return qty;
	}

	public void setQty(java.math.BigDecimal qty) {
		this.qty = qty;
	}

	public java.lang.String getAllocationdetailsid() {
		return allocationdetailsid;
	}

	public void setAllocationdetailsid(java.lang.String allocationdetailsid) {
		this.allocationdetailsid = allocationdetailsid;
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

}