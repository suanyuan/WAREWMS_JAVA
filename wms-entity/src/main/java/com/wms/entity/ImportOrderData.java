package com.wms.entity;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the DOC_ORDER_HEADER database table.
 * 
 */
@Entity
@Data
public class ImportOrderData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String seq;
	
	private String orderno;

	private String customerid;

	private String consigneeid;

	private String cAddress1;

	private String cTel1;

	private String sku;

	private String carrierid;

	private String userdefine1;

	private String userdefine2;

	private String qtyordered;

	private String lotatt01;
	private String lotatt02;
	private String lotatt03;
	private String lotatt04;
	private String lotatt05;
	private String lotatt06;
	private String lotatt07;
	private String lotatt08;
	private String lotatt09;
	private String lotatt10;
	private String lotatt11;
	private String lotatt12;
	private String lotatt13;
	private String lotatt14;
	private String lotatt15;

}