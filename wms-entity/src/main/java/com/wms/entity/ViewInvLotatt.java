package com.wms.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the VIEW_INV_LOTATT database table.
 * 
 */
@Entity
@Table(name="VIEW_INV_LOTATT")
@NamedQuery(name="ViewInvLotatt.findAll", query="SELECT v FROM ViewInvLotatt v")
public class ViewInvLotatt implements Serializable {
	private static final long serialVersionUID = 1L;

	private String allocationrule;

	private String alternatesku1;

	private String alternatesku2;

	private String alternatesku3;

	private String alternatesku4;

	private String alternatesku5;

	private String configlist01;

	private String configlist02;

	private String fmcustomerid;

	private String fmid;

	private String fmlocation;

	private String fmlotnum;

	private BigDecimal fmqty;
	private BigDecimal fmqtyEach;

	@Id
	private String fmsku;

	@Column(name="FMUOM_NAME")
	private String fmuomName;

	@Column(name="I_MV")
	private BigDecimal iMv;

	@Column(name="I_PA")
	private BigDecimal iPa;

	private String imageaddress;

	private String lotatt01;

	private String lotatt01text;

	private String lotatt02;

	private String lotatt02text;

	private String lotatt03;

	private String lotatt03text;

	private String lotatt04;

	private String lotatt04text;

	private String lotatt05;

	private String lotatt05text;

	private String lotatt06;

	private String lotatt06text;

	private String lotatt07;

	private String lotatt07text;

	private String lotatt08;

	private String lotatt08text;

	private String lotatt09;

	private String lotatt09text;

	private String lotatt10;

	private String lotatt10text;

	private String lotatt11;

	private String lotatt11text;

	private String lotatt12;

	private String lotatt12text;

	private String lpn;

	private BigDecimal netweight;

	@Column(name="O_MV")
	private BigDecimal oMv;

	private BigDecimal pkey;

	private BigDecimal price;

	private BigDecimal qtyallocated;
	private BigDecimal qtyallocatedEach ;//分配数量
	private BigDecimal qtyavailed;
	private BigDecimal qtyavailedEach; //可用数量
	private BigDecimal qtyholded;
	private BigDecimal qtyholdedEach;//冻结数量
	private BigDecimal qtyrpin;

	private BigDecimal qtyrpout;

	private String reservedfield01;

	private String reservedfield02;

	private String reservedfield03;

	private String reservedfield04;

	private String reservedfield05;

	private String rotationid;

	private String skudescrc;

	private String skudescre;

	private String skugroup1;

	private String skugroup2;

	private String skugroup3;

	private String skugroup4;

	private String skugroup5;

	private String softallocationrule;

	private BigDecimal toadjqty;

	private BigDecimal totalcubic;

	private BigDecimal totalgrossweight;

	private String uom;

	private String warehouseid;

	private int onholdlocker; //冻结
	private String onholdlockerEx; //导出冻结

	public ViewInvLotatt() {
	}

	public BigDecimal getFmqtyEach() {
		return fmqtyEach;
	}

	public void setFmqtyEach(BigDecimal fmqtyEach) {
		this.fmqtyEach = fmqtyEach;
	}

	public BigDecimal getQtyallocatedEach() {
		return qtyallocatedEach;
	}

	public void setQtyallocatedEach(BigDecimal qtyallocatedEach) {
		this.qtyallocatedEach = qtyallocatedEach;
	}

	public BigDecimal getQtyavailedEach() {
		return qtyavailedEach;
	}

	public void setQtyavailedEach(BigDecimal qtyavailedEach) {
		this.qtyavailedEach = qtyavailedEach;
	}

	public BigDecimal getQtyholdedEach() {
		return qtyholdedEach;
	}

