package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class DocPoHeader  implements Serializable {

	private String pono;

	private String potype;
    private String potypeName;//附加
	private String postatus;
    private String postatusName;//附加
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

	private Double hedi09;

	private Double hedi10;

	private String warehouseid;
	private String warehouseName;//附加

	private String createsource;

	private String releasestatus;

	private String userdefinea;

	private String userdefineb;

}
