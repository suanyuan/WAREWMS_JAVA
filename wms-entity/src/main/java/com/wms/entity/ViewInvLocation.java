package com.wms.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the VIEW_INV_LOCATION database table.
 * 
 */
@Entity
@Table(name="VIEW_INV_LOCATION")
@NamedQuery(name="ViewInvLocation.findAll", query="SELECT v FROM ViewInvLocation v")
public class ViewInvLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fmcustomerid;

	private String fmlocation;


	
	@Id
	private String fmsku;

	@Column(name="FMUOM_NAME")
	private String fmuomName;

	@Column(name="I_MV")
	private BigDecimal iMv;

	@Column(name="I_PA")
	private BigDecimal iPa;

	@Column(name="I_RP")
	private BigDecimal iRp;

	@Column(name="O_MV")
	private BigDecimal oMv;

	@Column(name="O_RP")
	private BigDecimal oRp;
	private BigDecimal fmqty;
	private BigDecimal fmqtyEach; //数量

	private BigDecimal qtyallocated;//分配件数
	private BigDecimal qtyallocatedEach ;//分配数量
	private BigDecimal qtyavailed; //可用件数
	private BigDecimal qtyavailedEach; //可用数量
	private BigDecimal qtyholdedEach;//冻结数量

	private BigDecimal qtyholded;//冻结件数

	private String skudescrc;

	private String skudescre;

	private BigDecimal totalcubic;

	private BigDecimal totalgrossweight;

	private String warehouseid;
	private String lotatt14;
	private String customerid;
	private String lotatt03;
	private String lotatt12;
	private String lotatt13;
	private String lotatt04;
	private String lotatt05;
	private String lotatt06;

	private String lotatt07;
	private String lotatt01;
	private String lotatt02;
	private String lotatt08;
	private String lotatt09;
	private String lotatt11;
	private String lotatt10;
	private String name;
	private String productRegisterNo;
	private String enterpriseName;
	private String defaultreceivinguom;
	private String reservedfield02;//产品描述

