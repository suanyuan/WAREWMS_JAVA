package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the VIEW_INV_TRANS database table.
 * 
 */
@Entity
@Table(name="VIEW_INV_TRANS")
@NamedQuery(name="ViewInvTran.findAll", query="SELECT v FROM ViewInvTran v")
public class ViewInvTran implements Serializable {
	private static final long serialVersionUID = 1L;

	private String addtime;

	private BigDecimal doclineno;

	private String docno;

	private String doctype;

	@Column(name="DOCTYPE_NAME")
	private String doctypeName;

	private String editwho;

	private String fmcustomerid;

	private String fmid;

	private String fmlocation;

	private String fmlotatt01;

	private String fmlotatt02;

	private String fmlotatt03;

	private String fmlotatt04;

	private String fmlotatt05;

	private String fmlotatt06;
	private String fmlotatt07;

	private String fmlotatt08;

	private String fmlotatt09;

	private String fmlotatt10;

	private String fmlotatt11;

	private String fmlotatt12;
	private String fmlotatt13;

	private String fmlotatt14;

	private String fmlotatt15;

	private String fmlotatt16;

	private String fmlotatt17;

	private String fmlotatt18;




	private String fmlotnum;

	private String fmpackid;

	private BigDecimal fmqty;

	@Column(name="FMQTY_EACH")
	private BigDecimal fmqtyEach;

	private String fmsku;

	private String fmuom;

	@Column(name="FMUOM_NAME")
	private String fmuomName;

	private String operator;

	private String reason;

	private String reasoncode;

	private String status;

	@Column(name="STATUS_NAME")
	private String statusName;

	private String tocustomerid;

	private String toid;

	private String tolocation;

	private String tolotatt01;

	private String tolotatt02;

	private String tolotatt03;

	private String tolotatt04;

	private String tolotatt05;

	private String tolotatt06;
	private String tolotatt07;

	private String tolotatt08;

	private String tolotatt09;

	private String tolotatt10;

	private String tolotatt11;

	private String tolotatt12;
	private String tolotatt13;

	private String tolotatt14;

	private String tolotatt15;

	private String tolotatt16;

	private String tolotatt17;

	private String tolotatt18;
	private String tolotnum;

	private String topackid;

	private BigDecimal toqty;

	@Column(name="TOQTY_EACH")
	private BigDecimal toqtyEach;

	private String tosku;

	private BigDecimal totalcubic;

	private BigDecimal totalgrossweight;

	private BigDecimal totalnetweight;

	private BigDecimal totalprice;

	private String touom;

	@Column(name="TOUOM_NAME")
	private String touomName;

	@Id
	private String transactionid;

	private String transactiontime;

	private String transactiontype;

	@Column(name="TRANSACTIONTYPE_NAME")
	private String transactiontypeName;

	private String userdefine1;

	private String userdefine2;

	private String userdefine3;

	private String userdefine4;

	private String userdefine5;
	
	private String warehouseid;

	public ViewInvTran() {
	}

	public String getFmlotatt07() {
		return fmlotatt07;
	}

	public void setFmlotatt07(String fmlotatt07) {
		this.fmlotatt07 = fmlotatt07;
	}

	public String getFmlotatt08() {
		return fmlotatt08;
	}

	public void setFmlotatt08(String fmlotatt08) {
		this.fmlotatt08 = fmlotatt08;
	}

	public String getFmlotatt09() {
		return fmlotatt09;
	}

	public void setFmlotatt09(String fmlotatt09) {
		this.fmlotatt09 = fmlotatt09;
	}

	public String getFmlotatt10() {
		return fmlotatt10;
	}

	public void setFmlotatt10(String fmlotatt10) {
		this.fmlotatt10 = fmlotatt10;
	}

	public String getFmlotatt11() {
		return fmlotatt11;
	}

	public void setFmlotatt11(String fmlotatt11) {
		this.fmlotatt11 = fmlotatt11;
	}

	public String getFmlotatt12() {
		return fmlotatt12;
	}

	public void setFmlotatt12(String fmlotatt12) {
		this.fmlotatt12 = fmlotatt12;
	}

	public String getFmlotatt13() {
		return fmlotatt13;
	}

	public void setFmlotatt13(String fmlotatt13) {
		this.fmlotatt13 = fmlotatt13;
	}

	public String getFmlotatt14() {
		return fmlotatt14;
	}

	public void setFmlotatt14(String fmlotatt14) {
		this.fmlotatt14 = fmlotatt14;
	}

	public String getFmlotatt15() {
		return fmlotatt15;
	}

	public void setFmlotatt15(String fmlotatt15) {
		this.fmlotatt15 = fmlotatt15;
	}

	public String getFmlotatt16() {
		return fmlotatt16;
	}

