package com.wms.query;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.wms.mybatis.entity.SfcCustomer;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class ViewInvTranQuery implements IQuery {

	private java.lang.String addtime;
	private java.math.BigDecimal doclineno;
	private java.lang.String docno;
	private java.lang.String doctype;
	private java.lang.String doctypeName;
	private java.lang.String editwho;
	private java.lang.String fmcustomerid;
	private java.lang.String fmid;
	private java.lang.String fmlocation;
	private java.lang.String fmlotatt01;
	private java.lang.String fmlotatt02;
	private java.lang.String fmlotatt03;
	private java.lang.String fmlotatt04;
	private java.lang.String fmlotatt05;
	private java.lang.String fmlotatt06;
	private java.lang.String fmlotnum;
	private java.lang.String fmpackid;
	private java.math.BigDecimal fmqty;
	private java.math.BigDecimal fmqtyEach;
	private java.lang.String fmsku;
	private java.lang.String fmuom;
	private java.lang.String fmuomName;
	private java.lang.String operator;
	private java.lang.String reason;
	private java.lang.String reasoncode;
	private java.lang.String status;
	private java.lang.String statusName;
	private java.lang.String tocustomerid;
	private java.lang.String toid;
	private java.lang.String tolocation;
	private java.lang.String tolotatt01;
	private java.lang.String tolotatt02;
	private java.lang.String tolotatt03;
	private java.lang.String tolotatt04;
	private java.lang.String tolotatt05;
	private java.lang.String tolotatt06;
	private java.lang.String tolotnum;
	private java.lang.String topackid;
	private java.math.BigDecimal toqty;
	private java.math.BigDecimal toqtyEach;
	private java.lang.String tosku;
	private java.math.BigDecimal totalcubic;
	private java.math.BigDecimal totalgrossweight;
	private java.math.BigDecimal totalnetweight;
	private java.math.BigDecimal totalprice;
	private java.lang.String touom;
	private java.lang.String touomName;
	private java.lang.String transactionid;
	private java.lang.String transactiontime;
	private java.lang.String transactiontype;
	private java.lang.String transactiontypeName;
	private java.lang.String userdefine1;
	private java.lang.String userdefine2;
	private java.lang.String userdefine3;
	private java.lang.String userdefine4;
	private java.lang.String userdefine5;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;

	public java.lang.String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(java.lang.String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public Set<SfcCustomer> getCustomerSet() {
		return customerSet;
	}

	public void setCustomerSet(Set<SfcCustomer> customerSet) {
		this.customerSet = customerSet;
	}

	public java.lang.String getAddtime() {
		return addtime;
	}

	public void setAddtime(java.lang.String addtime) {
		this.addtime = addtime;
	}

	public java.math.BigDecimal getDoclineno() {
		return doclineno;
	}

	public void setDoclineno(java.math.BigDecimal doclineno) {
		this.doclineno = doclineno;
	}

	public java.lang.String getDocno() {
		return docno;
	}

	public void setDocno(java.lang.String docno) {
		this.docno = docno;
	}

	public java.lang.String getDoctype() {
		return doctype;
	}

	public void setDoctype(java.lang.String doctype) {
		this.doctype = doctype;
	}

	public java.lang.String getDoctypeName() {
		return doctypeName;
	}

	public void setDoctypeName(java.lang.String doctypeName) {
		this.doctypeName = doctypeName;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	public java.lang.String getFmcustomerid() {
		return fmcustomerid;
	}

	public void setFmcustomerid(java.lang.String fmcustomerid) {
		this.fmcustomerid = fmcustomerid;
	}

	public java.lang.String getFmid() {
		return fmid;
	}

	public void setFmid(java.lang.String fmid) {
		this.fmid = fmid;
	}

	public java.lang.String getFmlocation() {
		return fmlocation;
	}

	public void setFmlocation(java.lang.String fmlocation) {
		this.fmlocation = fmlocation;
	}

	public java.lang.String getFmlotatt01() {
		return fmlotatt01;
	}

	public void setFmlotatt01(java.lang.String fmlotatt01) {
		this.fmlotatt01 = fmlotatt01;
	}

	public java.lang.String getFmlotatt02() {
		return fmlotatt02;
	}

	public void setFmlotatt02(java.lang.String fmlotatt02) {
		this.fmlotatt02 = fmlotatt02;
	}

	public java.lang.String getFmlotatt03() {
		return fmlotatt03;
	}

	public void setFmlotatt03(java.lang.String fmlotatt03) {
		this.fmlotatt03 = fmlotatt03;
	}

	public java.lang.String getFmlotatt04() {
		return fmlotatt04;
	}

	public void setFmlotatt04(java.lang.String fmlotatt04) {
		this.fmlotatt04 = fmlotatt04;
	}

	public java.lang.String getFmlotatt05() {
		return fmlotatt05;
	}

	public void setFmlotatt05(java.lang.String fmlotatt05) {
		this.fmlotatt05 = fmlotatt05;
	}

	public java.lang.String getFmlotatt06() {
		return fmlotatt06;
	}

	public void setFmlotatt06(java.lang.String fmlotatt06) {
		this.fmlotatt06 = fmlotatt06;
	}

	public java.lang.String getFmlotnum() {
		return fmlotnum;
	}

	public void setFmlotnum(java.lang.String fmlotnum) {
		this.fmlotnum = fmlotnum;
	}

	public java.lang.String getFmpackid() {
		return fmpackid;
	}

	public void setFmpackid(java.lang.String fmpackid) {
		this.fmpackid = fmpackid;
	}

	public java.math.BigDecimal getFmqty() {
		return fmqty;
	}

	public void setFmqty(java.math.BigDecimal fmqty) {
		this.fmqty = fmqty;
	}

	public java.math.BigDecimal getFmqtyEach() {
		return fmqtyEach;
	}

	public void setFmqtyEach(java.math.BigDecimal fmqtyEach) {
		this.fmqtyEach = fmqtyEach;
	}

	public java.lang.String getFmsku() {
		return fmsku;
	}

	public void setFmsku(java.lang.String fmsku) {
		this.fmsku = fmsku;
	}

	public java.lang.String getFmuom() {
		return fmuom;
	}

	public void setFmuom(java.lang.String fmuom) {
		this.fmuom = fmuom;
	}

	public java.lang.String getFmuomName() {
		return fmuomName;
	}

	public void setFmuomName(java.lang.String fmuomName) {
		this.fmuomName = fmuomName;
	}

	public java.lang.String getOperator() {
		return operator;
	}

	public void setOperator(java.lang.String operator) {
		this.operator = operator;
	}

	public java.lang.String getReason() {
		return reason;
	}

	public void setReason(java.lang.String reason) {
		this.reason = reason;
	}

	public java.lang.String getReasoncode() {
		return reasoncode;
	}

	public void setReasoncode(java.lang.String reasoncode) {
		this.reasoncode = reasoncode;
	}

	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	public java.lang.String getStatusName() {
		return statusName;
	}

	public void setStatusName(java.lang.String statusName) {
		this.statusName = statusName;
	}

	public java.lang.String getTocustomerid() {
		return tocustomerid;
	}

	public void setTocustomerid(java.lang.String tocustomerid) {
		this.tocustomerid = tocustomerid;
	}

	public java.lang.String getToid() {
		return toid;
	}

	public void setToid(java.lang.String toid) {
		this.toid = toid;
	}

	public java.lang.String getTolocation() {
		return tolocation;
	}

	public void setTolocation(java.lang.String tolocation) {
		this.tolocation = tolocation;
	}

	public java.lang.String getTolotatt01() {
		return tolotatt01;
	}

	public void setTolotatt01(java.lang.String tolotatt01) {
		this.tolotatt01 = tolotatt01;
	}

	public java.lang.String getTolotatt02() {
		return tolotatt02;
	}

	public void setTolotatt02(java.lang.String tolotatt02) {
		this.tolotatt02 = tolotatt02;
	}

	public java.lang.String getTolotatt03() {
		return tolotatt03;
	}

	public void setTolotatt03(java.lang.String tolotatt03) {
		this.tolotatt03 = tolotatt03;
	}

	public java.lang.String getTolotatt04() {
		return tolotatt04;
	}

	public void setTolotatt04(java.lang.String tolotatt04) {
		this.tolotatt04 = tolotatt04;
	}

	public java.lang.String getTolotatt05() {
		return tolotatt05;
	}

	public void setTolotatt05(java.lang.String tolotatt05) {
		this.tolotatt05 = tolotatt05;
	}

	public java.lang.String getTolotatt06() {
		return tolotatt06;
	}

	public void setTolotatt06(java.lang.String tolotatt06) {
		this.tolotatt06 = tolotatt06;
	}

	public java.lang.String getTolotnum() {
		return tolotnum;
	}

	public void setTolotnum(java.lang.String tolotnum) {
		this.tolotnum = tolotnum;
	}

	public java.lang.String getTopackid() {
		return topackid;
	}

	public void setTopackid(java.lang.String topackid) {
		this.topackid = topackid;
	}

	public java.math.BigDecimal getToqty() {
		return toqty;
	}

	public void setToqty(java.math.BigDecimal toqty) {
		this.toqty = toqty;
	}

	public java.math.BigDecimal getToqtyEach() {
		return toqtyEach;
	}

	public void setToqtyEach(java.math.BigDecimal toqtyEach) {
		this.toqtyEach = toqtyEach;
	}

	public java.lang.String getTosku() {
		return tosku;
	}

	public void setTosku(java.lang.String tosku) {
		this.tosku = tosku;
	}

	public java.math.BigDecimal getTotalcubic() {
		return totalcubic;
	}

	public void setTotalcubic(java.math.BigDecimal totalcubic) {
		this.totalcubic = totalcubic;
	}

	public java.math.BigDecimal getTotalgrossweight() {
		return totalgrossweight;
	}

	public void setTotalgrossweight(java.math.BigDecimal totalgrossweight) {
		this.totalgrossweight = totalgrossweight;
	}

	public java.math.BigDecimal getTotalnetweight() {
		return totalnetweight;
	}

	public void setTotalnetweight(java.math.BigDecimal totalnetweight) {
		this.totalnetweight = totalnetweight;
	}

	public java.math.BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(java.math.BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public java.lang.String getTouom() {
		return touom;
	}

	public void setTouom(java.lang.String touom) {
		this.touom = touom;
	}

	public java.lang.String getTouomName() {
		return touomName;
	}

	public void setTouomName(java.lang.String touomName) {
		this.touomName = touomName;
	}

	public java.lang.String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(java.lang.String transactionid) {
		this.transactionid = transactionid;
	}

	public java.lang.String getTransactiontime() {
		return transactiontime;
	}

	public void setTransactiontime(java.lang.String transactiontime) {
		this.transactiontime = transactiontime;
	}

	public java.lang.String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(java.lang.String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public java.lang.String getTransactiontypeName() {
		return transactiontypeName;
	}

	public void setTransactiontypeName(java.lang.String transactiontypeName) {
		this.transactiontypeName = transactiontypeName;
	}

	public java.lang.String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(java.lang.String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public java.lang.String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(java.lang.String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public java.lang.String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(java.lang.String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public java.lang.String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(java.lang.String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public java.lang.String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(java.lang.String userdefine5) {
		this.userdefine5 = userdefine5;
	}

}