package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class DocPoDetails  implements Serializable {

	private String pono;

	private Double polineno;

	private String customerid;

	private String sku;

	private String skudescrc;

	private String skudescre;

	private Double orderedqty;

	private Double orderedqtyEach;

	private Double receivedqty;

	private Double receivedqtyEach;

	private java.util.Date receivedtime;

	private String uom;

	private String packid;

	private Double totalcubic;

	private Double totalgrossweight;

	private Double totalnetweight;

	private Double totalprice;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private java.util.Date addtime;

	private String addwho;

	private java.util.Date edittime;

	private String editwho;

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

	private String notes;

	private String lotatt12;
	private String lotatt13;
	private String lotatt14;
	private String lotatt15;
	private String lotatt16;
	private String lotatt17;
	private String lotatt18;

	private String polinestatus;
	private String polinestatusName;//附加

	private String dEdi01;

	private String dEdi02;

	private String dEdi03;

	private String dEdi04;

	private String dEdi05;

	private String dEdi06;

	private String dEdi07;

	private String dEdi08;

	private Double dEdi09;

	private Double dEdi10;

	private Double qtyreleased;

}
