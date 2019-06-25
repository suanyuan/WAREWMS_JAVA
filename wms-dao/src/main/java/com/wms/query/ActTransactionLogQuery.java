package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class ActTransactionLogQuery implements IQuery {

	private String transactionid;
	private String transactiontype;
	private String fmcustomerid;
	private String fmsku;
	private String docno;
	private String doclineno;
	private String doctype;
	private String fmlotnum;
	private String fmlocation;
	private String fmid;
	private String fmpackid;
	private String fmuom;
	private String fmqty;
	private String fmqtyEach;
	private String status;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String totalprice;
	private String totalnetweight;
	private String totalgrossweight;
	private String totalcubic;
	private String transactiontime;
	private String tocustomerid;
	private String tosku;
	private String reasoncode;
	private String reason;
	private String editransactiontime;
	private String toid;
	private String tolocation;
	private String operator;
	private String topackid;
	private String touom;
	private String toqty;
	private String toqtyEach;
	private String tolotnum;
	private String qcTaskid;
	private String qcSequence;
	private String qcFlag;
	private String paTaskid;
	private String paSequence;
	private String paFlag;
	private String edicanceltransactiontime;
	private String warehouseid;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String edisendflag;

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getFmcustomerid() {
		return fmcustomerid;
	}

	public void setFmcustomerid(String fmcustomerid) {
		this.fmcustomerid = fmcustomerid;
	}

	public String getFmsku() {
		return fmsku;
	}

	public void setFmsku(String fmsku) {
		this.fmsku = fmsku;
	}

	public String getDocno() {
		return docno;
	}

	public void setDocno(String docno) {
		this.docno = docno;
	}

	public String getDoclineno() {
		return doclineno;
	}

	public void setDoclineno(String doclineno) {
		this.doclineno = doclineno;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public String getFmlotnum() {
		return fmlotnum;
	}

	public void setFmlotnum(String fmlotnum) {
		this.fmlotnum = fmlotnum;
	}

	public String getFmlocation() {
		return fmlocation;
	}

	public void setFmlocation(String fmlocation) {
		this.fmlocation = fmlocation;
	}

	public String getFmid() {
		return fmid;
	}

	public void setFmid(String fmid) {
		this.fmid = fmid;
	}

	public String getFmpackid() {
		return fmpackid;
	}

	public void setFmpackid(String fmpackid) {
		this.fmpackid = fmpackid;
	}

	public String getFmuom() {
		return fmuom;
	}

	public void setFmuom(String fmuom) {
		this.fmuom = fmuom;
	}

	public String getFmqty() {
		return fmqty;
	}

	public void setFmqty(String fmqty) {
		this.fmqty = fmqty;
	}

	public String getFmqtyEach() {
		return fmqtyEach;
	}

	public void setFmqtyEach(String fmqtyEach) {
		this.fmqtyEach = fmqtyEach;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getEdittime() {
		return edittime;
	}

	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}

	public String getTotalnetweight() {
		return totalnetweight;
	}

	public void setTotalnetweight(String totalnetweight) {
		this.totalnetweight = totalnetweight;
	}

	public String getTotalgrossweight() {
		return totalgrossweight;
	}

	public void setTotalgrossweight(String totalgrossweight) {
		this.totalgrossweight = totalgrossweight;
	}

	public String getTotalcubic() {
		return totalcubic;
	}

	public void setTotalcubic(String totalcubic) {
		this.totalcubic = totalcubic;
	}

	public String getTransactiontime() {
		return transactiontime;
	}

	public void setTransactiontime(String transactiontime) {
		this.transactiontime = transactiontime;
	}

	public String getTocustomerid() {
		return tocustomerid;
	}

	public void setTocustomerid(String tocustomerid) {
		this.tocustomerid = tocustomerid;
	}

	public String getTosku() {
		return tosku;
	}

	public void setTosku(String tosku) {
		this.tosku = tosku;
	}

	public String getReasoncode() {
		return reasoncode;
	}

	public void setReasoncode(String reasoncode) {
		this.reasoncode = reasoncode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEditransactiontime() {
		return editransactiontime;
	}

	public void setEditransactiontime(String editransactiontime) {
		this.editransactiontime = editransactiontime;
	}

	public String getToid() {
		return toid;
	}

	public void setToid(String toid) {
		this.toid = toid;
	}

	public String getTolocation() {
		return tolocation;
	}

	public void setTolocation(String tolocation) {
		this.tolocation = tolocation;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTopackid() {
		return topackid;
	}

	public void setTopackid(String topackid) {
		this.topackid = topackid;
	}

	public String getTouom() {
		return touom;
	}

	public void setTouom(String touom) {
		this.touom = touom;
	}

	public String getToqty() {
		return toqty;
	}

	public void setToqty(String toqty) {
		this.toqty = toqty;
	}

	public String getToqtyEach() {
		return toqtyEach;
	}

	public void setToqtyEach(String toqtyEach) {
		this.toqtyEach = toqtyEach;
	}

	public String getTolotnum() {
		return tolotnum;
	}

	public void setTolotnum(String tolotnum) {
		this.tolotnum = tolotnum;
	}

	public String getQcTaskid() {
		return qcTaskid;
	}

	public void setQcTaskid(String qcTaskid) {
		this.qcTaskid = qcTaskid;
	}

	public String getQcSequence() {
		return qcSequence;
	}

	public void setQcSequence(String qcSequence) {
		this.qcSequence = qcSequence;
	}

	public String getQcFlag() {
		return qcFlag;
	}

	public void setQcFlag(String qcFlag) {
		this.qcFlag = qcFlag;
	}

	public String getPaTaskid() {
		return paTaskid;
	}

	public void setPaTaskid(String paTaskid) {
		this.paTaskid = paTaskid;
	}

	public String getPaSequence() {
		return paSequence;
	}

	public void setPaSequence(String paSequence) {
		this.paSequence = paSequence;
	}

	public String getPaFlag() {
		return paFlag;
	}

	public void setPaFlag(String paFlag) {
		this.paFlag = paFlag;
	}

	public String getEdicanceltransactiontime() {
		return edicanceltransactiontime;
	}

	public void setEdicanceltransactiontime(String edicanceltransactiontime) {
		this.edicanceltransactiontime = edicanceltransactiontime;
	}

	public String getWarehouseid() {
		return warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public String getUserdefine1() {
		return userdefine1;
	}

	public void setUserdefine1(String userdefine1) {
		this.userdefine1 = userdefine1;
	}

	public String getUserdefine2() {
		return userdefine2;
	}

	public void setUserdefine2(String userdefine2) {
		this.userdefine2 = userdefine2;
	}

	public String getUserdefine3() {
		return userdefine3;
	}

	public void setUserdefine3(String userdefine3) {
		this.userdefine3 = userdefine3;
	}

	public String getUserdefine4() {
		return userdefine4;
	}

	public void setUserdefine4(String userdefine4) {
		this.userdefine4 = userdefine4;
	}

	public String getUserdefine5() {
		return userdefine5;
	}

	public void setUserdefine5(String userdefine5) {
		this.userdefine5 = userdefine5;
	}

	public String getEdisendflag() {
		return edisendflag;
	}

	public void setEdisendflag(String edisendflag) {
		this.edisendflag = edisendflag;
	}

}