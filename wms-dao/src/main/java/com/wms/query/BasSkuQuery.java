package com.wms.query;

import java.util.Date;
import java.util.Set;

import com.wms.mybatis.entity.SfcCustomer;

public class BasSkuQuery implements IQuery {

	private java.lang.String customerid;
	private java.lang.String sku;
	private java.lang.String activeFlag;
	private java.util.Date addtime;

	private String addTimeStart;
	private String addTimeEnd;
	private String edittimeStart;
	private String edittimeEnd;

	private java.lang.String addwho;
	private java.lang.String allocationrule;
	private java.lang.String alternateSku1;
	private java.lang.String alternateSku2;
	private java.lang.String alternateSku3;
	private java.lang.String alternateSku4;
	private java.lang.String alternateSku5;
	private java.math.BigDecimal cube;
	private java.lang.String defaulthold;
	private java.lang.String defaultreceivinguom;
	private java.lang.String defaultshipmentuom;
	private java.lang.String descrC;
	private java.lang.String descrE;
	private java.util.Date edittime;
	private java.lang.String editwho;
	private java.util.Date firstinbounddate;
	private java.math.BigDecimal grossweight;
	private java.math.BigDecimal inboundlifedays;
	private java.lang.String lotid;
	private java.math.BigDecimal netweight;
	private java.lang.String onestepallocation;
	private java.math.BigDecimal outboundlifedays;
	private java.math.BigDecimal overrcvpercentage;
	private java.lang.String overreceiving;
	private java.lang.String packid;
	private java.math.BigDecimal price;
	private java.lang.String putawayrule;
	private java.math.BigDecimal qtymax;
	private java.math.BigDecimal qtymin;
	private java.lang.String replenishrule;
	private java.lang.String reservecode;
	private java.lang.String reservedfield01;
	private java.lang.String reservedfield02;
	private java.lang.String reservedfield03;
	private java.lang.String reservedfield04;
	private java.lang.String reservedfield05;
	private java.lang.String rotationid;
	private java.lang.String skuGroup1;
	private java.lang.String skuGroup2;
	private java.lang.String skuGroup3;
	private java.lang.String skuGroup4;
	private java.lang.String skuGroup5;
	private java.math.BigDecimal skuhigh;
	private java.math.BigDecimal skulength;
	private java.math.BigDecimal skuwidth;
	private java.lang.String softallocationrule;
	private java.math.BigDecimal tare;
	private java.math.BigDecimal qty;
	private java.math.BigDecimal qtyallocated;
	private java.math.BigDecimal qtyonhold;
	private java.lang.String warehouseid;
	private Set<SfcCustomer> customerSet;


	private String firstop;

	private String skuGroup6;

	private String skuGroup7;

	private String skuGroup8;

	private String skuGroup9;






	public String getFirstop() {
		return firstop;
	}

	public void setFirstop(String firstop) {
		this.firstop = firstop;
	}

	public String getSkuGroup6() {
		return skuGroup6;
	}

