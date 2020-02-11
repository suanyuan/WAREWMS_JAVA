package com.wms.vo;

import com.wms.entity.DocAsnDetail;
import com.wms.utils.serialzer.JsonDatetimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

public class DocAsnHeaderVO {
	
	private Integer seq;
	private java.lang.String asnno;
	private java.util.Date actualarrivetime;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.lang.String archiveflag;
	private java.lang.String asnPrintFlag;
	private java.util.Date asncreationtime;
	private java.lang.String asnreference1;
	private java.lang.String asnreference2;
	private java.lang.String asnreference3;
	private java.lang.String asnreference4;
	private java.lang.String asnreference5;
	private java.lang.String asnstatus;
	private java.lang.String asnstatusName;
	private java.lang.String asntype;
	private java.lang.String asntypeName;
	private java.lang.String bAddress1;
	private java.lang.String bAddress2;
	private java.lang.String bAddress3;
	private java.lang.String bAddress4;
	private java.lang.String bCity;
	private java.lang.String bContact;
	private java.lang.String bCountry;
	private java.lang.String bFax;
	private java.lang.String bMail;
	private java.lang.String bProvince;
	private java.lang.String bTel1;
	private java.lang.String bTel2;
	private java.lang.String bZip;
	private java.lang.String billingclassGroup;
	private java.lang.String billingid;
	private java.lang.String billingname;
	private java.lang.String bytraceFlag;
	private java.lang.String carrieraddress1;
	private java.lang.String carrieraddress2;
	private java.lang.String carrieraddress3;
	private java.lang.String carrieraddress4;
	private java.lang.String carriercity;
	private java.lang.String carriercontact;
	private java.lang.String carriercountry;
	private java.lang.String carrierfax;
	private java.lang.String carrierid;
	private java.lang.String carriermail;
	private java.lang.String carriername;
	private java.lang.String carrierprovince;
	private java.lang.String carriertel1;
	private java.lang.String carriertel2;
	private java.lang.String carrierzip;
	private java.lang.String countryofdestination;
	private java.lang.String countryoforigin;
	private java.lang.String createsource;
	private java.lang.String customerid;
	private java.lang.String deliveryterms;
	private java.lang.String deliverytermsdescr;
	private java.lang.String deliveryvehicleno;
	private java.lang.String deliveryvehicletype;
	private java.lang.String door;
	private java.lang.String driver;
	private java.lang.String edisendflag;
	private java.util.Date edisendtime;
	private java.util.Date edisendtime2;
	private java.util.Date edisendtime3;
	private java.util.Date edisendtime4;
	private java.util.Date edisendtime5;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.util.Date expectedarrivetime1;
	private java.util.Date expectedarrivetime2;
	private java.lang.String followup;
	private java.lang.String hEdi01;
	private java.lang.String hEdi02;
	private java.lang.String hEdi03;
	private java.lang.String hEdi04;
	private java.lang.String hEdi05;
	private java.lang.String hEdi06;
	private java.lang.String hEdi07;
	private java.lang.String hEdi08;
	private java.math.BigDecimal hEdi09;
	private java.math.BigDecimal hEdi10;
	private java.lang.String hEdi11;
	private java.lang.String iAddress1;
	private java.lang.String iAddress2;
	private java.lang.String iAddress3;
	private java.lang.String iAddress4;
	private java.lang.String iCity;
	private java.lang.String iContact;
	private java.lang.String iCountry;
	private java.lang.String iFax;
	private java.lang.String iMail;
	private java.lang.String iProvince;
	private java.lang.String iTel1;
	private java.lang.String iTel2;
	private java.lang.String iZip;
	private java.lang.String issuepartyid;
	private java.lang.String issuepartyname;
	private java.util.Date lastreceivingtime;
	private java.util.Date medicalxmltime;
	private java.lang.String notes;
	private java.lang.String packmaterialconsume;
	private java.lang.String paymentterms;
	private java.lang.String paymenttermsdescr;
	private java.lang.String placeofdelivery;
	private java.lang.String placeofdischarge;
	private java.lang.String placeofloading;
	private java.lang.String pono;
	private java.lang.String priority;
	private java.lang.String qcstatus;
	private Double receiveid;
	private java.lang.String releasestatus;
	private java.lang.String releasestatusName;
	private java.lang.String reserveFlag;
	private java.lang.String returnPrintFlag;
	private java.lang.String serialnocatch;
	private java.lang.String supplierAddress1;
	private java.lang.String supplierAddress2;
	private java.lang.String supplierAddress3;
	private java.lang.String supplierAddress4;
	private java.lang.String supplierCity;
	private java.lang.String supplierContact;
	private java.lang.String supplierCountry;
	private java.lang.String supplierFax;
	private java.lang.String supplierMail;
	private java.lang.String supplierName;
	private java.lang.String supplierProvince;
	private java.lang.String supplierTel1;
	private java.lang.String supplierTel2;
	private java.lang.String supplierZip;
	private java.lang.String supplierid;
	private java.lang.String userdefine1;
	private java.lang.String userdefine2;
	private java.lang.String userdefine3;
	private java.lang.String userdefine4;
	private java.lang.String userdefine5;
	private java.lang.String userdefine6;
	private java.lang.String userdefinea;
	private java.lang.String userdefineb;
	private java.lang.String warehouseid;
	private java.lang.String zonegroup;
	private java.util.Set<DocAsnDetail> docAsnDetailSet;
	private List<DocAsnDetailVO> docAsnDetailVOList;
	private String coldTag;
	private String sup;
	private String customerIdRef;
	private Double expectedqty;//件数
	private Double expectedqtyNum;//数量
	private String loatt03;
	private String pname;//产品线

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Double getExpectedqty() {
		return expectedqty;
	}