	public void setFmlotatt16(String fmlotatt16) {
		this.fmlotatt16 = fmlotatt16;
	}

	public String getFmlotatt17() {
		return fmlotatt17;
	}

	public void setFmlotatt17(String fmlotatt17) {
		this.fmlotatt17 = fmlotatt17;
	}

	public String getFmlotatt18() {
		return fmlotatt18;
	}

	public void setFmlotatt18(String fmlotatt18) {
		this.fmlotatt18 = fmlotatt18;
	}

	public String getTolotatt07() {
		return tolotatt07;
	}

	public void setTolotatt07(String tolotatt07) {
		this.tolotatt07 = tolotatt07;
	}

	public String getTolotatt08() {
		return tolotatt08;
	}

	public void setTolotatt08(String tolotatt08) {
		this.tolotatt08 = tolotatt08;
	}

	public String getTolotatt09() {
		return tolotatt09;
	}

	public void setTolotatt09(String tolotatt09) {
		this.tolotatt09 = tolotatt09;
	}

	public String getTolotatt10() {
		return tolotatt10;
	}

	public void setTolotatt10(String tolotatt10) {
		this.tolotatt10 = tolotatt10;
	}

	public String getTolotatt11() {
		return tolotatt11;
	}

	public void setTolotatt11(String tolotatt11) {
		this.tolotatt11 = tolotatt11;
	}

	public String getTolotatt12() {
		return tolotatt12;
	}

	public void setTolotatt12(String tolotatt12) {
		this.tolotatt12 = tolotatt12;
	}

	public String getTolotatt13() {
		return tolotatt13;
	}

	public void setTolotatt13(String tolotatt13) {
		this.tolotatt13 = tolotatt13;
	}

	public String getTolotatt14() {
		return tolotatt14;
	}

	public void setTolotatt14(String tolotatt14) {
		this.tolotatt14 = tolotatt14;
	}

	public String getTolotatt15() {
		return tolotatt15;
	}

	public void setTolotatt15(String tolotatt15) {
		this.tolotatt15 = tolotatt15;
	}

	public String getTolotatt16() {
		return tolotatt16;
	}

	public void setTolotatt16(String tolotatt16) {
		this.tolotatt16 = tolotatt16;
	}

	public String getTolotatt17() {
		return tolotatt17;
	}

	public void setTolotatt17(String tolotatt17) {
		this.tolotatt17 = tolotatt17;
	}

	public String getTolotatt18() {
		return tolotatt18;
	}

