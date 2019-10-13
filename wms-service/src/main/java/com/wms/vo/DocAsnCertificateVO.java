package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocAsnCertificateVO {
	private Integer seq;
	private String customerid;
	private String sku;
	private String lotatt04;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private String addtime;
	private String addwho;
	@JsonSerialize(using = JsonDatetimeSerializer.class)
	private String edittime;
	private String editwho;
	private String certificateContext;
	private String specsName;

	public String getSpecsName() {
		return specsName;
	}

	public void setSpecsName(String specsName) {
		this.specsName = specsName;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}



	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getCertificateContext() {
		return certificateContext;
	}

	public void setCertificateContext(String certificateContext) {
		this.certificateContext = certificateContext;
	}

}