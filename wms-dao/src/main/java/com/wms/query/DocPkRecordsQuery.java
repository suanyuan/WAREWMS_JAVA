package com.wms.query;

import lombok.Data;

@Data
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

	public DocPkRecordsQuery() {
	}

	public DocPkRecordsQuery(String orderno, String allocationdetailsid) {
		this.orderno = orderno;
		this.allocationdetailsid = allocationdetailsid;
	}
}