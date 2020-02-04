package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the DOC_ASN_HEADER database table.
 * 
 */
@Entity
@Table(name="DOC_ASN_HEADER")
@NamedQuery(name="DocAsnHeader.findAll", query="SELECT d FROM DocAsnHeader d")
public class DocAsnHeader implements Serializable {

    /**
     * 定向订单
     */
    public static String ASN_TYPE_DX = "DX";

    /**
     * 引用订单
     */
    public static String ASN_TYPE_YY = "YY";

	private static final long serialVersionUID = 1L;

	@Id
	private String asnno;

	@Temporal(TemporalType.DATE)
	private Date actualarrivetime;

	@Temporal(TemporalType.DATE)
	private Date addtime;

	private String addwho;

	private String archiveflag;

	@Column(name="ASN_PRINT_FLAG")
	private String asnPrintFlag;

	@Temporal(TemporalType.DATE)
	private Date asncreationtime;

	private String asnreference1;

	private String asnreference2;

	private String asnreference3;

	private String asnreference4;

	private String asnreference5;

	private String asnstatus;
	private String asnstatusName;

	private String asntype;
	private String asntypeName;

	@Column(name="B_ADDRESS1")
	private String bAddress1;

	@Column(name="B_ADDRESS2")
	private String bAddress2;

	@Column(name="B_ADDRESS3")
	private String bAddress3;

	@Column(name="B_ADDRESS4")
	private String bAddress4;

	@Column(name="B_CITY")
	private String bCity;

	@Column(name="B_CONTACT")
	private String bContact;

	@Column(name="B_COUNTRY")
	private String bCountry;

	@Column(name="B_FAX")
	private String bFax;

	@Column(name="B_MAIL")
	private String bMail;

	@Column(name="B_PROVINCE")
	private String bProvince;

	@Column(name="B_TEL1")
	private String bTel1;

	@Column(name="B_TEL2")
	private String bTel2;

	@Column(name="B_ZIP")
	private String bZip;

	@Column(name="BILLINGCLASS_GROUP")
	private String billingclassGroup;

	private String billingid;

	private String billingname;

	@Column(name="BYTRACE_FLAG")
	private String bytraceFlag;

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

	private String countryofdestination;

	private String countryoforigin;

	private String createsource;

	private String customerid;

	private String deliveryterms;

	private String deliverytermsdescr;

	private String deliveryvehicleno;

	private String deliveryvehicletype;

	private String door;

	private String driver;

	private String edisendflag;

	@Temporal(TemporalType.DATE)
	private Date edisendtime;

	@Temporal(TemporalType.DATE)
	private Date edisendtime2;

	@Temporal(TemporalType.DATE)
	private Date edisendtime3;

	@Temporal(TemporalType.DATE)
	private Date edisendtime4;

	@Temporal(TemporalType.DATE)
	private Date edisendtime5;

	@Temporal(TemporalType.DATE)
	private Date edittime;

	private String editwho;

	@Temporal(TemporalType.DATE)
	private Date expectedarrivetime1;

	@Temporal(TemporalType.DATE)
	private Date expectedarrivetime2;

	private String followup;

	@Column(name="H_EDI_01")
	private String hEdi01;

	@Column(name="H_EDI_02")
	private String hEdi02;

	@Column(name="H_EDI_03")
	private String hEdi03;

	@Column(name="H_EDI_04")
	private String hEdi04;

	@Column(name="H_EDI_05")
	private String hEdi05;

	@Column(name="H_EDI_06")
	private String hEdi06;

	@Column(name="H_EDI_07")
	private String hEdi07;

	@Column(name="H_EDI_08")
	private String hEdi08;

	@Column(name="H_EDI_09")
	private BigDecimal hEdi09;

	@Column(name="H_EDI_10")
	private BigDecimal hEdi10;

	@Column(name="H_EDI_11")
	private String hEdi11;

	@Column(name="I_ADDRESS1")
	private String iAddress1;

	@Column(name="I_ADDRESS2")
	private String iAddress2;

	@Column(name="I_ADDRESS3")
	private String iAddress3;

	@Column(name="I_ADDRESS4")
	private String iAddress4;

	@Column(name="I_CITY")
	private String iCity;

	@Column(name="I_CONTACT")
	private String iContact;

	@Column(name="I_COUNTRY")
	private String iCountry;

	@Column(name="I_FAX")
	private String iFax;

