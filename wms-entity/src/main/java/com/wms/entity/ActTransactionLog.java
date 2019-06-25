package com.wms.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ActTransactionLog  implements Serializable {

	private String transactionid;

	private String transactiontype;

	private String fmcustomerid;

	private String fmsku;

	private String docno;

	private Long doclineno;

	private String doctype;

	private String fmlotnum;

	private String fmlocation;

	private String fmid;

	private String fmpackid;

	private String fmuom;

	private Double fmqty;

	private Double fmqtyEach;

	private String status;

	private java.sql.Date addtime;

	private String addwho;

	private java.sql.Date edittime;

	private String editwho;

	private Double totalprice;

	private Double totalnetweight;

	private Double totalgrossweight;

	private Double totalcubic;

	private java.sql.Date transactiontime;

	private String tocustomerid;

	private String tosku;

	private String reasoncode;

	private String reason;

	private java.sql.Date editransactiontime;

	private String toid;

	private String tolocation;

	private String operator;

	private String topackid;

	private String touom;

	private Double toqty;

	private Double toqtyEach;

	private String tolotnum;

	private String qcTaskid;

	private String qcSequence;

	private String qcFlag;

	private String paTaskid;

	private Long paSequence;

	private String paFlag;

	private java.sql.Date edicanceltransactiontime;

	private String warehouseid;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;

	private String edisendflag;

}
