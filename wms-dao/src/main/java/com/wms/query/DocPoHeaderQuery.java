package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

import java.util.Date;

public class DocPoHeaderQuery implements IQuery {

	private String pono;
	private String potype;
	private String postatus;
	private Date pocreationtime;
	private Date expectedarrivetime1;
	private Date expectedarrivetime2;
	private String poreference1;
	private String poreference2;
	private String poreference3;
	private String poreference4;
	private String poreference5;
	private String supplierid;
	private String supplierName;
	private String supplierAddress1;
	private String supplierAddress2;
	private String supplierAddress3;
	private String supplierAddress4;
	private String supplierCity;
	private String supplierProvince;
	private String supplierCountry;
	private String supplierZip;
	private String customerid;
	private String edisendflag;
	private String supplierContact;
	private String supplierMail;
	private String supplierFax;
	private String supplierTel1;
	private String supplierTel2;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String notes;
	private Date addtime;
	private String addwho;
	private Date edittime;
	private String editwho;
	private Date edisendtime;
	private String hedi01;
	private String hedi02;
	private String hedi03;
	private String hedi04;
	private String hedi05;
	private String hedi06;
	private String hedi07;
	private String hedi08;
	private String hedi09;
	private String hedi10;
	private String warehouseid;
	private String createsource;
	private String releasestatus;
	private String userdefinea;
	private String userdefineb;
	private String sku;//附加

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getPono() {
		return pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public String getPotype() {
		return potype;
	}

	public void setPotype(String potype) {
		this.potype = potype;
	}

	public String getPostatus() {
		return postatus;
	}

	public void setPostatus(String postatus) {
		this.postatus = postatus;
	}

	public Date getPocreationtime() {
		return pocreationtime;
	}

	public void setPocreationtime(Date pocreationtime) {
		this.pocreationtime = pocreationtime;
	}

	public Date getExpectedarrivetime1() {
		return expectedarrivetime1;
	}

	public void setExpectedarrivetime1(Date expectedarrivetime1) {
		this.expectedarrivetime1 = expectedarrivetime1;
	}

	public Date getExpectedarrivetime2() {
		return expectedarrivetime2;
	}

	public void setExpectedarrivetime2(Date expectedarrivetime2) {
		this.expectedarrivetime2 = expectedarrivetime2;
	}

	public String getPoreference1() {
		return poreference1;
	}

	public void setPoreference1(String poreference1) {
		this.poreference1 = poreference1;
	}

	public String getPoreference2() {
		return poreference2;
	}

	public void setPoreference2(String poreference2) {
		this.poreference2 = poreference2;
	}

	public String getPoreference3() {
		return poreference3;
	}

	public void setPoreference3(String poreference3) {
		this.poreference3 = poreference3;
	}

	public String getPoreference4() {
		return poreference4;
	}

	public void setPoreference4(String poreference4) {
		this.poreference4 = poreference4;
	}

	public String getPoreference5() {
		return poreference5;
	}

	public void setPoreference5(String poreference5) {
		this.poreference5 = poreference5;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAddress1() {
		return supplierAddress1;
	}

	public void setSupplierAddress1(String supplierAddress1) {
		this.supplierAddress1 = supplierAddress1;
	}

	public String getSupplierAddress2() {
		return supplierAddress2;
	}

	public void setSupplierAddress2(String supplierAddress2) {
		this.supplierAddress2 = supplierAddress2;
	}

	public String getSupplierAddress3() {
		return supplierAddress3;
	}

	public void setSupplierAddress3(String supplierAddress3) {
		this.supplierAddress3 = supplierAddress3;
	}

	public String getSupplierAddress4() {
		return supplierAddress4;
	}

	public void setSupplierAddress4(String supplierAddress4) {
		this.supplierAddress4 = supplierAddress4;
	}

	public String getSupplierCity() {
		return supplierCity;
	}

	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}

	public String getSupplierProvince() {
		return supplierProvince;
	}

	public void setSupplierProvince(String supplierProvince) {
		this.supplierProvince = supplierProvince;
	}

	public String getSupplierCountry() {
		return supplierCountry;
	}

	public void setSupplierCountry(String supplierCountry) {
		this.supplierCountry = supplierCountry;
	}

	public String getSupplierZip() {
		return supplierZip;
	}

	public void setSupplierZip(String supplierZip) {
		this.supplierZip = supplierZip;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getEdisendflag() {
		return edisendflag;
	}

	public void setEdisendflag(String edisendflag) {
		this.edisendflag = edisendflag;
	}

	public String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierMail() {
		return supplierMail;
	}

	public void setSupplierMail(String supplierMail) {
		this.supplierMail = supplierMail;
	}

	public String getSupplierFax() {
		return supplierFax;
	}

	public void setSupplierFax(String supplierFax) {
		this.supplierFax = supplierFax;
	}

	public String getSupplierTel1() {
		return supplierTel1;
	}

	public void setSupplierTel1(String supplierTel1) {
		this.supplierTel1 = supplierTel1;
	}

	public String getSupplierTel2() {
		return supplierTel2;
	}

	public void setSupplierTel2(String supplierTel2) {
		this.supplierTel2 = supplierTel2;
	}

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public Date getEdisendtime() {
		return edisendtime;
	}

	public void setEdisendtime(Date edisendtime) {
		this.edisendtime = edisendtime;
	}

	public String getHedi01() {
		return hedi01;
	}

	public void setHedi01(String hedi01) {
		this.hedi01 = hedi01;
	}

	public String getHedi02() {
		return hedi02;
	}

	public void setHedi02(String hedi02) {
		this.hedi02 = hedi02;
	}

	public String getHedi03() {
		return hedi03;
	}

	public void setHedi03(String hedi03) {
		this.hedi03 = hedi03;
	}

	public String getHedi04() {
		return hedi04;
	}

	public void setHedi04(String hedi04) {
		this.hedi04 = hedi04;
	}

	public String getHedi05() {
		return hedi05;
	}

	public void setHedi05(String hedi05) {
		this.hedi05 = hedi05;
	}

	public String getHedi06() {
		return hedi06;
	}

	public void setHedi06(String hedi06) {
		this.hedi06 = hedi06;
	}

	public String getHedi07() {
		return hedi07;
	}

	public void setHedi07(String hedi07) {
		this.hedi07 = hedi07;
	}

	public String getHedi08() {
		return hedi08;
	}

	public void setHedi08(String hedi08) {
		this.hedi08 = hedi08;
	}

	public String getHedi09() {
		return hedi09;
	}

	public void setHedi09(String hedi09) {
		this.hedi09 = hedi09;
	}

	public String getHedi10() {
		return hedi10;
	}

	public void setHedi10(String hedi10) {
		this.hedi10 = hedi10;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getCreatesource() {
		return createsource;
	}

	public void setCreatesource(String createsource) {
		this.createsource = createsource;
	}

	public String getReleasestatus() {
		return releasestatus;
	}

	public void setReleasestatus(String releasestatus) {
		this.releasestatus = releasestatus;
	}

	public String getUserdefinea() {
		return userdefinea;
	}

	public void setUserdefinea(String userdefinea) {
		this.userdefinea = userdefinea;
	}

	public String getUserdefineb() {
		return userdefineb;
	}

	public void setUserdefineb(String userdefineb) {
		this.userdefineb = userdefineb;
	}
}