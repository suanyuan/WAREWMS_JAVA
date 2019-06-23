package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the DOC_ORDER_HEADER database table.
 * 
 */
@Entity
public class DocOrderHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String orderno;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	private BigDecimal allocationcount;

	private String archiveflag;

	private String bAddress1;

	private String bAddress2;

	private String bAddress3;

	private String bAddress4;

	private String bCity;

	private String bContact;

	private String bCountry;

	private String bEmail;

	private String bFax;

	private String bProvince;

	private String bTel1;

	private String bTel2;

	private String bZip;

	private String billingid;

	private String billingname;

	private String cAddress1;

	private String cAddress2;

	private String cAddress3;

	private String cAddress4;

	private String cCity;

	private String cContact;

	private String cCountry;

	private String cFax;

	private String cMail;

	private String cProvince;

	private String cTel1;

	private String cTel2;

	private String cZip;

	private String carrieraddress1;

	private String carrieraddress2;

	private String carrieraddress3;

	private String carrieraddress4;

	private String carriercity;

	private String carriercontact;

	private String carriercountry;

	private String carrierfax;

	private String carrierid;

	private String carriermail;

	private String carriername;

	private String carrierprovince;

	private String carriertel1;

	private String carriertel2;

	private String carrierzip;

	private String channel;

	private String consigneeid;

	private String consigneename;

	private String consigneenameE;

	private String createsource;

	private String customerid;

	private String deliveryno;

	private String deliverynoteprintflag;

	private String deliveryterms;

	private String deliverytermsdescr;

	private String door;

	private String edisendflag;

	private String edisendflag2;

	private String edisendflag3;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edisendtime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edisendtime2;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edisendtime3;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edisendtime4;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edisendtime5;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	private String erpcancelflag;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expectedshipmenttime1;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expectedshipmenttime2;

	private String expressprintflag;

	private String followup;

	private String fulAlc;

	private String hEdi01;

	private String hEdi02;

	private String hEdi03;

	private String hEdi04;

	private String hEdi05;

	private String hEdi06;

	private String hEdi07;

	private String hEdi08;

	private BigDecimal hEdi09;

	private BigDecimal hEdi10;

	private String hEdi11;

	private String hEdi12;

	private String hEdi13;

	private String hEdi14;

	private String hEdi15;

	private String iAddress1;

	private String iAddress2;

	private String iAddress3;

	private String iAddress4;

	private String iCity;

	private String iContact;

	private String iCountry;

	private String iFax;

	private String iMail;

	private String iProvince;

	private String iTel1;

	private String iTel2;

	private String iZip;

	private BigDecimal invoiceamount;

	private String invoiceitem;

	private String invoiceno;

	private String invoiceprintflag;

	private String invoicetitle;

	private String invoicetype;

	private String issuepartyid;

	private String issuepartyname;

	private Date lastshipmenttime;

	private Date medicalxmltime;

	private String notes;

	private String orderPrintFlag;

	private Date ordertime;

	private String ordertype;
	
	private String ordertypeName;

	private String paymentterms;

	private String paymenttermsdescr;

	private String pickingPrintFlag;

	private String placeofdelivery;

	private String placeofdischarge;

	private String placeofloading;

	private String priority;

	private String puttolocation;

	private String releasestatus;

	private String releasestatusName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date requireddeliverytime;

	private String requiredeliveryno;

	private String rfgettask;

	private String route;

	private String salesorderno;

	private String serialnocatch;

	private String signDay;

	private String singlematch;

	private String soreference1;

	private String soreference2;

	private String soreference3;

	private String soreference4;

	private String soreference5;

	private String soreference6;

	private String sostatus;

	private String sostatusName;

	private String stop;

	private String transportation;

	private String udfprintflag1;

	private String udfprintflag2;

	private String udfprintflag3;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String userdefine6;

	private String userdefinea;

	private String userdefineb;

	private String warehouseid;

	private String waveno;

	private String weightingflag;

	private String zonegroup;
	
	private String allocationDetailsId;

	private BigDecimal totalQty;

	private BigDecimal totalGrossWeight;

	private BigDecimal totalCube;
	
	private List<DocOrderDetail> docOrderDetailList;

	public DocOrderHeader() {
	}

	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return this.addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public BigDecimal getAllocationcount() {
		return this.allocationcount;
	}

	public void setAllocationcount(BigDecimal allocationcount) {
		this.allocationcount = allocationcount;
	}

	public String getArchiveflag() {
		return this.archiveflag;
	}

	public void setArchiveflag(String archiveflag) {
		this.archiveflag = archiveflag;
	}

	public String getBAddress1() {
		return this.bAddress1;
	}

	public void setBAddress1(String bAddress1) {
		this.bAddress1 = bAddress1;
	}

	public String getBAddress2() {
		return this.bAddress2;
	}

	public void setBAddress2(String bAddress2) {
		this.bAddress2 = bAddress2;
	}

	public String getBAddress3() {
		return this.bAddress3;
	}

	public void setBAddress3(String bAddress3) {
		this.bAddress3 = bAddress3;
	}

	public String getBAddress4() {
		return this.bAddress4;
	}

	public void setBAddress4(String bAddress4) {
		this.bAddress4 = bAddress4;
	}

	public String getBCity() {
		return this.bCity;
	}

	public void setBCity(String bCity) {
		this.bCity = bCity;
	}

	public String getBContact() {
		return this.bContact;
	}

	public void setBContact(String bContact) {
		this.bContact = bContact;
	}

	public String getBCountry() {
		return this.bCountry;
	}

	public void setBCountry(String bCountry) {
		this.bCountry = bCountry;
	}

	public String getBEmail() {
		return this.bEmail;
	}

	public void setBEmail(String bEmail) {
		this.bEmail = bEmail;
	}

	public String getBFax() {
		return this.bFax;
	}

	public void setBFax(String bFax) {
		this.bFax = bFax;
	}

	public String getBProvince() {
		return this.bProvince;
	}

	public void setBProvince(String bProvince) {
		this.bProvince = bProvince;
	}

	public String getBTel1() {
		return this.bTel1;
	}

	public void setBTel1(String bTel1) {
		this.bTel1 = bTel1;
	}

	public String getBTel2() {
		return this.bTel2;
	}

	public void setBTel2(String bTel2) {
		this.bTel2 = bTel2;
	}

	public String getBZip() {
		return this.bZip;
	}

	public void setBZip(String bZip) {
		this.bZip = bZip;
	}

	public String getBillingid() {
		return this.billingid;
	}

	public void setBillingid(String billingid) {
		this.billingid = billingid;
	}

	public String getBillingname() {
		return this.billingname;
	}

	public void setBillingname(String billingname) {
		this.billingname = billingname;
	}

	public String getCAddress1() {
		return this.cAddress1;
	}

	public void setCAddress1(String cAddress1) {
		this.cAddress1 = cAddress1;
	}

	public String getCAddress2() {
		return this.cAddress2;
	}

	public void setCAddress2(String cAddress2) {
		this.cAddress2 = cAddress2;
	}

	public String getCAddress3() {
		return this.cAddress3;
	}

	public void setCAddress3(String cAddress3) {
		this.cAddress3 = cAddress3;
	}

	public String getCAddress4() {
		return this.cAddress4;
	}

	public void setCAddress4(String cAddress4) {
		this.cAddress4 = cAddress4;
	}

	public String getCCity() {
		return this.cCity;
	}

	public void setCCity(String cCity) {
		this.cCity = cCity;
	}

	public String getCContact() {
		return this.cContact;
	}

	public void setCContact(String cContact) {
		this.cContact = cContact;
	}

	public String getCCountry() {
		return this.cCountry;
	}

	public void setCCountry(String cCountry) {
		this.cCountry = cCountry;
	}

	public String getCFax() {
		return this.cFax;
	}

	public void setCFax(String cFax) {
		this.cFax = cFax;
	}

	public String getCMail() {
		return this.cMail;
	}

	public void setCMail(String cMail) {
		this.cMail = cMail;
	}

	public String getCProvince() {
		return this.cProvince;
	}

	public void setCProvince(String cProvince) {
		this.cProvince = cProvince;
	}

	public String getCTel1() {
		return this.cTel1;
	}

	public void setCTel1(String cTel1) {
		this.cTel1 = cTel1;
	}

	public String getCTel2() {
		return this.cTel2;
	}

	public void setCTel2(String cTel2) {
		this.cTel2 = cTel2;
	}

	public String getCZip() {
		return this.cZip;
	}

	public void setCZip(String cZip) {
		this.cZip = cZip;
	}

	public String getCarrieraddress1() {
		return this.carrieraddress1;
	}

	public void setCarrieraddress1(String carrieraddress1) {
		this.carrieraddress1 = carrieraddress1;
	}

	public String getCarrieraddress2() {
		return this.carrieraddress2;
	}

	public void setCarrieraddress2(String carrieraddress2) {
		this.carrieraddress2 = carrieraddress2;
	}

	public String getCarrieraddress3() {
		return this.carrieraddress3;
	}

	public void setCarrieraddress3(String carrieraddress3) {
		this.carrieraddress3 = carrieraddress3;
	}

	public String getCarrieraddress4() {
		return this.carrieraddress4;
	}

	public void setCarrieraddress4(String carrieraddress4) {
		this.carrieraddress4 = carrieraddress4;
	}

	public String getCarriercity() {
		return this.carriercity;
	}

	public void setCarriercity(String carriercity) {
		this.carriercity = carriercity;
	}

	public String getCarriercontact() {
		return this.carriercontact;
	}

	public void setCarriercontact(String carriercontact) {
		this.carriercontact = carriercontact;
	}

	public String getCarriercountry() {
		return this.carriercountry;
	}

	public void setCarriercountry(String carriercountry) {
		this.carriercountry = carriercountry;
	}

	public String getCarrierfax() {
		return this.carrierfax;
	}

	public void setCarrierfax(String carrierfax) {
		this.carrierfax = carrierfax;
	}

	public String getCarrierid() {
		return this.carrierid;
	}

	public void setCarrierid(String carrierid) {
		this.carrierid = carrierid;
	}

	public String getCarriermail() {
		return this.carriermail;
	}

	public void setCarriermail(String carriermail) {
		this.carriermail = carriermail;
	}

	public String getCarriername() {
		return this.carriername;
	}

	public void setCarriername(String carriername) {
		this.carriername = carriername;
	}

	public String getCarrierprovince() {
		return this.carrierprovince;
	}

	public void setCarrierprovince(String carrierprovince) {
		this.carrierprovince = carrierprovince;
	}

	public String getCarriertel1() {
		return this.carriertel1;
	}

	public void setCarriertel1(String carriertel1) {
		this.carriertel1 = carriertel1;
	}

	public String getCarriertel2() {
		return this.carriertel2;
	}

	public void setCarriertel2(String carriertel2) {
		this.carriertel2 = carriertel2;
	}

	public String getCarrierzip() {
		return this.carrierzip;
	}

	public void setCarrierzip(String carrierzip) {
		this.carrierzip = carrierzip;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getConsigneeid() {
		return this.consigneeid;
	}

	public void setConsigneeid(String consigneeid) {
		this.consigneeid = consigneeid;
	}

	public String getConsigneename() {
		return this.consigneename;
	}

	public void setConsigneename(String consigneename) {
		this.consigneename = consigneename;
	}

	public String getConsigneenameE() {
		return this.consigneenameE;
	}

	public void setConsigneenameE(String consigneenameE) {
		this.consigneenameE = consigneenameE;
	}

	public String getCreatesource() {
		return this.createsource;
	}

	public void setCreatesource(String createsource) {
		this.createsource = createsource;
	}

	public String getCustomerid() {
		return this.customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getDeliveryno() {
		return this.deliveryno;
	}

	public void setDeliveryno(String deliveryno) {
		this.deliveryno = deliveryno;
	}

	public String getDeliverynoteprintflag() {
		return this.deliverynoteprintflag;
	}

	public void setDeliverynoteprintflag(String deliverynoteprintflag) {
		this.deliverynoteprintflag = deliverynoteprintflag;
	}

	public String getDeliveryterms() {
		return this.deliveryterms;
	}

	public void setDeliveryterms(String deliveryterms) {
		this.deliveryterms = deliveryterms;
	}

	public String getDeliverytermsdescr() {
		return this.deliverytermsdescr;
	}

	public void setDeliverytermsdescr(String deliverytermsdescr) {
		this.deliverytermsdescr = deliverytermsdescr;
	}

	public String getDoor() {
		return this.door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getEdisendflag() {
		return this.edisendflag;
	}

	public void setEdisendflag(String edisendflag) {
		this.edisendflag = edisendflag;
	}

	public String getEdisendflag2() {
		return this.edisendflag2;
	}

	public void setEdisendflag2(String edisendflag2) {
		this.edisendflag2 = edisendflag2;
	}

	public String getEdisendflag3() {
		return this.edisendflag3;
	}

	public void setEdisendflag3(String edisendflag3) {
		this.edisendflag3 = edisendflag3;
	}

	public Date getEdisendtime() {
		return this.edisendtime;
	}

	public void setEdisendtime(Date edisendtime) {
		this.edisendtime = edisendtime;
	}

	public Date getEdisendtime2() {
		return this.edisendtime2;
	}

	public void setEdisendtime2(Date edisendtime2) {
		this.edisendtime2 = edisendtime2;
	}

	public Date getEdisendtime3() {
		return this.edisendtime3;
	}

	public void setEdisendtime3(Date edisendtime3) {
		this.edisendtime3 = edisendtime3;
	}

	public Date getEdisendtime4() {
		return this.edisendtime4;
	}

	public void setEdisendtime4(Date edisendtime4) {
		this.edisendtime4 = edisendtime4;
	}

	public Date getEdisendtime5() {
		return this.edisendtime5;
	}

	public void setEdisendtime5(Date edisendtime5) {
		this.edisendtime5 = edisendtime5;
	}

	public Date getEdittime() {
		return this.edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return this.editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getErpcancelflag() {
		return this.erpcancelflag;
	}

	public void setErpcancelflag(String erpcancelflag) {
		this.erpcancelflag = erpcancelflag;
	}

	public Date getExpectedshipmenttime1() {
		return this.expectedshipmenttime1;
	}

	public void setExpectedshipmenttime1(Date expectedshipmenttime1) {
		this.expectedshipmenttime1 = expectedshipmenttime1;
	}

	public Date getExpectedshipmenttime2() {
		return this.expectedshipmenttime2;
	}

	public void setExpectedshipmenttime2(Date expectedshipmenttime2) {
		this.expectedshipmenttime2 = expectedshipmenttime2;
	}

	public String getExpressprintflag() {
		return this.expressprintflag;
	}

	public void setExpressprintflag(String expressprintflag) {
		this.expressprintflag = expressprintflag;
	}

	public String getFollowup() {
		return this.followup;
	}

	public void setFollowup(String followup) {
		this.followup = followup;
	}

	public String getFulAlc() {
		return this.fulAlc;
	}

	public void setFulAlc(String fulAlc) {
		this.fulAlc = fulAlc;
	}

	public String getHEdi01() {
		return this.hEdi01;
	}

	public void setHEdi01(String hEdi01) {
		this.hEdi01 = hEdi01;
	}

	public String getHEdi02() {
		return this.hEdi02;
	}

	public void setHEdi02(String hEdi02) {
		this.hEdi02 = hEdi02;
	}

	public String getHEdi03() {
		return this.hEdi03;
	}

	public void setHEdi03(String hEdi03) {
		this.hEdi03 = hEdi03;
	}

	public String getHEdi04() {
		return this.hEdi04;
	}

	public void setHEdi04(String hEdi04) {
		this.hEdi04 = hEdi04;
	}

	public String getHEdi05() {
		return this.hEdi05;
	}

	public void setHEdi05(String hEdi05) {
		this.hEdi05 = hEdi05;
	}

	public String getHEdi06() {
		return this.hEdi06;
	}

	public void setHEdi06(String hEdi06) {
		this.hEdi06 = hEdi06;
	}

	public String getHEdi07() {
		return this.hEdi07;
	}

	public void setHEdi07(String hEdi07) {
		this.hEdi07 = hEdi07;
	}

	public String getHEdi08() {
		return this.hEdi08;
	}

	public void setHEdi08(String hEdi08) {
		this.hEdi08 = hEdi08;
	}

	public BigDecimal getHEdi09() {
		return this.hEdi09;
	}

	public void setHEdi09(BigDecimal hEdi09) {
		this.hEdi09 = hEdi09;
	}

	public BigDecimal getHEdi10() {
		return this.hEdi10;
	}

	public void setHEdi10(BigDecimal hEdi10) {
		this.hEdi10 = hEdi10;
	}

	public String getHEdi11() {
		return this.hEdi11;
	}

	public void setHEdi11(String hEdi11) {
		this.hEdi11 = hEdi11;
	}

	public String getHEdi12() {
		return this.hEdi12;
	}

	public void setHEdi12(String hEdi12) {
		this.hEdi12 = hEdi12;
	}

	public String getHEdi13() {
		return this.hEdi13;
	}

	public void setHEdi13(String hEdi13) {
		this.hEdi13 = hEdi13;
	}

	public String getHEdi14() {
		return this.hEdi14;
	}

	public void setHEdi14(String hEdi14) {
		this.hEdi14 = hEdi14;
	}

	public String getHEdi15() {
		return this.hEdi15;
	}

	public void setHEdi15(String hEdi15) {
		this.hEdi15 = hEdi15;
	}

	public String getIAddress1() {
		return this.iAddress1;
	}

	public void setIAddress1(String iAddress1) {
		this.iAddress1 = iAddress1;
	}

	public String getIAddress2() {
		return this.iAddress2;
	}

	public void setIAddress2(String iAddress2) {
		this.iAddress2 = iAddress2;
	}

	public String getIAddress3() {
		return this.iAddress3;
	}

	public void setIAddress3(String iAddress3) {
		this.iAddress3 = iAddress3;
	}

	public String getIAddress4() {
		return this.iAddress4;
	}

	public void setIAddress4(String iAddress4) {
		this.iAddress4 = iAddress4;
	}

	public String getICity() {
		return this.iCity;
	}

	public void setICity(String iCity) {
		this.iCity = iCity;
	}

	public String getIContact() {
		return this.iContact;
	}

	public void setIContact(String iContact) {
		this.iContact = iContact;
	}

	public String getICountry() {
		return this.iCountry;
	}

	public void setICountry(String iCountry) {
		this.iCountry = iCountry;
	}

	public String getIFax() {
		return this.iFax;
	}

	public void setIFax(String iFax) {
		this.iFax = iFax;
	}

	public String getIMail() {
		return this.iMail;
	}

	public void setIMail(String iMail) {
		this.iMail = iMail;
	}

	public String getIProvince() {
		return this.iProvince;
	}

	public void setIProvince(String iProvince) {
		this.iProvince = iProvince;
	}

	public String getITel1() {
		return this.iTel1;
	}

	public void setITel1(String iTel1) {
		this.iTel1 = iTel1;
	}

	public String getITel2() {
		return this.iTel2;
	}

	public void setITel2(String iTel2) {
		this.iTel2 = iTel2;
	}

	public String getIZip() {
		return this.iZip;
	}

	public void setIZip(String iZip) {
		this.iZip = iZip;
	}

	public BigDecimal getInvoiceamount() {
		return this.invoiceamount;
	}

	public void setInvoiceamount(BigDecimal invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	public String getInvoiceitem() {
		return this.invoiceitem;
	}

	public void setInvoiceitem(String invoiceitem) {
		this.invoiceitem = invoiceitem;
	}

	public String getInvoiceno() {
		return this.invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getInvoiceprintflag() {
		return this.invoiceprintflag;
	}

	public void setInvoiceprintflag(String invoiceprintflag) {
		this.invoiceprintflag = invoiceprintflag;
	}

	public String getInvoicetitle() {
		return this.invoicetitle;
	}

	public void setInvoicetitle(String invoicetitle) {
		this.invoicetitle = invoicetitle;
	}

	public String getInvoicetype() {
		return this.invoicetype;
	}

	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}

	public String getIssuepartyid() {
		return this.issuepartyid;
	}

	public void setIssuepartyid(String issuepartyid) {
		this.issuepartyid = issuepartyid;
	}

	public String getIssuepartyname() {
		return this.issuepartyname;
	}

	public void setIssuepartyname(String issuepartyname) {
		this.issuepartyname = issuepartyname;
	}

	public Date getLastshipmenttime() {
		return this.lastshipmenttime;
	}

	public void setLastshipmenttime(Date lastshipmenttime) {
		this.lastshipmenttime = lastshipmenttime;
	}

	public Date getMedicalxmltime() {
		return this.medicalxmltime;
	}

	public void setMedicalxmltime(Date medicalxmltime) {
		this.medicalxmltime = medicalxmltime;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getOrderPrintFlag() {
		return this.orderPrintFlag;
	}

	public void setOrderPrintFlag(String orderPrintFlag) {
		this.orderPrintFlag = orderPrintFlag;
	}

	public Date getOrdertime() {
		return this.ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getOrdertypeName() {
		return ordertypeName;
	}

	public void setOrdertypeName(String ordertypeName) {
		this.ordertypeName = ordertypeName;
	}

	public String getPaymentterms() {
		return this.paymentterms;
	}

	public void setPaymentterms(String paymentterms) {
		this.paymentterms = paymentterms;
	}

	public String getPaymenttermsdescr() {
		return this.paymenttermsdescr;
	}

	public void setPaymenttermsdescr(String paymenttermsdescr) {
		this.paymenttermsdescr = paymenttermsdescr;
	}

	public String getPickingPrintFlag() {
		return this.pickingPrintFlag;
	}

	public void setPickingPrintFlag(String pickingPrintFlag) {
		this.pickingPrintFlag = pickingPrintFlag;
	}

	public String getPlaceofdelivery() {
		return this.placeofdelivery;
	}

	public void setPlaceofdelivery(String placeofdelivery) {
		this.placeofdelivery = placeofdelivery;
	}

	public String getPlaceofdischarge() {
		return this.placeofdischarge;
	}

	public void setPlaceofdischarge(String placeofdischarge) {
		this.placeofdischarge = placeofdischarge;
	}

	public String getPlaceofloading() {
		return this.placeofloading;
	}

	public void setPlaceofloading(String placeofloading) {
		this.placeofloading = placeofloading;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPuttolocation() {
		return this.puttolocation;
	}

	public void setPuttolocation(String puttolocation) {
		this.puttolocation = puttolocation;
	}

	public String getReleasestatus() {
		return this.releasestatus;
	}

	public void setReleasestatus(String releasestatus) {
		this.releasestatus = releasestatus;
	}

	public String getReleasestatusName() {
		return releasestatusName;
	}

	public void setReleasestatusName(String releasestatusName) {
		this.releasestatusName = releasestatusName;
	}

	public Date getRequireddeliverytime() {
		return this.requireddeliverytime;
	}

	public void setRequireddeliverytime(Date requireddeliverytime) {
		this.requireddeliverytime = requireddeliverytime;
	}

	public String getRequiredeliveryno() {
		return this.requiredeliveryno;
	}

	public void setRequiredeliveryno(String requiredeliveryno) {
		this.requiredeliveryno = requiredeliveryno;
	}

	public String getRfgettask() {
		return this.rfgettask;
	}

	public void setRfgettask(String rfgettask) {
		this.rfgettask = rfgettask;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getSalesorderno() {
		return this.salesorderno;
	}

	public void setSalesorderno(String salesorderno) {
		this.salesorderno = salesorderno;
	}

	public String getSerialnocatch() {
		return this.serialnocatch;
	}

	public void setSerialnocatch(String serialnocatch) {
		this.serialnocatch = serialnocatch;
	}

	public String getSignDay() {
		return this.signDay;
	}

	public void setSignDay(String signDay) {
		this.signDay = signDay;
	}

	public String getSinglematch() {
		return this.singlematch;
	}

	public void setSinglematch(String singlematch) {
		this.singlematch = singlematch;
	}

	public String getSoreference1() {
		return this.soreference1;
	}

	public void setSoreference1(String soreference1) {
		this.soreference1 = soreference1;
	}

	public String getSoreference2() {
		return this.soreference2;
	}

	public void setSoreference2(String soreference2) {
		this.soreference2 = soreference2;
	}

	public String getSoreference3() {
		return this.soreference3;
	}

	public void setSoreference3(String soreference3) {
		this.soreference3 = soreference3;
	}

	public String getSoreference4() {
		return this.soreference4;
	}

	public void setSoreference4(String soreference4) {
		this.soreference4 = soreference4;
	}

	public String getSoreference5() {
		return this.soreference5;
	}

	public void setSoreference5(String soreference5) {
		this.soreference5 = soreference5;
	}

	public String getSoreference6() {
		return this.soreference6;
	}

	public void setSoreference6(String soreference6) {
		this.soreference6 = soreference6;
	}

	public String getSostatus() {
		return this.sostatus;
	}

	public void setSostatus(String sostatus) {
		this.sostatus = sostatus;
	}

	public String getSostatusName() {
		return sostatusName;
	}

	public void setSostatusName(String sostatusName) {
		this.sostatusName = sostatusName;
	}

	public String getStop() {
		return this.stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getTransportation() {
		return this.transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public String getUdfprintflag1() {
		return this.udfprintflag1;
	}

	public void setUdfprintflag1(String udfprintflag1) {
		this.udfprintflag1 = udfprintflag1;
	}

	public String getUdfprintflag2() {
		return this.udfprintflag2;
	}

	public void setUdfprintflag2(String udfprintflag2) {
		this.udfprintflag2 = udfprintflag2;
	}

	public String getUdfprintflag3() {
		return this.udfprintflag3;
	}

	public void setUdfprintflag3(String udfprintflag3) {
		this.udfprintflag3 = udfprintflag3;
	}

	public String getUserdefine1() {
		return this.userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return this.userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return this.userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return this.userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return this.userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public String getUserdefine6() {
		return this.userdefine6;
	}

	public void setUserdefine6(String userdefine6) {
		this.userdefine6 = userdefine6;
	}

	public String getUserdefinea() {
		return this.userdefinea;
	}

	public void setUserdefinea(String userdefinea) {
		this.userdefinea = userdefinea;
	}

	public String getUserdefineb() {
		return this.userdefineb;
	}

	public void setUserdefineb(String userdefineb) {
		this.userdefineb = userdefineb;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getWaveno() {
		return this.waveno;
	}

	public void setWaveno(String waveno) {
		this.waveno = waveno;
	}

	public String getWeightingflag() {
		return this.weightingflag;
	}

	public void setWeightingflag(String weightingflag) {
		this.weightingflag = weightingflag;
	}

	public String getZonegroup() {
		return this.zonegroup;
	}

	public void setZonegroup(String zonegroup) {
		this.zonegroup = zonegroup;
	}

	public String getAllocationDetailsId() {
		return allocationDetailsId;
	}

	public void setAllocationDetailsId(String allocationDetailsId) {
		this.allocationDetailsId = allocationDetailsId;
	}

	public BigDecimal getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(BigDecimal totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getTotalGrossWeight() {
		return totalGrossWeight;
	}

	public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	public BigDecimal getTotalCube() {
		return totalCube;
	}

	public void setTotalCube(BigDecimal totalCube) {
		this.totalCube = totalCube;
	}

	public List<DocOrderDetail> getDocOrderDetailList() {
		return docOrderDetailList;
	}

	public void setDocOrderDetailList(List<DocOrderDetail> docOrderDetailList) {
		this.docOrderDetailList = docOrderDetailList;
	}
}