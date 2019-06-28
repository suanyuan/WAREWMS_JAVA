package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasGtnQuery implements IQuery {

	private String sku;
	private String gtncode;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGtncode() {
		return gtncode;
	}

	public void setGtncode(String gtncode) {
		this.gtncode = gtncode;
	}

}