	public void setSkuGroup6(String skuGroup6) {
		this.skuGroup6 = skuGroup6;
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





	public BasSkuQuery() {
	}

	public java.lang.String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(java.lang.String customerid) {
		this.customerid = customerid;
	}

	public java.lang.String getSku() {
		return sku;
	}

	public void setSku(java.lang.String sku) {
		this.sku = sku;
	}

	public java.lang.String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(java.lang.String activeFlag) {
		this.activeFlag = activeFlag;
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

	public java.lang.String getAllocationrule() {
		return allocationrule;
	}

	public void setAllocationrule(java.lang.String allocationrule) {
		this.allocationrule = allocationrule;
	}

	public java.lang.String getAlternateSku1() {
		return alternateSku1;
	}

	public void setAlternateSku1(java.lang.String alternateSku1) {
		this.alternateSku1 = alternateSku1;
	}

	public java.lang.String getAlternateSku2() {
		return alternateSku2;
	}

	public void setAlternateSku2(java.lang.String alternateSku2) {
		this.alternateSku2 = alternateSku2;
	}

	public java.lang.String getAlternateSku3() {
		return alternateSku3;
	}

	public void setAlternateSku3(java.lang.String alternateSku3) {
		this.alternateSku3 = alternateSku3;
	}

	public java.lang.String getAlternateSku4() {
		return alternateSku4;
	}

	public void setAlternateSku4(java.lang.String alternateSku4) {
		this.alternateSku4 = alternateSku4;
	}

	public java.lang.String getAlternateSku5() {
		return alternateSku5;
	}

	public void setAlternateSku5(java.lang.String alternateSku5) {
		this.alternateSku5 = alternateSku5;
	}

	public java.math.BigDecimal getCube() {
		return cube;
	}

	public void setCube(java.math.BigDecimal cube) {
		this.cube = cube;
	}

	public java.lang.String getDefaulthold() {
		return defaulthold;
	}

	public void setDefaulthold(java.lang.String defaulthold) {
		this.defaulthold = defaulthold;
	}

	public java.lang.String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(java.lang.String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public java.lang.String getDefaultshipmentuom() {
		return defaultshipmentuom;
	}

	public void setDefaultshipmentuom(java.lang.String defaultshipmentuom) {
		this.defaultshipmentuom = defaultshipmentuom;
	}

	public java.lang.String getDescrC() {
		return descrC;
	}

	public void setDescrC(java.lang.String descrC) {
		this.descrC = descrC;
	}

	public java.lang.String getDescrE() {
		return descrE;
	}

	public void setDescrE(java.lang.String descrE) {
		this.descrE = descrE;
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

	public java.util.Date getFirstinbounddate() {
		return firstinbounddate;
	}

	public void setFirstinbounddate(java.util.Date firstinbounddate) {
		this.firstinbounddate = firstinbounddate;
	}

	public java.math.BigDecimal getGrossweight() {
		return grossweight;
	}

	public void setGrossweight(java.math.BigDecimal grossweight) {
		this.grossweight = grossweight;
	}

	public java.math.BigDecimal getInboundlifedays() {
		return inboundlifedays;
	}

	public void setInboundlifedays(java.math.BigDecimal inboundlifedays) {
		this.inboundlifedays = inboundlifedays;
	}

	public java.lang.String getLotid() {
		return lotid;
	}

	public void setLotid(java.lang.String lotid) {
		this.lotid = lotid;
	}

	public java.math.BigDecimal getNetweight() {
		return netweight;
	}

	public void setNetweight(java.math.BigDecimal netweight) {
		this.netweight = netweight;
	}

	public java.lang.String getOnestepallocation() {
		return onestepallocation;
	}

	public void setOnestepallocation(java.lang.String onestepallocation) {
		this.onestepallocation = onestepallocation;
	}

	public java.math.BigDecimal getOutboundlifedays() {
		return outboundlifedays;
	}

	public void setOutboundlifedays(java.math.BigDecimal outboundlifedays) {
		this.outboundlifedays = outboundlifedays;
	}

	public java.math.BigDecimal getOverrcvpercentage() {
		return overrcvpercentage;
	}

	public void setOverrcvpercentage(java.math.BigDecimal overrcvpercentage) {
		this.overrcvpercentage = overrcvpercentage;
	}

	public java.lang.String getOverreceiving() {
		return overreceiving;
	}

	public void setOverreceiving(java.lang.String overreceiving) {
		this.overreceiving = overreceiving;
	}

	public java.lang.String getPackid() {
		return packid;
	}

	public void setPackid(java.lang.String packid) {
		this.packid = packid;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.lang.String getPutawayrule() {
		return putawayrule;
	}

	public void setPutawayrule(java.lang.String putawayrule) {
		this.putawayrule = putawayrule;
	}

	public java.math.BigDecimal getQtymax() {
		return qtymax;
	}

	public void setQtymax(java.math.BigDecimal qtymax) {
		this.qtymax = qtymax;
	}

	public java.math.BigDecimal getQtymin() {
		return qtymin;
	}

	public void setQtymin(java.math.BigDecimal qtymin) {
		this.qtymin = qtymin;
	}

	public java.lang.String getReplenishrule() {
		return replenishrule;
	}

	public void setReplenishrule(java.lang.String replenishrule) {
		this.replenishrule = replenishrule;
	}

	public java.lang.String getReservecode() {
		return reservecode;
	}

	public void setReservecode(java.lang.String reservecode) {
		this.reservecode = reservecode;
	}

	public java.lang.String getReservedfield01() {
		return reservedfield01;
	}

	public void setReservedfield01(java.lang.String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public java.lang.String getReservedfield02() {
		return reservedfield02;
	}

	public void setReservedfield02(java.lang.String reservedfield02) {
		this.reservedfield02 = reservedfield02;
	}

	public java.lang.String getReservedfield03() {
		return reservedfield03;
	}

	public void setReservedfield03(java.lang.String reservedfield03) {
		this.reservedfield03 = reservedfield03;
	}

	public java.lang.String getReservedfield04() {
		return reservedfield04;
	}

	public void setReservedfield04(java.lang.String reservedfield04) {
		this.reservedfield04 = reservedfield04;
	}

	public java.lang.String getReservedfield05() {
		return reservedfield05;
	}

	public void setReservedfield05(java.lang.String reservedfield05) {
		this.reservedfield05 = reservedfield05;
	}

	public java.lang.String getRotationid() {
		return rotationid;
	}

	public void setRotationid(java.lang.String rotationid) {
		this.rotationid = rotationid;
	}

	public java.lang.String getSkuGroup1() {
		return skuGroup1;
	}

	public void setSkuGroup1(java.lang.String skuGroup1) {
		this.skuGroup1 = skuGroup1;
	}

	public java.lang.String getSkuGroup2() {
		return skuGroup2;
	}

	public void setSkuGroup2(java.lang.String skuGroup2) {
		this.skuGroup2 = skuGroup2;
	}

	public java.lang.String getSkuGroup3() {
		return skuGroup3;
	}

	public void setSkuGroup3(java.lang.String skuGroup3) {
		this.skuGroup3 = skuGroup3;
	}

	public java.lang.String getSkuGroup4() {
		return skuGroup4;
	}

	public void setSkuGroup4(java.lang.String skuGroup4) {
		this.skuGroup4 = skuGroup4;
	}

	public java.lang.String getSkuGroup5() {
		return skuGroup5;
	}

	public void setSkuGroup5(java.lang.String skuGroup5) {
		this.skuGroup5 = skuGroup5;
	}

	public java.math.BigDecimal getSkuhigh() {
		return skuhigh;
	}

	public void setSkuhigh(java.math.BigDecimal skuhigh) {
		this.skuhigh = skuhigh;
	}

	public java.math.BigDecimal getSkulength() {
		return skulength;
	}

	public void setSkulength(java.math.BigDecimal skulength) {
		this.skulength = skulength;
	}

	public java.math.BigDecimal getSkuwidth() {
		return skuwidth;
	}

	public void setSkuwidth(java.math.BigDecimal skuwidth) {
		this.skuwidth = skuwidth;
	}

	public java.lang.String getSoftallocationrule() {
		return softallocationrule;
	}

	public void setSoftallocationrule(java.lang.String softallocationrule) {
		this.softallocationrule = softallocationrule;
	}

	public java.math.BigDecimal getTare() {
		return tare;
	}

	public void setTare(java.math.BigDecimal tare) {
		this.tare = tare;
	}

	public java.math.BigDecimal getQty() {
		return qty;
	}

	public void setQty(java.math.BigDecimal qty) {
		this.qty = qty;
	}

	public java.math.BigDecimal getQtyallocated() {
		return qtyallocated;
	}

	public void setQtyallocated(java.math.BigDecimal qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public java.math.BigDecimal getQtyonhold() {
		return qtyonhold;
	}

	public void setQtyonhold(java.math.BigDecimal qtyonhold) {
		this.qtyonhold = qtyonhold;
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


	public String getAddTimeStart() {
		return addTimeStart;
	}

	public void setAddTimeStart(String addTimeStart) {
		this.addTimeStart = addTimeStart;
	}

	public String getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}

	public String getEdittimeStart() {
		return edittimeStart;
	}

	public void setEdittimeStart(String edittimeStart) {
		this.edittimeStart = edittimeStart;
	}

	public String getEdittimeEnd() {
		return edittimeEnd;
	}

	public void setEdittimeEnd(String edittimeEnd) {
		this.edittimeEnd = edittimeEnd;
	}
}