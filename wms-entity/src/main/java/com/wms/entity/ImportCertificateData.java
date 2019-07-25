package com.wms.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
public class ImportCertificateData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;

	private String customerid;
	private String sku;
	private String lotatt04;

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSeq() {
		return seq;
	}

	public String getCustomerid() {
		return customerid;
	}

	public String getSku() {
		return sku;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public ImportCertificateData() {
	}


}