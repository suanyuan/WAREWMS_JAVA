package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasGtnVO {

	private Integer seq;
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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}