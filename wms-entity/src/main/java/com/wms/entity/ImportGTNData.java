package com.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
public class ImportGTNData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	private String sku;
	private String gtnCode;


	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGtnCode() {
		return gtnCode;
	}

	public void setGtnCode(String gtnCode) {
		this.gtnCode = gtnCode;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}


	public ImportGTNData() {

	}
}