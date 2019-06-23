package com.wms.vo.form;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocOrderHeaderForm {

	private java.lang.String orderno;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.math.BigDecimal allocationcount;
	private java.lang.String archiveflag;
	private java.lang.String bAddress1;
	private java.lang.String bAddress2;
	private java.lang.String bAddress3;
	private java.lang.String bAddress4;
	private java.lang.String bCity;
	private java.lang.String bContact;
	private java.lang.String bCountry;
	private java.lang.String bEmail;
	private java.lang.String bFax;
	private java.lang.String bProvince;
	private java.lang.String bTel1;
	private java.lang.String bTel2;
	private java.lang.String bZip;
	private java.lang.String billingid;
	private java.lang.String billingname;
	private java.lang.String cAddress1;
	private java.lang.String cAddress2;
	private java.lang.String cAddress3;
	private java.lang.String cAddress4;
	private java.lang.String cCity;
	private java.lang.String cContact;
	private java.lang.String cCountry;
	private java.lang.String cFax;
	private java.lang.String cMail;
	private java.lang.String cProvince;
	private java.lang.String cTel1;
	private java.lang.String cTel2;
	private java.lang.String cZip;
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
	private java.lang.String channel;
	private java.lang.String consigneeid;
	private java.lang.String consigneename;
	private java.lang.String consigneenameE;
	private java.lang.String createsource;
	private java.lang.String customerid;
	private java.lang.String deliveryno;
	private java.lang.String deliverynoteprintflag;
	private java.lang.String deliveryterms;
	private java.lang.String deliverytermsdescr;
	private java.lang.String door;
	private java.lang.String edisendflag;
	private java.lang.String edisendflag2;
	private java.lang.String edisendflag3;
	private java.util.Date edisendtime;
	private java.util.Date edisendtime2;
	private java.util.Date edisendtime3;
	private java.util.Date edisendtime4;
	private java.util.Date edisendtime5;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.lang.String erpcancelflag;
	private java.util.Date expectedshipmenttime1;
	private java.util.Date expectedshipmenttime2;
	private java.lang.String expressprintflag;
	private java.lang.String followup;
	private java.lang.String fulAlc;
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
	private java.lang.String hEdi12;
	private java.lang.String hEdi13;
	private java.lang.String hEdi14;
	private java.lang.String hEdi15;
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
	private java.math.BigDecimal invoiceamount;
	private java.lang.String invoiceitem;
	private java.lang.String invoiceno;
	private java.lang.String invoiceprintflag;
	private java.lang.String invoicetitle;
	private java.lang.String invoicetype;
	private java.lang.String issuepartyid;
	private java.lang.String issuepartyname;
	private java.util.Date lastshipmenttime;
	private java.util.Date medicalxmltime;
	private java.lang.String notes;
	private java.lang.String orderPrintFlag;
	private java.util.Date ordertime;
	private java.lang.String ordertype;
	private java.lang.String ordertypeName;
	private java.lang.String paymentterms;
	private java.lang.String paymenttermsdescr;
	private java.lang.String pickingPrintFlag;
	private java.lang.String placeofdelivery;
	private java.lang.String placeofdischarge;
	private java.lang.String placeofloading;
	private java.lang.String priority;
	private java.lang.String puttolocation;
	private java.lang.String releasestatus;
	private java.lang.String releasestatusName;
	private java.util.Date requireddeliverytime;
	private java.lang.String requiredeliveryno;
	private java.lang.String rfgettask;
	private java.lang.String route;
	private java.lang.String salesorderno;
	private java.lang.String serialnocatch;
	private java.lang.String signDay;
	private java.lang.String singlematch;
	private java.lang.String soreference1;
	private java.lang.String soreference2;
	private java.lang.String soreference3;
	private java.lang.String soreference4;
	private java.lang.String soreference5;
	private java.lang.String soreference6;
	private java.lang.String sostatus;
	private java.lang.String sostatusName;
	private java.lang.String stop;
	private java.lang.String transportation;
	private java.lang.String udfprintflag1;
	private java.lang.String udfprintflag2;
	private java.lang.String udfprintflag3;
	private java.lang.String userdefine1;
	private java.lang.String userdefine2;
	private java.lang.String userdefine3;
	private java.lang.String userdefine4;
	private java.lang.String userdefine5;
	private java.lang.String userdefine6;
	private java.lang.String userdefinea;
	private java.lang.String userdefineb;
	private java.lang.String warehouseid;
	private java.lang.String waveno;
	private java.lang.String weightingflag;
	private java.lang.String zonegroup;

	public java.lang.String getOrderno() {
		return orderno;
	}

	public void setOrderno(java.lang.String orderno) {
		this.orderno = orderno;
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

	public java.math.BigDecimal getAllocationcount() {
		return allocationcount;
	}

	public void setAllocationcount(java.math.BigDecimal allocationcount) {
		this.allocationcount = allocationcount;
	}

	public java.lang.String getArchiveflag() {
		return archiveflag;
	}

	public void setArchiveflag(java.lang.String archiveflag) {
		this.archiveflag = archiveflag;
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

	public java.lang.String getBEmail() {
		return bEmail;
	}

	public void setBEmail(java.lang.String bEmail) {
		this.bEmail = bEmail;
	}

	public java.lang.String getBFax() {
		return bFax;
	}

	public void setBFax(java.lang.String bFax) {
		this.bFax = bFax;
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

	public java.lang.String getCAddress1() {
		return cAddress1;
	}

	public void setCAddress1(java.lang.String cAddress1) {
		this.cAddress1 = cAddress1;
	}

	public java.lang.String getCAddress2() {
		return cAddress2;
	}

	public void setCAddress2(java.lang.String cAddress2) {
		this.cAddress2 = cAddress2;
	}

	public java.lang.String getCAddress3() {
		return cAddress3;
	}

	public void setCAddress3(java.lang.String cAddress3) {
		this.cAddress3 = cAddress3;
	}

	public java.lang.String getCAddress4() {
		return cAddress4;
	}

	public void setCAddress4(java.lang.String cAddress4) {
		this.cAddress4 = cAddress4;
	}

	public java.lang.String getCCity() {
		return cCity;
	}

	public void setCCity(java.lang.String cCity) {
		this.cCity = cCity;
	}

	public java.lang.String getCContact() {
		return cContact;
	}

	public void setCContact(java.lang.String cContact) {
		this.cContact = cContact;
	}

	public java.lang.String getCCountry() {
		return cCountry;
	}

	public void setCCountry(java.lang.String cCountry) {
		this.cCountry = cCountry;
	}

	public java.lang.String getCFax() {
		return cFax;
	}

	public void setCFax(java.lang.String cFax) {
		this.cFax = cFax;
	}

	public java.lang.String getCMail() {
		return cMail;
	}

	public void setCMail(java.lang.String cMail) {
		this.cMail = cMail;
	}

	public java.lang.String getCProvince() {
		return cProvince;
	}

	public void setCProvince(java.lang.String cProvince) {
		this.cProvince = cProvince;
	}

	public java.lang.String getCTel1() {
		return cTel1;
	}

	public void setCTel1(java.lang.String cTel1) {
		this.cTel1 = cTel1;
	}

	public java.lang.String getCTel2() {
		return cTel2;
	}

	public void setCTel2(java.lang.String cTel2) {
		this.cTel2 = cTel2;
	}

	public java.lang.String getCZip() {
		return cZip;
	}

	public void setCZip(java.lang.String cZip) {
		this.cZip = cZip;
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

	public java.lang.String getChannel() {
		return channel;
	}

	public void setChannel(java.lang.String channel) {
		this.channel = channel;
	}

	public java.lang.String getConsigneeid() {
		return consigneeid;
	}

	public void setConsigneeid(java.lang.String consigneeid) {
		this.consigneeid = consigneeid;
	}

	public java.lang.String getConsigneename() {
		return consigneename;
	}

	public void setConsigneename(java.lang.String consigneename) {
		this.consigneename = consigneename;
	}

	public java.lang.String getConsigneenameE() {
		return consigneenameE;
	}

	public void setConsigneenameE(java.lang.String consigneenameE) {
		this.consigneenameE = consigneenameE;
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

	public java.lang.String getDeliveryno() {
		return deliveryno;
	}

	public void setDeliveryno(java.lang.String deliveryno) {
		this.deliveryno = deliveryno;
	}

	public java.lang.String getDeliverynoteprintflag() {
		return deliverynoteprintflag;
	}

	public void setDeliverynoteprintflag(java.lang.String deliverynoteprintflag) {
		this.deliverynoteprintflag = deliverynoteprintflag;
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

	public java.lang.String getDoor() {
		return door;
	}

	public void setDoor(java.lang.String door) {
		this.door = door;
	}

	public java.lang.String getEdisendflag() {
		return edisendflag;
	}

	public void setEdisendflag(java.lang.String edisendflag) {
		this.edisendflag = edisendflag;
	}

	public java.lang.String getEdisendflag2() {
		return edisendflag2;
	}

	public void setEdisendflag2(java.lang.String edisendflag2) {
		this.edisendflag2 = edisendflag2;
	}

	public java.lang.String getEdisendflag3() {
		return edisendflag3;
	}

	public void setEdisendflag3(java.lang.String edisendflag3) {
		this.edisendflag3 = edisendflag3;
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

	public java.lang.String getErpcancelflag() {
		return erpcancelflag;
	}

	public void setErpcancelflag(java.lang.String erpcancelflag) {
		this.erpcancelflag = erpcancelflag;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getExpectedshipmenttime1() {
		return expectedshipmenttime1;
	}

	public void setExpectedshipmenttime1(java.util.Date expectedshipmenttime1) {
		this.expectedshipmenttime1 = expectedshipmenttime1;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getExpectedshipmenttime2() {
		return expectedshipmenttime2;
	}

	public void setExpectedshipmenttime2(java.util.Date expectedshipmenttime2) {
		this.expectedshipmenttime2 = expectedshipmenttime2;
	}

	public java.lang.String getExpressprintflag() {
		return expressprintflag;
	}

	public void setExpressprintflag(java.lang.String expressprintflag) {
		this.expressprintflag = expressprintflag;
	}

	public java.lang.String getFollowup() {
		return followup;
	}

	public void setFollowup(java.lang.String followup) {
		this.followup = followup;
	}

	public java.lang.String getFulAlc() {
		return fulAlc;
	}

	public void setFulAlc(java.lang.String fulAlc) {
		this.fulAlc = fulAlc;
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

	public java.lang.String getHEdi12() {
		return hEdi12;
	}

	public void setHEdi12(java.lang.String hEdi12) {
		this.hEdi12 = hEdi12;
	}

	public java.lang.String getHEdi13() {
		return hEdi13;
	}

	public void setHEdi13(java.lang.String hEdi13) {
		this.hEdi13 = hEdi13;
	}

	public java.lang.String getHEdi14() {
		return hEdi14;
	}

	public void setHEdi14(java.lang.String hEdi14) {
		this.hEdi14 = hEdi14;
	}

	public java.lang.String getHEdi15() {
		return hEdi15;
	}

	public void setHEdi15(java.lang.String hEdi15) {
		this.hEdi15 = hEdi15;
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

	public java.math.BigDecimal getInvoiceamount() {
		return invoiceamount;
	}

	public void setInvoiceamount(java.math.BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	public java.lang.String getInvoiceitem() {
		return invoiceitem;
	}

	public void setInvoiceitem(java.lang.String invoiceitem) {
		this.invoiceitem = invoiceitem;
	}

	public java.lang.String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(java.lang.String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public java.lang.String getInvoiceprintflag() {
		return invoiceprintflag;
	}

	public void setInvoiceprintflag(java.lang.String invoiceprintflag) {
		this.invoiceprintflag = invoiceprintflag;
	}

	public java.lang.String getInvoicetitle() {
		return invoicetitle;
	}

	public void setInvoicetitle(java.lang.String invoicetitle) {
		this.invoicetitle = invoicetitle;
	}

	public java.lang.String getInvoicetype() {
		return invoicetype;
	}

	public void setInvoicetype(java.lang.String invoicetype) {
		this.invoicetype = invoicetype;
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
	public java.util.Date getLastshipmenttime() {
		return lastshipmenttime;
	}

	public void setLastshipmenttime(java.util.Date lastshipmenttime) {
		this.lastshipmenttime = lastshipmenttime;
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

	public java.lang.String getOrderPrintFlag() {
		return orderPrintFlag;
	}

	public void setOrderPrintFlag(java.lang.String orderPrintFlag) {
		this.orderPrintFlag = orderPrintFlag;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(java.util.Date ordertime) {
		this.ordertime = ordertime;
	}

	public java.lang.String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(java.lang.String ordertype) {
		this.ordertype = ordertype;
	}

	public java.lang.String getOrdertypeName() {
		return ordertypeName;
	}

	public void setOrdertypeName(java.lang.String ordertypeName) {
		this.ordertypeName = ordertypeName;
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

	public java.lang.String getPickingPrintFlag() {
		return pickingPrintFlag;
	}

	public void setPickingPrintFlag(java.lang.String pickingPrintFlag) {
		this.pickingPrintFlag = pickingPrintFlag;
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

	public java.lang.String getPriority() {
		return priority;
	}

	public void setPriority(java.lang.String priority) {
		this.priority = priority;
	}

	public java.lang.String getPuttolocation() {
		return puttolocation;
	}

	public void setPuttolocation(java.lang.String puttolocation) {
		this.puttolocation = puttolocation;
	}

	public java.lang.String getReleasestatus() {
		return releasestatus;
	}

	public void setReleasestatus(java.lang.String releasestatus) {
		this.releasestatus = releasestatus;
	}

	public java.lang.String getReleasestatusName() {
		return releasestatusName;
	}

	public void setReleasestatusName(java.lang.String releasestatusName) {
		this.releasestatusName = releasestatusName;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getRequireddeliverytime() {
		return requireddeliverytime;
	}

	public void setRequireddeliverytime(java.util.Date requireddeliverytime) {
		this.requireddeliverytime = requireddeliverytime;
	}

	public java.lang.String getRequiredeliveryno() {
		return requiredeliveryno;
	}

	public void setRequiredeliveryno(java.lang.String requiredeliveryno) {
		this.requiredeliveryno = requiredeliveryno;
	}

	public java.lang.String getRfgettask() {
		return rfgettask;
	}

	public void setRfgettask(java.lang.String rfgettask) {
		this.rfgettask = rfgettask;
	}

	public java.lang.String getRoute() {
		return route;
	}

	public void setRoute(java.lang.String route) {
		this.route = route;
	}

	public java.lang.String getSalesorderno() {
		return salesorderno;
	}

	public void setSalesorderno(java.lang.String salesorderno) {
		this.salesorderno = salesorderno;
	}

	public java.lang.String getSerialnocatch() {
		return serialnocatch;
	}

	public void setSerialnocatch(java.lang.String serialnocatch) {
		this.serialnocatch = serialnocatch;
	}

	public java.lang.String getSignDay() {
		return signDay;
	}

	public void setSignDay(java.lang.String signDay) {
		this.signDay = signDay;
	}

	public java.lang.String getSinglematch() {
		return singlematch;
	}

	public void setSinglematch(java.lang.String singlematch) {
		this.singlematch = singlematch;
	}

	public java.lang.String getSoreference1() {
		return soreference1;
	}

	public void setSoreference1(java.lang.String soreference1) {
		this.soreference1 = soreference1;
	}

	public java.lang.String getSoreference2() {
		return soreference2;
	}

	public void setSoreference2(java.lang.String soreference2) {
		this.soreference2 = soreference2;
	}

	public java.lang.String getSoreference3() {
		return soreference3;
	}

	public void setSoreference3(java.lang.String soreference3) {
		this.soreference3 = soreference3;
	}

	public java.lang.String getSoreference4() {
		return soreference4;
	}

	public void setSoreference4(java.lang.String soreference4) {
		this.soreference4 = soreference4;
	}

	public java.lang.String getSoreference5() {
		return soreference5;
	}

	public void setSoreference5(java.lang.String soreference5) {
		this.soreference5 = soreference5;
	}

	public java.lang.String getSoreference6() {
		return soreference6;
	}

	public void setSoreference6(java.lang.String soreference6) {
		this.soreference6 = soreference6;
	}

	public java.lang.String getSostatus() {
		return sostatus;
	}

	public void setSostatus(java.lang.String sostatus) {
		this.sostatus = sostatus;
	}

	public java.lang.String getSostatusName() {
		return sostatusName;
	}

	public void setSostatusName(java.lang.String sostatusName) {
		this.sostatusName = sostatusName;
	}

	public java.lang.String getStop() {
		return stop;
	}

	public void setStop(java.lang.String stop) {
		this.stop = stop;
	}

	public java.lang.String getTransportation() {
		return transportation;
	}

	public void setTransportation(java.lang.String transportation) {
		this.transportation = transportation;
	}

	public java.lang.String getUdfprintflag1() {
		return udfprintflag1;
	}

	public void setUdfprintflag1(java.lang.String udfprintflag1) {
		this.udfprintflag1 = udfprintflag1;
	}

	public java.lang.String getUdfprintflag2() {
		return udfprintflag2;
	}

	public void setUdfprintflag2(java.lang.String udfprintflag2) {
		this.udfprintflag2 = udfprintflag2;
	}

	public java.lang.String getUdfprintflag3() {
		return udfprintflag3;
	}

	public void setUdfprintflag3(java.lang.String udfprintflag3) {
		this.udfprintflag3 = udfprintflag3;
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

	public java.lang.String getWaveno() {
		return waveno;
	}

	public void setWaveno(java.lang.String waveno) {
		this.waveno = waveno;
	}

	public java.lang.String getWeightingflag() {
		return weightingflag;
	}

	public void setWeightingflag(java.lang.String weightingflag) {
		this.weightingflag = weightingflag;
	}

	public java.lang.String getZonegroup() {
		return zonegroup;
	}

	public void setZonegroup(java.lang.String zonegroup) {
		this.zonegroup = zonegroup;
	}

}