	@Column(name="I_MAIL")
	private String iMail;

	@Column(name="I_PROVINCE")
	private String iProvince;

	@Column(name="I_TEL1")
	private String iTel1;

	@Column(name="I_TEL2")
	private String iTel2;

	@Column(name="I_ZIP")
	private String iZip;

	private String issuepartyid;

	private String issuepartyname;

	@Temporal(TemporalType.DATE)
	private Date lastreceivingtime;

	@Temporal(TemporalType.DATE)
	private Date medicalxmltime;

	private String notes;

	private String packmaterialconsume;

	private String paymentterms;

	private String paymenttermsdescr;

	private String placeofdelivery;

	private String placeofdischarge;

	private String placeofloading;

	private String pono;

	private String priority;

	private String qcstatus;

	private Double receiveid;

	private String releasestatus;
	private String releasestatusName;

	@Column(name="RESERVE_FLAG")
	private String reserveFlag;

	@Column(name="RETURN_PRINT_FLAG")
	private String returnPrintFlag;

	private String serialnocatch;

	@Column(name="SUPPLIER_ADDRESS1")
	private String supplierAddress1;

	@Column(name="SUPPLIER_ADDRESS2")
	private String supplierAddress2;

	@Column(name="SUPPLIER_ADDRESS3")
	private String supplierAddress3;

	@Column(name="SUPPLIER_ADDRESS4")
	private String supplierAddress4;

	@Column(name="SUPPLIER_CITY")
	private String supplierCity;

	@Column(name="SUPPLIER_CONTACT")
	private String supplierContact;

	@Column(name="SUPPLIER_COUNTRY")
	private String supplierCountry;

	@Column(name="SUPPLIER_FAX")
	private String supplierFax;

	@Column(name="SUPPLIER_MAIL")
	private String supplierMail;

	@Column(name="SUPPLIER_NAME")
	private String supplierName;

	@Column(name="SUPPLIER_PROVINCE")
	private String supplierProvince;

	@Column(name="SUPPLIER_TEL1")
	private String supplierTel1;

	@Column(name="SUPPLIER_TEL2")
	private String supplierTel2;

	@Column(name="SUPPLIER_ZIP")
	private String supplierZip;

	private String supplierid;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String userdefine6;

	private String userdefinea;

	private String userdefineb;

	private String warehouseid;

	private String zonegroup;

	private String reservedfield07;
	private String  sup;
	private Set<DocAsnDetail> docAsnDetailSet;



	private List<DocAsnDetail> headerTakedetls;
//	用于导出
    private List<DocAsnDetail> details;
	private String coldTag; //冷链标记
	private String mdate; //预期到货时间

	private String supplierIdRef;
	private String customerIdRef;


	public String getSup() {
		return sup;
	}

	public void setSup(String sup) {
		this.sup = sup;
	}

	public String getSupplierIdRef() {
		return supplierIdRef;
	}

	public void setSupplierIdRef(String supplierIdRef) {
		this.supplierIdRef = supplierIdRef;
	}

	public String getCustomerIdRef() {
		return customerIdRef;
	}

	public void setCustomerIdRef(String customerIdRef) {
		this.customerIdRef = customerIdRef;
	}

	public List<DocAsnDetail> getHeaderTakedetls() {
		return headerTakedetls;
	}

	public void setHeaderTakedetls(List<DocAsnDetail> headerTakedetls) {
		this.headerTakedetls = headerTakedetls;
	}

	public List<DocAsnDetail> getDetails() {
		return details;
	}

	public void setDetails(List<DocAsnDetail> details) {
		this.details = details;
	}

	public Set<DocAsnDetail> getDocAsnDetailSet() {
		return docAsnDetailSet;
	}

	public void setDocAsnDetailSet(Set<DocAsnDetail> docAsnDetailSet) {
		this.docAsnDetailSet = docAsnDetailSet;
	}

	public DocAsnHeader() {
	}
	
	public String getAsnstatusName() {
		return asnstatusName;
	}

	public void setAsnstatusName(String asnstatusName) {
		this.asnstatusName = asnstatusName;
	}

	public String getAsntypeName() {
		return asntypeName;
	}

	public void setAsntypeName(String asntypeName) {
		this.asntypeName = asntypeName;
	}

	public String getReleasestatusName() {
		return releasestatusName;
	}