	public void setTolotatt18(String tolotatt18) {
		this.tolotatt18 = tolotatt18;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public BigDecimal getDoclineno() {
		return this.doclineno;
	}

	public void setDoclineno(BigDecimal doclineno) {
		this.doclineno = doclineno;
	}

	public String getDocno() {
		return this.docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getDoctype() {
		return this.doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getDoctypeName() {
		return this.doctypeName;
	}

	public void setDoctypeName(String doctypeName) {
		this.doctypeName = doctypeName;
	}

	public String getEditwho() {
		return this.editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getFmcustomerid() {
		return this.fmcustomerid;
	}

	public void setFmcustomerid(String fmcustomerid) {
		this.fmcustomerid = fmcustomerid;
	}

	public String getFmid() {
		return this.fmid;
	}

	public void setFmid(String fmid) {
		this.fmid = fmid;
	}

	public String getFmlocation() {
		return this.fmlocation;
	}

	public void setFmlocation(String fmlocation) {
		this.fmlocation = fmlocation;
	}

	public String getFmlotatt01() {
		return this.fmlotatt01;
	}

	public void setFmlotatt01(String fmlotatt01) {
		this.fmlotatt01 = fmlotatt01;
	}

	public String getFmlotatt02() {
		return this.fmlotatt02;
	}

	public void setFmlotatt02(String fmlotatt02) {
		this.fmlotatt02 = fmlotatt02;
	}

	public String getFmlotatt03() {
		return this.fmlotatt03;
	}

	public void setFmlotatt03(String fmlotatt03) {
		this.fmlotatt03 = fmlotatt03;
	}

	public String getFmlotatt04() {
		return this.fmlotatt04;
	}

	public void setFmlotatt04(String fmlotatt04) {
		this.fmlotatt04 = fmlotatt04;
	}

	public String getFmlotatt05() {
		return this.fmlotatt05;
	}

	public void setFmlotatt05(String fmlotatt05) {
		this.fmlotatt05 = fmlotatt05;
	}

	public String getFmlotatt06() {
		return this.fmlotatt06;
	}

	public void setFmlotatt06(String fmlotatt06) {
		this.fmlotatt06 = fmlotatt06;
	}

	public String getFmlotnum() {
		return this.fmlotnum;
	}

	public void setFmlotnum(String fmlotnum) {
		this.fmlotnum = fmlotnum;
	}

	public String getFmpackid() {
		return this.fmpackid;
	}

	public void setFmpackid(String fmpackid) {
		this.fmpackid = fmpackid;
	}

	public BigDecimal getFmqty() {
		return this.fmqty;
	}

	public void setFmqty(BigDecimal fmqty) {
		this.fmqty = fmqty;
	}

	public BigDecimal getFmqtyEach() {
		return this.fmqtyEach;
	}

	public void setFmqtyEach(BigDecimal fmqtyEach) {
		this.fmqtyEach = fmqtyEach;
	}

	public String getFmsku() {
		return this.fmsku;
	}

	public void setFmsku(String fmsku) {
		this.fmsku = fmsku;
	}

	public String getFmuom() {
		return this.fmuom;
	}

	public void setFmuom(String fmuom) {
		this.fmuom = fmuom;
	}

	public String getFmuomName() {
		return this.fmuomName;
	}

	public void setFmuomName(String fmuomName) {
		this.fmuomName = fmuomName;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasoncode() {
		return this.reasoncode;
	}

	public void setReasoncode(String reasoncode) {
		this.reasoncode = reasoncode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getTocustomerid() {
		return this.tocustomerid;
	}

	public void setTocustomerid(String tocustomerid) {
		this.tocustomerid = tocustomerid;
	}

	public String getToid() {
		return this.toid;
	}

	public void setToid(String toid) {
		this.toid = toid;
	}

	public String getTolocation() {
		return this.tolocation;
	}

	public void setTolocation(String tolocation) {
		this.tolocation = tolocation;
	}

	public String getTolotatt01() {
		return this.tolotatt01;
	}

	public void setTolotatt01(String tolotatt01) {
		this.tolotatt01 = tolotatt01;
	}

	public String getTolotatt02() {
		return this.tolotatt02;
	}

	public void setTolotatt02(String tolotatt02) {
		this.tolotatt02 = tolotatt02;
	}

	public String getTolotatt03() {
		return this.tolotatt03;
	}

	public void setTolotatt03(String tolotatt03) {
		this.tolotatt03 = tolotatt03;
	}

	public String getTolotatt04() {
		return this.tolotatt04;
	}

	public void setTolotatt04(String tolotatt04) {
		this.tolotatt04 = tolotatt04;
	}

	public String getTolotatt05() {
		return this.tolotatt05;
	}

	public void setTolotatt05(String tolotatt05) {
		this.tolotatt05 = tolotatt05;
	}

	public String getTolotatt06() {
		return this.tolotatt06;
	}

	public void setTolotatt06(String tolotatt06) {
		this.tolotatt06 = tolotatt06;
	}

	public String getTolotnum() {
		return this.tolotnum;
	}

	public void setTolotnum(String tolotnum) {
		this.tolotnum = tolotnum;
	}

	public String getTopackid() {
		return this.topackid;
	}

	public void setTopackid(String topackid) {
		this.topackid = topackid;
	}

	public BigDecimal getToqty() {
		return this.toqty;
	}

	public void setToqty(BigDecimal toqty) {
		this.toqty = toqty;
	}

	public BigDecimal getToqtyEach() {
		return this.toqtyEach;
	}

	public void setToqtyEach(BigDecimal toqtyEach) {
		this.toqtyEach = toqtyEach;
	}

	public String getTosku() {
		return this.tosku;
	}

	public void setTosku(String tosku) {
		this.tosku = tosku;
	}

	public BigDecimal getTotalcubic() {
		return this.totalcubic;
	}

	public void setTotalcubic(BigDecimal totalcubic) {
		this.totalcubic = totalcubic;
	}

	public BigDecimal getTotalgrossweight() {
		return this.totalgrossweight;
	}

	public void setTotalgrossweight(BigDecimal totalgrossweight) {
		this.totalgrossweight = totalgrossweight;
	}

	public BigDecimal getTotalnetweight() {
		return this.totalnetweight;
	}

	public void setTotalnetweight(BigDecimal totalnetweight) {
		this.totalnetweight = totalnetweight;
	}

	public BigDecimal getTotalprice() {
		return this.totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getTouom() {
		return this.touom;
	}

	public void setTouom(String touom) {
		this.touom = touom;
	}

	public String getTouomName() {
		return this.touomName;
	}

	public void setTouomName(String touomName) {
		this.touomName = touomName;
	}

	public String getTransactionid() {
		return this.transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getTransactiontime() {
		return this.transactiontime;
	}

	public void setTransactiontime(String transactiontime) {
		this.transactiontime = transactiontime;
	}

	public String getTransactiontype() {
		return this.transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getTransactiontypeName() {
		return this.transactiontypeName;
	}

	public void setTransactiontypeName(String transactiontypeName) {
		this.transactiontypeName = transactiontypeName;
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

}