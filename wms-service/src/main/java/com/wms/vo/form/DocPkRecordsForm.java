package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocPkRecordsForm {

	private String orderno;
	private long pklineno;
	private String customerid;
	private String sku;
	private String skudesce;
	private long pickedqty;
	private long pickedqtyEach;
	private String allocationdetailsid;
	private String locationid;
	private String lotnum;
	private String addwho;
	private java.sql.Timestamp addtime;
	private String editwho;
	private java.sql.Timestamp edittime;


	private String version;


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public long getPklineno() {
		return pklineno;
	}

	public void setPklineno(long pklineno) {
		this.pklineno = pklineno;
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

	public String getSkudesce() {
		return skudesce;
	}

	public void setSkudesce(String skudesce) {
		this.skudesce = skudesce;
	}

	public long getPickedqty() {
		return pickedqty;
	}

	public void setPickedqty(long pickedqty) {
		this.pickedqty = pickedqty;
	}

	public long getPickedqtyEach() {
		return pickedqtyEach;
	}

	public void setPickedqtyEach(long pickedqtyEach) {
		this.pickedqtyEach = pickedqtyEach;
	}

	public String getAllocationdetailsid() {
		return allocationdetailsid;
	}

	public void setAllocationdetailsid(String allocationdetailsid) {
		this.allocationdetailsid = allocationdetailsid;
	}

	public String getLocationid() {
		return locationid;
	}

	public void setLocationid(String locationid) {
		this.locationid = locationid;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public java.sql.Timestamp getAddtime() {
		return addtime;
	}

	public void setAddtime(java.sql.Timestamp addtime) {
		this.addtime = addtime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public java.sql.Timestamp getEdittime() {
		return edittime;
	}

	public void setEdittime(java.sql.Timestamp edittime) {
		this.edittime = edittime;
	}

}