	public void setExpectedqty(Double expectedqty) {
		this.expectedqty = expectedqty;
	}

	public Double getExpectedqtyNum() {
		return expectedqtyNum;
	}

	public void setExpectedqtyNum(Double expectedqtyNum) {
		this.expectedqtyNum = expectedqtyNum;
	}

	public String getLoatt03() {
		return loatt03;
	}

	public void setLoatt03(String loatt03) {
		this.loatt03 = loatt03;
	}



	public String getCustomerIdRef() {
		return customerIdRef;
	}

	public void setCustomerIdRef(String customerIdRef) {
		this.customerIdRef = customerIdRef;
	}

	public List<DocAsnDetailVO> getDocAsnDetailVOList() {
		return docAsnDetailVOList;
	}

	public void setDocAsnDetailVOList(List<DocAsnDetailVO> docAsnDetailVOList) {
		this.docAsnDetailVOList = docAsnDetailVOList;
	}


	public String getSup() {
		return sup;
	}

	public void setSup(String sup) {
		this.sup = sup;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public java.lang.String getAsnstatusName() {
		return asnstatusName;
	}

	public void setAsnstatusName(java.lang.String asnstatusName) {
		this.asnstatusName = asnstatusName;
	}

	public java.lang.String getAsntypeName() {
		return asntypeName;
	}

	public void setAsntypeName(java.lang.String asntypeName) {
		this.asntypeName = asntypeName;
	}

	public java.lang.String getReleasestatusName() {
		return releasestatusName;
	}

	public void setReleasestatusName(java.lang.String releasestatusName) {
		this.releasestatusName = releasestatusName;
	}

	public java.util.Set getDocAsnDetailSet() {
		return docAsnDetailSet;
	}

	public void setDocAsnDetailSet(java.util.Set docAsnDetailSet) {
		this.docAsnDetailSet = docAsnDetailSet;
	}

	public java.lang.String getAsnno() {
		return asnno;
	}

	public void setAsnno(java.lang.String asnno) {
		this.asnno = asnno;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getActualarrivetime() {
		return actualarrivetime;
	}

	public void setActualarrivetime(java.util.Date actualarrivetime) {
		this.actualarrivetime = actualarrivetime;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddwho() {
		return addwho;
	}

	public void setAddwho(java.lang.String addwho) {
		this.addwho = addwho;
	}

	public java.lang.String getArchiveflag() {
		return archiveflag;
	}

	public void setArchiveflag(java.lang.String archiveflag) {
		this.archiveflag = archiveflag;
	}

	public java.lang.String getAsnPrintFlag() {
		return asnPrintFlag;
	}

	public void setAsnPrintFlag(java.lang.String asnPrintFlag) {
		this.asnPrintFlag = asnPrintFlag;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAsncreationtime() {
		return asncreationtime;
	}

	public void setAsncreationtime(java.util.Date asncreationtime) {
		this.asncreationtime = asncreationtime;
	}

	public java.lang.String getAsnreference1() {
		return asnreference1;
	}

	public void setAsnreference1(java.lang.String asnreference1) {
		this.asnreference1 = asnreference1;
	}

	public java.lang.String getAsnreference2() {
		return asnreference2;
	}

	public void setAsnreference2(java.lang.String asnreference2) {
		this.asnreference2 = asnreference2;
	}

	public java.lang.String getAsnreference3() {
		return asnreference3;
	}

	public void setAsnreference3(java.lang.String asnreference3) {
		this.asnreference3 = asnreference3;
	}

	public java.lang.String getAsnreference4() {
		return asnreference4;
	}

	public void setAsnreference4(java.lang.String asnreference4) {
		this.asnreference4 = asnreference4;
	}

	public java.lang.String getAsnreference5() {
		return asnreference5;
	}

	public void setAsnreference5(java.lang.String asnreference5) {
		this.asnreference5 = asnreference5;
	}

	public java.lang.String getAsnstatus() {
		return asnstatus;
	}

	public void setAsnstatus(java.lang.String asnstatus) {
		this.asnstatus = asnstatus;
	}

	public java.lang.String getAsntype() {
		return asntype;
	}

	public void setAsntype(java.lang.String asntype) {
		this.asntype = asntype;
	}

	public java.lang.String getBAddress1() {
		return bAddress1;
	}

	public void setBAddress1(java.lang.String bAddress1) {
		this.bAddress1 = bAddress1;
	}

	public java.lang.String getBAddress2() {
		return bAddress2;
	}

	public void setBAddress2(java.lang.String bAddress2) {
		this.bAddress2 = bAddress2;
	}

	public java.lang.String getBAddress3() {
		return bAddress3;
	}

	public void setBAddress3(java.lang.String bAddress3) {
		this.bAddress3 = bAddress3;
	}

	public java.lang.String getBAddress4() {
		return bAddress4;
	}

	public void setBAddress4(java.lang.String bAddress4) {
		this.bAddress4 = bAddress4;
	}

	public java.lang.String getBCity() {
		return bCity;
	}

	public void setBCity(java.lang.String bCity) {
		this.bCity = bCity;
	}

	public java.lang.String getBContact() {
		return bContact;
	}

	public void setBContact(java.lang.String bContact) {
		this.bContact = bContact;
	}

	public java.lang.String getBCountry() {
		return bCountry;
	}

	public void setBCountry(java.lang.String bCountry) {
		this.bCountry = bCountry;
	}

	public java.lang.String getBFax() {
		return bFax;
	}

	public void setBFax(java.lang.String bFax) {
		this.bFax = bFax;
	}

	public java.lang.String getBMail() {
		return bMail;
	}

	public void setBMail(java.lang.String bMail) {
		this.bMail = bMail;
	}

	public java.lang.String getBProvince() {
		return bProvince;
	}

	public void setBProvince(java.lang.String bProvince) {
		this.bProvince = bProvince;
	}

	public java.lang.String getBTel1() {
		return bTel1;
	}

	public void setBTel1(java.lang.String bTel1) {
		this.bTel1 = bTel1;
	}

	public java.lang.String getBTel2() {
		return bTel2;
	}

	public void setBTel2(java.lang.String bTel2) {
		this.bTel2 = bTel2;
	}

	public java.lang.String getBZip() {
		return bZip;
	}

	public void setBZip(java.lang.String bZip) {
		this.bZip = bZip;
	}

	public java.lang.String getBillingclassGroup() {
		return billingclassGroup;
	}

	public void setBillingclassGroup(java.lang.String billingclassGroup) {
		this.billingclassGroup = billingclassGroup;
	}

	public java.lang.String getBillingid() {
		return billingid;
	}

	public void setBillingid(java.lang.String billingid) {
		this.billingid = billingid;
	}

	public java.lang.String getBillingname() {
		return billingname;
	}

	public void setBillingname(java.lang.String billingname) {
		this.billingname = billingname;
	}

	public java.lang.String getBytraceFlag() {
		return bytraceFlag;
	}

	public void setBytraceFlag(java.lang.String bytraceFlag) {
		this.bytraceFlag = bytraceFlag;
	}

	public java.lang.String getCarrieraddress1() {
		return carrieraddress1;
	}

	public void setCarrieraddress1(java.lang.String carrieraddress1) {
		this.carrieraddress1 = carrieraddress1;
	}

	public java.lang.String getCarrieraddress2() {
		return carrieraddress2;
	}

	public void setCarrieraddress2(java.lang.String carrieraddress2) {
		this.carrieraddress2 = carrieraddress2;
	}

	public java.lang.String getCarrieraddress3() {
		return carrieraddress3;
	}

	public void setCarrieraddress3(java.lang.String carrieraddress3) {
		this.carrieraddress3 = carrieraddress3;
	}

	public java.lang.String getCarrieraddress4() {
		return carrieraddress4;
	}

	public void setCarrieraddress4(java.lang.String carrieraddress4) {
		this.carrieraddress4 = carrieraddress4;
	}

	public java.lang.String getCarriercity() {
		return carriercity;
	}

	public void setCarriercity(java.lang.String carriercity) {
		this.carriercity = carriercity;
	}

	public java.lang.String getCarriercontact() {
		return carriercontact;
	}

	public void setCarriercontact(java.lang.String carriercontact) {
		this.carriercontact = carriercontact;
	}

	public java.lang.String getCarriercountry() {
		return carriercountry;
	}

	public void setCarriercountry(java.lang.String carriercountry) {
		this.carriercountry = carriercountry;
	}

	public java.lang.String getCarrierfax() {
		return carrierfax;
	}

	public void setCarrierfax(java.lang.String carrierfax) {
		this.carrierfax = carrierfax;
	}

	public java.lang.String getCarrierid() {
		return carrierid;
	}

	public void setCarrierid(java.lang.String carrierid) {
		this.carrierid = carrierid;
	}

	public java.lang.String getCarriermail() {
		return carriermail;
	}

	public void setCarriermail(java.lang.String carriermail) {
		this.carriermail = carriermail;
	}

	public java.lang.String getCarriername() {
		return carriername;
	}

	public void setCarriername(java.lang.String carriername) {
		this.carriername = carriername;
	}

	public java.lang.String getCarrierprovince() {
		return carrierprovince;
	}

	public void setCarrierprovince(java.lang.String carrierprovince) {
		this.carrierprovince = carrierprovince;
	}

	public java.lang.String getCarriertel1() {
		return carriertel1;
	}

	public void setCarriertel1(java.lang.String carriertel1) {
		this.carriertel1 = carriertel1;
	}

	public java.lang.String getCarriertel2() {
		return carriertel2;
	}

	public void setCarriertel2(java.lang.String carriertel2) {
		this.carriertel2 = carriertel2;
	}

	public java.lang.String getCarrierzip() {
		return carrierzip;
	}

	public void setCarrierzip(java.lang.String carrierzip) {
		this.carrierzip = carrierzip;
	}

	public java.lang.String getCountryofdestination() {
		return countryofdestination;
	}

	public void setCountryofdestination(java.lang.String countryofdestination) {
		this.countryofdestination = countryofdestination;
	}

	public java.lang.String getCountryoforigin() {
		return countryoforigin;
	}

	public void setCountryoforigin(java.lang.String countryoforigin) {
		this.countryoforigin = countryoforigin;
	}

	public java.lang.String getCreatesource() {
		return createsource;
	}

	public void setCreatesource(java.lang.String createsource) {
		this.createsource = createsource;
	}

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.lang.String getDeliveryterms() {
		return deliveryterms;
	}

	public void setDeliveryterms(java.lang.String deliveryterms) {
		this.deliveryterms = deliveryterms;
	}

	public java.lang.String getDeliverytermsdescr() {
		return deliverytermsdescr;
	}

	public void setDeliverytermsdescr(java.lang.String deliverytermsdescr) {
		this.deliverytermsdescr = deliverytermsdescr;
	}

	public java.lang.String getDeliveryvehicleno() {
		return deliveryvehicleno;
	}

	public void setDeliveryvehicleno(java.lang.String deliveryvehicleno) {
		this.deliveryvehicleno = deliveryvehicleno;
	}

	public java.lang.String getDeliveryvehicletype() {
		return deliveryvehicletype;
	}

	public void setDeliveryvehicletype(java.lang.String deliveryvehicletype) {
		this.deliveryvehicletype = deliveryvehicletype;
	}

	public java.lang.String getDoor() {
		return door;
	}

	public void setDoor(java.lang.String door) {
		this.door = door;
	}

	public java.lang.String getDriver() {
		return driver;
	}

	public void setDriver(java.lang.String driver) {
		this.driver = driver;
	}

	public java.lang.String getEdisendflag() {
		return edisendflag;
	}

	public void setEdisendflag(java.lang.String edisendflag) {
		this.edisendflag = edisendflag;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdisendtime() {
		return edisendtime;
	}

	public void setEdisendtime(java.util.Date edisendtime) {
		this.edisendtime = edisendtime;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdisendtime2() {
		return edisendtime2;
	}

	public void setEdisendtime2(java.util.Date edisendtime2) {
		this.edisendtime2 = edisendtime2;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdisendtime3() {
		return edisendtime3;
	}

	public void setEdisendtime3(java.util.Date edisendtime3) {
		this.edisendtime3 = edisendtime3;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdisendtime4() {
		return edisendtime4;
	}

	public void setEdisendtime4(java.util.Date edisendtime4) {
		this.edisendtime4 = edisendtime4;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdisendtime5() {
		return edisendtime5;
	}

	public void setEdisendtime5(java.util.Date edisendtime5) {
		this.edisendtime5 = edisendtime5;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getExpectedarrivetime1() {
		return expectedarrivetime1;
	}

	public void setExpectedarrivetime1(java.util.Date expectedarrivetime1) {
		this.expectedarrivetime1 = expectedarrivetime1;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getExpectedarrivetime2() {
		return expectedarrivetime2;
	}

	public void setExpectedarrivetime2(java.util.Date expectedarrivetime2) {
		this.expectedarrivetime2 = expectedarrivetime2;
	}

	public java.lang.String getFollowup() {
		return followup;
	}

	public void setFollowup(java.lang.String followup) {
		this.followup = followup;
	}

	public java.lang.String getHEdi01() {
		return hEdi01;
	}

	public void setHEdi01(java.lang.String hEdi01) {
		this.hEdi01 = hEdi01;
	}

	public java.lang.String getHEdi02() {
		return hEdi02;
	}

	public void setHEdi02(java.lang.String hEdi02) {
		this.hEdi02 = hEdi02;
	}

	public java.lang.String getHEdi03() {
		return hEdi03;
	}

	public void setHEdi03(java.lang.String hEdi03) {
		this.hEdi03 = hEdi03;
	}

	public java.lang.String getHEdi04() {
		return hEdi04;
	}

	public void setHEdi04(java.lang.String hEdi04) {
		this.hEdi04 = hEdi04;
	}

	public java.lang.String getHEdi05() {
		return hEdi05;
	}

	public void setHEdi05(java.lang.String hEdi05) {
		this.hEdi05 = hEdi05;
	}

	public java.lang.String getHEdi06() {
		return hEdi06;
	}

	public void setHEdi06(java.lang.String hEdi06) {
		this.hEdi06 = hEdi06;
	}

	public java.lang.String getHEdi07() {
		return hEdi07;
	}

	public void setHEdi07(java.lang.String hEdi07) {
		this.hEdi07 = hEdi07;
	}

	public java.lang.String getHEdi08() {
		return hEdi08;
	}

	public void setHEdi08(java.lang.String hEdi08) {
		this.hEdi08 = hEdi08;
	}

	public java.math.BigDecimal getHEdi09() {
		return hEdi09;
	}

	public void setHEdi09(java.math.BigDecimal hEdi09) {
		this.hEdi09 = hEdi09;
	}

	public java.math.BigDecimal getHEdi10() {
		return hEdi10;
	}

	public void setHEdi10(java.math.BigDecimal hEdi10) {
		this.hEdi10 = hEdi10;
	}

	public java.lang.String getHEdi11() {
		return hEdi11;
	}

	public void setHEdi11(java.lang.String hEdi11) {
		this.hEdi11 = hEdi11;
	}

	public java.lang.String getIAddress1() {
		return iAddress1;
	}

	public void setIAddress1(java.lang.String iAddress1) {
		this.iAddress1 = iAddress1;
	}

	public java.lang.String getIAddress2() {
		return iAddress2;
	}

	public void setIAddress2(java.lang.String iAddress2) {
		this.iAddress2 = iAddress2;
	}

	public java.lang.String getIAddress3() {
		return iAddress3;
	}

	public void setIAddress3(java.lang.String iAddress3) {
		this.iAddress3 = iAddress3;
	}

	public java.lang.String getIAddress4() {
		return iAddress4;
	}

	public void setIAddress4(java.lang.String iAddress4) {
		this.iAddress4 = iAddress4;
	}

	public java.lang.String getICity() {
		return iCity;
	}

	public void setICity(java.lang.String iCity) {
		this.iCity = iCity;
	}

	public java.lang.String getIContact() {
		return iContact;
	}

	public void setIContact(java.lang.String iContact) {
		this.iContact = iContact;
	}

	public java.lang.String getICountry() {
		return iCountry;
	}

	public void setICountry(java.lang.String iCountry) {
		this.iCountry = iCountry;
	}

	public java.lang.String getIFax() {
		return iFax;
	}

	public void setIFax(java.lang.String iFax) {
		this.iFax = iFax;
	}

	public java.lang.String getIMail() {
		return iMail;
	}

	public void setIMail(java.lang.String iMail) {
		this.iMail = iMail;
	}

	public java.lang.String getIProvince() {
		return iProvince;
	}

	public void setIProvince(java.lang.String iProvince) {
		this.iProvince = iProvince;
	}

	public java.lang.String getITel1() {
		return iTel1;
	}

	public void setITel1(java.lang.String iTel1) {
		this.iTel1 = iTel1;
	}

	public java.lang.String getITel2() {
		return iTel2;
	}

	public void setITel2(java.lang.String iTel2) {
		this.iTel2 = iTel2;
	}

	public java.lang.String getIZip() {
		return iZip;
	}

	public void setIZip(java.lang.String iZip) {
		this.iZip = iZip;
	}

	public java.lang.String getIssuepartyid() {
		return issuepartyid;
	}

	public void setIssuepartyid(java.lang.String issuepartyid) {
		this.issuepartyid = issuepartyid;
	}

	public java.lang.String getIssuepartyname() {
		return issuepartyname;
	}

	public void setIssuepartyname(java.lang.String issuepartyname) {
		this.issuepartyname = issuepartyname;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getLastreceivingtime() {
		return lastreceivingtime;
	}

	public void setLastreceivingtime(java.util.Date lastreceivingtime) {
		this.lastreceivingtime = lastreceivingtime;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getMedicalxmltime() {
		return medicalxmltime;
	}

	public void setMedicalxmltime(java.util.Date medicalxmltime) {
		this.medicalxmltime = medicalxmltime;
	}

	public java.lang.String getNotes() {
		return notes;
	}

	public void setNotes(java.lang.String notes) {
		this.notes = notes;
	}

	public java.lang.String getPackmaterialconsume() {
		return packmaterialconsume;
	}

	public void setPackmaterialconsume(java.lang.String packmaterialconsume) {
		this.packmaterialconsume = packmaterialconsume;
	}

	public java.lang.String getPaymentterms() {
		return paymentterms;
	}

	public void setPaymentterms(java.lang.String paymentterms) {
		this.paymentterms = paymentterms;
	}

	public java.lang.String getPaymenttermsdescr() {
		return paymenttermsdescr;
	}

	public void setPaymenttermsdescr(java.lang.String paymenttermsdescr) {
		this.paymenttermsdescr = paymenttermsdescr;
	}

	public java.lang.String getPlaceofdelivery() {
		return placeofdelivery;
	}

	public void setPlaceofdelivery(java.lang.String placeofdelivery) {
		this.placeofdelivery = placeofdelivery;
	}

	public java.lang.String getPlaceofdischarge() {
		return placeofdischarge;
	}

	public void setPlaceofdischarge(java.lang.String placeofdischarge) {
		this.placeofdischarge = placeofdischarge;
	}

	public java.lang.String getPlaceofloading() {
		return placeofloading;
	}

	public void setPlaceofloading(java.lang.String placeofloading) {
		this.placeofloading = placeofloading;
	}

	public java.lang.String getPono() {
		return pono;
	}

	public void setPono(java.lang.String pono) {
		this.pono = pono;
	}

	public java.lang.String getPriority() {
		return priority;
	}

	public void setPriority(java.lang.String priority) {
		this.priority = priority;
	}

	public java.lang.String getQcstatus() {
		return qcstatus;
	}

	public void setQcstatus(java.lang.String qcstatus) {
		this.qcstatus = qcstatus;
	}

	public Double getReceiveid() {
		return receiveid;
	}

	public void setReceiveid(Double receiveid) {
		this.receiveid = receiveid;
	}

	public java.lang.String getReleasestatus() {
		return releasestatus;
	}

	public void setReleasestatus(java.lang.String releasestatus) {
		this.releasestatus = releasestatus;
	}

	public java.lang.String getReserveFlag() {
		return reserveFlag;
	}

	public void setReserveFlag(java.lang.String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	public java.lang.String getReturnPrintFlag() {
		return returnPrintFlag;
	}

	public void setReturnPrintFlag(java.lang.String returnPrintFlag) {
		this.returnPrintFlag = returnPrintFlag;
	}

	public java.lang.String getSerialnocatch() {
		return serialnocatch;
	}

	public void setSerialnocatch(java.lang.String serialnocatch) {
		this.serialnocatch = serialnocatch;
	}

	public java.lang.String getSupplierAddress1() {
		return supplierAddress1;
	}

	public void setSupplierAddress1(java.lang.String supplierAddress1) {
		this.supplierAddress1 = supplierAddress1;
	}

	public java.lang.String getSupplierAddress2() {
		return supplierAddress2;
	}

	public void setSupplierAddress2(java.lang.String supplierAddress2) {
		this.supplierAddress2 = supplierAddress2;
	}

	public java.lang.String getSupplierAddress3() {
		return supplierAddress3;
	}

	public void setSupplierAddress3(java.lang.String supplierAddress3) {
		this.supplierAddress3 = supplierAddress3;
	}

	public java.lang.String getSupplierAddress4() {
		return supplierAddress4;
	}

	public void setSupplierAddress4(java.lang.String supplierAddress4) {
		this.supplierAddress4 = supplierAddress4;
	}

	public java.lang.String getSupplierCity() {
		return supplierCity;
	}

	public void setSupplierCity(java.lang.String supplierCity) {
		this.supplierCity = supplierCity;
	}

	public java.lang.String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(java.lang.String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public java.lang.String getSupplierCountry() {
		return supplierCountry;
	}

	public void setSupplierCountry(java.lang.String supplierCountry) {
		this.supplierCountry = supplierCountry;
	}

	public java.lang.String getSupplierFax() {
		return supplierFax;
	}

	public void setSupplierFax(java.lang.String supplierFax) {
		this.supplierFax = supplierFax;
	}

	public java.lang.String getSupplierMail() {
		return supplierMail;
	}

	public void setSupplierMail(java.lang.String supplierMail) {
		this.supplierMail = supplierMail;
	}

	public java.lang.String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}

	public java.lang.String getSupplierProvince() {
		return supplierProvince;
	}

	public void setSupplierProvince(java.lang.String supplierProvince) {
		this.supplierProvince = supplierProvince;
	}

	public java.lang.String getSupplierTel1() {
		return supplierTel1;
	}

	public void setSupplierTel1(java.lang.String supplierTel1) {
		this.supplierTel1 = supplierTel1;
	}

	public java.lang.String getSupplierTel2() {
		return supplierTel2;
	}

	public void setSupplierTel2(java.lang.String supplierTel2) {
		this.supplierTel2 = supplierTel2;
	}

	public java.lang.String getSupplierZip() {
		return supplierZip;
	}

	public void setSupplierZip(java.lang.String supplierZip) {
		this.supplierZip = supplierZip;
	}

	public java.lang.String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(java.lang.String supplierid) {
		this.supplierid = supplierid;
	}

	public java.lang.String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(java.lang.String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public java.lang.String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(java.lang.String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public java.lang.String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(java.lang.String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public java.lang.String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(java.lang.String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public java.lang.String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(java.lang.String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public java.lang.String getUserdefine6() {
		return userdefine6;
	}

	public void setUserdefine6(java.lang.String userdefine6) {
		this.userdefine6 = userdefine6;
	}

	public java.lang.String getUserdefinea() {
		return userdefinea;
	}

	public void setUserdefinea(java.lang.String userdefinea) {
		this.userdefinea = userdefinea;
	}

	public java.lang.String getUserdefineb() {
		return userdefineb;
	}

	public void setUserdefineb(java.lang.String userdefineb) {
		this.userdefineb = userdefineb;
	}

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public java.lang.String getZonegroup() {
		return zonegroup;
	}

	public void setZonegroup(java.lang.String zonegroup) {
		this.zonegroup = zonegroup;
	}

	public String getColdTag() {
		return coldTag;
	}

	public void setColdTag(String coldTag) {
		this.coldTag = coldTag;
	}
}