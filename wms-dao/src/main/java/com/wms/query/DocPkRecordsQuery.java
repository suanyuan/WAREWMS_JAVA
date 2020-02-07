package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocPkRecordsQuery implements IQuery {

	private String orderno;
	private String pklineno;
	private String customerid;
	private String sku;
	private String skudesce;
	private String pickedqty;
	private String pickedqtyEach;
	private String allocationdetailsid;
	private String locationid;
	private String lotnum;
	private String addwho;
	private String addtime;
	private String editwho;
	private String edittime;


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

	public String getPklineno() {
		return pklineno;
	}

	public void setPklineno(String pklineno) {
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

	public String getPickedqty() {
		return pickedqty;
	}

	public void setPickedqty(String pickedqty) {
		this.pickedqty = pickedqty;
	}

	public String getPickedqtyEach() {
		return pickedqtyEach;
	}

	public void setPickedqtyEach(String pickedqtyEach) {
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

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

}