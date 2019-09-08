package com.wms.query;

public class DocQcDetailsQuery implements IQuery {

	private String qcno;         //验收单号
	private String pano;         //上架单号
	private String qclineno;     //验收细单行号
	private String linestatus;   //验收状态
	private String palineno;     //上架细单行号
	private String customerid;
	private String sku;
	private String lotnum;
	private String paqtyExpected;
	private String qcqtyExpected;
	private String qcqtyCompleted;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String qcdescr;
	private String qcresult;
	private String filecontent;
	private String notes;
	private String addtime;
	private String addwho;
	private String edittime;
	private String editwho;
	private String packid;
	private String transactionid;
//查询条件
	private String lotatt10;  //质量状态
	private String descrc;  //规格型号
	private String shippershortname;  //货主简称
	private String lotatt12;                  //产品名称
	private String lotatt08;                   //供应商
	private String lotatt15;                   //生产企业
	private String lotatt03Start; //入库日期
	private String lotatt03End;     //入库日期
	private String lotatt14;    //入库单号

	public String getQcno() {
		return qcno;
	}

	public void setQcno(String qcno) {
		this.qcno = qcno;
	}

	public String getQclineno() {
		return qclineno;
	}

	public void setQclineno(String qclineno) {
		this.qclineno = qclineno;
	}

	public String getPano() {
		return pano;
	}

	public void setPano(String pano) {
		this.pano = pano;
	}

	public String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
	}

	public String getPalineno() {
		return palineno;
	}

	public void setPalineno(String palineno) {
		this.palineno = palineno;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getLotnum() {
		return lotnum;
	}

	public void setLotnum(String lotnum) {
		this.lotnum = lotnum;
	}

	public String getPaqtyExpected() {
		return paqtyExpected;
	}

	public void setPaqtyExpected(String paqtyExpected) {
		this.paqtyExpected = paqtyExpected;
	}

	public String getQcqtyExpected() {
		return qcqtyExpected;
	}

	public void setQcqtyExpected(String qcqtyExpected) {
		this.qcqtyExpected = qcqtyExpected;
	}

	public String getQcqtyCompleted() {
		return qcqtyCompleted;
	}

	public void setQcqtyCompleted(String qcqtyCompleted) {
		this.qcqtyCompleted = qcqtyCompleted;
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

	public String getQcdescr() {
		return qcdescr;
	}

	public void setQcdescr(String qcdescr) {
		this.qcdescr = qcdescr;
	}

	public String getQcresult() {
		return qcresult;
	}

	public void setQcresult(String qcresult) {
		this.qcresult = qcresult;
	}

	public String getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getDescrc() {
		return descrc;
	}

	public void setDescrc(String descrc) {
		this.descrc = descrc;
	}

	public String getShippershortname() {
		return shippershortname;
	}

	public void setShippershortname(String shippershortname) {
		this.shippershortname = shippershortname;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt15() {
		return lotatt15;
	}

	public void setLotatt15(String lotatt15) {
		this.lotatt15 = lotatt15;
	}

	public String getLotatt03Start() {
		return lotatt03Start;
	}

	public void setLotatt03Start(String lotatt03Start) {
		this.lotatt03Start = lotatt03Start;
	}

	public String getLotatt03End() {
		return lotatt03End;
	}

	public void setLotatt03End(String lotatt03End) {
		this.lotatt03End = lotatt03End;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}
}