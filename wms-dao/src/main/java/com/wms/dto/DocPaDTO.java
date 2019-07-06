package com.wms.dto;

import com.wms.entity.DocAsnDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/4
 */
@Data
public class DocPaDTO extends DocAsnDetail{

    private String asnno;

    private Date actualarrivetime;

    private Date addtime;

    private String addwho;

    private String archiveflag;

    private String asnPrintFlag;

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

    private String bAddress1;

    private String bAddress2;

    private String bAddress3;

    private String bAddress4;

    private String bCity;

    private String bContact;

    private String bCountry;

    private String bFax;

    private String bMail;

    private String bProvince;

    private String bTel1;

    private String bTel2;

    private String bZip;

    private String billingclassGroup;

    private String billingid;

    private String billingname;

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

    private Date edisendtime;

    private Date edisendtime2;

    private Date edisendtime3;

    private Date edisendtime4;

    private Date edisendtime5;

    private Date edittime;

    private String editwho;

    private Date expectedarrivetime1;

    private Date expectedarrivetime2;

    private String followup;

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

    private String issuepartyid;

    private String issuepartyname;

    private Date lastreceivingtime;

    private String lotnum;

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

    private double receiveid;

    private String releasestatus;
    private String releasestatusName;

    private String reserveFlag;

    private String returnPrintFlag;

    private String serialnocatch;

    private String supplierAddress1;

    private String supplierAddress2;

    private String supplierAddress3;

    private String supplierAddress4;

    private String supplierCity;

    private String supplierContact;

    private String supplierCountry;

    private String supplierFax;

    private String supplierMail;

    private String supplierName;

    private String supplierProvince;

    private String supplierTel1;

    private String supplierTel2;

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
}