package com.wms.query;

import java.util.Set;

import com.wms.mybatis.entity.SfcCustomer;

public class BasPackageQuery implements IQuery {

	private java.lang.String packid;
	private java.util.Date addtime;
	private java.lang.String addwho;
	private java.lang.String cartonizeuom1;
	private java.lang.String cartonizeuom2;
	private java.lang.String cartonizeuom3;
	private java.lang.String cartonizeuom4;
	private java.lang.String cartonizeuom5;
	private java.math.BigDecimal cubeuom1;
	private java.math.BigDecimal cubeuom2;
	private java.math.BigDecimal cubeuom3;
	private java.math.BigDecimal cubeuom4;
	private java.math.BigDecimal cubeuom5;
	private java.lang.String descr;
	private java.lang.String descr1;
	private java.lang.String descr2;
	private java.lang.String descr3;
	private java.lang.String descr4;
	private java.lang.String descr5;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.math.BigDecimal heightuom1;
	private java.math.BigDecimal heightuom2;
	private java.math.BigDecimal heightuom3;
	private java.math.BigDecimal heightuom4;
	private java.math.BigDecimal heightuom5;
	private java.lang.String inLabel1;
	private java.lang.String inLabel2;
	private java.lang.String inLabel3;
	private java.lang.String inLabel4;
	private java.lang.String inLabel5;
	private java.math.BigDecimal lengthuom1;
	private java.math.BigDecimal lengthuom2;
	private java.math.BigDecimal lengthuom3;
	private java.math.BigDecimal lengthuom4;
	private java.math.BigDecimal lengthuom5;
	private java.lang.String outLabel1;
	private java.lang.String outLabel2;
	private java.lang.String outLabel3;
	private java.lang.String outLabel4;
	private java.lang.String outLabel5;
	private java.lang.String packMaterial2;
	private java.lang.String packmaterial1;
	private java.lang.String packmaterial2;
	private java.lang.String packmaterial3;
	private java.lang.String packmaterial4;
	private java.lang.String packmaterial5;
	private java.lang.String packuom1;
	private java.lang.String packuom2;
	private java.lang.String packuom3;
	private java.lang.String packuom4;
	private java.lang.String packuom5;
	private java.math.BigDecimal pallethi;
	private java.math.BigDecimal palletti;
	private java.math.BigDecimal palletwoodheight;
	private java.math.BigDecimal palletwoodlength;
	private java.math.BigDecimal palletwoodwidth;
	private java.math.BigDecimal qtyMaterial2;
	private java.math.BigDecimal qty1;
	private java.math.BigDecimal qty2;
	private java.math.BigDecimal qty3;
	private java.math.BigDecimal qty4;
	private java.math.BigDecimal qty5;
	private java.lang.String rplLabel1;
	private java.lang.String rplLabel2;
	private java.lang.String rplLabel3;
	private java.lang.String rplLabel4;
	private java.lang.String sn1;
	private java.lang.String sn2;
	private java.lang.String sn3;
	private java.math.BigDecimal weightuom1;
	private java.math.BigDecimal weightuom2;
	private java.math.BigDecimal weightuom3;
	private java.math.BigDecimal weightuom4;
	private java.math.BigDecimal weightuom5;
	private java.math.BigDecimal widthuom1;
	private java.math.BigDecimal widthuom2;
	private java.math.BigDecimal widthuom3;
	private java.math.BigDecimal widthuom4;
	private java.math.BigDecimal widthuom5;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;

	public java.lang.String getPackid() {
		return packid;
	}

	public void setPackid(java.lang.String packid) {
		this.packid = packid;
	}