//	bas_package

	private BigDecimal qty1;//转换率
	private String unit; //单位  中文
	//总计
	private BigDecimal fmqtySum;
	private BigDecimal fmqtyEachSum;
	private BigDecimal qtyallocatedSum;
	private BigDecimal qtyallocatedEachSum;
	private BigDecimal qtyavailedSum;
	private BigDecimal qtyavailedEachSum;


	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getReservedfield02() {
		return reservedfield02;
	}

	public void setReservedfield02(String reservedfield02) {
		this.reservedfield02 = reservedfield02;
	}

	public BigDecimal getFmqtySum() {
		return fmqtySum;
	}

	public void setFmqtySum(BigDecimal fmqtySum) {
		this.fmqtySum = fmqtySum;
	}

	public BigDecimal getFmqtyEachSum() {
		return fmqtyEachSum;
	}

	public void setFmqtyEachSum(BigDecimal fmqtyEachSum) {
		this.fmqtyEachSum = fmqtyEachSum;
	}

	public BigDecimal getQtyallocatedSum() {
		return qtyallocatedSum;
	}

	public void setQtyallocatedSum(BigDecimal qtyallocatedSum) {
		this.qtyallocatedSum = qtyallocatedSum;
	}

	public BigDecimal getQtyallocatedEachSum() {
		return qtyallocatedEachSum;
	}

	public void setQtyallocatedEachSum(BigDecimal qtyallocatedEachSum) {
		this.qtyallocatedEachSum = qtyallocatedEachSum;
	}

	public BigDecimal getQtyavailedSum() {
		return qtyavailedSum;
	}

	public void setQtyavailedSum(BigDecimal qtyavailedSum) {
		this.qtyavailedSum = qtyavailedSum;
	}

	public BigDecimal getQtyavailedEachSum() {
		return qtyavailedEachSum;
	}

	public void setQtyavailedEachSum(BigDecimal qtyavailedEachSum) {
		this.qtyavailedEachSum = qtyavailedEachSum;
	}

	public String getLotatt13() {
		return lotatt13;
	}

	public void setLotatt13(String lotatt13) {
		this.lotatt13 = lotatt13;
	}

	public BigDecimal getQtyavailedEach() {
		return qtyavailedEach;
	}

	public void setQtyavailedEach(BigDecimal qtyavailedEach) {
		this.qtyavailedEach = qtyavailedEach;
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

	public BigDecimal getiRp() {
		return iRp;
	}

	public void setiRp(BigDecimal iRp) {
		this.iRp = iRp;
	}

	public BigDecimal getoMv() {
		return oMv;
	}

	public void setoMv(BigDecimal oMv) {
		this.oMv = oMv;
	}

	public BigDecimal getoRp() {
		return oRp;
	}

	public void setoRp(BigDecimal oRp) {
		this.oRp = oRp;
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

	public BigDecimal getQtyholdedEach() {
		return qtyholdedEach;
	}

	public void setQtyholdedEach(BigDecimal qtyholdedEach) {
		this.qtyholdedEach = qtyholdedEach;
	}

	public String getLotatt09() {
		return lotatt09;
	}

	public void setLotatt09(String lotatt09) {
		this.lotatt09 = lotatt09;
	}

	public String getLotatt06() {
		return lotatt06;
	}

	public void setLotatt06(String lotatt06) {
		this.lotatt06 = lotatt06;
	}

	public String getDefaultreceivinguom() {
		return defaultreceivinguom;
	}

	public void setDefaultreceivinguom(String defaultreceivinguom) {
		this.defaultreceivinguom = defaultreceivinguom;
	}

	public String getLotatt14() {
		return lotatt14;
	}

	public void setLotatt14(String lotatt14) {
		this.lotatt14 = lotatt14;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getLotatt03() {
		return lotatt03;
	}

	public void setLotatt03(String lotatt03) {
		this.lotatt03 = lotatt03;
	}

	public String getLotatt12() {
		return lotatt12;
	}

	public void setLotatt12(String lotatt12) {
		this.lotatt12 = lotatt12;
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

	public String getLotatt07() {
		return lotatt07;
	}

	public void setLotatt07(String lotatt07) {
		this.lotatt07 = lotatt07;
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

	public String getLotatt08() {
		return lotatt08;
	}

	public void setLotatt08(String lotatt08) {
		this.lotatt08 = lotatt08;
	}

	public String getLotatt11() {
		return lotatt11;
	}

	public void setLotatt11(String lotatt11) {
		this.lotatt11 = lotatt11;
	}

	public String getLotatt10() {
		return lotatt10;
	}

	public void setLotatt10(String lotatt10) {
		this.lotatt10 = lotatt10;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductRegisterNo() {
		return productRegisterNo;
	}

	public void setProductRegisterNo(String productRegisterNo) {
		this.productRegisterNo = productRegisterNo;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public ViewInvLocation() {
	}

	public String getFmcustomerid() {
		return this.fmcustomerid;
	}

	public void setFmcustomerid(String fmcustomerid) {
		this.fmcustomerid = fmcustomerid;
	}

	public String getFmlocation() {
		return this.fmlocation;
	}

	public void setFmlocation(String fmlocation) {
		this.fmlocation = fmlocation;
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

	public BigDecimal getIRp() {
		return this.iRp;
	}

	public void setIRp(BigDecimal iRp) {
		this.iRp = iRp;
	}

	public BigDecimal getOMv() {
		return this.oMv;
	}

	public void setOMv(BigDecimal oMv) {
		this.oMv = oMv;
	}

	public BigDecimal getORp() {
		return this.oRp;
	}

	public void setORp(BigDecimal oRp) {
		this.oRp = oRp;
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

	public String getWarehouseid() {
		return this.warehouseid;
	}

	public void setWarehouseid(String warehouseid) {
		this.warehouseid = warehouseid;
	}

	public BigDecimal getQty1() {
		return qty1;
	}

	public void setQty1(BigDecimal qty1) {
		this.qty1 = qty1;
	}
}