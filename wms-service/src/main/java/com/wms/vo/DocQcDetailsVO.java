package com.wms.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocQcDetailsVO {

	private String pano;     //上架单号
	private String qcno;
	private String qclineno;
	private String linestatus;
	private Double palineno;
	private String customerid;
	private String sku;
	private String lotnum;
	private Double paqtyExpected;
	private Double qcqtyExpected;
	private Double qcqtyExpectedEach;

	private Double qcqtyCompleted;
	private Double qcqtyCompletedEach;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String qcdescr;
	private String qcresult;
	private String filecontent;
	private String notes;
	private java.util.Date addtime;
	private String addwho;
	private java.util.Date edittime;
	private String editwho;
	private String packid;
	private String transactionid;

//inv_lot_att by lotnum

	private String lotatt01;  //生产日期
	private String lotatt02;  //效期
	private String lotatt03;  //入库日期
	private String lotatt04;  //生产批号
	private String lotatt05;  //序列号
	private String lotatt06;  //产品注册证
	private String lotatt07;  //灭菌批号
	private String lotatt08;  //供应商
	private String lotatt10;  //质量状态
	private String lotatt11;  //存储条件
	private String lotatt12;  //产品名称
	private String lotatt14;  //入库单号
	private String lotatt15;  //生产企业
//bas_sku
	private String descrc;  //规格型号
	private String reservedfield06;  //生产许可证号/备案号
	private String reservedfield09;  //医疗器械
	private String reservedfield14;  //企业信息（默认）

	//bas_package
	private Double qty1;  //转换率
//bas_customer
	private String shippershortname;  //货主简称

	//合计
	private Double qcqtyExpectedSum;
	private Double qcqtyExpectedEachSum;

	private Double qcqtyCompletedSum;
	private Double qcqtyCompletedEachSum;

	public Double getQcqtyExpectedSum() {
		return qcqtyExpectedSum;
	}

	public void setQcqtyExpectedSum(Double qcqtyExpectedSum) {
		this.qcqtyExpectedSum = qcqtyExpectedSum;
	}

	public Double getQcqtyExpectedEachSum() {
		return qcqtyExpectedEachSum;
	}

	public void setQcqtyExpectedEachSum(Double qcqtyExpectedEachSum) {
		this.qcqtyExpectedEachSum = qcqtyExpectedEachSum;
	}

	public Double getQcqtyCompletedSum() {
		return qcqtyCompletedSum;
	}

	public void setQcqtyCompletedSum(Double qcqtyCompletedSum) {
		this.qcqtyCompletedSum = qcqtyCompletedSum;
	}

	public Double getQcqtyCompletedEachSum() {
		return qcqtyCompletedEachSum;
	}

	public void setQcqtyCompletedEachSum(Double qcqtyCompletedEachSum) {
		this.qcqtyCompletedEachSum = qcqtyCompletedEachSum;
	}

	public String getReservedfield14() {
		return reservedfield14;
	}

	public void setReservedfield14(String reservedfield14) {
		this.reservedfield14 = reservedfield14;
	}

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt04() {
		return lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt05() {
		return lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt15() {
		return lotatt15;
	}

	public void setLotatt15(String lotatt15) {
		this.lotatt15 = lotatt15;
	}

	public String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getPano() {
		return pano;
	}

	public void setPano(String pano) {
		this.pano = pano;
	}

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

	public String getLinestatus() {
		return linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
	}

	public Double getPalineno() {
		return palineno;
	}

	public void setPalineno(Double palineno) {
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

	public Double getPaqtyExpected() {
		return paqtyExpected;
	}

	public void setPaqtyExpected(Double paqtyExpected) {
		this.paqtyExpected = paqtyExpected;
	}

	public Double getQcqtyExpected() {
		return qcqtyExpected;
	}

	public void setQcqtyExpected(Double qcqtyExpected) {
		this.qcqtyExpected = qcqtyExpected;
	}

	public Double getQcqtyCompleted() {
		return qcqtyCompleted;
	}

	public void setQcqtyCompleted(Double qcqtyCompleted) {
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

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	@JsonSerialize(using = JsonDatetimeSerializer.class)
	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
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

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getDescrc() {
		return descrc;
	}

	public void setDescrc(String descrc) {
		this.descrc = descrc;
	}

	public String getReservedfield06() {
		return reservedfield06;
	}

	public void setReservedfield06(String reservedfield06) {
		this.reservedfield06 = reservedfield06;
	}

	public Double getQty1() {
		return qty1;
	}

	public void setQty1(Double qty1) {
		this.qty1 = qty1;
	}

	public String getShippershortname() {
		return shippershortname;
	}

	public void setShippershortname(String shippershortname) {
		this.shippershortname = shippershortname;
	}

	public Double getQcqtyExpectedEach() {
		return qcqtyExpectedEach;
	}

	public void setQcqtyExpectedEach(Double qcqtyExpectedEach) {
		this.qcqtyExpectedEach = qcqtyExpectedEach;
	}

	public Double getQcqtyCompletedEach() {
		return qcqtyCompletedEach;
	}

	public void setQcqtyCompletedEach(Double qcqtyCompletedEach) {
		this.qcqtyCompletedEach = qcqtyCompletedEach;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getReservedfield09() {
		return reservedfield09;
	}

	public void setReservedfield09(String reservedfield09) {
		this.reservedfield09 = reservedfield09;
	}
}