	public java.util.Date getAddtime() {
		return addtime;
	}

	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}

	public java.lang.String getAddwho() {
		return addwho;
	}

	public void setAddwho(java.lang.String addwho) {
		this.addwho = addwho;
	}

	public java.lang.String getCartonizeuom1() {
		return cartonizeuom1;
	}

	public void setCartonizeuom1(java.lang.String cartonizeuom1) {
		this.cartonizeuom1 = cartonizeuom1;
	}

	public java.lang.String getCartonizeuom2() {
		return cartonizeuom2;
	}

	public void setCartonizeuom2(java.lang.String cartonizeuom2) {
		this.cartonizeuom2 = cartonizeuom2;
	}

	public java.lang.String getCartonizeuom3() {
		return cartonizeuom3;
	}

	public void setCartonizeuom3(java.lang.String cartonizeuom3) {
		this.cartonizeuom3 = cartonizeuom3;
	}

	public java.lang.String getCartonizeuom4() {
		return cartonizeuom4;
	}

	public void setCartonizeuom4(java.lang.String cartonizeuom4) {
		this.cartonizeuom4 = cartonizeuom4;
	}

	public java.lang.String getCartonizeuom5() {
		return cartonizeuom5;
	}

	public void setCartonizeuom5(java.lang.String cartonizeuom5) {
		this.cartonizeuom5 = cartonizeuom5;
	}

	public java.math.BigDecimal getCubeuom1() {
		return cubeuom1;
	}

	public void setCubeuom1(java.math.BigDecimal cubeuom1) {
		this.cubeuom1 = cubeuom1;
	}

	public java.math.BigDecimal getCubeuom2() {
		return cubeuom2;
	}

	public void setCubeuom2(java.math.BigDecimal cubeuom2) {
		this.cubeuom2 = cubeuom2;
	}

	public java.math.BigDecimal getCubeuom3() {
		return cubeuom3;
	}

	public void setCubeuom3(java.math.BigDecimal cubeuom3) {
		this.cubeuom3 = cubeuom3;
	}

	public java.math.BigDecimal getCubeuom4() {
		return cubeuom4;
	}

	public void setCubeuom4(java.math.BigDecimal cubeuom4) {
		this.cubeuom4 = cubeuom4;
	}

	public java.math.BigDecimal getCubeuom5() {
		return cubeuom5;
	}

	public void setCubeuom5(java.math.BigDecimal cubeuom5) {
		this.cubeuom5 = cubeuom5;
	}

	public java.lang.String getDescr() {
		return descr;
	}

	public void setDescr(java.lang.String descr) {
		this.descr = descr;
	}

	public java.lang.String getDescr1() {
		return descr1;
	}

	public void setDescr1(java.lang.String descr1) {
		this.descr1 = descr1;
	}

	public java.lang.String getDescr2() {
		return descr2;
	}

	public void setDescr2(java.lang.String descr2) {
		this.descr2 = descr2;
	}

	public java.lang.String getDescr3() {
		return descr3;
	}

	public void setDescr3(java.lang.String descr3) {
		this.descr3 = descr3;
	}

	public java.lang.String getDescr4() {
		return descr4;
	}

	public void setDescr4(java.lang.String descr4) {
		this.descr4 = descr4;
	}

	public java.lang.String getDescr5() {
		return descr5;
	}

	public void setDescr5(java.lang.String descr5) {
		this.descr5 = descr5;
	}

	public java.util.Date getEdittime() {
		return edittime;
	}

	public void setEdittime(java.util.Date edittime) {
		this.edittime = edittime;
	}

	public java.lang.String getEditwho() {
		return editwho;
	}

	public void setEditwho(java.lang.String editwho) {
		this.editwho = editwho;
	}

	public java.math.BigDecimal getHeightuom1() {
		return heightuom1;
	}

	public void setHeightuom1(java.math.BigDecimal heightuom1) {
		this.heightuom1 = heightuom1;
	}

	public java.math.BigDecimal getHeightuom2() {
		return heightuom2;
	}

	public void setHeightuom2(java.math.BigDecimal heightuom2) {
		this.heightuom2 = heightuom2;
	}

	public java.math.BigDecimal getHeightuom3() {
		return heightuom3;
	}

	public void setHeightuom3(java.math.BigDecimal heightuom3) {
		this.heightuom3 = heightuom3;
	}

	public java.math.BigDecimal getHeightuom4() {
		return heightuom4;
	}

	public void setHeightuom4(java.math.BigDecimal heightuom4) {
		this.heightuom4 = heightuom4;
	}

	public java.math.BigDecimal getHeightuom5() {
		return heightuom5;
	}

	public void setHeightuom5(java.math.BigDecimal heightuom5) {
		this.heightuom5 = heightuom5;
	}

	public java.lang.String getInLabel1() {
		return inLabel1;
	}

	public void setInLabel1(java.lang.String inLabel1) {
		this.inLabel1 = inLabel1;
	}

	public java.lang.String getInLabel2() {
		return inLabel2;
	}

	public void setInLabel2(java.lang.String inLabel2) {
		this.inLabel2 = inLabel2;
	}

	public java.lang.String getInLabel3() {
		return inLabel3;
	}

	public void setInLabel3(java.lang.String inLabel3) {
		this.inLabel3 = inLabel3;
	}

	public java.lang.String getInLabel4() {
		return inLabel4;
	}

	public void setInLabel4(java.lang.String inLabel4) {
		this.inLabel4 = inLabel4;
	}

	public java.lang.String getInLabel5() {
		return inLabel5;
	}

	public void setInLabel5(java.lang.String inLabel5) {
		this.inLabel5 = inLabel5;
	}

	public java.math.BigDecimal getLengthuom1() {
		return lengthuom1;
	}

	public void setLengthuom1(java.math.BigDecimal lengthuom1) {
		this.lengthuom1 = lengthuom1;
	}

	public java.math.BigDecimal getLengthuom2() {
		return lengthuom2;
	}

	public void setLengthuom2(java.math.BigDecimal lengthuom2) {
		this.lengthuom2 = lengthuom2;
	}

	public java.math.BigDecimal getLengthuom3() {
		return lengthuom3;
	}

	public void setLengthuom3(java.math.BigDecimal lengthuom3) {
		this.lengthuom3 = lengthuom3;
	}

	public java.math.BigDecimal getLengthuom4() {
		return lengthuom4;
	}

	public void setLengthuom4(java.math.BigDecimal lengthuom4) {
		this.lengthuom4 = lengthuom4;
	}

	public java.math.BigDecimal getLengthuom5() {
		return lengthuom5;
	}

	public void setLengthuom5(java.math.BigDecimal lengthuom5) {
		this.lengthuom5 = lengthuom5;
	}

	public java.lang.String getOutLabel1() {
		return outLabel1;
	}

	public void setOutLabel1(java.lang.String outLabel1) {
		this.outLabel1 = outLabel1;
	}

	public java.lang.String getOutLabel2() {
		return outLabel2;
	}

	public void setOutLabel2(java.lang.String outLabel2) {
		this.outLabel2 = outLabel2;
	}

	public java.lang.String getOutLabel3() {
		return outLabel3;
	}

	public void setOutLabel3(java.lang.String outLabel3) {
		this.outLabel3 = outLabel3;
	}

	public java.lang.String getOutLabel4() {
		return outLabel4;
	}

	public void setOutLabel4(java.lang.String outLabel4) {
		this.outLabel4 = outLabel4;
	}

	public java.lang.String getOutLabel5() {
		return outLabel5;
	}

	public void setOutLabel5(java.lang.String outLabel5) {
		this.outLabel5 = outLabel5;
	}

	public java.lang.String getPackMaterial2() {
		return packMaterial2;
	}

	public void setPackMaterial2(java.lang.String packMaterial2) {
		this.packMaterial2 = packMaterial2;
	}

	public java.lang.String getPackmaterial1() {
		return packmaterial1;
	}

	public void setPackmaterial1(java.lang.String packmaterial1) {
		this.packmaterial1 = packmaterial1;
	}

	public java.lang.String getPackmaterial2() {
		return packmaterial2;
	}

	public void setPackmaterial2(java.lang.String packmaterial2) {
		this.packmaterial2 = packmaterial2;
	}

	public java.lang.String getPackmaterial3() {
		return packmaterial3;
	}

	public void setPackmaterial3(java.lang.String packmaterial3) {
		this.packmaterial3 = packmaterial3;
	}

	public java.lang.String getPackmaterial4() {
		return packmaterial4;
	}

	public void setPackmaterial4(java.lang.String packmaterial4) {
		this.packmaterial4 = packmaterial4;
	}

	public java.lang.String getPackmaterial5() {
		return packmaterial5;
	}

	public void setPackmaterial5(java.lang.String packmaterial5) {
		this.packmaterial5 = packmaterial5;
	}

	public java.lang.String getPackuom1() {
		return packuom1;
	}

	public void setPackuom1(java.lang.String packuom1) {
		this.packuom1 = packuom1;
	}

	public java.lang.String getPackuom2() {
		return packuom2;
	}

	public void setPackuom2(java.lang.String packuom2) {
		this.packuom2 = packuom2;
	}

	public java.lang.String getPackuom3() {
		return packuom3;
	}

	public void setPackuom3(java.lang.String packuom3) {
		this.packuom3 = packuom3;
	}

	public java.lang.String getPackuom4() {
		return packuom4;
	}

	public void setPackuom4(java.lang.String packuom4) {
		this.packuom4 = packuom4;
	}

	public java.lang.String getPackuom5() {
		return packuom5;
	}

	public void setPackuom5(java.lang.String packuom5) {
		this.packuom5 = packuom5;
	}

	public java.math.BigDecimal getPallethi() {
		return pallethi;
	}

	public void setPallethi(java.math.BigDecimal pallethi) {
		this.pallethi = pallethi;
	}

	public java.math.BigDecimal getPalletti() {
		return palletti;
	}

	public void setPalletti(java.math.BigDecimal palletti) {
		this.palletti = palletti;
	}

	public java.math.BigDecimal getPalletwoodheight() {
		return palletwoodheight;
	}

	public void setPalletwoodheight(java.math.BigDecimal palletwoodheight) {
		this.palletwoodheight = palletwoodheight;
	}

	public java.math.BigDecimal getPalletwoodlength() {
		return palletwoodlength;
	}

	public void setPalletwoodlength(java.math.BigDecimal palletwoodlength) {
		this.palletwoodlength = palletwoodlength;
	}

	public java.math.BigDecimal getPalletwoodwidth() {
		return palletwoodwidth;
	}

	public void setPalletwoodwidth(java.math.BigDecimal palletwoodwidth) {
		this.palletwoodwidth = palletwoodwidth;
	}

	public java.math.BigDecimal getQtyMaterial2() {
		return qtyMaterial2;
	}

	public void setQtyMaterial2(java.math.BigDecimal qtyMaterial2) {
		this.qtyMaterial2 = qtyMaterial2;
	}

	public java.math.BigDecimal getQty1() {
		return qty1;
	}

	public void setQty1(java.math.BigDecimal qty1) {
		this.qty1 = qty1;
	}

	public java.math.BigDecimal getQty2() {
		return qty2;
	}

	public void setQty2(java.math.BigDecimal qty2) {
		this.qty2 = qty2;
	}

	public java.math.BigDecimal getQty3() {
		return qty3;
	}

	public void setQty3(java.math.BigDecimal qty3) {
		this.qty3 = qty3;
	}

	public java.math.BigDecimal getQty4() {
		return qty4;
	}

	public void setQty4(java.math.BigDecimal qty4) {
		this.qty4 = qty4;
	}

	public java.math.BigDecimal getQty5() {
		return qty5;
	}

	public void setQty5(java.math.BigDecimal qty5) {
		this.qty5 = qty5;
	}

	public java.lang.String getRplLabel1() {
		return rplLabel1;
	}

	public void setRplLabel1(java.lang.String rplLabel1) {
		this.rplLabel1 = rplLabel1;
	}

	public java.lang.String getRplLabel2() {
		return rplLabel2;
	}

	public void setRplLabel2(java.lang.String rplLabel2) {
		this.rplLabel2 = rplLabel2;
	}

	public java.lang.String getRplLabel3() {
		return rplLabel3;
	}

	public void setRplLabel3(java.lang.String rplLabel3) {
		this.rplLabel3 = rplLabel3;
	}

	public java.lang.String getRplLabel4() {
		return rplLabel4;
	}

	public void setRplLabel4(java.lang.String rplLabel4) {
		this.rplLabel4 = rplLabel4;
	}

	public java.lang.String getSn1() {
		return sn1;
	}

	public void setSn1(java.lang.String sn1) {
		this.sn1 = sn1;
	}

	public java.lang.String getSn2() {
		return sn2;
	}

	public void setSn2(java.lang.String sn2) {
		this.sn2 = sn2;
	}

	public java.lang.String getSn3() {
		return sn3;
	}

	public void setSn3(java.lang.String sn3) {
		this.sn3 = sn3;
	}

	public java.math.BigDecimal getWeightuom1() {
		return weightuom1;
	}

	public void setWeightuom1(java.math.BigDecimal weightuom1) {
		this.weightuom1 = weightuom1;
	}

	public java.math.BigDecimal getWeightuom2() {
		return weightuom2;
	}

	public void setWeightuom2(java.math.BigDecimal weightuom2) {
		this.weightuom2 = weightuom2;
	}

	public java.math.BigDecimal getWeightuom3() {
		return weightuom3;
	}

	public void setWeightuom3(java.math.BigDecimal weightuom3) {
		this.weightuom3 = weightuom3;
	}

	public java.math.BigDecimal getWeightuom4() {
		return weightuom4;
	}

	public void setWeightuom4(java.math.BigDecimal weightuom4) {
		this.weightuom4 = weightuom4;
	}

	public java.math.BigDecimal getWeightuom5() {
		return weightuom5;
	}

	public void setWeightuom5(java.math.BigDecimal weightuom5) {
		this.weightuom5 = weightuom5;
	}

	public java.math.BigDecimal getWidthuom1() {
		return widthuom1;
	}

	public void setWidthuom1(java.math.BigDecimal widthuom1) {
		this.widthuom1 = widthuom1;
	}

	public java.math.BigDecimal getWidthuom2() {
		return widthuom2;
	}

	public void setWidthuom2(java.math.BigDecimal widthuom2) {
		this.widthuom2 = widthuom2;
	}

	public java.math.BigDecimal getWidthuom3() {
		return widthuom3;
	}

	public void setWidthuom3(java.math.BigDecimal widthuom3) {
		this.widthuom3 = widthuom3;
	}

	public java.math.BigDecimal getWidthuom4() {
		return widthuom4;
	}

	public void setWidthuom4(java.math.BigDecimal widthuom4) {
		this.widthuom4 = widthuom4;
	}

	public java.math.BigDecimal getWidthuom5() {
		return widthuom5;
	}

	public void setWidthuom5(java.math.BigDecimal widthuom5) {
		this.widthuom5 = widthuom5;
	}

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

}