	public void setQtyholdedEach(BigDecimal qtyholdedEach) {
		this.qtyholdedEach = qtyholdedEach;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public BigDecimal getiMv() {
		return iMv;
	}

	public void setiMv(BigDecimal iMv) {
		this.iMv = iMv;
	}

	public BigDecimal getiPa() {
		return iPa;
	}

	public void setiPa(BigDecimal iPa) {
		this.iPa = iPa;
	}

	public BigDecimal getoMv() {
		return oMv;
	}

	public void setoMv(BigDecimal oMv) {
		this.oMv = oMv;
	}

	public String getAllocationrule() {
		return this.allocationrule;
	}

	public void setAllocationrule(String allocationrule) {
		this.allocationrule = allocationrule;
	}

	public String getAlternatesku1() {
		return this.alternatesku1;
	}

	public void setAlternatesku1(String alternatesku1) {
		this.alternatesku1 = alternatesku1;
	}

	public String getAlternatesku2() {
		return this.alternatesku2;
	}

	public void setAlternatesku2(String alternatesku2) {
		this.alternatesku2 = alternatesku2;
	}

	public String getAlternatesku3() {
		return this.alternatesku3;
	}

	public void setAlternatesku3(String alternatesku3) {
		this.alternatesku3 = alternatesku3;
	}

	public String getAlternatesku4() {
		return this.alternatesku4;
	}

	public void setAlternatesku4(String alternatesku4) {
		this.alternatesku4 = alternatesku4;
	}

	public String getAlternatesku5() {
		return this.alternatesku5;
	}

	public void setAlternatesku5(String alternatesku5) {
		this.alternatesku5 = alternatesku5;
	}

	public String getConfiglist01() {
		return this.configlist01;
	}

	public void setConfiglist01(String configlist01) {
		this.configlist01 = configlist01;
	}

	public String getConfiglist02() {
		return this.configlist02;
	}

	public void setConfiglist02(String configlist02) {
		this.configlist02 = configlist02;
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

	public String getFmlotnum() {
		return this.fmlotnum;
	}

	public void setFmlotnum(String fmlotnum) {
		this.fmlotnum = fmlotnum;
	}

	public BigDecimal getFmqty() {
		return this.fmqty;
	}

	public void setFmqty(BigDecimal fmqty) {
		this.fmqty = fmqty;
	}

	public String getFmsku() {
		return this.fmsku;
	}

	public void setFmsku(String fmsku) {
		this.fmsku = fmsku;
	}

	public String getFmuomName() {
		return this.fmuomName;
	}

	public void setFmuomName(String fmuomName) {
		this.fmuomName = fmuomName;
	}

	public BigDecimal getIMv() {
		return this.iMv;
	}

	public void setIMv(BigDecimal iMv) {
		this.iMv = iMv;
	}

	public BigDecimal getIPa() {
		return this.iPa;
	}

	public void setIPa(BigDecimal iPa) {
		this.iPa = iPa;
	}

	public String getImageaddress() {
		return this.imageaddress;
	}

	public void setImageaddress(String imageaddress) {
		this.imageaddress = imageaddress;
	}

	public String getLotatt01() {
		return this.lotatt01;
	}

	public void setLotatt01(String lotatt01) {
		this.lotatt01 = lotatt01;
	}

	public String getLotatt01text() {
		return this.lotatt01text;
	}

	public void setLotatt01text(String lotatt01text) {
		this.lotatt01text = lotatt01text;
	}

	public String getLotatt02() {
		return this.lotatt02;
	}

	public void setLotatt02(String lotatt02) {
		this.lotatt02 = lotatt02;
	}

	public String getLotatt02text() {
		return this.lotatt02text;
	}

	public void setLotatt02text(String lotatt02text) {
		this.lotatt02text = lotatt02text;
	}

	public String getLotatt03() {
		return this.lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt03text() {
		return this.lotatt03text;
	}

	public void setLotatt03text(String lotatt03text) {
		this.lotatt03text = lotatt03text;
	}

	public String getLotatt04() {
		return this.lotatt04;
	}

	public void setLotatt04(String lotatt04) {
		this.lotatt04 = lotatt04;
	}

	public String getLotatt04text() {
		return this.lotatt04text;
	}

	public void setLotatt04text(String lotatt04text) {
		this.lotatt04text = lotatt04text;
	}

	public String getLotatt05() {
		return this.lotatt05;
	}

	public void setLotatt05(String lotatt05) {
		this.lotatt05 = lotatt05;
	}

	public String getLotatt05text() {
		return this.lotatt05text;
	}

	public void setLotatt05text(String lotatt05text) {
		this.lotatt05text = lotatt05text;
	}

	public String getLotatt06() {
		return this.lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getLotatt06text() {
		return this.lotatt06text;
	}

	public void setLotatt06text(String lotatt06text) {
		this.lotatt06text = lotatt06text;
	}

	public String getLotatt07() {
		return this.lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
	}

	public String getLotatt07text() {
		return this.lotatt07text;
	}

	public void setLotatt07text(String lotatt07text) {
		this.lotatt07text = lotatt07text;
	}

	public String getLotatt08() {
		return this.lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt08text() {
		return this.lotatt08text;
	}

	public void setLotatt08text(String lotatt08text) {
		this.lotatt08text = lotatt08text;
	}

	public String getLotatt09() {
		return this.lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt09text() {
		return this.lotatt09text;
	}

	public void setLotatt09text(String lotatt09text) {
		this.lotatt09text = lotatt09text;
	}

	public String getLotatt10() {
		return this.lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getLotatt10text() {
		return this.lotatt10text;
	}

	public void setLotatt10text(String lotatt10text) {
		this.lotatt10text = lotatt10text;
	}

	public String getLotatt11() {
		return this.lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt11text() {
		return this.lotatt11text;
	}

	public void setLotatt11text(String lotatt11text) {
		this.lotatt11text = lotatt11text;
	}

	public String getLotatt12() {
		return this.lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
	}

	public String getLotatt12text() {
		return this.lotatt12text;
	}

	public void setLotatt12text(String lotatt12text) {
		this.lotatt12text = lotatt12text;
	}

	public String getLpn() {
		return this.lpn;
	}

	public void setLpn(String lpn) {
		this.lpn = lpn;
	}

	public BigDecimal getNetweight() {
		return this.netweight;
	}

	public void setNetweight(BigDecimal netweight) {
		this.netweight = netweight;
	}

	public BigDecimal getOMv() {
		return this.oMv;
	}

	public void setOMv(BigDecimal oMv) {
		this.oMv = oMv;
	}

	public BigDecimal getPkey() {
		return this.pkey;
	}

	public void setPkey(BigDecimal pkey) {
		this.pkey = pkey;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQtyallocated() {
		return this.qtyallocated;
	}

	public void setQtyallocated(BigDecimal qtyallocated) {
		this.qtyallocated = qtyallocated;
	}

	public BigDecimal getQtyavailed() {
		return this.qtyavailed;
	}

	public void setQtyavailed(BigDecimal qtyavailed) {
		this.qtyavailed = qtyavailed;
	}

	public BigDecimal getQtyholded() {
		return this.qtyholded;
	}

	public void setQtyholded(BigDecimal qtyholded) {
		this.qtyholded = qtyholded;
	}

	public BigDecimal getQtyrpin() {
		return this.qtyrpin;
	}

	public void setQtyrpin(BigDecimal qtyrpin) {
		this.qtyrpin = qtyrpin;
	}

	public BigDecimal getQtyrpout() {
		return this.qtyrpout;
	}

	public void setQtyrpout(BigDecimal qtyrpout) {
		this.qtyrpout = qtyrpout;
	}

	public String getReservedfield01() {
		return this.reservedfield01;
	}

	public void setReservedfield01(String reservedfield01) {
		this.reservedfield01 = reservedfield01;
	}

	public String getReservedfield02() {
		return this.reservedfield02;
	}

	public void setReservedfield02(String reservedfield02) {
		this.reservedfield02 = reservedfield02;
	}

	public String getReservedfield03() {
		return this.reservedfield03;
	}

	public void setReservedfield03(String reservedfield03) {
		this.reservedfield03 = reservedfield03;
	}

	public String getReservedfield04() {
		return this.reservedfield04;
	}

	public void setReservedfield04(String reservedfield04) {
		this.reservedfield04 = reservedfield04;
	}

	public String getReservedfield05() {
		return this.reservedfield05;
	}

	public void setReservedfield05(String reservedfield05) {
		this.reservedfield05 = reservedfield05;
	}

	public String getRotationid() {
		return this.rotationid;
	}

	public void setRotationid(String rotationid) {
		this.rotationid = rotationid;
	}

	public String getSkudescrc() {
		return this.skudescrc;
	}

	public void setSkudescrc(String skudescrc) {
		this.skudescrc = skudescrc;
	}

	public String getSkudescre() {
		return this.skudescre;
	}

	public void setSkudescre(String skudescre) {
		this.skudescre = skudescre;
	}

	public String getSkugroup1() {
		return this.skugroup1;
	}

	public void setSkugroup1(String skugroup1) {
		this.skugroup1 = skugroup1;
	}

	public String getSkugroup2() {
		return this.skugroup2;
	}

	public void setSkugroup2(String skugroup2) {
		this.skugroup2 = skugroup2;
	}

	public String getSkugroup3() {
		return this.skugroup3;
	}

	public void setSkugroup3(String skugroup3) {
		this.skugroup3 = skugroup3;
	}

	public String getSkugroup4() {
		return this.skugroup4;
	}

	public void setSkugroup4(String skugroup4) {
		this.skugroup4 = skugroup4;
	}

	public String getSkugroup5() {
		return this.skugroup5;
	}

	public void setSkugroup5(String skugroup5) {
		this.skugroup5 = skugroup5;
	}

	public String getSoftallocationrule() {
		return this.softallocationrule;
	}

	public void setSoftallocationrule(String softallocationrule) {
		this.softallocationrule = softallocationrule;
	}

	public BigDecimal getToadjqty() {
		return this.toadjqty;
	}

	public void setToadjqty(BigDecimal toadjqty) {
		this.toadjqty = toadjqty;
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

	public String getUom() {
		return this.uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public int getOnholdlocker() {
		return onholdlocker;
	}

	public void setOnholdlocker(int onholdlocker) {
		this.onholdlocker = onholdlocker;
	}

	public String getOnholdlockerEx() {
		return onholdlockerEx;
	}

	public void setOnholdlockerEx(String onholdlockerEx) {
		this.onholdlockerEx = onholdlockerEx;
	}
}