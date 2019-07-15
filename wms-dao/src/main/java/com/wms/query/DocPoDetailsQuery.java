package com.wms.query;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.wms.utils.serialzer.JsonDatetimeSerializer;

public class DocPoDetailsQuery implements IQuery {

	private String pono;
	private String polineno;
	private String customerid;
	private String sku;
	private String skudescrc;
	private String skudescre;
	private String orderedqty;
	private String orderedqtyEach;
	private String receivedqty;
	private String receivedqtyEach;
	private String receivedtime;
	private String uom;
	private String packid;
	private String totalcubic;
	private String totalgrossweight;
	private String totalnetweight;
	private String totalprice;
	private String userdefine1;
	private String userdefine2;
	private String userdefine3;
	private String userdefine4;
	private String userdefine5;
	private String addtime;
	private String addwho;
	private String edittime;
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
	private String dEdi01;
	private String dEdi02;
	private String dEdi03;
	private String dEdi04;
	private String dEdi05;
	private String dEdi06;
	private String dEdi07;
	private String dEdi08;
	private String dEdi09;
	private String dEdi10;
	private String qtyreleased;

	public String getPono() {
		return pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public String getPolineno() {
		return polineno;
	}

	public void setPolineno(String polineno) {
		this.polineno = polineno;
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

	public String getSkudescrc() {
		return skudescrc;
	}

	public void setSkudescrc(String skudescrc) {
		this.skudescrc = skudescrc;
	}

	public String getSkudescre() {
		return skudescre;
	}

	public void setSkudescre(String skudescre) {
		this.skudescre = skudescre;
	}

	public String getOrderedqty() {
		return orderedqty;
	}

	public void setOrderedqty(String orderedqty) {
		this.orderedqty = orderedqty;
	}

	public String getOrderedqtyEach() {
		return orderedqtyEach;
	}

	public void setOrderedqtyEach(String orderedqtyEach) {
		this.orderedqtyEach = orderedqtyEach;
	}

	public String getReceivedqty() {
		return receivedqty;
	}

	public void setReceivedqty(String receivedqty) {
		this.receivedqty = receivedqty;
	}

	public String getReceivedqtyEach() {
		return receivedqtyEach;
	}

	public void setReceivedqtyEach(String receivedqtyEach) {
		this.receivedqtyEach = receivedqtyEach;
	}

	public String getReceivedtime() {
		return receivedtime;
	}

	public void setReceivedtime(String receivedtime) {
		this.receivedtime = receivedtime;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public String getTotalcubic() {
		return totalcubic;
	}

	public void setTotalcubic(String totalcubic) {
		this.totalcubic = totalcubic;
	}

	public String getTotalgrossweight() {
		return totalgrossweight;
	}

	public void setTotalgrossweight(String totalgrossweight) {
		this.totalgrossweight = totalgrossweight;
	}

	public String getTotalnetweight() {
		return totalnetweight;
	}

	public void setTotalnetweight(String totalnetweight) {
		this.totalnetweight = totalnetweight;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
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

	public String getLotatt01() {
		return lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt02() {
		return lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
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

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt09() {
		return lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotatt13() {
		return lotatt13;
	}

	public void setLotatt13(String lotatt13) {
		this.lotatt13 = lotatt13;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public String getLotatt15() {
		return lotatt15;
	}

	public void setLotatt15(String lotatt15) {
		this.lotatt15 = lotatt15;
	}

	public String getLotatt16() {
		return lotatt16;
	}

	public void setLotatt16(String lotatt16) {
		this.lotatt16 = lotatt16;
	}

	public String getLotatt17() {
		return lotatt17;
	}

	public void setLotatt17(String lotatt17) {
		this.lotatt17 = lotatt17;
	}

	public String getLotatt18() {
		return lotatt18;
	}

	public void setLotatt18(String lotatt18) {
		this.lotatt18 = lotatt18;
	}

	public String getPolinestatus() {
		return polinestatus;
	}

	public void setPolinestatus(String polinestatus) {
		this.polinestatus = polinestatus;
	}

	public String getDEdi01() {
		return dEdi01;
	}

	public void setDEdi01(String dEdi01) {
		this.dEdi01 = dEdi01;
	}

	public String getDEdi02() {
		return dEdi02;
	}

	public void setDEdi02(String dEdi02) {
		this.dEdi02 = dEdi02;
	}

	public String getDEdi03() {
		return dEdi03;
	}

	public void setDEdi03(String dEdi03) {
		this.dEdi03 = dEdi03;
	}

	public String getDEdi04() {
		return dEdi04;
	}

	public void setDEdi04(String dEdi04) {
		this.dEdi04 = dEdi04;
	}

	public String getDEdi05() {
		return dEdi05;
	}

	public void setDEdi05(String dEdi05) {
		this.dEdi05 = dEdi05;
	}

	public String getDEdi06() {
		return dEdi06;
	}

	public void setDEdi06(String dEdi06) {
		this.dEdi06 = dEdi06;
	}

	public String getDEdi07() {
		return dEdi07;
	}

	public void setDEdi07(String dEdi07) {
		this.dEdi07 = dEdi07;
	}

	public String getDEdi08() {
		return dEdi08;
	}

	public void setDEdi08(String dEdi08) {
		this.dEdi08 = dEdi08;
	}

	public String getDEdi09() {
		return dEdi09;
	}

	public void setDEdi09(String dEdi09) {
		this.dEdi09 = dEdi09;
	}

	public String getDEdi10() {
		return dEdi10;
	}

	public void setDEdi10(String dEdi10) {
		this.dEdi10 = dEdi10;
	}

	public String getQtyreleased() {
		return qtyreleased;
	}

	public void setQtyreleased(String qtyreleased) {
		this.qtyreleased = qtyreleased;
	}

}