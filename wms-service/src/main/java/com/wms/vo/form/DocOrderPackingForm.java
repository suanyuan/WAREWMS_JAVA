package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocOrderPackingForm {

	private String traceid;
	private String orderno;
	private String sku;
	private java.math.BigDecimal qty;
	private String allocationdetailsid;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;

	public String getTraceid() {
		return traceid;
	}

	public void setTraceid(String traceid) {
		this.traceid = traceid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public java.math.BigDecimal getQty() {
		return qty;
	}

	public void setQty(java.math.BigDecimal qty) {
		this.qty = qty;
	}

	public String getAllocationdetailsid() {
		return allocationdetailsid;
	}

	public void setAllocationdetailsid(String allocationdetailsid) {
		this.allocationdetailsid = allocationdetailsid;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

}