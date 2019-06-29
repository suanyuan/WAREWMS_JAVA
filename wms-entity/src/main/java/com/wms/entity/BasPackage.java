package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BAS_PACKAGE database table.
 * 
 */
@Entity
public class BasPackage implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	private String packid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	private String cartonizeuom1;

	private String cartonizeuom2;

	private String cartonizeuom3;

	private String cartonizeuom4;

	private String cartonizeuom5;

	private BigDecimal cubeuom1;

	private BigDecimal cubeuom2;

	private BigDecimal cubeuom3;

	private BigDecimal cubeuom4;

	private BigDecimal cubeuom5;

	private String descr;

	private String descr1;

	private String descr2;

	private String descr3;

	private String descr4;

	private String descr5;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	private BigDecimal heightuom1;

	private BigDecimal heightuom2;

	private BigDecimal heightuom3;

	private BigDecimal heightuom4;

	private BigDecimal heightuom5;

	private String inLabel1;

	private String inLabel2;

	private String inLabel3;

	private String inLabel4;

	private String inLabel5;

	private BigDecimal lengthuom1;

	private BigDecimal lengthuom2;

	private BigDecimal lengthuom3;

	private BigDecimal lengthuom4;

	private BigDecimal lengthuom5;

	private String outLabel1;

	private String outLabel2;

	private String outLabel3;

	private String outLabel4;

	private String outLabel5;

	private String packMaterial2;

	private String packmaterial1;

	private String packmaterial2;

	private String packmaterial3;

	private String packmaterial4;

	private String packmaterial5;

	private String packuom1;

	private String packuom2;

	private String packuom3;

	private String packuom4;

	private String packuom5;

	private BigDecimal pallethi;

	private BigDecimal palletti;

	private BigDecimal palletwoodheight;

	private BigDecimal palletwoodlength;

	private BigDecimal palletwoodwidth;

	private BigDecimal qtyMaterial2;

    /**
     * 件-数量换算
     */
	private BigDecimal qty1;

	private BigDecimal qty2;

	private BigDecimal qty3;

	private BigDecimal qty4;

	private BigDecimal qty5;

	private String rplLabel1;

	private String rplLabel2;

	private String rplLabel3;

	private String rplLabel4;

	private String sn1;

	private String sn2;

	private String sn3;

	private BigDecimal weightuom1;

	private BigDecimal weightuom2;

	private BigDecimal weightuom3;

	private BigDecimal weightuom4;

	private BigDecimal weightuom5;

	private BigDecimal widthuom1;

	private BigDecimal widthuom2;

	private BigDecimal widthuom3;

	private BigDecimal widthuom4;

	private BigDecimal widthuom5;

	public BasPackage() {
	}

	public String getPackid() {
		return this.packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public Date getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return this.addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getCartonizeuom1() {
		return this.cartonizeuom1;
	}

	public void setCartonizeuom1(String cartonizeuom1) {
		this.cartonizeuom1 = cartonizeuom1;
	}

	public String getCartonizeuom2() {
		return this.cartonizeuom2;
	}

	public void setCartonizeuom2(String cartonizeuom2) {
		this.cartonizeuom2 = cartonizeuom2;
	}

	public String getCartonizeuom3() {
		return this.cartonizeuom3;
	}

	public void setCartonizeuom3(String cartonizeuom3) {
		this.cartonizeuom3 = cartonizeuom3;
	}

	public String getCartonizeuom4() {
		return this.cartonizeuom4;
	}

	public void setCartonizeuom4(String cartonizeuom4) {
		this.cartonizeuom4 = cartonizeuom4;
	}

	public String getCartonizeuom5() {
		return this.cartonizeuom5;
	}

	public void setCartonizeuom5(String cartonizeuom5) {
		this.cartonizeuom5 = cartonizeuom5;
	}

	public BigDecimal getCubeuom1() {
		return this.cubeuom1;
	}

	public void setCubeuom1(BigDecimal cubeuom1) {
		this.cubeuom1 = cubeuom1;
	}

	public BigDecimal getCubeuom2() {
		return this.cubeuom2;
	}

	public void setCubeuom2(BigDecimal cubeuom2) {
		this.cubeuom2 = cubeuom2;
	}

	public BigDecimal getCubeuom3() {
		return this.cubeuom3;
	}

	public void setCubeuom3(BigDecimal cubeuom3) {
		this.cubeuom3 = cubeuom3;
	}

	public BigDecimal getCubeuom4() {
		return this.cubeuom4;
	}

	public void setCubeuom4(BigDecimal cubeuom4) {
		this.cubeuom4 = cubeuom4;
	}

	public BigDecimal getCubeuom5() {
		return this.cubeuom5;
	}

	public void setCubeuom5(BigDecimal cubeuom5) {
		this.cubeuom5 = cubeuom5;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDescr1() {
		return this.descr1;
	}

	public void setDescr1(String descr1) {
		this.descr1 = descr1;
	}

	public String getDescr2() {
		return this.descr2;
	}

	public void setDescr2(String descr2) {
		this.descr2 = descr2;
	}

	public String getDescr3() {
		return this.descr3;
	}

	public void setDescr3(String descr3) {
		this.descr3 = descr3;
	}

	public String getDescr4() {
		return this.descr4;
	}

	public void setDescr4(String descr4) {
		this.descr4 = descr4;
	}

	public String getDescr5() {
		return this.descr5;
	}

	public void setDescr5(String descr5) {
		this.descr5 = descr5;
	}

	public Date getEdittime() {
		return this.edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return this.editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public BigDecimal getHeightuom1() {
		return this.heightuom1;
	}

	public void setHeightuom1(BigDecimal heightuom1) {
		this.heightuom1 = heightuom1;
	}

	public BigDecimal getHeightuom2() {
		return this.heightuom2;
	}

	public void setHeightuom2(BigDecimal heightuom2) {
		this.heightuom2 = heightuom2;
	}

	public BigDecimal getHeightuom3() {
		return this.heightuom3;
	}

	public void setHeightuom3(BigDecimal heightuom3) {
		this.heightuom3 = heightuom3;
	}

	public BigDecimal getHeightuom4() {
		return this.heightuom4;
	}

	public void setHeightuom4(BigDecimal heightuom4) {
		this.heightuom4 = heightuom4;
	}

	public BigDecimal getHeightuom5() {
		return this.heightuom5;
	}

	public void setHeightuom5(BigDecimal heightuom5) {
		this.heightuom5 = heightuom5;
	}

	public String getInLabel1() {
		return this.inLabel1;
	}

	public void setInLabel1(String inLabel1) {
		this.inLabel1 = inLabel1;
	}

	public String getInLabel2() {
		return this.inLabel2;
	}

	public void setInLabel2(String inLabel2) {
		this.inLabel2 = inLabel2;
	}

	public String getInLabel3() {
		return this.inLabel3;
	}

	public void setInLabel3(String inLabel3) {
		this.inLabel3 = inLabel3;
	}

	public String getInLabel4() {
		return this.inLabel4;
	}

	public void setInLabel4(String inLabel4) {
		this.inLabel4 = inLabel4;
	}

	public String getInLabel5() {
		return this.inLabel5;
	}

	public void setInLabel5(String inLabel5) {
		this.inLabel5 = inLabel5;
	}

	public BigDecimal getLengthuom1() {
		return this.lengthuom1;
	}

	public void setLengthuom1(BigDecimal lengthuom1) {
		this.lengthuom1 = lengthuom1;
	}

	public BigDecimal getLengthuom2() {
		return this.lengthuom2;
	}

	public void setLengthuom2(BigDecimal lengthuom2) {
		this.lengthuom2 = lengthuom2;
	}

	public BigDecimal getLengthuom3() {
		return this.lengthuom3;
	}

	public void setLengthuom3(BigDecimal lengthuom3) {
		this.lengthuom3 = lengthuom3;
	}

	public BigDecimal getLengthuom4() {
		return this.lengthuom4;
	}

	public void setLengthuom4(BigDecimal lengthuom4) {
		this.lengthuom4 = lengthuom4;
	}

	public BigDecimal getLengthuom5() {
		return this.lengthuom5;
	}

	public void setLengthuom5(BigDecimal lengthuom5) {
		this.lengthuom5 = lengthuom5;
	}

	public String getOutLabel1() {
		return this.outLabel1;
	}

	public void setOutLabel1(String outLabel1) {
		this.outLabel1 = outLabel1;
	}

	public String getOutLabel2() {
		return this.outLabel2;
	}

	public void setOutLabel2(String outLabel2) {
		this.outLabel2 = outLabel2;
	}

	public String getOutLabel3() {
		return this.outLabel3;
	}

	public void setOutLabel3(String outLabel3) {
		this.outLabel3 = outLabel3;
	}

	public String getOutLabel4() {
		return this.outLabel4;
	}

	public void setOutLabel4(String outLabel4) {
		this.outLabel4 = outLabel4;
	}

	public String getOutLabel5() {
		return this.outLabel5;
	}

	public void setOutLabel5(String outLabel5) {
		this.outLabel5 = outLabel5;
	}

	public String getPackMaterial2() {
		return this.packMaterial2;
	}

	public void setPackMaterial2(String packMaterial2) {
		this.packMaterial2 = packMaterial2;
	}

	public String getPackmaterial1() {
		return this.packmaterial1;
	}

	public void setPackmaterial1(String packmaterial1) {
		this.packmaterial1 = packmaterial1;
	}

	public String getPackmaterial2() {
		return this.packmaterial2;
	}

	public void setPackmaterial2(String packmaterial2) {
		this.packmaterial2 = packmaterial2;
	}

	public String getPackmaterial3() {
		return this.packmaterial3;
	}

	public void setPackmaterial3(String packmaterial3) {
		this.packmaterial3 = packmaterial3;
	}

	public String getPackmaterial4() {
		return this.packmaterial4;
	}

	public void setPackmaterial4(String packmaterial4) {
		this.packmaterial4 = packmaterial4;
	}

	public String getPackmaterial5() {
		return this.packmaterial5;
	}

	public void setPackmaterial5(String packmaterial5) {
		this.packmaterial5 = packmaterial5;
	}

	public String getPackuom1() {
		return this.packuom1;
	}

	public void setPackuom1(String packuom1) {
		this.packuom1 = packuom1;
	}

	public String getPackuom2() {
		return this.packuom2;
	}

	public void setPackuom2(String packuom2) {
		this.packuom2 = packuom2;
	}

	public String getPackuom3() {
		return this.packuom3;
	}

	public void setPackuom3(String packuom3) {
		this.packuom3 = packuom3;
	}

	public String getPackuom4() {
		return this.packuom4;
	}

	public void setPackuom4(String packuom4) {
		this.packuom4 = packuom4;
	}

	public String getPackuom5() {
		return this.packuom5;
	}

	public void setPackuom5(String packuom5) {
		this.packuom5 = packuom5;
	}

	public BigDecimal getPallethi() {
		return this.pallethi;
	}

	public void setPallethi(BigDecimal pallethi) {
		this.pallethi = pallethi;
	}

	public BigDecimal getPalletti() {
		return this.palletti;
	}

	public void setPalletti(BigDecimal palletti) {
		this.palletti = palletti;
	}

	public BigDecimal getPalletwoodheight() {
		return this.palletwoodheight;
	}

	public void setPalletwoodheight(BigDecimal palletwoodheight) {
		this.palletwoodheight = palletwoodheight;
	}

	public BigDecimal getPalletwoodlength() {
		return this.palletwoodlength;
	}

	public void setPalletwoodlength(BigDecimal palletwoodlength) {
		this.palletwoodlength = palletwoodlength;
	}

	public BigDecimal getPalletwoodwidth() {
		return this.palletwoodwidth;
	}

	public void setPalletwoodwidth(BigDecimal palletwoodwidth) {
		this.palletwoodwidth = palletwoodwidth;
	}

	public BigDecimal getQtyMaterial2() {
		return this.qtyMaterial2;
	}

	public void setQtyMaterial2(BigDecimal qtyMaterial2) {
		this.qtyMaterial2 = qtyMaterial2;
	}

	public BigDecimal getQty1() {
		return this.qty1;
	}

	public void setQty1(BigDecimal qty1) {
		this.qty1 = qty1;
	}

	public BigDecimal getQty2() {
		return this.qty2;
	}

	public void setQty2(BigDecimal qty2) {
		this.qty2 = qty2;
	}

	public BigDecimal getQty3() {
		return this.qty3;
	}

	public void setQty3(BigDecimal qty3) {
		this.qty3 = qty3;
	}

	public BigDecimal getQty4() {
		return this.qty4;
	}

	public void setQty4(BigDecimal qty4) {
		this.qty4 = qty4;
	}

	public BigDecimal getQty5() {
		return this.qty5;
	}

	public void setQty5(BigDecimal qty5) {
		this.qty5 = qty5;
	}

	public String getRplLabel1() {
		return this.rplLabel1;
	}

	public void setRplLabel1(String rplLabel1) {
		this.rplLabel1 = rplLabel1;
	}

	public String getRplLabel2() {
		return this.rplLabel2;
	}

	public void setRplLabel2(String rplLabel2) {
		this.rplLabel2 = rplLabel2;
	}

	public String getRplLabel3() {
		return this.rplLabel3;
	}

	public void setRplLabel3(String rplLabel3) {
		this.rplLabel3 = rplLabel3;
	}

	public String getRplLabel4() {
		return this.rplLabel4;
	}

	public void setRplLabel4(String rplLabel4) {
		this.rplLabel4 = rplLabel4;
	}

	public String getSn1() {
		return this.sn1;
	}

	public void setSn1(String sn1) {
		this.sn1 = sn1;
	}

	public String getSn2() {
		return this.sn2;
	}

	public void setSn2(String sn2) {
		this.sn2 = sn2;
	}

	public String getSn3() {
		return this.sn3;
	}

	public void setSn3(String sn3) {
		this.sn3 = sn3;
	}

	public BigDecimal getWeightuom1() {
		return this.weightuom1;
	}

	public void setWeightuom1(BigDecimal weightuom1) {
		this.weightuom1 = weightuom1;
	}

	public BigDecimal getWeightuom2() {
		return this.weightuom2;
	}

	public void setWeightuom2(BigDecimal weightuom2) {
		this.weightuom2 = weightuom2;
	}

	public BigDecimal getWeightuom3() {
		return this.weightuom3;
	}

	public void setWeightuom3(BigDecimal weightuom3) {
		this.weightuom3 = weightuom3;
	}

	public BigDecimal getWeightuom4() {
		return this.weightuom4;
	}

	public void setWeightuom4(BigDecimal weightuom4) {
		this.weightuom4 = weightuom4;
	}

	public BigDecimal getWeightuom5() {
		return this.weightuom5;
	}

	public void setWeightuom5(BigDecimal weightuom5) {
		this.weightuom5 = weightuom5;
	}

	public BigDecimal getWidthuom1() {
		return this.widthuom1;
	}

	public void setWidthuom1(BigDecimal widthuom1) {
		this.widthuom1 = widthuom1;
	}

	public BigDecimal getWidthuom2() {
		return this.widthuom2;
	}

	public void setWidthuom2(BigDecimal widthuom2) {
		this.widthuom2 = widthuom2;
	}

	public BigDecimal getWidthuom3() {
		return this.widthuom3;
	}

	public void setWidthuom3(BigDecimal widthuom3) {
		this.widthuom3 = widthuom3;
	}

	public BigDecimal getWidthuom4() {
		return this.widthuom4;
	}

	public void setWidthuom4(BigDecimal widthuom4) {
		this.widthuom4 = widthuom4;
	}

	public BigDecimal getWidthuom5() {
		return this.widthuom5;
	}

	public void setWidthuom5(BigDecimal widthuom5) {
		this.widthuom5 = widthuom5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((packid == null) ? 0 : packid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasPackage other = (BasPackage) obj;
		if (packid == null) {
			if (other.packid != null)
				return false;
		} else if (!packid.equals(other.packid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasPackage [hashCode=" + hashCode + ", packid=" + packid
				+ ", addtime=" + addtime + ", addwho=" + addwho
				+ ", cartonizeuom1=" + cartonizeuom1 + ", cartonizeuom2="
				+ cartonizeuom2 + ", cartonizeuom3=" + cartonizeuom3
				+ ", cartonizeuom4=" + cartonizeuom4 + ", cartonizeuom5="
				+ cartonizeuom5 + ", cubeuom1=" + cubeuom1 + ", cubeuom2="
				+ cubeuom2 + ", cubeuom3=" + cubeuom3 + ", cubeuom4="
				+ cubeuom4 + ", cubeuom5=" + cubeuom5 + ", descr=" + descr
				+ ", descr1=" + descr1 + ", descr2=" + descr2 + ", descr3="
				+ descr3 + ", descr4=" + descr4 + ", descr5=" + descr5
				+ ", edittime=" + edittime + ", editwho=" + editwho
				+ ", heightuom1=" + heightuom1 + ", heightuom2=" + heightuom2
				+ ", heightuom3=" + heightuom3 + ", heightuom4=" + heightuom4
				+ ", heightuom5=" + heightuom5 + ", inLabel1=" + inLabel1
				+ ", inLabel2=" + inLabel2 + ", inLabel3=" + inLabel3
				+ ", inLabel4=" + inLabel4 + ", inLabel5=" + inLabel5
				+ ", lengthuom1=" + lengthuom1 + ", lengthuom2=" + lengthuom2
				+ ", lengthuom3=" + lengthuom3 + ", lengthuom4=" + lengthuom4
				+ ", lengthuom5=" + lengthuom5 + ", outLabel1=" + outLabel1
				+ ", outLabel2=" + outLabel2 + ", outLabel3=" + outLabel3
				+ ", outLabel4=" + outLabel4 + ", outLabel5=" + outLabel5
				+ ", packMaterial2=" + packMaterial2 + ", packmaterial1="
				+ packmaterial1 + ", packmaterial2=" + packmaterial2
				+ ", packmaterial3=" + packmaterial3 + ", packmaterial4="
				+ packmaterial4 + ", packmaterial5=" + packmaterial5
				+ ", packuom1=" + packuom1 + ", packuom2=" + packuom2
				+ ", packuom3=" + packuom3 + ", packuom4=" + packuom4
				+ ", packuom5=" + packuom5 + ", pallethi=" + pallethi
				+ ", palletti=" + palletti + ", palletwoodheight="
				+ palletwoodheight + ", palletwoodlength=" + palletwoodlength
				+ ", palletwoodwidth=" + palletwoodwidth + ", qtyMaterial2="
				+ qtyMaterial2 + ", qty1=" + qty1 + ", qty2=" + qty2
				+ ", qty3=" + qty3 + ", qty4=" + qty4 + ", qty5=" + qty5
				+ ", rplLabel1=" + rplLabel1 + ", rplLabel2=" + rplLabel2
				+ ", rplLabel3=" + rplLabel3 + ", rplLabel4=" + rplLabel4
				+ ", sn1=" + sn1 + ", sn2=" + sn2 + ", sn3=" + sn3
				+ ", weightuom1=" + weightuom1 + ", weightuom2=" + weightuom2
				+ ", weightuom3=" + weightuom3 + ", weightuom4=" + weightuom4
				+ ", weightuom5=" + weightuom5 + ", widthuom1=" + widthuom1
				+ ", widthuom2=" + widthuom2 + ", widthuom3=" + widthuom3
				+ ", widthuom4=" + widthuom4 + ", widthuom5=" + widthuom5 + "]";
	}

}