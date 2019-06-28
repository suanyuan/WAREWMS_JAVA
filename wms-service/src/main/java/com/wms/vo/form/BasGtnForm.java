package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasGtnForm {

	private java.lang.String sku;
	private java.lang.String gtncode;

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getGtncode() {
		return gtncode;
	}

	public void setGtncode(java.lang.String gtncode) {
		this.gtncode = gtncode;
	}

}