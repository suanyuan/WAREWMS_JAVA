package com.wms.query;

public class InvLotQuery implements IQuery {

	private String lotnum;
	private String customerid;
	private String sku;
	private String qty;
	private String cubic;
	private String grossweight;
	private String netweight;
	private String price;
	private String qtypreallocated;
	private String qtyallocated;
	private String qtyonhold;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getCubic() {
		return cubic;
	}

	public void setCubic(String cubic) {
		this.cubic = cubic;
	}

	public String getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(String grossweight) {
		this.grossweight = grossweight;
	}

	public String getNetweight() {
		return netweight;
	}

	public void setNetweight(String netweight) {
		this.netweight = netweight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQtypreallocated() {
		return qtypreallocated;
	}

	public void setQtypreallocated(String qtypreallocated) {
		this.qtypreallocated = qtypreallocated;
	}

	public String getQtyallocated() {
		return qtyallocated;
	}

	public void setQtyallocated(String qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public String getQtyonhold() {
		return qtyonhold;
	}

	public void setQtyonhold(String qtyonhold) {
		this.qtyonhold = qtyonhold;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

}