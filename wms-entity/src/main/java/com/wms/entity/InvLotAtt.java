package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class InvLotAtt  implements Serializable {

	private String lotnum;

	private String customerid;

	private String sku;

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

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private java.sql.Date receivingtime;

	private String qcreportfilename;

}