	public void setReleasestatusName(String releasestatusName) {
		this.releasestatusName = releasestatusName;
	}

	public String getAsnno() {
		return this.asnno;
	}

	public void setAsnno(String asnno) {
		this.asnno = asnno;
	}

	public Date getActualarrivetime() {
		return this.actualarrivetime;
	}

	public void setActualarrivetime(Date actualarrivetime) {
		this.actualarrivetime = actualarrivetime;
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

	public String getArchiveflag() {
		return this.archiveflag;
	}

	public void setArchiveflag(String archiveflag) {
		this.archiveflag = archiveflag;
	}

	public String getAsnPrintFlag() {
		return this.asnPrintFlag;
	}

	public void setAsnPrintFlag(String asnPrintFlag) {
		this.asnPrintFlag = asnPrintFlag;
	}

	public Date getAsncreationtime() {
		return this.asncreationtime;
	}

	public void setAsncreationtime(Date asncreationtime) {
		this.asncreationtime = asncreationtime;
	}

	public String getAsnreference1() {
		return this.asnreference1;
	}

	public void setAsnreference1(String asnreference1) {
		this.asnreference1 = asnreference1;
	}

	public String getAsnreference2() {
		return this.asnreference2;
	}

	public void setAsnreference2(String asnreference2) {
		this.asnreference2 = asnreference2;
	}

	public String getAsnreference3() {
		return this.asnreference3;
	}

	public void setAsnreference3(String asnreference3) {
		this.asnreference3 = asnreference3;
	}

	public String getAsnreference4() {
		return this.asnreference4;
	}

	public void setAsnreference4(String asnreference4) {
		this.asnreference4 = asnreference4;
	}

	public String getAsnreference5() {
		return this.asnreference5;
	}

	public void setAsnreference5(String asnreference5) {
		this.asnreference5 = asnreference5;
	}

	public String getAsnstatus() {
		return this.asnstatus;
	}

	public void setAsnstatus(String asnstatus) {
		this.asnstatus = asnstatus;
	}

	public String getAsntype() {
		return this.asntype;
	}

	public void setAsntype(String asntype) {
		this.asntype = asntype;
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

	public String getBFax() {
		return this.bFax;
	}

	public void setBFax(String bFax) {
		this.bFax = bFax;
	}

	public String getBMail() {
		return this.bMail;
	}

	public void setBMail(String bMail) {
		this.bMail = bMail;
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

	public String getBillingclassGroup() {
		return this.billingclassGroup;
	}

	public void setBillingclassGroup(String billingclassGroup) {
		this.billingclassGroup = billingclassGroup;
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

	public String getBytraceFlag() {
		return this.bytraceFlag;
	}

	public void setBytraceFlag(String bytraceFlag) {
		this.bytraceFlag = bytraceFlag;
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

	public String getCountryofdestination() {
		return this.countryofdestination;
	}

	public void setCountryofdestination(String countryofdestination) {
		this.countryofdestination = countryofdestination;
	}

	public String getCountryoforigin() {
		return this.countryoforigin;
	}

	public void setCountryoforigin(String countryoforigin) {
		this.countryoforigin = countryoforigin;
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

	public String getDeliveryvehicleno() {
		return this.deliveryvehicleno;
	}

	public void setDeliveryvehicleno(String deliveryvehicleno) {
		this.deliveryvehicleno = deliveryvehicleno;
	}

	public String getDeliveryvehicletype() {
		return this.deliveryvehicletype;
	}

	public void setDeliveryvehicletype(String deliveryvehicletype) {
		this.deliveryvehicletype = deliveryvehicletype;
	}

	public String getDoor() {
		return this.door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getEdisendflag() {
		return this.edisendflag;
	}

	public void setEdisendflag(String edisendflag) {
		this.edisendflag = edisendflag;
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

	public Date getExpectedarrivetime1() {
		return this.expectedarrivetime1;
	}

	public void setExpectedarrivetime1(Date expectedarrivetime1) {
		this.expectedarrivetime1 = expectedarrivetime1;
	}

	public Date getExpectedarrivetime2() {
		return this.expectedarrivetime2;
	}

	public void setExpectedarrivetime2(Date expectedarrivetime2) {
		this.expectedarrivetime2 = expectedarrivetime2;
	}

	public String getFollowup() {
		return this.followup;
	}

	public void setFollowup(String followup) {
		this.followup = followup;
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

	public Date getLastreceivingtime() {
		return this.lastreceivingtime;
	}

	public void setLastreceivingtime(Date lastreceivingtime) {
		this.lastreceivingtime = lastreceivingtime;
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

	public String getPackmaterialconsume() {
		return this.packmaterialconsume;
	}

	public void setPackmaterialconsume(String packmaterialconsume) {
		this.packmaterialconsume = packmaterialconsume;
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

	public String getPono() {
		return this.pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getQcstatus() {
		return this.qcstatus;
	}

	public void setQcstatus(String qcstatus) {
		this.qcstatus = qcstatus;
	}

	public Double getReceiveid() {
		return this.receiveid;
	}

	public void setReceiveid(Double receiveid) {
		this.receiveid = receiveid;
	}

	public String getReleasestatus() {
		return this.releasestatus;
	}

	public void setReleasestatus(String releasestatus) {
		this.releasestatus = releasestatus;
	}

	public String getReserveFlag() {
		return this.reserveFlag;
	}

	public void setReserveFlag(String reserveFlag) {
		this.reserveFlag = reserveFlag;
	}

	public String getReturnPrintFlag() {
		return this.returnPrintFlag;
	}

	public void setReturnPrintFlag(String returnPrintFlag) {
		this.returnPrintFlag = returnPrintFlag;
	}

	public String getSerialnocatch() {
		return this.serialnocatch;
	}

	public void setSerialnocatch(String serialnocatch) {
		this.serialnocatch = serialnocatch;
	}

	public String getSupplierAddress1() {
		return this.supplierAddress1;
	}

	public void setSupplierAddress1(String supplierAddress1) {
		this.supplierAddress1 = supplierAddress1;
	}

	public String getSupplierAddress2() {
		return this.supplierAddress2;
	}

	public void setSupplierAddress2(String supplierAddress2) {
		this.supplierAddress2 = supplierAddress2;
	}

	public String getSupplierAddress3() {
		return this.supplierAddress3;
	}

	public void setSupplierAddress3(String supplierAddress3) {
		this.supplierAddress3 = supplierAddress3;
	}

	public String getSupplierAddress4() {
		return this.supplierAddress4;
	}

	public void setSupplierAddress4(String supplierAddress4) {
		this.supplierAddress4 = supplierAddress4;
	}

	public String getSupplierCity() {
		return this.supplierCity;
	}

	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}

	public String getSupplierContact() {
		return this.supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierCountry() {
		return this.supplierCountry;
	}

	public void setSupplierCountry(String supplierCountry) {
		this.supplierCountry = supplierCountry;
	}

	public String getSupplierFax() {
		return this.supplierFax;
	}

	public void setSupplierFax(String supplierFax) {
		this.supplierFax = supplierFax;
	}

	public String getSupplierMail() {
		return this.supplierMail;
	}

	public void setSupplierMail(String supplierMail) {
		this.supplierMail = supplierMail;
	}

	public String getSupplierName() {
		return this.supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierProvince() {
		return this.supplierProvince;
	}

	public void setSupplierProvince(String supplierProvince) {
		this.supplierProvince = supplierProvince;
	}

	public String getSupplierTel1() {
		return this.supplierTel1;
	}

	public void setSupplierTel1(String supplierTel1) {
		this.supplierTel1 = supplierTel1;
	}

	public String getSupplierTel2() {
		return this.supplierTel2;
	}

	public void setSupplierTel2(String supplierTel2) {
		this.supplierTel2 = supplierTel2;
	}

	public String getSupplierZip() {
		return this.supplierZip;
	}

	public void setSupplierZip(String supplierZip) {
		this.supplierZip = supplierZip;
	}

	public String getSupplierid() {
		return this.supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
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

	public String getZonegroup() {
		return this.zonegroup;
	}

	public void setZonegroup(String zonegroup) {
		this.zonegroup = zonegroup;
	}

	public String getReservedfield07() {
		return reservedfield07;
	}

	public void setReservedfield07(String reservedfield07) {
		this.reservedfield07 = reservedfield07;
	}

	public String getColdTag() {
		return coldTag;
	}

	public void setColdTag(String coldTag) {
		this.coldTag = coldTag;
	}

	public String getMdate() {
		return mdate;
	}

	public void setMdate(String mdate) {
		this.mdate = mdate;
	}
}