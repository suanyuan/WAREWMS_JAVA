package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
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
@Data
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

	private Set<DocAsnDetail> docAsnDetailSet;


	private List<DocAsnDetail> headerTakedetls;
//	用于导出
    private List<DocAsnDetail> details;
	private String coldTag; //冷链标记
	private String mdate; //预期到货时间

	private String supplierIdRef;
	private String customerIdRef;
	private String lotatt03;//入库日期
	private String lineName;//产品线
	private String gysName;//供应商名称
}