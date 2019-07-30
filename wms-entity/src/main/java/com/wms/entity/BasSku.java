package com.wms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the BAS_SKU database table.
 * 
 */
@Entity
public class BasSku implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Transient
	private int hashCode = Integer.MIN_VALUE;

	@Id
	private String customerid;

	private String sku;

	private String activeFlag;

	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;

	private String addwho;

	private String allocationrule;

	private String alternateSku1;

	private String alternateSku2;

	private String alternateSku3;

	private String alternateSku4;

	private String alternateSku5;

	private BigDecimal cube;

	private String defaulthold;

	private String defaultreceivinguom;

	private String defaultshipmentuom;
    private String shelflifetype;

    public String getShelflifetype() {
        return shelflifetype;
    }

    public void setShelflifetype(String shelflifetype) {
        this.shelflifetype = shelflifetype;
    }

    /**
     * 中文产品名称
     */
	private String descrC;

    /**
     * 规格/型号
     */
	private String descrE;

	@Temporal(TemporalType.TIMESTAMP)
	private Date edittime;

	private String editwho;

	@Temporal(TemporalType.TIMESTAMP)
	private Date firstinbounddate;

	private BigDecimal grossweight;

	private BigDecimal inboundlifedays;

	private String lotid;

	private BigDecimal netweight;

	private String onestepallocation;

	private BigDecimal outboundlifedays;

	private BigDecimal overrcvpercentage;

	private String overreceiving;

	private String packid;
	private String descr;
	private BigDecimal price;

	private String putawayrule;

	private BigDecimal qtymax;

	private BigDecimal qtymin;

	private String replenishrule;

	private String reservecode;

	private String reservedfield01;

	private String reservedfield02;

	private String reservedfield03; //注册证号

	private String reservedfield04; //管理分类

	private String reservedfield05;//分类目录

	private String reservedfield06;//是否冷链

	private String reservedfield07;//是否医疗器械
	private String reservedfield08;
	private String reservedfield09;
	private String reservedfield10;
	private String reservedfield11;
	private String reservedfield12;
	private String reservedfield13;
	private String reservedfield14;
	private String reservedfield15;
	private String reservedfield16;
	private String reservedfield17;
	private String reservedfield18;

	private String rotationid;

	private String skuGroup1;

	private String skuGroup2;

	private String skuGroup3;

	private String skuGroup4;

	private String skuGroup5;

	private String skuGroup6;     //供应商代码

	private String skuGroup6Name;//附加供应商名称

	private String skuGroup7;

	private String skuGroup8;

	private String skuGroup9;


	private String firstop;

	private BigDecimal skuhigh;

	private BigDecimal skulength;

	private BigDecimal skuwidth;

	private String softallocationrule;

	private BigDecimal tare;

	private BigDecimal qty;

	private BigDecimal qtyallocated;

	private BigDecimal qtyonhold;

	public BasSku() {
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getSkuGroup7() {
		return skuGroup7;
	}

	public void setSkuGroup7(String skuGroup7) {
		this.skuGroup7 = skuGroup7;
	}

	public String getSkuGroup8() {
		return skuGroup8;
	}

	public void setSkuGroup8(String skuGroup8) {
		this.skuGroup8 = skuGroup8;
	}

	public String getSkuGroup9() {
		return skuGroup9;
	}

	public void setSkuGroup9(String skuGroup9) {
		this.skuGroup9 = skuGroup9;
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

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getAddwho() {
		return addwho;
	}

	public void setAddwho(String addwho) {
		this.addwho = addwho;
	}

	public String getAllocationrule() {
		return allocationrule;
	}

	public void setAllocationrule(String allocationrule) {
		this.allocationrule = allocationrule;
	}

	public String getAlternateSku1() {
		return alternateSku1;
	}

	public void setAlternateSku1(String alternateSku1) {
		this.alternateSku1 = alternateSku1;
	}

	public String getAlternateSku2() {
		return alternateSku2;
	}

	public void setAlternateSku2(String alternateSku2) {
		this.alternateSku2 = alternateSku2;
	}

	public String getAlternateSku3() {
		return alternateSku3;
	}

	public void setAlternateSku3(String alternateSku3) {
		this.alternateSku3 = alternateSku3;
	}

	public String getAlternateSku4() {
		return alternateSku4;
	}

	public void setAlternateSku4(String alternateSku4) {
		this.alternateSku4 = alternateSku4;
	}

	public String getAlternateSku5() {
		return alternateSku5;
	}

	public void setAlternateSku5(String alternateSku5) {
		this.alternateSku5 = alternateSku5;
	}

	public BigDecimal getCube() {
		return cube;
	}

	public void setCube(BigDecimal cube) {
		this.cube = cube;
	}

	public String getDefaulthold() {
		return defaulthold;
	}

	public void setDefaulthold(String defaulthold) {
		this.defaulthold = defaulthold;
	}

	public String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public String getDefaultshipmentuom() {
		return defaultshipmentuom;
	}

	public void setDefaultshipmentuom(String defaultshipmentuom) {
		this.defaultshipmentuom = defaultshipmentuom;
	}

	public String getDescrC() {
		return descrC;
	}

	public void setDescrC(String descrC) {
		this.descrC = descrC;
	}

	public String getDescrE() {
		return descrE;
	}

	public void setDescrE(String descrE) {
		this.descrE = descrE;
	}

	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;
	}

	public String getEditwho() {
		return editwho;
	}

	public void setEditwho(String editwho) {
		this.editwho = editwho;
	}

	public Date getFirstinbounddate() {
		return firstinbounddate;
	}

	public void setFirstinbounddate(Date firstinbounddate) {
		this.firstinbounddate = firstinbounddate;
	}

	public BigDecimal getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public BigDecimal getInboundlifedays() {
		return inboundlifedays;
	}

	public void setInboundlifedays(BigDecimal inboundlifedays) {
		this.inboundlifedays = inboundlifedays;
	}

	public String getLotid() {
		return lotid;
	}

	public void setLotid(String lotid) {
		this.lotid = lotid;
	}

	public BigDecimal getNetweight() {
		return netweight;
	}

	public void setNetweight(BigDecimal netweight) {
		this.netweight = netweight;
	}

	public String getOnestepallocation() {
		return onestepallocation;
	}

	public void setOnestepallocation(String onestepallocation) {
		this.onestepallocation = onestepallocation;
	}

	public BigDecimal getOutboundlifedays() {
		return outboundlifedays;
	}

	public void setOutboundlifedays(BigDecimal outboundlifedays) {
		this.outboundlifedays = outboundlifedays;
	}

	public BigDecimal getOverrcvpercentage() {
		return overrcvpercentage;
	}

	public void setOverrcvpercentage(BigDecimal overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public String getOverreceiving() {
		return overreceiving;
	}

	public void setOverreceiving(String overreceiving) {
		this.overreceiving = overreceiving;
	}

	public String getPackid() {
		return packid;
	}

	public void setPackid(String packid) {
		this.packid = packid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPutawayrule() {
		return putawayrule;
	}

	public void setPutawayrule(String putawayrule) {
		this.putawayrule = putawayrule;
	}

	public BigDecimal getQtymax() {
		return qtymax;
	}

	public void setQtymax(BigDecimal qtymax) {
		this.qtymax = qtymax;
	}

	public BigDecimal getQtymin() {
		return qtymin;
	}

	public void setQtymin(BigDecimal qtymin) {
		this.qtymin = qtymin;
	}

	public String getReplenishrule() {
		return replenishrule;
	}

	public void setReplenishrule(String replenishrule) {
		this.replenishrule = replenishrule;
	}

	public String getReservecode() {
		return reservecode;
	}

	public void setReservecode(String reservecode) {
		this.reservecode = reservecode;
	}

	public String getReservedfield01() {
		return reservedfield01;
	}

	public void setReservedfield01(String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public String getReservedfield02() {
		return reservedfield02;
	}

	public void setReservedfield02(String reservedfield02) {
		this.reservedfield02 = reservedfield02;
	}

	public String getReservedfield03() {
		return reservedfield03;
	}

	public void setReservedfield03(String reservedfield03) {
		this.reservedfield03 = reservedfield03;
	}

	public String getReservedfield04() {
		return reservedfield04;
	}

	public void setReservedfield04(String reservedfield04) {
		this.reservedfield04 = reservedfield04;
	}

	public String getReservedfield05() {
		return reservedfield05;
	}

	public void setReservedfield05(String reservedfield05) {
		this.reservedfield05 = reservedfield05;
	}

	public String getRotationid() {
		return rotationid;
	}

	public void setRotationid(String rotationid) {
		this.rotationid = rotationid;
	}

	public String getSkuGroup1() {
		return skuGroup1;
	}

	public void setSkuGroup1(String skuGroup1) {
		this.skuGroup1 = skuGroup1;
	}

	public String getSkuGroup2() {
		return skuGroup2;
	}

	public void setSkuGroup2(String skuGroup2) {
		this.skuGroup2 = skuGroup2;
	}

	public String getSkuGroup3() {
		return skuGroup3;
	}

	public void setSkuGroup3(String skuGroup3) {
		this.skuGroup3 = skuGroup3;
	}

	public String getSkuGroup4() {
		return skuGroup4;
	}

	public void setSkuGroup4(String skuGroup4) {
		this.skuGroup4 = skuGroup4;
	}

	public String getSkuGroup5() {
		return skuGroup5;
	}

	public void setSkuGroup5(String skuGroup5) {
		this.skuGroup5 = skuGroup5;
	}

	public BigDecimal getSkuhigh() {
		return skuhigh;
	}

	public void setSkuhigh(BigDecimal skuhigh) {
		this.skuhigh = skuhigh;
	}

	public BigDecimal getSkulength() {
		return skulength;
	}

	public void setSkulength(BigDecimal skulength) {
		this.skulength = skulength;
	}

	public BigDecimal getSkuwidth() {
		return skuwidth;
	}

	public void setSkuwidth(BigDecimal skuwidth) {
		this.skuwidth = skuwidth;
	}

	public String getSoftallocationrule() {
		return softallocationrule;
	}

	public void setSoftallocationrule(String softallocationrule) {
		this.softallocationrule = softallocationrule;
	}

	public BigDecimal getTare() {
		return tare;
	}

	public void setTare(BigDecimal tare) {
		this.tare = tare;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getQtyallocated() {
		return qtyallocated;
	}

	public void setQtyallocated(BigDecimal qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public BigDecimal getQtyonhold() {
		return qtyonhold;
	}

	public void setQtyonhold(BigDecimal qtyonhold) {
		this.qtyonhold = qtyonhold;
	}

	public String getSkuGroup6Name() {
		return skuGroup6Name;
	}

	public void setSkuGroup6Name(String skuGroup6Name) {
		this.skuGroup6Name = skuGroup6Name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customerid == null) ? 0 : customerid.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
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
		BasSku other = (BasSku) obj;
		if (customerid == null) {
			if (other.customerid != null)
				return false;
		} else if (!customerid.equals(other.customerid))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BasSku [hashCode=" + hashCode + ", customerid=" + customerid
				+ ", sku=" + sku + ", activeFlag=" + activeFlag + ", addtime="
				+ addtime + ", addwho=" + addwho + ", allocationrule="
				+ allocationrule + ", alternateSku1=" + alternateSku1
				+ ", alternateSku2=" + alternateSku2 + ", alternateSku3="
				+ alternateSku3 + ", alternateSku4=" + alternateSku4
				+ ", alternateSku5=" + alternateSku5 + ", cube=" + cube
				+ ", defaulthold=" + defaulthold + ", defaultreceivinguom="
				+ defaultreceivinguom + ", defaultshipmentuom="
				+ defaultshipmentuom + ", descrC=" + descrC + ", descrE="
				+ descrE + ", edittime=" + edittime + ", editwho=" + editwho
				+ ", firstinbounddate=" + firstinbounddate + ", grossweight="
				+ grossweight + ", inboundlifedays=" + inboundlifedays
				+ ", lotid=" + lotid + ", netweight=" + netweight
				+ ", onestepallocation=" + onestepallocation
				+ ", outboundlifedays=" + outboundlifedays
				+ ", overrcvpercentage=" + overrcvpercentage
				+ ", overreceiving=" + overreceiving + ", packid=" + packid
				+ ", price=" + price + ", putawayrule=" + putawayrule
				+ ", qtymax=" + qtymax + ", qtymin=" + qtymin
				+ ", replenishrule=" + replenishrule + ", reservecode="
				+ reservecode + ", reservedfield01=" + reservedfield01
				+ ", reservedfield02=" + reservedfield02 + ", reservedfield03="
				+ reservedfield03 + ", reservedfield04=" + reservedfield04
				+ ", reservedfield05=" + reservedfield05 + ", rotationid="
				+ rotationid + ", skuGroup1=" + skuGroup1 + ", skuGroup2="
				+ skuGroup2 + ", skuGroup3=" + skuGroup3 + ", skuGroup4="
				+ skuGroup4 + ", skuGroup5=" + skuGroup5 + ", skuhigh="
				+ skuhigh + ", skulength=" + skulength + ", skuwidth="
				+ skuwidth + ", softallocationrule=" + softallocationrule
				+ ", tare=" + tare + ", qty=" + qty + ", qtyallocated="
				+ qtyallocated + ", qtyonhold=" + qtyonhold + "]";
	}

	public String getSkuGroup6() {
		return skuGroup6;
	}

	public void setSkuGroup6(String skuGroup6) {
		this.skuGroup6 = skuGroup6;
	}

	public String getFirstop() {
		return firstop;
	}

	public void setFirstop(String firstop) {
		this.firstop = firstop;
	}

	public String getReservedfield06() {
		return reservedfield06;
	}

	public void setReservedfield06(String reservedfield06) {
		this.reservedfield06 = reservedfield06;
	}

	public String getReservedfield07() {
		return reservedfield07;
	}

	public void setReservedfield07(String reservedfield07) {
		this.reservedfield07 = reservedfield07;
	}

	public String getReservedfield08() {
		return reservedfield08;
	}

	public void setReservedfield08(String reservedfield08) {
		this.reservedfield08 = reservedfield08;
	}

	public String getReservedfield09() {
		return reservedfield09;
	}

	public void setReservedfield09(String reservedfield09) {
		this.reservedfield09 = reservedfield09;
	}

	public String getReservedfield10() {
		return reservedfield10;
	}

	public void setReservedfield10(String reservedfield10) {
		this.reservedfield10 = reservedfield10;
	}

	public String getReservedfield11() {
		return reservedfield11;
	}

	public void setReservedfield11(String reservedfield11) {
		this.reservedfield11 = reservedfield11;
	}

	public String getReservedfield12() {
		return reservedfield12;
	}

	public void setReservedfield12(String reservedfield12) {
		this.reservedfield12 = reservedfield12;
	}

	public String getReservedfield13() {
		return reservedfield13;
	}

	public void setReservedfield13(String reservedfield13) {
		this.reservedfield13 = reservedfield13;
	}

	public String getReservedfield14() {
		return reservedfield14;
	}

	public void setReservedfield14(String reservedfield14) {
		this.reservedfield14 = reservedfield14;
	}

	public String getReservedfield15() {
		return reservedfield15;
	}

	public void setReservedfield15(String reservedfield15) {
		this.reservedfield15 = reservedfield15;
	}

	public String getReservedfield16() {
		return reservedfield16;
	}

	public void setReservedfield16(String reservedfield16) {
		this.reservedfield16 = reservedfield16;
	}

	public String getReservedfield17() {
		return reservedfield17;
	}

	public void setReservedfield17(String reservedfield17) {
		this.reservedfield17 = reservedfield17;
	}

	public String getReservedfield18() {
		return reservedfield18;
	}

	public void setReservedfield18(String reservedfield18) {
		this.reservedfield18 = reservedfield18;
	}
}