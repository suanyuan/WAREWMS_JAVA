package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class BasGtnLotattForm {

	private String lotnum;
	private String customerid;
	private String sku;
	private String lotatt02;
	private String lotatt04;
	private String lotatt05;
	private java.sql.Date addtime;
	private String addasnno;

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

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.sql.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.sql.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddasnno() {
		return addasnno;
	}

	public void setAddasnno(String addasnno) {
		this.addasnno = addasnno;
	}

}